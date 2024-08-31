package com.sparta.balloondelivery.data.entity;

import com.sparta.balloondelivery.util.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

    public Notice(String title, String content) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.content = content;
    }

}
