package com.sparta.balloondelivery.payment.service;

import com.sparta.balloondelivery.data.entity.Order;
import com.sparta.balloondelivery.data.entity.Payment;
import com.sparta.balloondelivery.data.entity.User;
import com.sparta.balloondelivery.data.repository.OrderRepository;
import com.sparta.balloondelivery.data.repository.PaymentRepository;
import com.sparta.balloondelivery.data.repository.UserRepository;
import com.sparta.balloondelivery.exception.BaseException;
import com.sparta.balloondelivery.payment.dto.PaymentRequest;
import com.sparta.balloondelivery.payment.dto.PaymentResponse;
import com.sparta.balloondelivery.util.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    @Transactional
    public void paymentResponse(Long userId, PaymentRequest.UpdateOrderStatus updateOrderStatus) {
        userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        Payment payment = paymentRepository.findByIdAndUserId(updateOrderStatus.getPaymentId(), userId)
                .orElseThrow(() -> new BaseException(ErrorCode.PAYMENT_NOT_FOUND));

        Order order = payment.getOrder();

        // 결제 정보 저장
        payment.updatePayment(updateOrderStatus);

        // 주문 상태 변경
        if (Payment.PaymentStatus.valueOf(updateOrderStatus.getPaymentStatus()) == Payment.PaymentStatus.COMPLETED) {
            order.updateOrder(Order.OrderStatus.WAITING_FOR_ORDER);
        } else if (payment.getPaymentStatus() == Payment.PaymentStatus.FAILED) {
            order.updateOrder(Order.OrderStatus.PAYMENT_FAILED);
        }
    }

    @Transactional
    public void cancelPayment(Long userId, UUID paymentId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        Payment payment = paymentRepository.findByIdAndUserId(paymentId, userId)
                .orElseThrow(() -> new BaseException(ErrorCode.PAYMENT_NOT_FOUND));


        Order order = payment.getOrder();

        // 조리중이거나 완료된 주문은 취소할 수 없음
        if (order.getOrderStatus() == Order.OrderStatus.COOKING || order.getOrderStatus() == Order.OrderStatus.COMPLETED) {
            throw new BaseException(ErrorCode.ORDER_CANNOT_BE_CANCELED);
        }

        // PG사에 결제 취소 요청
        boolean isCanceled = true;

        if (isCanceled) {
            // 결제 취소 완료 시
            payment.updatePayment(Payment.PaymentStatus.CANCELED);
        } else {
            // 결제 취소 실패 시
            throw new BaseException(ErrorCode.PAYMENT_CANCEL_FAILED);
        }

        // 결제 상태 변경
        payment.updatePayment(Payment.PaymentStatus.CANCELED);
        // 주문 상태 변경
        order.updateOrder(Order.OrderStatus.CANCELED);
    }


    public PaymentResponse.PaymentDetail getPayment(Long userId, UUID paymentId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        Payment payment = paymentRepository.findByIdAndUserId(paymentId, userId)
                .orElseThrow(() -> new BaseException(ErrorCode.PAYMENT_NOT_FOUND));

        return PaymentResponse.PaymentDetail.toDto(payment);
    }


    @Transactional
    public void deletePayment(Long userId, UUID paymentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        Payment payment = paymentRepository.findByIdAndUserId(paymentId, userId)
                .orElseThrow(() -> new BaseException(ErrorCode.PAYMENT_NOT_FOUND));

        payment.setDeletedYnTrue(user.getUsername());

    }

    public Page<PaymentResponse.PaymentDto> getPayments(Long userId, Pageable pageable) {
        userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        Page<Payment> payments = paymentRepository.findByUserIdAndDeletedYnFalse(userId, pageable);

        // PaymentDto로 변환
        return payments.map(PaymentResponse.PaymentDto::toDto);
    }
}
