package com.sparta.balloondelivery.auth.service;

import com.sparta.balloondelivery.auth.dto.SignupReqDto;
import com.sparta.balloondelivery.data.entity.User;
import com.sparta.balloondelivery.data.repository.AuthRepository;
import com.sparta.balloondelivery.exception.BaseException;
import com.sparta.balloondelivery.util.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    String secretKey;

    @Value("${spring.application.name}")
    String issuer;

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
}
