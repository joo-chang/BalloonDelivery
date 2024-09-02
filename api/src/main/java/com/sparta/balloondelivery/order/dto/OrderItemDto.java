package com.sparta.balloondelivery.order.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderItemDto {
    private UUID menuId;
    private Integer quantity;
}
