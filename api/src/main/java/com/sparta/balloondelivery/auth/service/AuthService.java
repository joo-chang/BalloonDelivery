package com.sparta.balloondelivery.auth.service;

import com.sparta.balloondelivery.auth.dto.SignInReqDto;
import com.sparta.balloondelivery.auth.dto.SignupReqDto;
import com.sparta.balloondelivery.data.entity.User;
import com.sparta.balloondelivery.data.entity.UserRole;
import com.sparta.balloondelivery.data.repository.AuthRepository;
import com.sparta.balloondelivery.exception.BaseException;
import com.sparta.balloondelivery.util.ErrorCode;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.secret}")
    String secretKey;

    @Value("${jwt.expiration}")
    Long accessExpiration;

    @Value("${ADMIN_TOKEN}")
    String ADMIN_TOKEN;

    public AuthService(AuthRepository authRepository, PasswordEncoder passwordEncoder, RedisTemplate<String, String> redisTemplate) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;
    }

    public User signUp(SignupReqDto request) {
        Optional<User> checkUser = authRepository.findByEmail(request.getEmail());
        if (checkUser.isPresent()) {
            throw new BaseException(ErrorCode.EXIST_USER);
        }
        if ((request.getRole()).equals(UserRole.MANAGER)) {
            if (!ADMIN_TOKEN.equals(request.getAdminToken())) {
                throw new BaseException(ErrorCode.INVALID_ADMIN_TOKEN);
            }
            request.setRole(UserRole.MANAGER);
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = new User(request.getEmail(), request.getUsername(), request.getPassword(), request.getRole());
        return authRepository.save(user);
    }


    public String signIn(SignInReqDto request) {
        User user = authRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BaseException(ErrorCode.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BaseException(ErrorCode.INVALID_CREDENTIALS);
        }

        saveUserRole(user.getId(), user.getRole());
        return createAccessToken(user.getId(), user.getRole());
    }

    public void saveUserRole(Long userId, UserRole role) {
        redisTemplate.opsForValue().set(userId.toString(), role.toString(), Duration.ofHours(1));
    }


    public String createAccessToken(Long userId, UserRole role) {

        return Jwts.builder()
                .claim("userId", userId)
                .claim("role", role)
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
