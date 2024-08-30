package com.sparta.balloondelivery.menu.dto.response;

import com.sparta.balloondelivery.data.entity.Menu;
import com.sparta.balloondelivery.data.entity.Visiable;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class MenuInfoResponse {
    private UUID id;
    private String name;
    private Integer price;
    private String content;
    private Visiable visiable;
    private UUID restaurantId;

    public static MenuInfoResponse toDto(Menu menu) {
        return MenuInfoResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .content(menu.getContent())
                .visiable(menu.getVisiable())
                .restaurantId(menu.getRestaurant().getId())
                .build();
    }
}
