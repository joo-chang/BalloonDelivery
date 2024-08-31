package com.sparta.balloondelivery.data.entity;

import com.sparta.balloondelivery.util.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "p_notices")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends BaseEntity {
    @Id
    private UUID id;
    private String title;
    private String content;

    public Notice(String title, String content) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.content = content;
    }
}
