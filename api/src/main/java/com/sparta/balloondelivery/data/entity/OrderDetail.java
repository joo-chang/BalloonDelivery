package com.sparta.balloondelivery.data.entity;

import com.sparta.balloondelivery.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "p_order_details")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail extends BaseEntity {
    @Id
    @Column(name = "order_detail_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Integer quantity;
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    public void setOrder(Order order) {
        this.order = order;
    }
}
