package com.sparta.balloondelivery.data.entity;

import com.sparta.balloondelivery.util.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "p_reports")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report extends BaseEntity {

    // 신고 제목, 내용, 신고자, 신고 처리자, 신고 상태(신고 접수, 신고 처리 중, 신고 처리 완료), 신고 답변, 신고 처리 일자
    @Id
    private UUID id;
    private Long userId;
    private String title;
    private String content;
    private String reporter;
    private String processor;
    @Enumerated(EnumType.STRING)
    private ReportStatus reportStatus;
    private String answer;
    private String processDate;


    public Report(String title, String content, String userName, Long userId) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.reporter = userName;
        this.reportStatus = ReportStatus.REPORTED;
        this.setCreatedBy(userName);
    }
}
