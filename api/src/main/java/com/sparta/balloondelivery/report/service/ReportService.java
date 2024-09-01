package com.sparta.balloondelivery.report.service;

import com.sparta.balloondelivery.data.entity.Report;
import com.sparta.balloondelivery.data.entity.UserRole;
import com.sparta.balloondelivery.data.repository.ReportRepository;
import com.sparta.balloondelivery.exception.BaseException;
import com.sparta.balloondelivery.report.dto.ReportReqDto;
import com.sparta.balloondelivery.report.dto.ReportResDto;
import com.sparta.balloondelivery.util.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public void createReport(String userId, String userName, ReportReqDto reportReqDto) {
        try {
            Report report = new Report(reportReqDto.getTitle(), reportReqDto.getContent(), userName, Long.parseLong(userId));
            reportRepository.save(report);
        } catch (Exception e) {
            throw new BaseException(ErrorCode.INVALID_PARAMETER);
        }
    }

    public ReportResDto getReport(UUID reportId, String userRole, String userId) {
        try {
            Report report = reportRepository.findById(reportId).orElseThrow(() -> new BaseException(ErrorCode.INVALID_PARAMETER));
            if (!userRole.equals(UserRole.MANAGER.name()) && !report.getUserId().equals(Long.parseLong(userId))) {
                throw new BaseException(ErrorCode.NO_PERMISSION);
            }
            return new ReportResDto(report);
        } catch (Exception e) {
            throw new BaseException(ErrorCode.INVALID_PARAMETER);
        }
    }

    public Page<ReportResDto> getAllReport(Pageable pageable,String userRole, String userId) {
        try {
            if(userRole.equals(UserRole.MANAGER.name())) {
                return reportRepository.findAll(pageable).map(ReportResDto::new);
            }
            return reportRepository.findByUserId(Long.parseLong(userId), pageable).map(ReportResDto::new);
        } catch (Exception e) {
            throw new BaseException(ErrorCode.INVALID_PARAMETER);
        }
    }

    public void updateReport(UUID reportId, String userId, String userName, ReportReqDto reportReqDto) {
        try {
            Report report = reportRepository.findById(reportId).orElseThrow(() -> new BaseException(ErrorCode.INVALID_PARAMETER));
            if (!report.getUserId().equals(Long.parseLong(userId))) {
                throw new BaseException(ErrorCode.NO_PERMISSION);
            }
            if(reportReqDto.getTitle() != null) {
                report.setTitle(reportReqDto.getTitle());
            }
            if(reportReqDto.getContent() != null) {
                report.setContent(reportReqDto.getContent());
            }
            report.setUpdatedBy(userName);
            reportRepository.save(report);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new BaseException(ErrorCode.INVALID_PARAMETER);
        }
    }

    public void deleteReport(UUID reportId, String userId, String userName, String userRole) {
        try {
            Report report = reportRepository.findById(reportId).orElseThrow(() -> new BaseException(ErrorCode.INVALID_PARAMETER));
            if (!report.getUserId().equals(Long.parseLong(userId)) && !userRole.equals(UserRole.MANAGER.name())) {
                throw new BaseException(ErrorCode.NO_PERMISSION);
            }
            report.setDeletedYnTrue(userName);
            reportRepository.save(report);
        } catch (Exception e) {
            throw new BaseException(ErrorCode.INVALID_PARAMETER);
        }
    }
}
