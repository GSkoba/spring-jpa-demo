package com.skobelev.product.api.repository;

import com.skobelev.product.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository
        extends JpaRepository<Product, Integer>  {
}
