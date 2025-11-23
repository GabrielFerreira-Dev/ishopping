package com.github.ishopping.order.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.github.ishopping.order.client")
public class ClientsConfig {
}
