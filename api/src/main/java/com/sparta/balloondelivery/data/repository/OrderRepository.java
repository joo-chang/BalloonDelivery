package com.sparta.balloondelivery.data.repository;

import com.sparta.balloondelivery.data.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserId(Long id);

    Optional<Order> findByPaymentId(UUID paymentId);

    List<Order> findByRestaurantIdOrderByCreatedAtDesc(UUID restaurantId);
}
