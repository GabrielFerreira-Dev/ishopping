package com.github.ishopping.clients.controller;

import com.github.ishopping.clients.model.Client;
import com.github.ishopping.clients.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<Client> save(@RequestBody Client client) {
        clientService.save(client);
        return ResponseEntity.ok(client);
    }

    @GetMapping("{id}")
    public ResponseEntity<Client> getById(@PathVariable("id") Long id) {
        return clientService
                .getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
