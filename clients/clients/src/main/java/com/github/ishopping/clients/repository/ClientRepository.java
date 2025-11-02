package com.github.ishopping.clients.repository;

import com.github.ishopping.clients.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
