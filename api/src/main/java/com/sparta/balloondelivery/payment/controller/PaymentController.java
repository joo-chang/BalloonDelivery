package com.sparta.balloondelivery.payment.controller;

import com.sparta.balloondelivery.payment.dto.PaymentRequest;
import com.sparta.balloondelivery.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    /**
     * 결제 응답 API (PG사로 부터 받은 정보)
     */
    @PutMapping
    public void paymentResponse(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody PaymentRequest.UpdateOrderStatus updateOrderStatus) {
        paymentService.paymentResponse(userId, updateOrderStatus);
    }

    /**
     * 결제 취소 API
     */
    @DeleteMapping("/{paymentId}")
    public void cancelPayment(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable UUID paymentId) {
        paymentService.cancelPayment(userId, paymentId);
    }

}
