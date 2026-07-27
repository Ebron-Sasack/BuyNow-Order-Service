package com.buynow.order_service.feign;

import com.buynow.order_service.dto.response.CartResponse;
import com.buynow.order_service.payload.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CART-SERVICE")
public interface OrderFeignClient {

    @GetMapping("${api.prefix}/carts/user/{userId}")
    ApiResponse<CartResponse> getCartByUserId(@PathVariable Long userId);


    @DeleteMapping("${api.prefix}/carts/clear/{userId}")
    void clearCart(@PathVariable Long userId);
}
