package com.github.ishopping.invoicing.mapper;

import com.github.ishopping.invoicing.model.Client;
import com.github.ishopping.invoicing.model.Order;
import com.github.ishopping.invoicing.model.OrderItem;
import com.github.ishopping.invoicing.subscriber.representation.DetailItemOrderRepresentation;
import com.github.ishopping.invoicing.subscriber.representation.DetailOrderRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public Order map(DetailOrderRepresentation representation) {
        Client client = new Client(
                representation.name(), representation.cpf(), representation.address(), representation.numberAddress(),
                representation.district(), representation.email(), representation.phoneNumber()
        );

        List<OrderItem> items = representation.itens()
                .stream().map(this::mapItem).toList();

        return new Order(representation.id(), client, representation.dataOrder(), representation.total(), items);
    }

    private OrderItem mapItem(DetailItemOrderRepresentation representation) {
        return new OrderItem(
                representation.productId(), representation.productName(), representation.unitValue(), representation.quantity()
        );
    }
}

