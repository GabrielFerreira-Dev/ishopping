package com.github.ishopping.invoicing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Getter
@Setter
public class OrderItem{
    Long id;
    String name;
    BigDecimal unitValue;
    Integer quantity;
    BigDecimal total;
}
