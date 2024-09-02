package com.sparta.balloondelivery.data.entity;

import com.sparta.balloondelivery.util.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.UUID;

@Entity(name = "p_notices")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends BaseEntity {
    @Id
    private UUID id;
    @Setter
    private String title;
    @Setter
    private String content;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    public Notice(String title, String content,User user) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.content = content;
        this.user = user;
        this.setCreatedBy(user.getUsername());
    }

}
