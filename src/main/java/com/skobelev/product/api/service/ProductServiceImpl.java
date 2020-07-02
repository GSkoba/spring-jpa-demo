package com.skobelev.product.api.service;

import com.skobelev.product.api.model.Product;
import com.skobelev.product.api.repository.ProductRepository;
import com.skobelev.product.api.transformer.TransformerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final TransformerService transformerService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              TransformerService transformerService) {
        this.productRepository = productRepository;
        this.transformerService = transformerService;
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Illegal id = " + id));
    }

    @Override
    public Product newProduct(Product product) {
        return productRepository.save(transformerService.apply(product));
    }

    @Override
    public List<Product> newProducts(List<Product> products) {
        return productRepository.saveAll(products.stream().map(transformerService).collect(Collectors.toList()));
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product updateProduct(Product product) {
        return newProduct(product);
    }
}
