package com.sparta.balloondelivery.payment.dto;

import com.sparta.balloondelivery.data.entity.Payment;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

public class PaymentResponse {
    @Getter
    @Builder
    public static class PaymentDetail {
        private UUID paymentId;
        private String paymentStatus;
        private String card;
        private String requestedAt;
        private String approvedAt;

        public static PaymentDetail toDto(Payment payment) {
            return PaymentDetail.builder()
                    .paymentId(payment.getId())
                    .paymentStatus(payment.getPaymentStatus().name())
                    .card(payment.getCard())
                    .requestedAt(payment.getRequestedAt().toString())
                    .approvedAt(payment.getApprovedAt().toString())
                    .build();
        }
    }


}
