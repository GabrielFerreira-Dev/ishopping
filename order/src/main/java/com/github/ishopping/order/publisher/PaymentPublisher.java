package com.github.ishopping.order.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ishopping.order.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentPublisher {

    private final DetailOrderMapper mapper;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${ishopping.config.kafka.topics.orders-paid}")
    private String topic;

    public void publish(Order order) {
        log.info("Publishing payed order {}", order.getId());

        try {
            var representation = mapper.map(order);
            var json = objectMapper.writeValueAsString(representation);
            kafkaTemplate.send(topic, "data", json);
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON.", e);
        } catch (RuntimeException e) {
            log.error("Technic error publishing into order topic", e);
        }

    }
}
