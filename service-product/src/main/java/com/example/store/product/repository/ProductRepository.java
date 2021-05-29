package com.example.store.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.store.product.model.Category;
import com.example.store.product.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	public List<Product> findByCategory(Category category);
	
}
