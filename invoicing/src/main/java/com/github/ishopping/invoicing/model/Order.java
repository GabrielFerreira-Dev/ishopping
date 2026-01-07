package com.github.ishopping.invoicing.model;

import java.math.BigDecimal;
import java.util.List;

public record Order(
        Long id, Client client, String data, BigDecimal total, List<OrderItem> items
) {
}
