package com.github.ishopping.order.model;

import com.github.ishopping.order.controller.dto.PaymentDataDTO;
import com.github.ishopping.order.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "data_order", nullable = false)
    private LocalDateTime dataOrder;

    @Column(name = "payment_key")
    private String paymentKey;

    @Column(name = "observation")
    private String observation;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "total", precision = 16, scale = 2, nullable = false)
    private BigDecimal total;

    @Column(name = "tracking_code")
    private String trackingCode;

    @Column(name = "url_invoice")
    private String urlInvoice;

    @Transient
    private PaymentData paymentData;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> itens;
}
