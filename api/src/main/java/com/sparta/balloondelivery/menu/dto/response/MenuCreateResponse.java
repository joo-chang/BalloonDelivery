package com.sparta.balloondelivery.menu.dto.response;

import com.sparta.balloondelivery.data.entity.Menu;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class MenuCreateResponse {
    private UUID id;
    private String name;
    private Integer price;
    private String content;
    private UUID restaurantId;

    public static MenuCreateResponse toDto(Menu menu) {
        return MenuCreateResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .content(menu.getContent())
                .restaurantId(menu.getRestaurant().getId())
                .build();
    }
}