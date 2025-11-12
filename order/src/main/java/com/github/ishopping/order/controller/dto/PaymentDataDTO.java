package com.github.ishopping.order.controller.dto;

import com.github.ishopping.order.model.enums.PaymentType;

public record PaymentDataDTO(
        String data, PaymentType paymentType) {
}
