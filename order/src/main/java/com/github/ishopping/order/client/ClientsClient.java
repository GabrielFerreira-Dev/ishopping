package com.github.ishopping.order.client;

import com.github.ishopping.order.client.representation.ClientRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clients", url = "${ishopping.orders.clients.clients.url}")
public interface ClientsClient {

    @GetMapping("{id}")
    ResponseEntity<ClientRepresentation> getData(@PathVariable("id") Long id);
}
