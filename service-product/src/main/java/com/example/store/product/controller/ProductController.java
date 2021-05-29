package com.example.store.product.controller;

import com.example.store.product.model.Category;
import com.example.store.product.model.Product;
import com.example.store.product.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/products")
@Slf4j
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping
	public @ResponseBody ResponseEntity<List<Product>> listProduct(@RequestParam(name = "categoryid", required = false) Long category_id) {
		List<Product> products = new ArrayList<>();
		if (category_id == null) {
			 products = productService.listAllProduct();
			 if (products.isEmpty()) {
				 return ResponseEntity.noContent().build();
			 }
		} else {
			products = productService.findByCategory(Category.builder().id(category_id).build());
			 if (products.isEmpty()) {
				 return ResponseEntity.notFound().build();
			 }
		}
		
		if (products.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(products);
		
	}
	
	@GetMapping(value = "/{id}")
	public @ResponseBody ResponseEntity<Product> getProduct(@PathVariable(name = "id") Long idProducto) {
		Product product = productService.getProduct(idProducto);
			 if (product == null) {
				 return ResponseEntity.noContent().build();
			 }

		return ResponseEntity.ok(product);
		
	}
	
	@PostMapping
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult result) 
	{
        if (result.hasErrors()){
	        log.info("hay errores" + result.hasErrors());
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage(result));
        }
		
		Product productCreate = productService.createProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);
		
	}
	
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) 
	{
		product.setId(id);
		Product productDB = productService.updateProduct(product);
		if (productDB == null) {
			 return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(productDB);
		
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) 
	{
		Product productDelete = productService.deleteProduct(id);
		if (productDelete == null) {
			 return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(productDelete);
		
	}
	
	@GetMapping(value = "/{id}/stock")
	public ResponseEntity<Product> updateStockProduct(@PathVariable("id") Long id, @RequestParam(name = "quantity", required = true) Double quantity) 
	{
		Product productDB = productService.updateStock(id, quantity);
		if (productDB == null) {
			 return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(productDB);
		
	}
	
    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info("cadena de errores" + jsonString);
        return jsonString;
    }
	
	
}
