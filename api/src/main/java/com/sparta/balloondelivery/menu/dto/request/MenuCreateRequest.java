package com.sparta.balloondelivery.menu.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MenuCreateRequest {
    private String name;
    private Integer price;
    private String content;
    private UUID restaurantId;

}
