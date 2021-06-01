package com.example.store.shopping.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.store.shopping.model.Customer;

@FeignClient(name = "service-customer", fallback = CustomerHystrixFallbackFactory.class)
public interface CustomerClient {

    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") long id);
}
