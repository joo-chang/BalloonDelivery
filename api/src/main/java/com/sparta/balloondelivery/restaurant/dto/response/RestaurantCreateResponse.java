package com.sparta.balloondelivery.restaurant.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class RestaurantCreateResponse {
    private UUID restaurantId;
    private String name;
    private String content;
    private String phone;
    private UUID userId;
    private UUID categoryId;
    private UUID locationId;

    public RestaurantCreateResponse(UUID restaurantId, String name, String content, String phone, UUID userId, UUID categoryId, UUID locationId) {
    }
}
