package com.github.ishopping.order.client;

import com.github.ishopping.order.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class BankServiceClient {

    public String requestPayment(Order order) {
        log.info("Requesting payment of ID Order: {}", order.getId());
        return UUID.randomUUID().toString();

    }
}
