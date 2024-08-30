package com.sparta.balloondelivery.order.controller;

import com.sparta.balloondelivery.order.dto.OrderRequest;
import com.sparta.balloondelivery.order.service.OrderService;
import com.sparta.balloondelivery.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    /**
     * 주문 생성 API
     */
    @PostMapping
    public ApiResponse<UUID> createOrder(
            @RequestHeader(value = "X-User-Id") String userId,
            @RequestBody OrderRequest.CreateOrder createOrder) {
        var orderId = orderService.createOrder(userId, createOrder);
        return ApiResponse.success(HttpStatus.OK.name(), orderId);
    }

    /**
     * 주문 조회 API
     */
    @GetMapping
    public ApiResponse<?> getMyOrders(
            @RequestHeader(value = "X-User-Id") String userId) {
        var response = orderService.getMyOrders(userId);
        return ApiResponse.success(HttpStatus.OK.name(), response);
    }

    /**
     * 가게 주문 조회 API
     */
    @GetMapping("/{restaurantId}")
    public ApiResponse<?> getRestaurantOrders(
            @RequestHeader(value = "X-User-Id") String userId,
            @PathVariable UUID restaurantId) {
        var response = orderService.getRestaurantOrders(userId, restaurantId);
        return ApiResponse.success(HttpStatus.OK.name(), response);
    }

    /**
     * 주문 상세 조회 API
     */
    @GetMapping("/{orderId}")
    public ApiResponse<?> getOrderDetail(
            @RequestHeader(value = "X-User-Id") String userId,
            @RequestHeader(value = "X-User-Role") String role,
            @PathVariable UUID orderId) {
        var response = orderService.getOrderDetail(userId, orderId);
        return ApiResponse.success(HttpStatus.OK.name(), response);
    }

    /**
     * 주문 취소 API
     * 고객이 주문 취소할 때는 주문 상태가 주문 대기 상태일 때만 가능하다.
     * 가게가 주문 취소할 때는 주문 상태가 주문 대기 상태이거나 조리중일 때 가능하다.
     */
    @PutMapping("/{orderId}")
    public ApiResponse<?> cancelOrder(
            @RequestHeader(value = "X-User-Id") String userId,
            @RequestHeader(value = "X-User-Role") String role,
            @PathVariable UUID orderId) {
        orderService.cancelOrder(userId, role, orderId);
        return ApiResponse.success(HttpStatus.OK.name(), null, "주문이 취소되었습니다.");
    }


}
