package com.buynow.order_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {

    private Long id;

    private Long userId;

    private BigDecimal totalAmount;

    private List<CartItemResponse> cartItems;
}