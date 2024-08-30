package com.sparta.balloondelivery.data.repository;

import com.sparta.balloondelivery.data.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByPaymentIdAndUserId(UUID paymentId, Long userId);

    Optional<Payment> findByOrderId(UUID orderId);
}
