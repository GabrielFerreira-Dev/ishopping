package com.github.ishopping.order.controller;

import com.github.ishopping.order.controller.dto.ReceptionCallbackPaymentDTO;
import com.github.ishopping.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/callback-payment")
@RequiredArgsConstructor
public class ReceptionCallbackPaymentController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Object> updateStatusPayment(
            @RequestBody ReceptionCallbackPaymentDTO body,
            @RequestHeader(required = true, name = "apiKey") String apiKey
            ) {

        orderService.updateStatusPayment(body.id(), body.paymentKey(), body.status(), body.observation());
        return ResponseEntity.ok().build();

    }

}
