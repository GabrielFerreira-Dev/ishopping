package com.github.ishopping.logistic.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ishopping.logistic.service.SendOrderService;
import com.github.ishopping.logistic.subscriber.representation.UpdateInvoiceRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class InvoicingSubscriber {

    private final ObjectMapper objectMapper;
    private final SendOrderService service;

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${ishopping.config.kafka.topics.orders-invoiced}")
    public void listen(String json) {
        log.info("Receiving order for shipping: {}", json);

        try {
            var representation =
                    objectMapper.readValue(json, UpdateInvoiceRepresentation.class);
            service.send(representation.id(), representation.urlInvoice());

            log.info("Order processed successfully! Id: {}", representation.id());
        } catch (Exception e) {
            log.error("Error preparing order for shipping", e);
        }


    }


}
