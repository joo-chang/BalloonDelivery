package com.sparta.balloondelivery.auth.controller;

import com.sparta.balloondelivery.auth.dto.SignupReqDto;
import com.sparta.balloondelivery.auth.service.AuthService;
import com.sparta.balloondelivery.data.entity.User;
import com.sparta.balloondelivery.util.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signUp")
    public ApiResponse<?> signUp(@RequestBody SignupReqDto request) {
        User user = authService.signUp(request);
        return ApiResponse.success("200", user.getId(), "회원가입에 성공했습니다.");
    }
}
