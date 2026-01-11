package com.github.ishopping.order.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ishopping.order.service.UpdateOrderStatusService;
import com.github.ishopping.order.subscriber.representation.UpdateOrderStatusRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateOrderStatusSubscriber {

    private final UpdateOrderStatusService service;
    private final ObjectMapper mapper;

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = {
            "${ishopping.config.kafka.topics.orders-invoiced}",
            "${ishopping.config.kafka.topics.orders-shipped}"
    })
    public void receiveUpdate(String json) {
        log.info("Receiving status update: {}", json);
        try {
            var updateStatus = mapper.readValue(json, UpdateOrderStatusRepresentation.class);

            service.updateStatus(
                    updateStatus.id(), updateStatus.status(),
                    updateStatus.urlInvoice(), updateStatus.trackingCode());


            log.info("Success updating order!");
        } catch (Exception e) {
            log.error("Error mapping object from json: {}", e.getMessage());
        }
    }
}
