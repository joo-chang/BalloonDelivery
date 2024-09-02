package com.sparta.balloondelivery.data.entity;

import com.sparta.balloondelivery.util.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "p_answers")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answer extends BaseEntity {
    @Id
    private UUID id;
    private UUID reportId;
    @Setter
    private String title;
    @Setter
    private String content;
    @OneToOne
    private User user;
    private String processDate;

    public Answer(UUID reportId, String title, String content, User user) {
        this.id = UUID.randomUUID();
        this.reportId = reportId;
        this.title = title;
        this.content = content;
        this.user = user;
        this.processDate = LocalDate.now().toString();
        this.setCreatedBy(user.getUsername());
    }
}
