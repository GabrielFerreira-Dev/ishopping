package com.github.ishopping.logistic.model;

public record UpdateSendOrder(Long id, OrderStatus status, String trackingCode) {
}
