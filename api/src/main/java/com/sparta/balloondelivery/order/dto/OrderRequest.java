package com.sparta.balloondelivery.order.dto;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

public class OrderRequest {

    @Getter
    public static class CreateOrder {
        private UUID restaurantId;
        private String orderType;
        private String request;

        private List<OrderItemDto> orderItems;
    }

}
