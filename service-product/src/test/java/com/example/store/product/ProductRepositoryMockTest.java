package com.example.store.product;

import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.store.product.model.Category;
import com.example.store.product.model.Product;
import com.example.store.product.repository.ProductRepository;

@DataJpaTest
class ProductRepositoryMockTest {

	@Autowired
	private ProductRepository productRepository;
	
	@Test
	 void whenFindByCatgory_thneReturnListProducts() {
		Product product1 = Product.builder()
				.name("computer")
				.category(Category.builder().id(1L).build())
				.description("")
				.stock(Double.parseDouble("10"))
				.price(Double.parseDouble("100.00"))
				.status("CREATE")
				.createAt(new Date()).build();
		
		productRepository.save(product1);
		
		List<Product> founds = productRepository.findByCategory(product1.getCategory());
		
		Assertions.assertThat(founds.size()).isEqualTo(1);
	}
	
}
