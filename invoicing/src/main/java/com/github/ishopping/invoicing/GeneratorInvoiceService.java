package com.github.ishopping.invoicing;

import com.github.ishopping.invoicing.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GeneratorInvoiceService {

    public void generate(Order order) {
        log.info("Invoice generated for the order {}", order.id());
    }
}
