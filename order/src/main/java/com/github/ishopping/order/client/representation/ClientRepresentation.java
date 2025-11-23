package com.github.ishopping.order.client.representation;

public record ClientRepresentation(
        Long id,
        String name,
        String cpf,
        String address,
        String numberAddress,
        String district,
        String email,
        String phoneNumber
) {
}
