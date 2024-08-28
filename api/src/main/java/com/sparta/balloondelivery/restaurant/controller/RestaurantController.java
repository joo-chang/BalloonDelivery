package com.sparta.balloondelivery.restaurant.controller;

import com.sparta.balloondelivery.restaurant.dto.request.RestaurantCreateRequest;
import com.sparta.balloondelivery.restaurant.dto.request.RestaurantUpdateRequest;
import com.sparta.balloondelivery.restaurant.dto.response.RestaurantCreateResponse;
import com.sparta.balloondelivery.restaurant.dto.response.RestaurantInfoResponse;
import com.sparta.balloondelivery.restaurant.dto.response.RestaurantPageInfoResponse;
import com.sparta.balloondelivery.restaurant.dto.response.RestaurantUpdateResponse;
import com.sparta.balloondelivery.restaurant.service.RestaurantService;
import com.sparta.balloondelivery.util.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
public class RestaurantController {
    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    /**
     * 가게 등록 API
     */
    @PostMapping("/restaurants")
    public ApiResponse<RestaurantCreateResponse> createRestaurant(
            @RequestHeader("userId") UUID userId, // 유저 ID를 헤더에서 받아옴
            @RequestBody RestaurantCreateRequest request
    ) {
        log.info("Received userId: {}", userId);
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

    /**
     * 가게 정보 수정 API
     */
    @PutMapping("/restaurants/{restaurant_id}")
    public ApiResponse<RestaurantUpdateResponse> updateRestaurant(
            @PathVariable("restaurant_id") UUID restaurantId,
            @RequestBody RestaurantUpdateRequest request
    ) {
        RestaurantUpdateResponse response = restaurantService.updateRestaurant(restaurantId, request);
        return ApiResponse.success("", response, "가게 정보 수정 성공");
    }

    /**
     * 가게 전체 조회 API
     */
    @GetMapping("/restaurants")
    public ApiResponse<RestaurantPageInfoResponse> getAllRestaurantsInfo(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        RestaurantPageInfoResponse response = restaurantService.getAllRestaurantsInfo(page, size);
        return ApiResponse.success("", response, "가게 목록 조회 성공");
    }

    @GetMapping("/restaurants/users")
    public ApiResponse<RestaurantPageInfoResponse> getMyRestaurants(
            @RequestHeader("userId") String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("Received userId: {}", userId);

        RestaurantPageInfoResponse response = restaurantService.getMyRestaurants(userId, page, size);
        return ApiResponse.success("", response, "내 가게 목록 조회 성공");
    }

}

