package com.sparta.balloondelivery.user.controller;

import com.sparta.balloondelivery.data.entity.UserRole;
import com.sparta.balloondelivery.user.dto.RoleUpdateReqDto;
import com.sparta.balloondelivery.user.dto.UserReqDto;
import com.sparta.balloondelivery.user.service.UserService;
import com.sparta.balloondelivery.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

// TODO : 회원 조회, 회원 수정, 회원 삭제 API 구현

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{targetUserId}")
    public ApiResponse<?> getUser(@RequestHeader(value = "X-User-Id", required = true) String userId,
                                  @PathVariable Long targetUserId) {
        log.info("userId : {}", userId);
        log.info("targetUserId : {}", targetUserId);
        return ApiResponse.success("OK", userService.getUser(userId, targetUserId), "회원 조회 성공");
    }

    @PatchMapping("")
    public ApiResponse<?> updateUser(@RequestHeader(value = "X-User-Id", required = true) String userId, @Valid @RequestBody UserReqDto userReqDto) {
        userService.updateUser(userId, userReqDto);
        return ApiResponse.success("OK", userId, "회원 수정 성공");
    }

    @DeleteMapping("")
    public ApiResponse<?> deleteUser(@RequestHeader(value = "X-User-Id", required = true) String userId) {
        userService.deleteUser(userId);
        return ApiResponse.success("OK", userId, "회원 삭제 성공");
    }

    @GetMapping("/all")
    public ApiResponse<?> getAllUsers(@RequestHeader(value = "X-User-Id", required = true) String userId, Pageable pageable) {
        return ApiResponse.success("OK", userService.getAllUsers(Long.valueOf(userId), pageable), "모든 회원 조회 성공");
    }

    @PatchMapping("/role/{targetUserId}")
    public ApiResponse<?> updateuserRole(@RequestHeader(value = "X-User-Id", required = true) String userId, @PathVariable Long targetUserId, @RequestBody RoleUpdateReqDto roleUpdateReqDto) {
        userService.updateUserRole(userId, targetUserId, roleUpdateReqDto);
        return ApiResponse.success("OK", userId, "회원 권한 수정 성공");
    }

}
