package com.sparta.balloondelivery.restaurant.controller;

import com.sparta.balloondelivery.exception.BaseException;
import com.sparta.balloondelivery.restaurant.dto.request.RestaurantCreateRequest;
import com.sparta.balloondelivery.restaurant.dto.request.RestaurantUpdateRequest;
import com.sparta.balloondelivery.restaurant.dto.response.*;
import com.sparta.balloondelivery.restaurant.service.RestaurantSearchService;
import com.sparta.balloondelivery.restaurant.service.RestaurantService;
import com.sparta.balloondelivery.util.ApiResponse;
import com.sparta.balloondelivery.util.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantSearchService restaurantSearchService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, RestaurantSearchService restaurantSearchService) {
        this.restaurantService = restaurantService;
        this.restaurantSearchService = restaurantSearchService;
    }

    private void checkUserRole(String userRole, Set<String> allowRole) {
        if (!allowRole.contains(userRole)) {
            throw new BaseException(ErrorCode.NO_PERMISSION);
        }
    }

    /**
     * 가게 등록 API
     */
    @PostMapping("/restaurants")
    public ApiResponse<RestaurantCreateResponse> createRestaurant(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Role") String userRole,
            @RequestBody RestaurantCreateRequest request
    ) {
        checkUserRole(userRole, Set.of("MASTER", "MANAGER", "OWNER"));
        RestaurantCreateResponse response = restaurantService.createRestaurant(userId, request);
        return ApiResponse.success("OK", response, "가게 등록 성공");
    }

    /**
     * 가게 조회 API
     */
    @GetMapping("/restaurants/{restaurant_id}")
    public ApiResponse<RestaurantInfoResponse> getRestaurantInfo(
            @RequestHeader("X-User-Role") String userRole,
            @PathVariable("restaurant_id") UUID id
    ) {
        checkUserRole(userRole, Set.of("MASTER", "MANAGER", "OWNER", "USER"));
        RestaurantInfoResponse response = restaurantService.getRestaurantInfo(id);
        return ApiResponse.success("OK", response, "가게 정보 조회 성공");
    }

    /**
     * 가게 정보 수정 API
     */
    @PutMapping("/restaurants/{restaurant_id}")
    public ApiResponse<RestaurantUpdateResponse> updateRestaurant(
            @RequestHeader("X-User-Role") String userRole,
            @RequestHeader("X-User-Name") String username,
            @PathVariable("restaurant_id") UUID id,
            @RequestBody RestaurantUpdateRequest request
    ) {
        checkUserRole(userRole, Set.of("MASTER", "MANAGER", "OWNER"));
        RestaurantUpdateResponse response = restaurantService.updateRestaurant(id, request, username);
        return ApiResponse.success("OK", response, "가게 정보 수정 성공");
    }

    /**
     * 가게 전체 조회 API
     */
    @GetMapping("/restaurants")
    public ApiResponse<RestaurantPageInfoResponse> getAllRestaurantInfo(
            @RequestHeader("X-User-Role") String userRole,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        checkUserRole(userRole, Set.of("MANAGER", "OWNER", "USER"));
        RestaurantPageInfoResponse response = restaurantService.getAllRestaurantInfo(page, size);
        return ApiResponse.success("OK", response, "가게 목록 조회 성공");
    }

    /**
     * 내 가게 목록 조회 API
     */
    @GetMapping("/restaurants/users")
    public ApiResponse<RestaurantPageInfoResponse> getMyRestaurantInfo(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Role") String userRole,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        checkUserRole(userRole, Set.of("MASTER", "MANAGER", "OWNER"));
        RestaurantPageInfoResponse response = restaurantService.getMyRestaurantInfo(userId, page, size);
        return ApiResponse.success("OK", response, "내 가게 목록 조회 성공");
    }

    /**
     * 가게 숨김/표시 API
     */
    @PatchMapping("/restaurants/{restaurant_id}/hide")
    public ApiResponse<RestaurantInfoResponse> hideRestaurant(
            @RequestHeader("X-User-Role") String userRole,
            @PathVariable("restaurant_id") UUID id
    ) {
        checkUserRole(userRole, Set.of("MASTER", "MANAGER", "OWNER"));
        RestaurantInfoResponse response = restaurantService.hideRestaurant(id);
        return ApiResponse.success("OK", response, "가게 표시 상태가 변경되었습니다.");
    }

    /**
     * 레스토랑 검색 API
     */
    @GetMapping("/search")
    public ApiResponse<List<RestaurantInfoResponse>> searchRestaurants(
            @RequestHeader("X-User-Role") String userRole,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        checkUserRole(userRole, Set.of("MASTER", "MANAGER", "OWNER", "USER"));

        List<RestaurantInfoResponse> response = restaurantSearchService.searchRestaurants(name, page, size, sortBy);
        return ApiResponse.success("OK", response, "레스토랑 검색 성공");
    }

    /**
     * 가게 삭제 API (소프트 삭제)
     */
    @DeleteMapping("/restaurants/{restaurant_id}")
    public ApiResponse<RestaurantDeletedResponse> deleteRestaurant(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Role") String userRole,
            @PathVariable("restaurant_id") UUID id
    ) {
        checkUserRole(userRole, Set.of("MASTER", "MANAGER", "OWNER"));
        RestaurantDeletedResponse response = restaurantService.deleteRestaurant(id, userId);
        return ApiResponse.success("OK", response, "가게 삭제 성공");
    }

}
