package com.sparta.balloondelivery.report.service;

import com.sparta.balloondelivery.data.entity.Report;
import com.sparta.balloondelivery.data.repository.ReportRepository;
import com.sparta.balloondelivery.exception.BaseException;
import com.sparta.balloondelivery.report.dto.ReportReqDto;
import com.sparta.balloondelivery.util.ErrorCode;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public void createReport(String userId, String userName, ReportReqDto reportReqDto) {
        try {
            Report report = new Report(reportReqDto.getTitle(), reportReqDto.getContent(), userName);
            reportRepository.save(report);
        } catch (Exception e) {
            throw new BaseException(ErrorCode.INVALID_PARAMETER);
        }
    }
}
