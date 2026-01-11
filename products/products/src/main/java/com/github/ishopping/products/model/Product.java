package com.github.ishopping.products.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "unit_value", nullable = false, precision = 16, scale = 2)
    private BigDecimal unitValue;

    @Column(name = "active")
    private boolean active;

    @PrePersist
    private void prePersist() {
        setActive(true);
    }
}
