package com.github.ishopping.order.repository;

import com.github.ishopping.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByIdAndPaymentKey(Long id, String paymentKey);
}
