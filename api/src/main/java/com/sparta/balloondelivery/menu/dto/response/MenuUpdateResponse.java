package com.sparta.balloondelivery.menu.dto.response;

import com.sparta.balloondelivery.data.entity.Menu;
import com.sparta.balloondelivery.data.entity.Restaurant;
import com.sparta.balloondelivery.data.entity.Visiable;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class MenuUpdateResponse {
    private UUID menuId;
    private String name;
    private Integer price;
    private String content;
    private Visiable visiable;
    private UUID restaurantId;

    public static MenuUpdateResponse toDto(Menu menu) {
        return MenuUpdateResponse.builder()
                .menuId(menu.getMenuId())
                .name(menu.getName())
                .price(menu.getPrice())
                .content(menu.getContent())
                .visiable(menu.getVisiable())
                .restaurantId(menu.getRestaurant().getRestaurantId())
                .build();
    }
}

