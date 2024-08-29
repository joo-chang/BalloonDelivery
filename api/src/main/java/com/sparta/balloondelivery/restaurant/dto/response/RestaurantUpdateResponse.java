package com.sparta.balloondelivery.restaurant.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class RestaurantUpdateResponse {
    private UUID restaurantId;
    private String name;
    private String content;
    private String phone;
    private UUID categoryId;
    private UUID locationId;
    private UUID addressId;
}
