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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    String secretKey;

    @Value("${jwt.expiration}")
    Long accessExpiration;

    public AuthService(AuthRepository authRepository, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signUp(SignupReqDto request) {
        User user = new User(request.getEmail(), request.getUsername(), request.getPassword(), request.getRole());
        Optional<User> checkUser = authRepository.findByEmail(user.getEmail());
        if (checkUser.isPresent()) {
            throw new BaseException(ErrorCode.EXIST_USER);
        }

        log.info("User: {}", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return authRepository.save(user);
    }


    public String signIn(SignInReqDto request) {
        User user = authRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BaseException(ErrorCode.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BaseException(ErrorCode.INVALID_CREDENTIALS);
        }

        return createAccessToken(user.getEmail(), user.getRole());
    }

    public String createAccessToken(String userId, UserRole role) {

        return Jwts.builder()
                .claim("user_id", userId)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
