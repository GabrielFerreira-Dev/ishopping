package com.github.ishopping.order.service;

import com.github.ishopping.order.model.enums.OrderStatus;
import com.github.ishopping.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateOrderStatusService {

    private final OrderRepository  repository;

    @Transactional
    public void updateStatus(Long id, OrderStatus status, String urlInvoice, String trackingCode) {
        repository.findById(id).ifPresent(order -> {
            order.setStatus(status);
            order.setUrlInvoice(urlInvoice);
            order.setTrackingCode(trackingCode);
        });
    }
}