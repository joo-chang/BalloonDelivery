package com.sparta.balloondelivery.report.dto;

import com.sparta.balloondelivery.data.entity.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResDto {
    private String title;
    private String content;
    private String answerer;
    private String processDate;

    public AnswerResDto(Answer answer) {
        this.title = answer.getTitle();
        this.content = answer.getContent();
        this.answerer = answer.getUser().getUsername();
        this.processDate = answer.getCreatedAt().toLocalDate().toString();
    }
}

