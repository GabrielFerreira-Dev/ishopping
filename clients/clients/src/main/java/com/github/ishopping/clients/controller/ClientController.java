package com.github.ishopping.clients.controller;

import com.github.ishopping.clients.model.Client;
import com.github.ishopping.clients.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Client prod = clientService.getById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Nonexistent client"
        ));

        clientService.delete(prod);
        return ResponseEntity.noContent().build();
    }
}
