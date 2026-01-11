package com.github.ishopping.logistic.subscriber.representation;

import com.github.ishopping.logistic.model.OrderStatus;

public record UpdateInvoiceRepresentation(
        Long id,
        OrderStatus status,
        String urlInvoice) {
}