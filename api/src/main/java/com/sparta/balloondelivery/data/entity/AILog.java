package com.sparta.balloondelivery.data.entity;

import com.sparta.balloondelivery.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "p_AI")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AILog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "log_id", columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "request", nullable = false, length = 1000)
    private String request;

    @Column(name = "response", nullable = false, length = 1000)
    private String response;

}
