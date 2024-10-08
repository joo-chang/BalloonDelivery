package com.sparta.balloondelivery.auth.service;

import com.sparta.balloondelivery.auth.dto.SignInReqDto;
import com.sparta.balloondelivery.auth.dto.SignUpReqDto;
import com.sparta.balloondelivery.data.entity.User;
import com.sparta.balloondelivery.data.entity.UserRole;
import com.sparta.balloondelivery.data.repository.AuthRepository;
import com.sparta.balloondelivery.data.repository.UserRepository;
import com.sparta.balloondelivery.exception.BaseException;
import com.sparta.balloondelivery.util.ErrorCode;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class AuthService {
    private static final String USER_ROLE =  "userRole";
    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    String secretKey;

    @Value("${jwt.expiration}")
    Long accessExpiration;

    @Value("${ADMIN_TOKEN}")
    String ADMIN_TOKEN;

    public AuthService(AuthRepository authRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signUp(SignUpReqDto request) {
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
        return createAccessToken(user.getId().toString(), user.getUsername().toString());
    }



    public String createAccessToken(String userId, String username) {
        return Jwts.builder()
                .claim("user_id", userId)
                .claim("username", username)
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    @Cacheable(cacheNames = USER_ROLE, key = "#userId")
    public UserRole getPermission(Long userId) {
        return userRepository.findRoleById(userId);
    }

}
