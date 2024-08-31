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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public OrderResponse.CreateOrder createOrder(Long userId, OrderRequest.CreateOrder createOrder) {

        // 유저 정보 체크, 가게 정보 체크
        User user = userRepository.findById(userId)
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

        return OrderResponse.CreateOrder.toDto(order.getId(), payment.getId());
    }

    // 주문 조회
    public List<OrderResponse.MyOrderList> getMyOrders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        List<Order> orders = orderRepository.findByUserId(user.getId());

        return orders.stream()
                .map(OrderResponse.MyOrderList::toDto)
                .toList();
    }

    // 가게 주문 조회
    public List<OrderResponse.RestaurantOrderList> getRestaurantOrders(Long userId, UUID restaurantId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND));

        List<Order> orders = orderRepository.findByRestaurantRestaurantIdAndDeletedYnFalseOrderByCreatedAtDesc(restaurant.getRestaurantId());

        return orders.stream()
                .map(OrderResponse.RestaurantOrderList::toDto)
                .toList();
    }

    public OrderResponse.OrderDetailResponse getOrderDetail(Long userId, UUID orderId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        Order order = orderRepository.findByIdAndUserId(orderId, userId);

        return OrderResponse.OrderDetailResponse.toDto(order);
    }

    public void cancelOrder(Long userId, String role, UUID orderId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        Order order = orderRepository.findByIdAndUserId(orderId, user.getId());

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

    public Page<OrderResponse.MyOrderList> searchOrder(Long userId, String restaurantName, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        Page<Order> orders = orderRepository.searchOrders(user.getId(), restaurantName, pageable);

        return orders.map(OrderResponse.MyOrderList::toDto);
    }

    public void updateOrder(Long userId, UUID orderId, OrderRequest.UpdateOrder updateOrder) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        Order order = orderRepository.findByIdAndUserId(orderId, user.getId());

        order.updateRequest(updateOrder.getRequest());
    }


    public void deleteOrder(Long userId, UUID orderId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        Order order = orderRepository.findByIdAndUserId(orderId, user.getId());

        order.setDeletedYnTrue(user.getUsername());
    }

    public void updateOrderStatus(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BaseException(ErrorCode.ORDER_NOT_FOUND));

        if (order.getOrderStatus() != Order.OrderStatus.WAITING_FOR_ORDER) {
            throw new BaseException(ErrorCode.ORDER_CANNOT_BE_UPDATED);
        }
        order.updateOrder(Order.OrderStatus.COOKING);
    }
}
