package com.github.ishopping.order.controller.dto;

import java.util.List;

public record NewOrderDTO(
        Long clientId, PaymentDataDTO paymentData, List<OrderItemDTO> itens) {
}
