package com.github.ishopping.invoicing.subscriber.representation;

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
        List<DetailItemOrderRepresentation> itens
) {
}
