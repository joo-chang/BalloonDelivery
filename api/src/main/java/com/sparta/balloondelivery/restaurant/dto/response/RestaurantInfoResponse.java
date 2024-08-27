package com.sparta.balloondelivery.restaurant.dto.response;

import com.sparta.balloondelivery.data.entity.Restaurant;
import lombok.Builder;

import java.util.UUID;

@Builder
public record RestaurantInfoResponse(
        UUID restaurantId,
        String name,
        String content,
        String phone,
        UUID userId,
        UUID categoryId,
        UUID locationId,
        UUID addressId
) {
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
