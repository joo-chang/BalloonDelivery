package com.sparta.balloondelivery.restaurant.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RestaurantPageInfoResponse {
    private List<RestaurantInfoResponse> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public RestaurantPageInfoResponse(List<RestaurantInfoResponse> content, int page, int size, long totalElements, int totalPages) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}
