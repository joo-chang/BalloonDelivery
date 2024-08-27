package com.sparta.balloondelivery.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "p_payments")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @Column(name = "payment_id")
    private UUID id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private Long price;
    private String card;


    private enum PaymentStatus {
        // 결제 요청, 결제 완료, 결제 실패, 결제 취소 요청, 결제 취소 완료)
        REQUESTED, COMPLETED, FAILED, CANCEL_REQUESTED, CANCELLED
    }
}
