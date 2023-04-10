package com.spring.authorization.testing.service.impl;



import com.spring.authorization.testing.model.Product;
import com.spring.authorization.testing.repository.ProductRepository;
import com.spring.authorization.testing.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {


    @Autowired
    private ProductRepository productRepository;
    public Product addProduct(Product product){
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    public List<Product> getListOfProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(String id){
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product with "+id+" not found"));
    }

    public Product deleteProductById(String id ){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with Id: "+id));

        productRepository.delete(product);
        return product;
    }

    public Product updateProductById(String id, Product product){

        Product editProduct = productRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Product not found of id - "+ id));

        editProduct.setName(product.getName());
        editProduct.setPrice(product.getPrice());

        return productRepository.save(editProduct);
    }

}
