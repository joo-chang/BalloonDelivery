package com.sparta.balloondelivery.user.controller;

import com.sparta.balloondelivery.data.entity.UserRole;
import com.sparta.balloondelivery.exception.BaseException;
import com.sparta.balloondelivery.user.dto.AddressReqDto;
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

import java.util.UUID;

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

        if (!userId.equals(targetUserId.toString()) && !userRole.equals(UserRole.MANAGER.name())) {
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
    @GetMapping("/address")
    public ApiResponse<?> getAddress(@RequestHeader(value = "X-User-Id", required = true) String userId, Pageable pageable) {
        return ApiResponse.success("OK", userService.getAddress(Long.parseLong(userId), pageable), SuccessCode.SUCCESS.getSuccessMsg());
    }

    @GetMapping("/address/{addressId}")
    public ApiResponse<?> getAddress(@RequestHeader(value = "X-User-Id", required = true) String userId, @PathVariable UUID addressId) {
        return ApiResponse.success("OK", userService.getAddressById(Long.parseLong(userId), addressId), SuccessCode.SUCCESS.getSuccessMsg());
    }

    @PostMapping("/address")
    public ApiResponse<?> addAddress(@RequestHeader(value = "X-User-Id", required = true) String userId,
                                     @RequestHeader(value = "X-User-Name", required = true) String userName,
                                     @Valid @RequestBody AddressReqDto addressReqDto) {
        userService.addAddress(Long.parseLong(userId), addressReqDto, userName);
        return ApiResponse.success("OK", userId, SuccessCode.SUCCESS.getSuccessMsg());
    }

    @PutMapping("/address/{addressId}")
    public ApiResponse<?> updateAddress(@RequestHeader(value = "X-User-Id", required = true) String userId,
                                        @RequestHeader(value = "X-User-Name", required = true) String userName,
                                        @Valid @RequestBody AddressReqDto addressReqDto, @PathVariable UUID addressId) {
        userService.updateAddress(Long.parseLong(userId),addressId, addressReqDto,userName);
        return ApiResponse.success("OK", userId, SuccessCode.SUCCESS.getSuccessMsg());
    }

    @DeleteMapping("/address/{addressId}")
    public ApiResponse<?> deleteAddress(@RequestHeader(value = "X-User-Id", required = true) String userId,
                                        @RequestHeader(value = "X-User-Name", required = true) String userName,
                                        @PathVariable UUID addressId) {
        userService.deleteAddress(Long.parseLong(userId),addressId,userName);
        return ApiResponse.success("OK", userId, SuccessCode.SUCCESS.getSuccessMsg());
    }

}
