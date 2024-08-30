package com.sparta.balloondelivery.order.dto;

import com.sparta.balloondelivery.data.entity.Order;
import com.sparta.balloondelivery.data.entity.OrderDetail;
import com.sparta.balloondelivery.data.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

public class OrderResponse {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyOrderList {
        private UUID id;
        private String orderStatus;
        private String orderType;
        private Long totalPrice;
        private RestaurantDto restaurantDto;

        public static MyOrderList toDto(Order order) {
            return MyOrderList.builder()
                    .id(order.getId())
                    .orderStatus(order.getOrderStatus().name())
                    .orderType(order.getOrderType().name())
                    .totalPrice(order.getTotalPrice())
                    .restaurantDto(RestaurantDto.toDto(order.getRestaurant()))
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RestaurantDto {
        private String name;
        private String content;
        private String phone;

        public static RestaurantDto toDto(Restaurant restaurant) {
            return RestaurantDto.builder()
                    .name(restaurant.getName())
                    .content(restaurant.getContent())
                    .phone(restaurant.getPhone())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RestaurantOrderList {
        private UUID id;
        private String orderStatus;
        private String orderType;
        private Long totalPrice;
        private String username;

        public static RestaurantOrderList toDto(Order order) {
            return RestaurantOrderList.builder()
                    .id(order.getId())
                    .orderStatus(order.getOrderStatus().name())
                    .orderType(order.getOrderType().name())
                    .totalPrice(order.getTotalPrice())
                    .username(order.getUser().getUsername())
                    .build();
        }
    }

    @Getter
    @Builder
    public class OrderDetailResponse {
        private UUID orderId;
        private String orderStatus;
        private String orderType;
        private String request;
        private Long totalPrice;

        private List<OrderDetailDto> orderItems;

        public static OrderDetailResponse toDto(Order order) {
            return OrderDetailResponse.builder()
                    .orderId(order.getId())
                    .orderStatus(order.getOrderStatus().name())
                    .orderType(order.getOrderType().name())
                    .request(order.getRequest())
                    .totalPrice(order.getTotalPrice())
                    .orderItems(order.getOrderDetails().stream()
                            .map(OrderDetailDto::toDto)
                            .toList())
                    .build();
        }

        @Builder
        @Getter
        private static class OrderDetailDto {
            private String menuName;
            private int quantity;
            private Long price;

            public static OrderDetailDto toDto(OrderDetail orderDetail) {
                return OrderDetailDto.builder()
                        .menuName(orderDetail.getMenu().getName())
                        .quantity(orderDetail.getQuantity())
                        .price(orderDetail.getPrice())
                        .build();
            }
        }
    }
}
