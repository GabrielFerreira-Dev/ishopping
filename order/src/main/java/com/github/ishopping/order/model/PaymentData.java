package com.github.ishopping.order.model;

import com.github.ishopping.order.model.enums.PaymentType;
import lombok.Data;

@Data
public class PaymentData {
    String data;
    PaymentType paymentType;
}
