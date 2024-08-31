package com.sparta.balloondelivery.notice.controller;

import com.sparta.balloondelivery.data.entity.UserRole;
import com.sparta.balloondelivery.exception.BaseException;
import com.sparta.balloondelivery.notice.dto.NoticeReqDto;
import com.sparta.balloondelivery.notice.service.NoticeService;
import com.sparta.balloondelivery.util.ApiResponse;
import com.sparta.balloondelivery.util.ErrorCode;
import com.sparta.balloondelivery.util.SuccessCode;
import org.springframework.web.bind.annotation.*;

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
}
