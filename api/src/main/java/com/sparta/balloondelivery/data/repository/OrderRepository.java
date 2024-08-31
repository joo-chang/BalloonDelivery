package com.sparta.balloondelivery.data.repository;

import com.sparta.balloondelivery.data.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserId(Long id);

    List<Order> findByRestaurantRestaurantIdAndDeletedYnFalseOrderByCreatedAtDesc(UUID restaurantId);

    Order findByIdAndUserId(UUID id, Long userId);

    // order에 userId와 restaurant name을 이용하여 조회 (페이징 처리), deletedYn이 false인 것만 조회
    @Query("select o from p_orders o where o.user.id = :userId and o.restaurant.name like %:restaurantName% and o.deletedYn = false")
    Page<Order> searchOrders(Long userId, String restaurantName, Pageable pageable);
}
