package com.github.ishopping.order.service;

import com.github.ishopping.order.client.BankServiceClient;
import com.github.ishopping.order.client.ClientsClient;
import com.github.ishopping.order.client.ProductsClient;
import com.github.ishopping.order.client.representation.ClientRepresentation;
import com.github.ishopping.order.client.representation.ProductRepresentation;
import com.github.ishopping.order.model.Order;
import com.github.ishopping.order.model.OrderItem;
import com.github.ishopping.order.model.PaymentData;
import com.github.ishopping.order.model.enums.OrderStatus;
import com.github.ishopping.order.model.enums.PaymentType;
import com.github.ishopping.order.model.exception.ItemNotFoundException;
import com.github.ishopping.order.publisher.representation.PaymentPublisher;
import com.github.ishopping.order.repository.OrderItemRepository;
import com.github.ishopping.order.repository.OrderRepository;
import com.github.ishopping.order.validator.OrderValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderValidator orderValidator;
    private final BankServiceClient bankServiceClient;
    private final ClientsClient apiClients;
    private final ProductsClient apiProducts;
    private final PaymentPublisher paymentPublisher;

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
            prepareAndPublishPaidOrder(order);
        } else {
            order.setStatus(OrderStatus.ERROR_PAYMENT);
            order.setObservation(observation);
        }
        orderRepository.save(order);
    }

    private void prepareAndPublishPaidOrder(Order order) {
        order.setStatus(OrderStatus.PAID);
        loadDataClient(order);
        loadItensOrder(order);
        paymentPublisher.publish(order);
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

    public Optional<Order> loadCompleteDataOrder(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        order.ifPresent(this::loadDataClient);
        order.ifPresent(this::loadItensOrder);

        return order;
    }

    private void loadDataClient(Order order) {
        Long clientId = order.getClientId();
        ResponseEntity<ClientRepresentation> response = apiClients.getData(clientId);
        order.setDataClient(response.getBody());
    }

    private void loadItensOrder(Order order) {
        List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
        order.setItens(orderItems);
        order.getItens().forEach(this::loadProductsData);

    }

    private void loadProductsData(OrderItem orderItem) {
        Long id = orderItem.getProductId();
        ResponseEntity<ProductRepresentation> response = apiProducts.getData(id);
        orderItem.setProductName(response.getBody().name());
    }
}