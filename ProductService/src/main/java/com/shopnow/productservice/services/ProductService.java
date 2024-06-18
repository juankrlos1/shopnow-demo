package com.shopnow.productservice.services;

import com.shopnow.productservice.models.Product;

import java.util.List;
import java.util.Optional;


public interface ProductService {

    List<Product> getAllProducts();

    Optional<Product> getProductById(String id);

    Product createProduct(Product product);

    Product updateProduct(String id, Product productDetails);

    void deleteProduct(String id);
}
