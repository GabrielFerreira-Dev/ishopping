package com.github.ishopping.order.service;

import com.github.ishopping.order.client.BankServiceClient;
import com.github.ishopping.order.model.Order;
import com.github.ishopping.order.repository.OrderItemRepository;
import com.github.ishopping.order.repository.OrderRepository;
import com.github.ishopping.order.validator.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderValidator orderValidator;
    private final BankServiceClient bankServiceClient;

    @Transactional
    public Order createOrder(Order order) {
        orderValidator.validate(order);
        persistOrder(order);
        sendPaymentRequest(order);
        return order;
    }

    private void sendPaymentRequest(Order order) {
        var paymentKey = bankServiceClient.requestPayment(order);
        order.setPaymentKey(paymentKey);
    }

    private void persistOrder(Order order) {
        orderRepository.save(order);
        orderItemRepository.saveAll(order.getItens());
    }

}
