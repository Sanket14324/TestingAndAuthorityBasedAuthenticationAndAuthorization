package com.spring.authorization.testing.impl;


import com.spring.authorization.testing.model.Product;
import com.spring.authorization.testing.repository.ProductRepository;
import com.spring.authorization.testing.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class ProductServiceImplTest {


    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductRepository productRepository;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addProduct() {
        Product product = new Product();
        product.setId("1");
        product.setName("Bottle");
        product.setPrice(100);

        when(productRepository.save(product)).thenReturn(product);

        Product actualProduct = productService.addProduct(product);

        assertAll(
                () -> assertEquals("Bottle", actualProduct.getName()),
                () -> assertEquals(100, actualProduct.getPrice())
        );


    }

    @Test
    void getListOfProducts() {
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

        when(productRepository.findAll()).thenReturn(productList);

        List<Product> actualProductList = productService.getListOfProducts();

        assertAll(
                () ->  assertEquals(3, actualProductList.size()),
                () ->  assertEquals("Bottle", actualProductList.get(0).getName()),
                () ->  assertEquals("Cup", actualProductList.get(1).getName()),
                () ->  assertEquals("Book", actualProductList.get(2).getName())
        );

    }

    @Test
    void getProductById() {
        Product product = new Product();
        product.setId("1");
        product.setName("Bottle");
        product.setPrice(100);

        when(productRepository.findById(anyString())).thenReturn(Optional.of(product));

        Product actualProduct = productService.getProductById("1");

        assertAll(
                () -> assertEquals("Bottle", actualProduct.getName()),
                () -> assertEquals(100, actualProduct.getPrice())
        );
    }

    @Test
    void deleteProductById() {
        Product product = new Product();
        product.setId("1");
        product.setName("Bottle");
        product.setPrice(100);

        when(productRepository.findById(anyString())).thenReturn(Optional.of(product));

        Product actualProduct = productService.getProductById("1");

        assertAll(
                () -> assertEquals("Bottle", actualProduct.getName()),
                () -> assertEquals(100, actualProduct.getPrice())
        );

    }

    @Test
    void updateProductById() {

        Product existingProduct = new Product();
        existingProduct.setId("1");
        existingProduct.setName("Fake name");
        existingProduct.setPrice(100);

        Product updatedProduct = new Product();
        updatedProduct.setId("1");
        updatedProduct.setName("FakeUpdated name");
        updatedProduct.setPrice(1000);

        Product rawProduct = new Product();
        rawProduct.setName("FakeUpdated name");
        rawProduct.setPrice(1000);

        when(productRepository.findById("1")).thenReturn(Optional.of(existingProduct));

        when(productRepository.save(existingProduct)).thenReturn(updatedProduct);

        Product actualProduct = productService.updateProductById("1", rawProduct);

        assertAll(
                () -> assertNotNull(actualProduct),
                () ->  assertEquals("1", actualProduct.getId()),
                () ->  assertEquals("FakeUpdated name", actualProduct.getName()),
                () -> assertEquals(1000, actualProduct.getPrice())
        );
    }
}