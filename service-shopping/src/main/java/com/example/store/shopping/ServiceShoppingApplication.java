package com.example.store.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class ServiceShoppingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceShoppingApplication.class, args);
	}

}
