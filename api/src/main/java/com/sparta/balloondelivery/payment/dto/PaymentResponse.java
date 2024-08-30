package com.sparta.balloondelivery.payment.dto;

import com.sparta.balloondelivery.data.entity.Payment;
import lombok.Builder;
import lombok.Getter;

public class PaymentResponse {
    @Getter
    @Builder
    public static class PaymentDetail {
        private String paymentId;
        private String paymentStatus;
        private String card;
        private String requestedAt;
        private String approvedAt;

        public static PaymentDetail toDto(Payment payment) {
            return PaymentDetail.builder()
                    .paymentId(payment.getId().toString())
                    .paymentStatus(payment.getPaymentStatus().name())
                    .card(payment.getCard())
                    .requestedAt(payment.getRequestedAt().toString())
                    .approvedAt(payment.getApprovedAt().toString())
                    .build();
        }
    }
}
