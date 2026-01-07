package com.github.ishopping.invoicing.model;

import java.math.BigDecimal;

public record OrderItem(
        Long id, String description, BigDecimal unitValue, Integer quantity, BigDecimal total
) {
}
