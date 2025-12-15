package com.github.ishopping.order.publisher.representation;

import com.github.ishopping.order.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public record DetailOrderRepresentation(
        Long id,
        Long clientId,
        String name,
        String cpf,
        String address,
        String numberAddress,
        String district,
        String email,
        String phoneNumber,
        String dataOrder,
        BigDecimal total,
        OrderStatus status,
        List<DetailItemOrderRepresentation> itens
) {
}
