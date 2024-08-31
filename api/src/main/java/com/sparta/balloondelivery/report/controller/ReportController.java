package com.sparta.balloondelivery.report.controller;

import com.sparta.balloondelivery.data.entity.UserRole;
import com.sparta.balloondelivery.exception.BaseException;
import com.sparta.balloondelivery.report.dto.ReportReqDto;
import com.sparta.balloondelivery.report.service.ReportService;
import com.sparta.balloondelivery.util.ApiResponse;
import com.sparta.balloondelivery.util.ErrorCode;
import com.sparta.balloondelivery.util.SuccessCode;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("")
    public ApiResponse<?> createReport(@RequestHeader(value = "X-User-Id", required = true) String userId,
                                       @RequestHeader(value = "X-User-Name", required = true) String userName,
                                       @RequestBody ReportReqDto reportReqDto) {
        reportService.createReport(userId, userName, reportReqDto);
        return ApiResponse.success("OK", SuccessCode.SUCCESS.getSuccessMsg());
    }

    @GetMapping("")
    public ApiResponse<?> getAllReport(@RequestHeader(value="X-User-Role", required = true) String userRole,
                                       @RequestHeader(value = "X-User-Id", required = true) String userId,
                                       Pageable pageable) {
        return ApiResponse.success("OK", reportService.getAllReport(pageable, userRole, userId), SuccessCode.FIND_SUCCESS.getSuccessMsg());
    }

    @GetMapping("/{reportId}")
    public ApiResponse<?> getReport(@PathVariable UUID reportId,
                                    @RequestHeader(value = "X-User-Id", required = true) String userId,
                                    @RequestHeader(value = "X-User-Role", required = true) String userRole) {
        return ApiResponse.success("OK", reportService.getReport(reportId, userRole, userId), SuccessCode.FIND_SUCCESS.getSuccessMsg());
    }
}
