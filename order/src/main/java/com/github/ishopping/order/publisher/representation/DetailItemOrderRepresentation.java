package com.github.ishopping.order.publisher.representation;

import java.math.BigDecimal;

public record DetailItemOrderRepresentation(
        Long idProduct,
        String productName,
        Integer quantity,
        BigDecimal unitValue
) {
    public BigDecimal getTotal() {
        return unitValue.multiply(BigDecimal.valueOf(quantity));
    }
}
