package com.sparta.balloondelivery.payment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentRequest {

    @Getter
    public static class UpdateOrderStatus {
        private UUID paymentId;
        private String paymentStatus;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime requestedAt;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime approvedAt;
    }
}
