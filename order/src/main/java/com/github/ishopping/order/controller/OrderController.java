package com.github.ishopping.order.controller;

import com.github.ishopping.order.controller.dto.NewOrderDTO;
import com.github.ishopping.order.controller.mapper.OrderMapper;
import com.github.ishopping.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody NewOrderDTO newOrderDTO) {
        var order = orderMapper.map(newOrderDTO);
        var newOrder = orderService.createOrder(order);
        return ResponseEntity.ok(newOrder.getId());
    }
}
