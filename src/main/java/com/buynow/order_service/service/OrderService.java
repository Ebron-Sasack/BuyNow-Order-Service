package com.buynow.order_service.service;

import com.buynow.order_service.dto.request.UpdateOrderStatusRequest;
import com.buynow.order_service.dto.response.OrderResponse;
import com.buynow.order_service.entity.Order;
import com.buynow.order_service.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder(Long userId);

    OrderResponse getOrderById(Long orderId);

    List<OrderResponse> getOrdersByUserId(Long userId);

    List<OrderResponse> getAllOrders();

    OrderResponse updateOrderStatus(Long orderId, OrderStatus status);

    OrderResponse cancelOrder(Long orderId);

    Order getOrderEntity(Long orderId);
}
