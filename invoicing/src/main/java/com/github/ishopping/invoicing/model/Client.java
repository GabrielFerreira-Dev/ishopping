package com.github.ishopping.invoicing.model;

public record Client(
        String name, String cpf, String address, String numberAddress,
        String district, String email, String phoneNumber
) {
}
