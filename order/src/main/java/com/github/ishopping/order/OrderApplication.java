package com.github.ishopping.order;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class OrderApplication {

	//@Bean
	public CommandLineRunner commandLineRunner(KafkaTemplate<String, String> template) {
		return args -> template.send("ishopping.orders-paid", "data", "{ json test }");
	}

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

}
