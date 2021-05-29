package com.example.store.product;

import java.util.Date;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.store.product.model.Category;
import com.example.store.product.model.Product;
import com.example.store.product.repository.ProductRepository;
import com.example.store.product.service.ProductServiceImpl;
import com.example.store.product.service.ProductService;

@SpringBootTest
public class ProductServiceMockTest {

	 @Mock
	 private ProductRepository productRepository;
	 
	 private ProductService ProductService;
	 
	 @BeforeEach
	 public void setup() {
		 MockitoAnnotations.initMocks(this);
		 //ProductService = new ProductServiceImpl(productRepository);
		 Product computer = Product.builder()
					.name("computer")
					.category(Category.builder().id(1L).build())
					.description("")
					.stock(Double.parseDouble("12"))
					.price(Double.parseDouble("25.00"))
					.status("CREATE")
					.createAt(new Date()).build();
			
		    Mockito.when(productRepository.findById(1L))
		    .thenReturn(Optional.of(computer));
		    
		    Mockito.when(productRepository.save(computer))
		    .thenReturn(computer);
		 
	 }
	 
	 @Test
	 void whenValidGetID_ThenReturnProduct() {
		 Product product = ProductService.getProduct(1L);
		 Assertions.assertThat(product.getName()).isEqualTo("computer");
	 }
	 
	 @Test
	 void whenValidUpdateStock_ThenReturnNewStock() {
		 Product newStock = ProductService.updateStock(1L,Double.parseDouble("8"));
		 Assertions.assertThat(newStock.getStock()).isEqualTo(20);
	 }
	 
}
