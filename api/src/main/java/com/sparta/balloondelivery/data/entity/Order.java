package com.sparta.balloondelivery.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "p_orders")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @Column(name = "order_id")
    private UUID id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Column(name = "order_type")
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    private String request;
    private Long totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public enum OrderStatus {
        // 결제 대기, 결제 실패, 주문 취소, 주문 대기, 조리 중, 주문 완료
        WAITING_FOR_PAYMENT, PAYMENT_FAILED, CANCELED, WAITING_FOR_ORDER, COOKING, COMPLETED
    }

    public enum OrderType {
        // 배달, 포장
        DELIVERY, TAKEOUT
    }
}
