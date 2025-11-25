package com.github.ishopping.order.controller.dto;


public record ReceptionCallbackPaymentDTO(
        Long id,
        String paymentKey,
        boolean status,
        String observation) {
}
