package com.example.store.shopping.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.store.shopping.model.Product;

@FeignClient(name = "service-product")
@RequestMapping(value = "/products")
public interface ProductClient {

	@GetMapping(value = "/{id}")
	public @ResponseBody ResponseEntity<Product> getProduct(@PathVariable(name = "id") Long idProducto);

	@GetMapping(value = "/{id}/stock")
	public ResponseEntity<Product> updateStockProduct(@PathVariable("id") Long id, @RequestParam(name = "quantity", required = true) Double quantity) ;
	
	
    }