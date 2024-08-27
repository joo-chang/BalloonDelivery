package com.sparta.balloondelivery.restaurant.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class RestaurantCreateRequest {
    private String name;
    private String content;
    private String phone;
    private String address1;
    private String address2;
    private String address3;
    private UUID categoryId;
    private UUID locationId;
}