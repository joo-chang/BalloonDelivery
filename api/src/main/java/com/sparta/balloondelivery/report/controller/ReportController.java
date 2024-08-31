package com.sparta.balloondelivery.report.controller;

import com.sparta.balloondelivery.report.dto.ReportReqDto;
import com.sparta.balloondelivery.report.service.ReportService;
import com.sparta.balloondelivery.util.ApiResponse;
import com.sparta.balloondelivery.util.SuccessCode;
import org.springframework.web.bind.annotation.*;

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
}
