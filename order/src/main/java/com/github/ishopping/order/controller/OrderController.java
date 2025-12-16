package com.github.ishopping.order.controller;

import com.github.ishopping.order.controller.dto.AddNewPaymentDTO;
import com.github.ishopping.order.controller.dto.NewOrderDTO;
import com.github.ishopping.order.controller.mapper.OrderMapper;
import com.github.ishopping.order.model.ErrorResponse;
import com.github.ishopping.order.model.Order;
import com.github.ishopping.order.model.exception.ItemNotFoundException;
import com.github.ishopping.order.model.exception.ValidationException;
import com.github.ishopping.order.publisher.DetailOrderMapper;
import com.github.ishopping.order.publisher.representation.DetailOrderRepresentation;
import com.github.ishopping.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final DetailOrderMapper detailOrderMapper;

    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody NewOrderDTO newOrderDTO) {
        try {
            var order = orderMapper.map(newOrderDTO);
            var newOrder = orderService.createOrder(order);
            return ResponseEntity.ok(newOrder.getId());
        } catch (ValidationException e) {
            var error = new ErrorResponse("Validation error", e.getField(), e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("payment")
    public ResponseEntity<Object> addNewPayment(@RequestBody AddNewPaymentDTO dto) {
        try {
            orderService.addNewPayment(dto.id(), dto.data(), dto.paymentType());
            return ResponseEntity.noContent().build();
        } catch (ItemNotFoundException e) {
            var error = new ErrorResponse("Item not found", "id", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<DetailOrderRepresentation> getOrderDetails(@PathVariable Long id) {
        Optional<Order> order = orderService.loadCompleteDataOrder(id);
        return order
                .map(detailOrderMapper::map)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
