package com.sparta.balloondelivery.order.controller;

import com.sparta.balloondelivery.order.dto.OrderRequest;
import com.sparta.balloondelivery.order.service.OrderService;
import com.sparta.balloondelivery.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    public ApiResponse<?> createOrder(
            @RequestHeader(value = "X-User-Id") Long userId,
            @RequestBody OrderRequest.CreateOrder createOrder) {
        var orderId = orderService.createOrder(userId, createOrder);
        return ApiResponse.success(HttpStatus.OK.name(), orderId);
    }

    /**
     * 주문 조회 API
     */
    @GetMapping
    public ApiResponse<?> getMyOrders(
            @RequestHeader(value = "X-User-Id") Long userId,
            Pageable pageable) {
        var response = orderService.getMyOrders(userId, pageable);
        return ApiResponse.success(HttpStatus.OK.name(), response);
    }

    /**
     * 가게 주문 조회 API
     */
    @GetMapping("/{restaurantId}/restaurant")
    public ApiResponse<?> getRestaurantOrders(
            @RequestHeader(value = "X-User-Id") Long userId,
            @PathVariable UUID restaurantId,
            Pageable pageable
    ) {
        var response = orderService.getRestaurantOrders(userId, restaurantId, pageable);
        return ApiResponse.success(HttpStatus.OK.name(), response);
    }

    /**
     * 주문 상세 조회 API
     */
    @GetMapping("/{orderId}")
    public ApiResponse<?> getOrderDetail(
            @RequestHeader(value = "X-User-Id") Long userId,
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
    @PutMapping("/{orderId}/cancel")
    public ApiResponse<?> cancelOrder(
            @RequestHeader(value = "X-User-Id") Long userId,
            @RequestHeader(value = "X-User-Role") String role,
            @PathVariable UUID orderId) {
        orderService.cancelOrder(userId, role, orderId);
        return ApiResponse.success(HttpStatus.OK.name(), null, "주문이 취소되었습니다.");
    }

    /**
     * 주문 검색 API
     */
    @GetMapping("/search")
    public ApiResponse<?> searchOrder(
            @RequestHeader(value = "X-User-Id") Long userId,
            @RequestParam String restaurantName,
            Pageable pageable
    ) {
        var response = orderService.searchOrder(userId, restaurantName, pageable);
        return ApiResponse.success(HttpStatus.OK.name(), response);
    }

    /**
     * 주문 요청 사항 수정 API
     */
    @PutMapping("/{orderId}")
    public ApiResponse<?> updateOrder(
            @RequestHeader(value = "X-User-Id") Long userId,
            @PathVariable UUID orderId,
            @RequestBody OrderRequest.UpdateOrder updateOrder) {
        orderService.updateOrder(userId, orderId, updateOrder);
        return ApiResponse.success(HttpStatus.OK.name(), null, "주문이 수정되었습니다.");
    }

    /**
     * 주문 내역 삭제 API
     */
    @DeleteMapping("/{orderId}")
    public ApiResponse<?> deleteOrder(
            @RequestHeader(value = "X-User-Id") Long userId,
            @PathVariable UUID orderId) {
        orderService.deleteOrder(userId, orderId);
        return ApiResponse.success(HttpStatus.OK.name(), null, "주문이 삭제되었습니다.");
    }

    /**
     * 주문 상태 변경 API ( 주문 수락 )
     */
    @PutMapping("/{orderId}/status")
    public ApiResponse<?> updateOrderStatus(
            @RequestHeader(value = "X-User-Id") Long userId,
            @PathVariable UUID orderId
    ) {
        orderService.updateOrderStatus(orderId);
        return ApiResponse.success(HttpStatus.OK.name(), null, "주문 상태가 변경되었습니다.");
    }

}
