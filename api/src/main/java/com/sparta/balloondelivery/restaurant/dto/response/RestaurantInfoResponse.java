package com.sparta.balloondelivery.restaurant.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class RestaurantInfoResponse {
    private UUID restaurantId;
    private String name;
    private String content;
    private String phone;
    private UUID userId;
    private UUID categoryId;
    private UUID locationId;
    private UUID addressId;

    public RestaurantInfoResponse(UUID restaurantId, String name, String content, String phone, UUID userId, UUID categoryId, UUID locationId, UUID addressId) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.content = content;
        this.phone = phone;
        this.userId = userId;
        this.categoryId = categoryId;
        this.locationId = locationId;
        this.addressId = addressId;
    }
}
