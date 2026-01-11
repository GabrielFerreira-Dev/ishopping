package com.github.ishopping.invoicing.publisher.representation;

public record UpdateOrderStatusPayment(
        Long id,
        OrderStatus status,
        String urlInvoice) {
}
