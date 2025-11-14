package com.github.ishopping.order.controller.mapper;

import com.github.ishopping.order.controller.dto.NewOrderDTO;
import com.github.ishopping.order.controller.dto.OrderItemDTO;
import com.github.ishopping.order.model.Order;
import com.github.ishopping.order.model.OrderItem;
import com.github.ishopping.order.model.enums.OrderStatus;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderItemMapper ORDER_ITEM_MAPPER = Mappers.getMapper(OrderItemMapper.class);

    @Mapping(source = "itens", target = "itens", qualifiedByName = "mapItens")
    @Mapping(source = "paymentData", target = "paymentData")
    Order map(NewOrderDTO dto);

    @Named("mapItens")
    default List<OrderItem> mapItens(List<OrderItemDTO> dtos) {
        return dtos.stream().map(ORDER_ITEM_MAPPER::map).toList();
    }

    @AfterMapping
    default void afterMapping(@MappingTarget Order order) {
        order.setStatus(OrderStatus.PLACED);
        order.setDataOrder(LocalDateTime.now());

        var total = calculateTotal(order);

        order.setTotal(total);

        order.getItens().forEach(item -> item.setOrder(order));
    }

    private static BigDecimal calculateTotal(Order order) {
        return order.getItens().stream().map(item ->
            item.getUnitValue().multiply( BigDecimal.valueOf(item.getQuantity()) )
        ).reduce(BigDecimal.ZERO, BigDecimal::add).abs();
    }
}
