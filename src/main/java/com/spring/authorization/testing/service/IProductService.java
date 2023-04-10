package com.spring.authorization.testing.service;



import com.spring.authorization.testing.model.Product;

import java.util.List;

public interface IProductService {

     Product addProduct(Product product);

    List<Product> getListOfProducts();

    Product getProductById(String id);

    Product deleteProductById(String id);

    Product updateProductById(String id, Product product);

}
