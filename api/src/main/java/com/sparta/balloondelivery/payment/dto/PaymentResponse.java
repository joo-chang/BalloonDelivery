package com.sparta.balloondelivery.payment.dto;

import com.sparta.balloondelivery.data.entity.Order;
import com.sparta.balloondelivery.data.entity.Payment;
import com.sparta.balloondelivery.data.entity.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
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

    @Getter
    @Builder
    public static class PaymentDto {
        private UUID paymentId;
        private String paymentStatus;
        private Long price;
        private String requestedAt;
        private String createdAt;
        private String updatedAt;
        private UUID orderId;
        private Long userId;

        public static PaymentDto toDto(Payment payment) {
            return PaymentDto.builder()
                    .paymentId(payment.getId())
                    .paymentStatus(payment.getPaymentStatus().name())
                    .price(payment.getPrice())
                    .requestedAt(payment.getRequestedAt().toString())
                    .createdAt(payment.getCreatedAt().toString())
                    .updatedAt(payment.getUpdatedAt().toString())
                    .orderId(payment.getOrder().getId())
                    .userId(payment.getUser().getId())
                    .build();
        }
    }
}
