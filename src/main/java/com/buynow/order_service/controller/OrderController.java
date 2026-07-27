package com.buynow.order_service.controller;
import com.buynow.order_service.dto.request.CreateOrderRequest;
import com.buynow.order_service.dto.request.UpdateOrderStatusRequest;
import com.buynow.order_service.dto.response.OrderResponse;
import com.buynow.order_service.payload.ApiResponse;
import com.buynow.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> placeOrder(@Valid @RequestBody CreateOrderRequest request) {

        OrderResponse order = orderService.placeOrder(request.getUserId());
        return ResponseEntity.ok(new ApiResponse<>("Order placed successfully", order));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(@PathVariable Long orderId) {

        OrderResponse order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(new ApiResponse<>("Order retrieved successfully", order));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrdersByUserId(
            @PathVariable Long userId) {

        List<OrderResponse> orders = orderService.getOrdersByUserId(userId);

        return ResponseEntity.ok(new ApiResponse<>("User orders retrieved successfully", orders));
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {

        List<OrderResponse> orders = orderService.getAllOrders();
        return ResponseEntity.ok(new ApiResponse<>("All orders retrieved successfully", orders));
    }


    @PutMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody UpdateOrderStatusRequest request) {

        OrderResponse order = orderService.updateOrderStatus(orderId, request.getStatus());
        return ResponseEntity.ok(new ApiResponse<>("Order status updated successfully", order));
    }


    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<OrderResponse>> cancelOrder(
            @PathVariable Long orderId) {

        OrderResponse order = orderService.cancelOrder(orderId);

        return ResponseEntity.ok(new ApiResponse<>("Order cancelled successfully", order));
    }
}
