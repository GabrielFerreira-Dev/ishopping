package com.github.ishopping.order.validator;

import com.github.ishopping.order.client.ClientsClient;
import com.github.ishopping.order.client.ProductsClient;
import com.github.ishopping.order.client.representation.ClientRepresentation;
import com.github.ishopping.order.client.representation.ProductRepresentation;
import com.github.ishopping.order.model.Order;
import com.github.ishopping.order.model.OrderItem;
import com.github.ishopping.order.model.exception.ValidationException;
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
            var message = String.format("Client ID %d not found.", id);
            throw new ValidationException("clientId", message);
        }
    }

    private void validateItem(OrderItem item) {
        try {
            ResponseEntity<ProductRepresentation> response = productsClient.getData(item.getProductId());
            ProductRepresentation product = response.getBody();
            log.info("Product ID {} found: {}", product.id(), product.name());
        } catch (FeignException.NotFound e) {
            var message = String.format("Item ID %d not found.", item.getProductId());
            throw new ValidationException("productId", message);
        }
    }

}
