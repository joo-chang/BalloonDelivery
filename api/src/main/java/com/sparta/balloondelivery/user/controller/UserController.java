package com.sparta.balloondelivery.user.controller;

import com.sparta.balloondelivery.data.entity.UserRole;
import com.sparta.balloondelivery.exception.BaseException;
import com.sparta.balloondelivery.user.dto.RoleUpdateReqDto;
import com.sparta.balloondelivery.user.dto.UserReqDto;
import com.sparta.balloondelivery.user.service.UserService;
import com.sparta.balloondelivery.util.ApiResponse;
import com.sparta.balloondelivery.util.ErrorCode;
import com.sparta.balloondelivery.util.SuccessCode;
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
                                  @RequestHeader(value = "X-User-Role", required = true) String userRole,
                                  @PathVariable Long targetUserId) {

        if (!userRole.equals(UserRole.MANAGER.name()) && !userId.equals(targetUserId.toString())) {
            throw new BaseException(ErrorCode.NO_PERMISSION);
        }
        return ApiResponse.success("OK", userService.getUser(targetUserId), SuccessCode.FIND_SUCCESS.getSuccessMsg());
    }

    @PatchMapping("")
    public ApiResponse<?> updateUser(@RequestHeader(value = "X-User-Id", required = true) String userId, @Valid @RequestBody UserReqDto userReqDto) {
        userService.updateUser(userId, userReqDto);
        return ApiResponse.success("OK", userId, SuccessCode.UPDATE_SUCCESS.getSuccessMsg());
    }

    @DeleteMapping("")
    public ApiResponse<?> deleteUser(@RequestHeader(value = "X-User-Id", required = true) String userId) {
        userService.deleteUser(userId);
        return ApiResponse.success("OK", userId, SuccessCode.DELETE_SUCCESS.getSuccessMsg());
    }

    @GetMapping("/all")
    public ApiResponse<?> getAllUsers(@RequestHeader(value = "X-User-Id", required = true) String userId,
                                      @RequestHeader(value = "X-User-Role", required = true) String userRole,
                                      Pageable pageable) {

        if (!userRole.equals(UserRole.MANAGER.name())) {
            throw new BaseException(ErrorCode.NO_PERMISSION);
        }
        return ApiResponse.success("OK", userService.getAllUsers(Long.valueOf(userId), pageable), SuccessCode.FIND_SUCCESS.getSuccessMsg());
    }

    @PatchMapping("/role/{targetUserId}")
    public ApiResponse<?> updateuserRole(@RequestHeader(value = "X-User-Id", required = true) String userId,
                                         @RequestHeader(value = "X-User-Role", required = true) String userRole,
                                         @PathVariable Long targetUserId, @RequestBody RoleUpdateReqDto roleUpdateReqDto) {

        if (!userRole.equals(UserRole.MANAGER.name())) {
            throw new BaseException(ErrorCode.NO_PERMISSION);
        }
        userService.updateUserRole(targetUserId, roleUpdateReqDto);
        return ApiResponse.success("OK", userId, SuccessCode.UPDATE_SUCCESS.getSuccessMsg());
    }

}
