package com.sparta.balloondelivery.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportReqDto {
    private String title;
    private String content;
}
