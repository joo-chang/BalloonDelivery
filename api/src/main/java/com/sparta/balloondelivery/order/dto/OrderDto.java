package com.sparta.balloondelivery.order.dto;

import com.sparta.balloondelivery.data.entity.Order;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String orderStatus;
    private String orderType;
    private String request;
    private Long totalPrice;
}
