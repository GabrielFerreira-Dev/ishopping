package com.github.ishopping.order.publisher;

import com.github.ishopping.order.model.Order;
import com.github.ishopping.order.publisher.representation.DetailOrderRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DetailOrderMapper {

    @Mapping(source = "id" , target = "id")
    @Mapping(source = "clientId" , target = "clientId")
    @Mapping(source = "dataClient.name" , target = "name")
    @Mapping(source = "dataClient.cpf" , target = "cpf")
    @Mapping(source = "dataClient.address" , target = "address")
    @Mapping(source = "dataClient.numberAddress" , target = "numberAddress")
    @Mapping(source = "dataClient.district" , target = "district")
    @Mapping(source = "dataClient.email" , target = "email")
    @Mapping(source = "dataClient.phoneNumber" , target = "phoneNumber")
    @Mapping(source = "dataOrder" , target = "dataOrder", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "total" , target = "total")
    @Mapping(source = "status" , target = "status")
    @Mapping(source = "itens" , target = "itens")
    DetailOrderRepresentation map(Order order);
}