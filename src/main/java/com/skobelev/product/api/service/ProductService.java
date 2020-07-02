package com.skobelev.product.api.service;

import com.skobelev.product.api.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();
    Product getById(Integer id);
    Product newProduct(Product product);
    List<Product> newProducts(List<Product> products);
    void deleteProduct(Integer id);
    Product updateProduct(Product product);
}
