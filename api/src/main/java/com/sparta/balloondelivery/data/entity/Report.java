package com.sparta.balloondelivery.data.entity;

import com.sparta.balloondelivery.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

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
    @Setter
    private String title;
    @Setter
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    @Setter
    private ReportStatus reportStatus;



    public Report(String title, String content, User user) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.content = content;
        this.user = user;
        this.reportStatus = ReportStatus.REPORTED;
        this.setCreatedBy(user.getUsername());
    }


    public Long getUserId() {
        return user.getId();
    }
}
