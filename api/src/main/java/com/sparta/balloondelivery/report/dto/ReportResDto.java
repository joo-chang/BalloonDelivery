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
    private String reportStatus;


    public ReportResDto(Report report) {
        this.id = report.getId();
        this.title = report.getTitle();
        this.content = report.getContent();
        this.reporter = report.getReporter();
        this.reportStatus = report.getReportStatus().name();
    }
}
