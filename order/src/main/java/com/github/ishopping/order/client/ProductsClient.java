package com.github.ishopping.order.client;

import com.github.ishopping.order.client.representation.ProductRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "products", url = "${ishopping.orders.clients.products.url}")
public interface ProductsClient {

    @GetMapping("{id}")
    ResponseEntity<ProductRepresentation> getData(@PathVariable("id") Long id);
}
