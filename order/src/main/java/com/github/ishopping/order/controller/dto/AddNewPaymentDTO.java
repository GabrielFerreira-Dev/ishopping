package com.github.ishopping.order.controller.dto;

import com.github.ishopping.order.model.enums.PaymentType;

public record AddNewPaymentDTO(Long id, String data, PaymentType paymentType) {
}
