package com.example.store.product.service;

import java.util.List;

import com.example.store.product.model.Category;
import com.example.store.product.model.Product;

public interface ProductService {

	public List<Product> listAllProduct();
	public Product getProduct(Long id);
	public Product createProduct(Product product);
	public Product updateProduct(Product product);
	public Product deleteProduct(Long id);
	public List<Product> findByCategory(Category category);
	public Product updateStock(Long id, Double quantity);
	
}
