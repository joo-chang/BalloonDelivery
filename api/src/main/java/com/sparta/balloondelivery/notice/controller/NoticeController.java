package com.sparta.balloondelivery.notice.controller;

import com.sparta.balloondelivery.data.entity.UserRole;
import com.sparta.balloondelivery.exception.BaseException;
import com.sparta.balloondelivery.notice.dto.NoticeReqDto;
import com.sparta.balloondelivery.notice.service.NoticeService;
import com.sparta.balloondelivery.util.ApiResponse;
import com.sparta.balloondelivery.util.ErrorCode;
import com.sparta.balloondelivery.util.SuccessCode;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping("")
    public ApiResponse<?> createNotice(@RequestHeader(value = "X-User-Role", required = true) String userRole,
                                       @RequestHeader(value = "X-User-Name", required = true) String userName,
                                       @RequestBody NoticeReqDto noticeReqDto) {
        if (!userRole.equals(UserRole.MANAGER.name())) {
            throw new BaseException(ErrorCode.NO_PERMISSION);
        }
        noticeService.createNotice(userName, noticeReqDto);
        return ApiResponse.success("OK", SuccessCode.SUCCESS.getSuccessMsg());
    }

    @GetMapping("")
    public ApiResponse<?> getAllNotice(Pageable pageable) {
        return ApiResponse.success("OK", noticeService.getAllNotice(pageable), SuccessCode.FIND_SUCCESS.getSuccessMsg());
    }

    @GetMapping("/{noticeId}")
    public ApiResponse<?> getNotice(@PathVariable UUID noticeId) {
        return ApiResponse.success("OK", noticeService.getNotice(noticeId), SuccessCode.FIND_SUCCESS.getSuccessMsg());
    }

    @DeleteMapping("/{noticeId}")
    public ApiResponse<?> deleteNotice(@PathVariable UUID noticeId,
                                       @RequestHeader(value = "X-User-Role", required = true) String userRole,
                                       @RequestHeader(value = "X-User-Name", required = true) String userName) {
        if (!userRole.equals(UserRole.MANAGER.name())) {
            throw new BaseException(ErrorCode.NO_PERMISSION);
        }
        noticeService.deleteNotice(noticeId, userName);
        return ApiResponse.success("OK", SuccessCode.DELETE_SUCCESS.getSuccessMsg());
    }

    @PatchMapping("/{noticeId}")
    public ApiResponse<?> updateNotice(@PathVariable UUID noticeId,
                                       @RequestHeader(value = "X-User-Role", required = true) String userRole,
                                       @RequestHeader(value = "X-User-Name", required = true) String userName,
                                       @RequestBody NoticeReqDto noticeReqDto) {
        if (!userRole.equals(UserRole.MANAGER.name())) {
            throw new BaseException(ErrorCode.NO_PERMISSION);
        }
        noticeService.updateNotice(noticeId, userName, noticeReqDto);
        return ApiResponse.success("OK", SuccessCode.UPDATE_SUCCESS.getSuccessMsg());
    }
}
