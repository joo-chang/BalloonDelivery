package com.sparta.balloondelivery.payment.service;

import com.sparta.balloondelivery.data.entity.Order;
import com.sparta.balloondelivery.data.entity.Payment;
import com.sparta.balloondelivery.data.entity.User;
import com.sparta.balloondelivery.data.repository.OrderRepository;
import com.sparta.balloondelivery.data.repository.PaymentRepository;
import com.sparta.balloondelivery.data.repository.UserRepository;
import com.sparta.balloondelivery.exception.BaseException;
import com.sparta.balloondelivery.payment.dto.PaymentRequest;
import com.sparta.balloondelivery.util.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        Payment payment = paymentRepository.findById(updateOrderStatus.getPaymentId())
                .orElseThrow(() -> new BaseException(ErrorCode.PAYMENT_NOT_FOUND));

        Order order = orderRepository.findByPaymentId(payment.getId())
                .orElseThrow(() -> new BaseException(ErrorCode.ORDER_NOT_FOUND));

        // 결제 정보 저장
        payment.updatePayment(updateOrderStatus);

        // 주문 상태 변경
        if (Payment.PaymentStatus.valueOf(updateOrderStatus.getPaymentStatus()) == Payment.PaymentStatus.COMPLETED) {
            order.updateOrderStatus(Order.OrderStatus.WAITING_FOR_ORDER);
        } else if (payment.getPaymentStatus() == Payment.PaymentStatus.FAILED) {
            order.updateOrderStatus(Order.OrderStatus.PAYMENT_FAILED);
        }
    }
}
