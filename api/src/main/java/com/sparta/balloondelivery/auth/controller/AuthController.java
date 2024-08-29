package com.sparta.balloondelivery.auth.controller;

import com.sparta.balloondelivery.auth.dto.SignInReqDto;
import com.sparta.balloondelivery.auth.dto.SignUpReqDto;
import com.sparta.balloondelivery.auth.service.AuthService;
import com.sparta.balloondelivery.data.entity.User;
import com.sparta.balloondelivery.data.entity.UserRole;
import com.sparta.balloondelivery.user.dto.AddressReqDto;
import com.sparta.balloondelivery.util.ApiResponse;
import com.sparta.balloondelivery.util.SuccessCode;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/signUp")
    public ApiResponse<?> signUp(@Valid @RequestBody SignUpReqDto request) {
        User user = authService.signUp(request);
        return ApiResponse.success("OK", user.getId(), SuccessCode.REGISTER_SUCCESS.getSuccessMsg());
    }

    @PostMapping("/signIn")
    public ApiResponse<?> signIn(@RequestBody SignInReqDto request) {
        String accessToken = authService.signIn(request);
        return ApiResponse.success("OK", accessToken, SuccessCode.LOGIN_SUCCESS.getSuccessMsg());
    }

    @GetMapping("/getPermission/{userId}")
    public String getPermission(@PathVariable Long userId) {
        return authService.getPermission(userId).name();
    }

}
