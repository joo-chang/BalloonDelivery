package com.sparta.balloondelivery.report.service;

import com.sparta.balloondelivery.data.entity.*;
import com.sparta.balloondelivery.data.repository.AnswerReporitory;
import com.sparta.balloondelivery.data.repository.ReportRepository;
import com.sparta.balloondelivery.data.repository.UserRepository;
import com.sparta.balloondelivery.exception.BaseException;
import com.sparta.balloondelivery.report.dto.AnswerResDto;
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
    private final AnswerReporitory answerReporitory;
    private final UserRepository userRepository;

    public ReportService(ReportRepository reportRepository, AnswerReporitory answerReporitory, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.answerReporitory = answerReporitory;
        this.userRepository = userRepository;
    }

    public void createReport(String userId, ReportReqDto reportReqDto) {
        try {
            User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
            Report report = new Report(reportReqDto.getTitle(), reportReqDto.getContent(),user);
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

    public Page<ReportResDto> getAllReport(Pageable pageable, String userRole, String userId) {
        try {
            if (userRole.equals(UserRole.MANAGER.name())) {
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
            if (reportReqDto.getTitle() != null) {
                report.setTitle(reportReqDto.getTitle());
            }
            if (reportReqDto.getContent() != null) {
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

    public void createAnswer(UUID reportId, String userId, String userName, ReportReqDto reportReqDto) {
        try {
            Report report = reportRepository.findById(reportId).orElseThrow(() -> new BaseException(ErrorCode.INVALID_PARAMETER));
            if (!report.getUserId().equals(Long.parseLong(userId))) {
                throw new BaseException(ErrorCode.NO_PERMISSION);
            }
            User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new BaseException(ErrorCode.INVALID_PARAMETER));
            Answer answer = new Answer(reportId, reportReqDto.getTitle(), reportReqDto.getContent(), user);
            answerReporitory.save(answer);
            report.setReportStatus(ReportStatus.PROCESSED);
            reportRepository.save(report);

        } catch (Exception e) {
            throw new BaseException(ErrorCode.INVALID_PARAMETER);
        }
    }


    public AnswerResDto getAnswer(UUID reportId, String userRole, String userId) {
        try {
            Report report = reportRepository.findById(reportId).orElseThrow(() -> new BaseException(ErrorCode.INVALID_PARAMETER));
            if (!userRole.equals(UserRole.MANAGER.name()) && !report.getUserId().equals(Long.parseLong(userId))) {
                throw new BaseException(ErrorCode.NO_PERMISSION);
            }
            return new AnswerResDto(answerReporitory.findByReportId(reportId).orElseThrow(() -> new BaseException(ErrorCode.INVALID_PARAMETER)));

        } catch (Exception e) {
            log.info(e.getMessage());
            throw new BaseException(ErrorCode.INVALID_PARAMETER);
        }
    }


    public void updateAnswer(UUID reportId, String userId, String userName, ReportReqDto reportReqDto) {
        try {
            Report report = reportRepository.findById(reportId).orElseThrow(() -> new BaseException(ErrorCode.INVALID_PARAMETER));
            if (!report.getUserId().equals(Long.parseLong(userId))) {
                throw new BaseException(ErrorCode.NO_PERMISSION);
            }
            Answer answer = answerReporitory.findByReportId(reportId).orElseThrow(() -> new BaseException(ErrorCode.INVALID_PARAMETER));
            if (reportReqDto.getTitle() != null) {
                answer.setTitle(reportReqDto.getTitle());
            }
            if (reportReqDto.getContent() != null) {
                answer.setContent(reportReqDto.getContent());
            }
            answer.setUpdatedBy(userName);
            answerReporitory.save(answer);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new BaseException(ErrorCode.INVALID_PARAMETER);
        }
    }

    public void deleteAnswer(UUID reportId, String userId, String userName) {
        try {
            Report report = reportRepository.findById(reportId).orElseThrow(() -> new BaseException(ErrorCode.INVALID_PARAMETER));
            if (!report.getUserId().equals(Long.parseLong(userId))) {
                throw new BaseException(ErrorCode.NO_PERMISSION);
            }
            Answer answer = answerReporitory.findByReportId(reportId).orElseThrow(() -> new BaseException(ErrorCode.INVALID_PARAMETER));
            answer.setDeletedYnTrue(userName);
            answerReporitory.save(answer);
            reportRepository.save(report);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new BaseException(ErrorCode.INVALID_PARAMETER);
        }
    }
}
