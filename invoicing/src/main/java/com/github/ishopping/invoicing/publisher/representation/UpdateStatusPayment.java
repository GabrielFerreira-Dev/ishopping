package com.github.ishopping.invoicing.publisher.representation;

public record UpdateStatusPayment(Long id, OrderStatus status, String urlInvoice) {
}
