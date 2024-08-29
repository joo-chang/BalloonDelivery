package com.sparta.balloondelivery.menu.dto.response;

import com.sparta.balloondelivery.data.entity.Menu;
import com.sparta.balloondelivery.data.entity.Restaurant;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class MenuCreateResponse {
    private UUID menuId;
    private String name;
    private Integer price;
    private String content;
    private UUID restaurantId;

    public static MenuCreateResponse toDto(Menu menu) {
        return MenuCreateResponse.builder()
                .menuId(menu.getMenuId())
                .name(menu.getName())
                .price(menu.getPrice())
                .content(menu.getContent())
                .restaurantId(menu.getRestaurant().getRestaurantId())
                .build();
    }
}