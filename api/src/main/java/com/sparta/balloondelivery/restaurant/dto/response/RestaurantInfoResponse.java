package com.sparta.balloondelivery.restaurant.dto.response;

import com.sparta.balloondelivery.data.entity.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class RestaurantInfoResponse {
    private UUID restaurantId;
    private String name;
    private String content;
    private String phone;
    private UUID userId;
    private UUID categoryId;
    private UUID locationId;
    private UUID addressId;

    public static RestaurantInfoResponse toDto(Restaurant restaurant) {
        return RestaurantInfoResponse.builder()
                .restaurantId(restaurant.getRestaurantId())
                .name(restaurant.getName())
                .content(restaurant.getContent())
                .phone(restaurant.getPhone())
                .userId(restaurant.getUser().getUserId())
                .categoryId(restaurant.getCategory().getCategoryId())
                .locationId(restaurant.getLocation().getLocationId())
                .addressId(restaurant.getAddress().getAddressId())
                .build();
    }
}
