package com.scm.scm.rest;

import com.scm.scm.model.Product;
import com.scm.scm.util.ProductDataUtil;
import java.io.IOException;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductDataUtil productDataUtil;

    public ProductController(ProductDataUtil productDataUtil) {
        this.productDataUtil = productDataUtil;
    }

    // Alle Produkte abrufen (READ)
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productDataUtil.loadProducts();
            return ResponseEntity.ok(products);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Ein Produkt nach ID abrufen (READ)
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        try {
            List<Product> products = productDataUtil.loadProducts();
            return products.stream()
                    .filter(product -> product.getProductId().equals(id))
                    .findFirst()
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
