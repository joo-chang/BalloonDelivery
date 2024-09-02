package com.sparta.balloondelivery.restaurant.dto.response;

import com.sparta.balloondelivery.data.entity.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class RestaurantDeletedResponse {
    private UUID id;
    private String name;
    private boolean isDeleted;

    public static RestaurantDeletedResponse toDto(Restaurant restaurant) {
        return RestaurantDeletedResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .isDeleted(restaurant.isDeleted())
                .build();
    }
}
