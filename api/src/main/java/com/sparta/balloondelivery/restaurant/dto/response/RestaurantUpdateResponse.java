package com.sparta.balloondelivery.restaurant.dto.response;

import com.sparta.balloondelivery.data.entity.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class RestaurantUpdateResponse {
    private UUID id;
    private String name;
    private String content;
    private String phone;
    private UUID categoryId;
    private UUID locationId;
    private UUID addressId;

    public static RestaurantUpdateResponse toDto(Restaurant restaurant) {
        return RestaurantUpdateResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .content(restaurant.getContent())
                .phone(restaurant.getPhone())
                .categoryId(restaurant.getCategory().getId())
                .locationId(restaurant.getLocation().getId())
                .addressId(restaurant.getAddress().getId())
                .build();
    }
}
