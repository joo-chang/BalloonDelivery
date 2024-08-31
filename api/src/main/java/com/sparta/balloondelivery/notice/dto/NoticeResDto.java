package com.sparta.balloondelivery.notice.dto;

import com.sparta.balloondelivery.data.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeResDto {
    private String title;
    private String content;
    private String createdBy;
    private String createdAt;
    private String updatedBy;
    private String updatedAt;

    public NoticeResDto(Notice notice) {
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.createdBy = notice.getCreatedBy();
        this.createdAt = notice.getCreatedAt().toString();
    }
}
