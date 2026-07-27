package com.buynow.order_service.service.implementation;

import com.buynow.order_service.dto.response.CartResponse;
import com.buynow.order_service.dto.response.OrderResponse;
import com.buynow.order_service.entity.Order;
import com.buynow.order_service.entity.OrderItem;
import com.buynow.order_service.enums.OrderStatus;
import com.buynow.order_service.exception.ResourceNotFoundException;
import com.buynow.order_service.feign.OrderFeignClient;
import com.buynow.order_service.repository.OrderItemRepository;
import com.buynow.order_service.repository.OrderRepository;
import com.buynow.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final OrderFeignClient orderFeignClient;


    @Override
    public OrderResponse placeOrder(Long userId) {
        CartResponse cart = orderFeignClient.getCartByUserId(userId).getData();
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        Order order = createOrder(cart);

        List<OrderItem> orderItems = createOrderItems(cart, order);
        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        orderFeignClient.clearCart(cart.getId());
        return convertToDto(savedOrder);
    }

    private Order createOrder(CartResponse cart) {

        Order order = new Order();
        order.setUserId(cart.getUserId());
        order.setTotalAmount(cart.getTotalAmount());
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        return order;
    }

    private List<OrderItem> createOrderItems(CartResponse cart, Order order) {

        return cart.getCartItems()
                .stream()
                .map(item -> {

                    OrderItem orderItem = new OrderItem();

                    orderItem.setProductId(item.getProductId());
                    orderItem.setProductName(item.getProductName());
                    orderItem.setProductImage(item.getProductImage());
                    orderItem.setUnitPrice(item.getUnitPrice());
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setTotalPrice(item.getTotalPrice());

                    orderItem.setOrder(order);

                    return orderItem;
                })
                .toList();
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order order = getOrderEntity(orderId);
        return convertToDto(order);
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = getOrderEntity(orderId);

        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now());

        Order updated = orderRepository.save(order);

        return convertToDto(updated);
    }

    @Override
    public OrderResponse cancelOrder(Long orderId) {
        Order order = getOrderEntity(orderId);

        order.setStatus(OrderStatus.CANCELLED);
        order.setUpdatedAt(LocalDateTime.now());

        Order updated = orderRepository.save(order);

        return convertToDto(updated);
    }

    @Override
    public Order getOrderEntity(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with id : " + orderId));
    }

    private OrderResponse convertToDto(Order order){
        return modelMapper.map(order,OrderResponse.class);
    }
}
