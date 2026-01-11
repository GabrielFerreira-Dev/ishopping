package com.github.ishopping.logistic.service;

import com.github.ishopping.logistic.model.OrderStatus;
import com.github.ishopping.logistic.model.UpdateSendOrder;
import com.github.ishopping.logistic.publisher.SendOrderPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class SendOrderService {

    private final SendOrderPublisher publisher;

    public void send(Long id, String urlInvoice) {
        var trackingCode = generateTrackingCode();
        var updateRepresentation =  new UpdateSendOrder(id, OrderStatus.SHIPPED, trackingCode);

        publisher.send(updateRepresentation);
    }

    private String generateTrackingCode() {
        var random = new Random();
        char firstLetter = (char) ('A' + random.nextInt(26));
        char secLetter = (char) ('A' + random.nextInt(26));

        int numbers = 10000000 + random.nextInt(900000000);

        return "" + firstLetter + secLetter + numbers + "BR";

    }


}
