package com.sparta.balloondelivery.order.controller;

import com.sparta.balloondelivery.order.dto.OrderRequest;
import com.sparta.balloondelivery.order.service.OrderService;
import com.sparta.balloondelivery.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // 주문 생성 API
    @PostMapping
    public ApiResponse<UUID> createOrder(@RequestBody OrderRequest.CreateOrder createOrder) {
        var orderId = orderService.createOrder(createOrder);
        return ApiResponse.success(HttpStatus.OK.name(), orderId);
    }
}
