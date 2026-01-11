package com.github.ishopping.products.service;

import com.github.ishopping.products.model.Product;
import com.github.ishopping.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Product save(Product product) {
        return repository.save(product);
    }

    public Optional<Product> getById(Long id) {
        return repository.findById(id);
    }

    public void delete(Product product) {
        product.setActive(false);
        repository.save(product);
    }
}
