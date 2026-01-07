package com.github.ishopping.invoicing.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ishopping.invoicing.GeneratorInvoiceService;
import com.github.ishopping.invoicing.mapper.OrderMapper;
import com.github.ishopping.invoicing.model.Order;
import com.github.ishopping.invoicing.subscriber.representation.DetailOrderRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderPaidSubscriber {

    private final ObjectMapper mapper;
    private final GeneratorInvoiceService service;
    private final OrderMapper orderMapper;

    @KafkaListener(groupId = "ishopping-invoice", topics = "${ishopping.config.kafka.topics.orders-paid}")
    public void listen(String json) {
        try {
            log.info("Receiving order to invoice: {}", json);
            var representation = mapper.readValue(json, DetailOrderRepresentation.class);
            Order order = orderMapper.map(representation);
            service.generate(order);
        } catch (Exception e) {
            log.error("Error consuming topic of paid orders");
        }

    }

}
