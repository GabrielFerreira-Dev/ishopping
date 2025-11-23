package com.github.ishopping.order.validator;

import com.github.ishopping.order.client.ClientsClient;
import com.github.ishopping.order.client.ProductsClient;
import com.github.ishopping.order.client.representation.ClientRepresentation;
import com.github.ishopping.order.client.representation.ProductRepresentation;
import com.github.ishopping.order.model.Order;
import com.github.ishopping.order.model.OrderItem;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderValidator {

    private final ProductsClient productsClient;
    private final ClientsClient clientsClient;

    public void validate(Order order) {
        Long clientId = order.getClientId();
        validateClient(clientId);
        order.getItens().forEach(this::validateItem);
    }

    private void validateClient(Long id) {
        try {
            ResponseEntity<ClientRepresentation> response = clientsClient.getData(id);
            ClientRepresentation client = response.getBody();
            log.info("Client ID {} found: {}", client.id(), client.name());
        } catch (FeignException.NotFound e) {
            log.error("Client not found");
        }
    }

    private void validateItem(OrderItem item) {
        try {
            ResponseEntity<ProductRepresentation> response = productsClient.getData(item.getProductId());
            ProductRepresentation product = response.getBody();
            log.info("Product ID {} found: {}", product.id(), product.name());
        } catch (FeignException.NotFound e) {
            log.error("Product not found");
        }
    }

}
