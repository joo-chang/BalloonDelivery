package com.sparta.balloondelivery.restaurant.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@Builder
public class RestaurantUpdateRequest {
    private Optional<String> name;
    private Optional<String> content;
    private Optional<String> phone;
    private Optional<UUID> categoryId;
    private Optional<UUID> locationId;
    private Optional<String> address1;
    private Optional<String> address2;
    private Optional<String> address3;
}
