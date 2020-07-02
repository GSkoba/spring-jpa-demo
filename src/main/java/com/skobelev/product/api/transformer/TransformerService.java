package com.skobelev.product.api.transformer;

import com.skobelev.product.api.model.Product;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.function.Function;

@Service
public class TransformerService implements Function<Product, Product> {

    @Override
    public Product apply(Product product) {
        if (product.getReceiptDate() == null) {
            product.setReceiptDate(Date.from(LocalDateTime.now().plusDays(30).toInstant(ZoneOffset.UTC)));
        }
        if (product.getRating() >= 8) {
            product.setFeatured(true);
        }
        return product;
    }
}
