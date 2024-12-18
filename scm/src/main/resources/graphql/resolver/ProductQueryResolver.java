package com.scm.scm.resolver;

import com.scm.scm.model.Product;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ProductQueryResolver {

    private final List<Product> productList = List.of(
            new Product("J001", "Diamond Engagement Ring", "14k white gold ring", "Rings", 2500.00, 50),
            new Product("J002", "Gold Wedding Band", "18k yellow gold wedding band", "Rings", 500.00, 200)
    );

    public Product getProductById(String productId) {
        return productList.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }
}