package com.github.ishopping.invoicing.subscriber.representation;

import java.math.BigDecimal;

public record DetailItemOrderRepresentation(
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal unitValue
) {
    public BigDecimal getTotal() {
        return unitValue.multiply(BigDecimal.valueOf(quantity));
    }
}
