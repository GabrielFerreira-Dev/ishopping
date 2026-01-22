package com.github.ishopping.products;

import com.github.ishopping.products.controller.ProductController;
import com.github.ishopping.products.model.Product;
import com.github.ishopping.products.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@SpringBootTest
class ProductsApplicationTests {

	@Test void save_returnsOkAndSavesProduct() {
		ProductService productService = Mockito.mock(ProductService.class);
		ProductController controller = new ProductController(productService);
		Product product = new Product();
		product.setId(1L);
		product.setName("Widget");
		product.setUnitValue(new java.math.BigDecimal("9.99"));

		ResponseEntity<Product> response = controller.save(product);

		Mockito.verify(productService).save(product);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertSame(product, response.getBody());
	}

	@Test
	void getById_returnsProductWhenFound() {
		ProductService productService = Mockito.mock(ProductService.class);
		ProductController controller = new ProductController(productService);
		Product product = new Product();
		product.setId(2L);
		product.setName("Gadget");
		product.setUnitValue(new java.math.BigDecimal("19.99"));

		Mockito.when(productService.getById(2L)).thenReturn(Optional.of(product));

		ResponseEntity<Product> response = controller.getById(2L);

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertSame(product, response.getBody());
	}

	@Test
	void getById_returnsNotFoundWhenMissing() {
		ProductService productService = Mockito.mock(ProductService.class);
		ProductController controller = new ProductController(productService);

		Mockito.when(productService.getById(99L)).thenReturn(Optional.empty());

		ResponseEntity<Product> response = controller.getById(99L);

		Assertions.assertEquals(org.springframework.http.HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void delete_removesProductWhenFound() {
		ProductService productService = Mockito.mock(ProductService.class);
		ProductController controller = new ProductController(productService);
		Product product = new Product();
		product.setId(3L);
		product.setName("Pendrive");
		product.setUnitValue(new java.math.BigDecimal("29.99"));

		Mockito.when(productService.getById(3L)).thenReturn(Optional.of(product));

		ResponseEntity<Void> response = controller.delete(3L);

		Mockito.verify(productService).delete(product);
		Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	void delete_throwsWhenProductMissing() {
		ProductService productService = Mockito.mock(ProductService.class);
		ProductController controller = new ProductController(productService);

		Mockito.when(productService.getById(100L)).thenReturn(java.util.Optional.empty());
		Assertions.assertThrows(ResponseStatusException.class, () -> {
			controller.delete(100L);
		});
	}


}
