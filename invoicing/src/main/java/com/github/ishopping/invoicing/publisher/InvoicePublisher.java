package com.github.ishopping.invoicing.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ishopping.invoicing.model.Order;
import com.github.ishopping.invoicing.publisher.representation.OrderStatus;
import com.github.ishopping.invoicing.publisher.representation.UpdateStatusPayment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class InvoicePublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${ishopping.config.kafka.topics.orders-invoiced}")
    private String topic;

    public void publish(Order order, String urlInvoice) {
        try {
            var representation = new UpdateStatusPayment(
                    order.id(), OrderStatus.INVOICED, urlInvoice);
            String json = objectMapper.writeValueAsString(representation);
            kafkaTemplate.send(topic, "data", json);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

}
