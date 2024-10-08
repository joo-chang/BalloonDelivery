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
    private UUID id;
    private String name;
    private String content;
    private String phone;
    private Boolean visible;
    private Long userId;
    private UUID categoryId;
    private UUID locationId;
    private UUID addressId;

    public static RestaurantInfoResponse toDto(Restaurant restaurant) {
        return RestaurantInfoResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .content(restaurant.getContent())
                .phone(restaurant.getPhone())
                .visible(restaurant.getVisible())
                .userId(restaurant.getUser().getId())
                .categoryId(restaurant.getCategory().getId())
                .locationId(restaurant.getLocation().getId())
                .addressId(restaurant.getAddress().getId())
                .build();
    }
}
