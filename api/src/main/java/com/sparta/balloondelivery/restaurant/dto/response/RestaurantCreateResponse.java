package com.sparta.balloondelivery.restaurant.dto.response;

import com.sparta.balloondelivery.data.entity.Restaurant;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RestaurantCreateResponse {
    private UUID id;
    private String name;
    private String content;
    private String phone;
    private Long userId;
    private UUID categoryId;
    private UUID locationId;
    private UUID addressId;

    public static RestaurantCreateResponse toDto(Restaurant restaurant) {
        return RestaurantCreateResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .content(restaurant.getContent())
                .phone(restaurant.getPhone())
                .userId(restaurant.getUser().getId())
                .categoryId(restaurant.getCategory().getId())
                .locationId(restaurant.getLocation().getId())
                .addressId(restaurant.getAddress().getId())
                .build();
    }
}
