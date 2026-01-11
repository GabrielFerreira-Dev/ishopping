package com.github.ishopping.order.subscriber.representation;

import com.github.ishopping.order.model.enums.OrderStatus;

public record UpdateOrderStatusRepresentation(
        Long id, OrderStatus status, String urlInvoice, String trackingCode
) {
}
