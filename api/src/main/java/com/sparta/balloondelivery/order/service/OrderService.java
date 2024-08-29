package com.sparta.balloondelivery.order.service;

import com.sparta.balloondelivery.data.entity.*;
import com.sparta.balloondelivery.data.entity.Order.OrderType;
import com.sparta.balloondelivery.data.repository.*;
import com.sparta.balloondelivery.exception.BaseException;
import com.sparta.balloondelivery.order.dto.OrderItemDto;
import com.sparta.balloondelivery.order.dto.OrderRequest;
import com.sparta.balloondelivery.util.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public UUID createOrder(OrderRequest.CreateOrder createOrder) {

        // TODO: 유저 정보 체크, 가게 정보 체크
        User user = userRepository.findById(createOrder.getUserId())
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
        Restaurant restaurant = restaurantRepository.findById(createOrder.getRestaurantId())
                .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND));

        Order order = Order.builder()
                .user(user)
                .restaurant(restaurant)
                .orderType(OrderType.valueOf(createOrder.getOrderType()))
                // 결제 대기 상태
                .orderStatus(Order.OrderStatus.WAITING_FOR_PAYMENT)
                .request(createOrder.getRequest())
                .build();

        // OrderDetail 넣어주기
        for (OrderItemDto orderItemDto : createOrder.getOrderItems()) {
            Menu menu = menuRepository.findById(orderItemDto.getMenuId())
                    .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND));
            OrderDetail orderDetail = OrderDetail.builder()
                    .menu(menu)
                    .quantity(orderItemDto.getQuantity())
                    .price(((long) menu.getPrice() * orderItemDto.getQuantity()))
                    .build();
            order.addOrderDetail(orderDetail);
        }

        orderRepository.save(order);

        //TODO : order 생성하면서 결제 요청. 결제 성공여부 API 만들어서 결제 내역 받기?
        // 결제 정보 넘겨서 paymentService에서 생성해야되나?
        Payment payment = Payment.builder()
                .order(order)
                .user(user)
                .paymentStatus(Payment.PaymentStatus.REQUESTED)
                .price(order.getTotalPrice())
                .build();

        paymentRepository.save(payment);

        return null;
    }
}
