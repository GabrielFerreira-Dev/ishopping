package com.github.ishopping.order.controller.dto;

import java.math.BigDecimal;

public record OrderItemDTO(
        Long productId, Integer quantity, BigDecimal unitValue) {
}
