package com.spring.authorization.testing.controller;



import com.spring.authorization.testing.model.Product;
import com.spring.authorization.testing.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    @InjectMocks
    ProductController productController;
    @Mock
    ProductServiceImpl productService;


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveProduct() {

        Product product = new Product();
        product.setId("1");
        product.setName("Bottle");
        product.setPrice(100);

        when(productService.addProduct(product)).thenReturn(product);

        ResponseEntity<Product> responseProduct = productController.saveProduct(product);
        Product actualProduct = responseProduct.getBody();

        assertAll(
                () -> assertEquals("Bottle", actualProduct.getName()),
                () -> assertEquals(100, actualProduct.getPrice())
        );
    }

    @Test
    void getAllProduct() {

        Product product = new Product();
        product.setId("1");
        product.setName("Bottle");
        product.setPrice(100);
        Product product1 = new Product();
        product1.setId("2");
        product1.setName("Cup");
        product1.setPrice(10);
        Product product2 = new Product();
        product2.setId("1");
        product2.setName("Book");
        product2.setPrice(1000);

        List<Product> productList = new ArrayList<>();

        productList.add(product);
        productList.add(product1);
        productList.add(product2);

        when(productService.getListOfProducts()).thenReturn(productList);

        ResponseEntity<Object> actualProductList = productController.getAllProduct();

        List<Product> actualList =(List<Product>)actualProductList.getBody();

        assertAll(
                () ->  assertEquals(3, actualList.size()),
                () ->  assertEquals("Bottle", actualList.get(0).getName()),
                () ->  assertEquals("Cup", actualList.get(1).getName()),
                () ->  assertEquals("Book", actualList.get(2).getName())
        );

    }

    @Test
    void deleteProductById() {

        Product product = new Product();
        product.setId("1");
        product.setName("Bottle");
        product.setPrice(100);

        when(productService.deleteProductById(anyString())).thenReturn(product);

        ResponseEntity<Object> actualResponseProduct = productController.deleteProductById("1");
        Product actualProduct = (Product)actualResponseProduct.getBody();

        assertAll(
                () -> assertEquals("Bottle", actualProduct.getName()),
                () -> assertEquals(100, actualProduct.getPrice())
        );
    }

    @Test
    void getProductById() {
        Product product = new Product();
        product.setId("1");
        product.setName("Bottle");
        product.setPrice(100);

        when(productService.getProductById(anyString())).thenReturn(product);

        ResponseEntity<Object> actualResponseProduct = productController.getProductById("1");

        Product actualProduct = (Product)actualResponseProduct.getBody();

        assertAll(
                () -> assertEquals("Bottle", actualProduct.getName()),
                () -> assertEquals(100, actualProduct.getPrice())
        );
    }

    @Test
    void updateProductById() {

    }
}