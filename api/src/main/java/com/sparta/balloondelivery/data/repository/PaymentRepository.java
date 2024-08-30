package com.sparta.balloondelivery.data.repository;

import com.sparta.balloondelivery.data.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByIdAndUserId(UUID id, Long userId);

    Optional<Payment> findByOrderId(UUID orderId);

    @Query("SELECT p FROM p_payments p WHERE p.user.userId = :userId ")
    Page<Payment> findByUserIdAndDeletedYnFalse(Long userId, Pageable pageable);
}
