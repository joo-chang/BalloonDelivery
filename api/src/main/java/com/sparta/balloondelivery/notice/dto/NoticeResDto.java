package com.sparta.balloondelivery.notice.dto;

import com.sparta.balloondelivery.data.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeResDto {
    private UUID id;
    private String title;
    private String content;
    private String createdBy;
    private String createdAt;
    private String updatedBy;
    private String updatedAt;

    public NoticeResDto(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.createdBy = notice.getCreatedBy();
        this.createdAt = formatDateTime(notice.getCreatedAt());
        this.updatedBy = notice.getUpdatedBy();
        this.updatedAt = formatDateTime(notice.getUpdatedAt());
    }

    private String formatDateTime(LocalDateTime dateTime) {
        return (dateTime != null) ? dateTime.toString() : null;
    }
}
