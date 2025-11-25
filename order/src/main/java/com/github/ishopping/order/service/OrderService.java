package com.github.ishopping.order.service;

import com.github.ishopping.order.client.BankServiceClient;
import com.github.ishopping.order.model.Order;
import com.github.ishopping.order.model.PaymentData;
import com.github.ishopping.order.model.enums.OrderStatus;
import com.github.ishopping.order.model.enums.PaymentType;
import com.github.ishopping.order.model.exception.ItemNotFoundException;
import com.github.ishopping.order.repository.OrderItemRepository;
import com.github.ishopping.order.repository.OrderRepository;
import com.github.ishopping.order.validator.OrderValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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

    public void updateStatusPayment(Long idOrder, String paymentKey, boolean success, String observation) {
        var orderFinded = orderRepository.findByIdAndPaymentKey(idOrder, paymentKey);

        if(orderFinded.isEmpty()) {
            var msg = String.format("Order not found for ID %d and payment key %s", idOrder, paymentKey);
            log.error(msg);
            return;
        }

        Order order = orderFinded.get();

        if(success) {
            order.setStatus(OrderStatus.PAID);
        } else {
            order.setStatus(OrderStatus.ERROR_PAYMENT);
            order.setObservation(observation);
        }
        orderRepository.save(order);
    }

    @Transactional
    public void addNewPayment(Long id, String data, PaymentType paymentType) {
        var orderFind = orderRepository.findById(id);

        if (orderFind.isEmpty()) {
            throw new ItemNotFoundException("Order not found by informed ID.");
        }

        var order = orderFind.get();
        PaymentData paymentData = new PaymentData();
        paymentData.setPaymentType(paymentType);
        paymentData.setData(data);

        order.setPaymentData(paymentData);

        order.setStatus(OrderStatus.PLACED);
        order.setObservation("New payment placed, waiting processing.");

        String newPaymentKey = bankServiceClient.requestPayment(order);
        order.setPaymentKey(newPaymentKey);

        orderRepository.save(order);
    }
}
