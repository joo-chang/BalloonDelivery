package com.sparta.balloondelivery.restaurant.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RestaurantCreateResponse {
    private UUID restaurantId;
    private String name;
    private String content;
    private String phone;
    private Long userId;
    private UUID categoryId;
    private UUID locationId;
    private UUID addressId;

    public RestaurantCreateResponse(UUID restaurantId, String name, String content, String phone, Long userId, UUID categoryId, UUID locationId, UUID addressId) {
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
