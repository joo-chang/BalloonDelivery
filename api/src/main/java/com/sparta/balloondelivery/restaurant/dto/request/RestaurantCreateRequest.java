package com.sparta.balloondelivery.restaurant.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RestaurantCreateRequest {
    private String name;
    private String content;
    private String phone;
    private String address1;
    private String address2;
    private String address3;
    private UUID categoryId;
    private UUID locationId;

    // All-args constructor (Lombok's @Builder includes this)
    public RestaurantCreateRequest(String name, String content, String phone, String address1, String address2, String address3, UUID categoryId, UUID locationId) {
        this.name = name;
        this.content = content;
        this.phone = phone;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.categoryId = categoryId;
        this.locationId = locationId;
    }
}
