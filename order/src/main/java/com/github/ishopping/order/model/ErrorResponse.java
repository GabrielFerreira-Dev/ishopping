package com.github.ishopping.order.model;

public record ErrorResponse(String message, String field, String error) {
}
