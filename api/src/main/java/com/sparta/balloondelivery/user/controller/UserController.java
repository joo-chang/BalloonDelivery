package com.sparta.balloondelivery.user.controller;

import com.sparta.balloondelivery.user.dto.UserReqDto;
import com.sparta.balloondelivery.user.service.UserService;
import com.sparta.balloondelivery.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

// TODO : 회원 조회, 회원 수정, 회원 삭제 API 구현

@Slf4j
@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ApiResponse<?> getUser(@RequestHeader(value = "X-User-Id", required = true) String userId) {
        return ApiResponse.success("OK",userService.getUser(userId),"회원 조회 성공");
    }

    @PutMapping("")
    public ApiResponse<?> updateUser(@RequestHeader(value = "X-User-Id", required = true) String userId, @Valid @RequestBody UserReqDto userReqDto) {
        userService.updateUser(userId, userReqDto);
        return ApiResponse.success("OK",userId,"회원 수정 성공");
    }






}
