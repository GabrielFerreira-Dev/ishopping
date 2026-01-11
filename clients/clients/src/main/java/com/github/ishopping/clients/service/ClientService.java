package com.github.ishopping.clients.service;

import com.github.ishopping.clients.model.Client;
import com.github.ishopping.clients.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;

    public Client save(Client client) {
        return repository.save(client);
    }

    public Optional<Client> getById(Long id) {
        return repository.findById(id);
    }

    public void delete(Client client) {
        client.setActive(false);
        repository.save(client);
    }
}
