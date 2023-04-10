package com.spring.authorization.testing.controller;



import com.spring.authorization.testing.model.Product;
import com.spring.authorization.testing.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {


    @Autowired
    private ProductServiceImpl productServiceImpl;

    @PreAuthorize("hasAuthority('product:write')")
    @PostMapping
    public ResponseEntity<Product> saveProduct(@Valid @RequestBody Product product){

        Product savedProduct = productServiceImpl.addProduct(product);

        return ResponseEntity.ok(savedProduct);

    }



    @PreAuthorize("hasAnyAuthority('product:read')")
    @GetMapping()
    public ResponseEntity<Object> getAllProduct(){

        List<Product>  productList = productServiceImpl.getListOfProducts();

        if(productList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no product added");
        }

        return ResponseEntity.ok(productList);
    }


    @PreAuthorize("hasAnyAuthority('product:write')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProductById(@PathVariable String id){
        Product product = productServiceImpl.deleteProductById(id);
        return ResponseEntity.ok(product);

    }


    @PreAuthorize("hasAnyAuthority('product:read')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable String id){
            Product product = productServiceImpl.getProductById(id);
            return ResponseEntity.ok(product);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('product:write')")
    public ResponseEntity<Object> updateProductById(@PathVariable String id, @RequestBody  Product product){

        Product updatedProduct = productServiceImpl.updateProductById(id, product);

        return ResponseEntity.ok(updatedProduct);
    }
}
