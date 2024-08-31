package com.sparta.balloondelivery.restaurant.controller;

import com.sparta.balloondelivery.restaurant.dto.request.RestaurantCreateRequest;
import com.sparta.balloondelivery.restaurant.dto.request.RestaurantUpdateRequest;
import com.sparta.balloondelivery.restaurant.dto.response.*;
import com.sparta.balloondelivery.restaurant.service.RestaurantSearchService;
import com.sparta.balloondelivery.restaurant.service.RestaurantService;
import com.sparta.balloondelivery.util.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantSearchService restaurantSearchService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, RestaurantSearchService restaurantSearchService) {
        this.restaurantService = restaurantService;
        this.restaurantSearchService = restaurantSearchService;
    }

    /**
     * 가게 등록 API
     */
    @PostMapping("/restaurants")
    public ApiResponse<RestaurantCreateResponse> createRestaurant(
            @RequestHeader("X-User-Id") Long userId,
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
            @PathVariable("restaurant_id") UUID id
    ) {
        RestaurantInfoResponse response = restaurantService.getRestaurantInfo(id);
        return ApiResponse.success("", response, "가게 정보 조회 성공");
    }

    /**
     * 가게 정보 수정 API
     */
    @PutMapping("/restaurants/{restaurant_id}")
    public ApiResponse<RestaurantUpdateResponse> updateRestaurant(
            @PathVariable("restaurant_id") UUID id,
            @RequestBody RestaurantUpdateRequest request
    ) {
        RestaurantUpdateResponse response = restaurantService.updateRestaurant(id, request);
        return ApiResponse.success("", response, "가게 정보 수정 성공");
    }

    /**
     * 가게 전체 조회 API
     */
    @GetMapping("/restaurants")
    public ApiResponse<RestaurantPageInfoResponse> getAllRestaurantInfo(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        RestaurantPageInfoResponse response = restaurantService.getAllRestaurantInfo(page, size);
        return ApiResponse.success("", response, "가게 목록 조회 성공");
    }

    @GetMapping("/restaurants/users")
    public ApiResponse<RestaurantPageInfoResponse> getMyRestaurantInfo(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("Received userId: {}", userId);

        RestaurantPageInfoResponse response = restaurantService.getMyRestaurantInfo(userId, page, size);
        return ApiResponse.success("", response, "내 가게 목록 조회 성공");
    }

    /**
     * 가게 숨김/표시 API
     */
    @PatchMapping("/restaurants/{restaurant_id}/hide")
    public ApiResponse<RestaurantInfoResponse> hideRestaurant(
            @PathVariable("restaurant_id") UUID id
    ) {
        // 서비스에서 상태 변경 및 정보 반환
        RestaurantInfoResponse response = restaurantService.hideRestaurant(id);
        return ApiResponse.success("", response, "가게 표시 상태가 변경되었습니다.");
    }


    /**
     * 가게 검색 API
     */
    @GetMapping("/search")
    public ApiResponse<List<RestaurantInfoResponse>> searchRestaurants(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID locationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<RestaurantInfoResponse> response = restaurantSearchService.searchRestaurants(name, categoryId, locationId, page, size);
        return ApiResponse.success("", response, "가게 검색 성공");
    }

    /**
     * 가게 삭제 API (소프트 삭제)
     */
    @DeleteMapping("/restaurants/{restaurant_id}")
    public ApiResponse<RestaurantDeletedResponse> deleteRestaurant(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable("restaurant_id") UUID id
    ) {
        RestaurantDeletedResponse response = restaurantService.deleteRestaurant(id, userId);
        return ApiResponse.success("", response, "가게 삭제 성공");
    }

}

