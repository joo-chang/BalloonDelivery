package com.sparta.balloondelivery.data.entity;

import com.sparta.balloondelivery.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "p_orders")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Column(name = "order_type")
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    private String request;

    @Builder.Default
    private Long totalPrice = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public void addOrderDetail(OrderDetail orderDetail) {
        // total price 계산
        Long price = orderDetail.getPrice();
        if (price != null) {
            totalPrice += orderDetail.getPrice();
        }
        orderDetails.add(orderDetail);
        // TODO: order 잘 들어가는지 테스트 해보기, 없어도 되는지?
        orderDetail.setOrder(this);
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public enum OrderStatus {
        // 결제 대기, 결제 실패, 주문 취소, 주문 대기, 조리 중, 주문 완료
        WAITING_FOR_PAYMENT, PAYMENT_FAILED, CANCELED, WAITING_FOR_ORDER, COOKING, COMPLETED
    }

    public enum OrderType {
        // 배달, 포장
        DELIVERY, TAKEOUT
    }

}
