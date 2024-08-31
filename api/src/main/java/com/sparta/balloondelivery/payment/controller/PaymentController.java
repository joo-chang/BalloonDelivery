package com.sparta.balloondelivery.payment.controller;

import com.sparta.balloondelivery.payment.dto.PaymentRequest;
import com.sparta.balloondelivery.payment.service.PaymentService;
import com.sparta.balloondelivery.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    public ApiResponse<?> paymentResponse(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody PaymentRequest.UpdateOrderStatus updateOrderStatus
    ) {
        paymentService.paymentResponse(userId, updateOrderStatus);
        return ApiResponse.success(HttpStatus.OK.name(), null);
    }

    /**
     * 결제 취소 API
     */
    @PutMapping("/{paymentId}/cancel")
    public ApiResponse<?> cancelPayment(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable UUID paymentId
    ) {
        paymentService.cancelPayment(userId, paymentId);
        return ApiResponse.success(HttpStatus.OK.name(), null);
    }


    /**
     * 결제 상세 조회 API
     */
    @GetMapping("/{paymentId}")
    public ApiResponse<?> getPayment(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable UUID paymentId
    ) {
        return ApiResponse.success(HttpStatus.OK.name(), paymentService.getPayment(userId, paymentId));
    }

    /**
     * 결제 내역 삭제 API
     */
    @DeleteMapping("/{paymentId}")
    public ApiResponse<?> deletePayment(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable UUID paymentId
    ) {
        paymentService.deletePayment(userId, paymentId);
        return ApiResponse.success(HttpStatus.OK.name(), null);
    }

    /**
     * 결제 내역 조회 API
     */
    @GetMapping
    public ApiResponse<?> getPayments(
            @RequestHeader("X-USER-ID") Long userId,
            Pageable pageable
    ) {
        return ApiResponse.success(HttpStatus.OK.name(), paymentService.getPayments(userId, pageable));
    }

    /**
     * 결제 재요청 API
     */
    @PutMapping("/{paymentId}/retry")
    public ApiResponse<?> retryPayment(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable UUID paymentId
    ) {
        return ApiResponse.success(HttpStatus.OK.name(), paymentService.retryPayment(userId, paymentId));
    }

}
