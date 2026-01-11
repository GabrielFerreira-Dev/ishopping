package com.github.ishopping.logistic.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ishopping.logistic.model.UpdateSendOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SendOrderPublisher {

    private final ObjectMapper mapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${ishopping.config.kafka.topics.orders-shipped}")
    private String topic;

    public void send(UpdateSendOrder updateSendOrder) {
        try {
            var json = mapper.writeValueAsString(updateSendOrder);
            kafkaTemplate.send(topic, "data", json);
            log.info("Order sent published {}, tracking code: {}", updateSendOrder.id(), updateSendOrder.trackingCode());
        } catch (Exception e) {
            log.error("Error publishing order submission", e);
        }
    }
}
