package com.github.ishopping.order.controller.mapper;

import com.github.ishopping.order.controller.dto.OrderItemDTO;
import com.github.ishopping.order.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItem map(OrderItemDTO dto);
}
