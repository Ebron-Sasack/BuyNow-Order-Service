package com.buynow.order_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {

    private Long id;

    private Long productId;

    private String productName;

    private String productImage;

    private BigDecimal unitPrice;

    private Integer quantity;

    private BigDecimal totalPrice;
}