package com.sparta.balloondelivery.restaurant.controller;

import com.sparta.balloondelivery.restaurant.dto.request.RestaurantCreateRequest;
import com.sparta.balloondelivery.restaurant.dto.response.RestaurantCreateResponse;
import com.sparta.balloondelivery.restaurant.dto.response.RestaurantInfoResponse;
import com.sparta.balloondelivery.restaurant.service.RestaurantService;
import com.sparta.balloondelivery.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class RestaurantController {
    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    /**
     * 가게 등록 API
     */
    @PostMapping("/restaurant")
    public ApiResponse<RestaurantCreateResponse> createRestaurant(
            @RequestHeader("userId") UUID userId, // 유저 ID를 헤더에서 받아옴
            @RequestBody RestaurantCreateRequest request
    ) {
        RestaurantCreateResponse response = restaurantService.createRestaurant(userId, request);
        return ApiResponse.success("", response, "가게 등록 성공");
    }

    /**
     * 가게 조회 API
     */
    @GetMapping("/restaurants/{restaurant_id}")
    public ApiResponse<RestaurantInfoResponse> getRestaurantInfo(
            @PathVariable("restaurant_id") UUID restaurantId
    ) {
        RestaurantInfoResponse response = restaurantService.getRestaurantInfo(restaurantId);
        return ApiResponse.success("", response, "가게 정보 조회 성공");
    }
}

