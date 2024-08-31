package com.sparta.balloondelivery.report.dto;

import com.sparta.balloondelivery.data.entity.Report;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResDto {
    private UUID id;
    private String title;
    private String content;
    private String reporter;
    private String processor;
    private String reportStatus;
    private String answer;
    private String processDate;

    public ReportResDto(Report report) {
        this.id = report.getId();
        this.title = report.getTitle();
        this.content = report.getContent();
        this.reporter = report.getReporter();
        this.processor = report.getProcessor();
        this.reportStatus = report.getReportStatus().name();
        this.answer = report.getAnswer();
        this.processDate = report.getProcessDate();
    }
}
