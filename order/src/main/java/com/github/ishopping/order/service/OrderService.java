package com.github.ishopping.order.service;

import com.github.ishopping.order.model.Order;
import com.github.ishopping.order.repository.OrderItemRepository;
import com.github.ishopping.order.repository.OrderRepository;
import com.github.ishopping.order.validator.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderValidator orderValidator;


    public Order createOrder(Order order) { return null; }

}
