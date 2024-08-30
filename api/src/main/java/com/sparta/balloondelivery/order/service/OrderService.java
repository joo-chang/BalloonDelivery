package com.sparta.balloondelivery.order.service;

import com.sparta.balloondelivery.data.entity.*;
import com.sparta.balloondelivery.data.entity.Order.OrderType;
import com.sparta.balloondelivery.data.repository.*;
import com.sparta.balloondelivery.exception.BaseException;
import com.sparta.balloondelivery.order.dto.OrderItemDto;
import com.sparta.balloondelivery.order.dto.OrderRequest;
import com.sparta.balloondelivery.order.dto.OrderResponse;
import com.sparta.balloondelivery.util.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
    public UUID createOrder(String userId, OrderRequest.CreateOrder createOrder) {

        // 유저 정보 체크, 가게 정보 체크
        User user = userRepository.findById(Long.parseLong(userId))
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

        // 결제 요청까지 바로 생성
        Payment payment = Payment.builder()
                .order(order)
                .user(user)
                .paymentStatus(Payment.PaymentStatus.REQUESTED)
                .price(order.getTotalPrice())
                .build();

        paymentRepository.save(payment);

        return order.getId();
    }

    public List<OrderResponse.MyOrderList> getMyOrders(String userId) {
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        List<Order> orders = orderRepository.findByUserId(user.getId());

        return orders.stream()
                .map(OrderResponse.MyOrderList::toDto)
                .toList();
    }

    public List<OrderResponse.RestaurantOrderList> getRestaurantOrders(String userId, UUID restaurantId) {
        userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND));

        List<Order> orders = orderRepository.findByRestaurantIdOrderByCreatedAtDesc(restaurant.getRestaurantId());

        return orders.stream()
                .map(OrderResponse.RestaurantOrderList::toDto)
                .toList();
    }

    public OrderResponse.OrderDetailResponse getOrderDetail(String userId, UUID orderId) {
        userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        Order order = orderRepository.findByOrderIdAndUserId(orderId, Long.parseLong(userId));

        return OrderResponse.OrderDetailResponse.toDto(order);
    }

    public void cancelOrder(String userId, String role, UUID orderId) {
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        Order order = orderRepository.findByOrderIdAndUserId(orderId, user.getId());

        Payment payment = paymentRepository.findByOrderId(order.getId())
                .orElseThrow(() -> new BaseException(ErrorCode.PAYMENT_NOT_FOUND));

        // 권한별 주문 취소 가능 여부 체크
        if (role.equals("USER")) {
            if (order.getOrderStatus() != Order.OrderStatus.WAITING_FOR_PAYMENT) {
                throw new BaseException(ErrorCode.ORDER_CANNOT_BE_CANCELED);
            }
        } else if (role.equals("RESTAURANT")) {
            if (order.getOrderStatus() != Order.OrderStatus.WAITING_FOR_PAYMENT && order.getOrderStatus() != Order.OrderStatus.COOKING) {
                throw new BaseException(ErrorCode.ORDER_CANNOT_BE_CANCELED);
            }
        }

        // 결제 취소 요청을 보냈다고 가정
        boolean isPaymentCanceled = true;

        if (isPaymentCanceled) {
            payment.updatePayment(Payment.PaymentStatus.CANCELED);
        }

        order.updateOrder(Order.OrderStatus.CANCELED);
    }
}
