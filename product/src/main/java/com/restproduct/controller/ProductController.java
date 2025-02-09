package com.restproduct.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restproduct.model.Product;
import com.restproduct.util.ProductDataUtil;

/**
 * Controller for managing products with CRUD operations.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductDataUtil productDataUtil;

    /**
     * Constructor to inject the ProductDataUtil dependency.
     *
     * @param productDataUtil Utility for managing product data.
     */
    public ProductController(ProductDataUtil productDataUtil) {
        this.productDataUtil = productDataUtil;
    }

    /**
     * Retrieves all products.
     *
     * @return List of all products.
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productDataUtil.loadProducts();
            return ResponseEntity.ok(products);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id Product ID.
     * @return Product if found, otherwise 404 Not Found.
     */
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

    /**
     * Adds a new product.
     *
     * @param newProduct Product data from request body.
     * @return Success or error message.
     */
    @PostMapping("/admin/add")
    public ResponseEntity<String> addProduct(@RequestBody Product newProduct) {
        try {
            List<Product> existingProducts = productDataUtil.loadProducts();
            List<Product> products = new ArrayList<>(existingProducts);
            products.add(newProduct);
            productDataUtil.saveProducts(products);
            return ResponseEntity.ok("Product added successfully.");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error while saving the product.");
        }
    }

    /**
     * Updates an existing product by its ID.
     *
     * @param id Product ID.
     * @param updatedProduct Updated product data.
     * @return Success message or 404 if product is not found.
     */
    @PutMapping("/admin/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable String id, @RequestBody Product updatedProduct) {
        try {
            List<Product> products = productDataUtil.loadProducts();
            for (Product product : products) {
                if (product.getProductId().equals(id)) {
                    boolean isUpdated = updateNonNullFields(updatedProduct, product);
                    if (isUpdated) {
                        productDataUtil.saveProducts(products);
                        return ResponseEntity.ok("Product updated successfully.");
                    } else {
                        return ResponseEntity.ok("No changes made.");
                    }
                }
            }
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error while updating the product.");
        }
    }

    /**
     * Updates only the non-null fields of an existing product.
     *
     * @param source Updated product data.
     * @param target Existing product to be updated.
     * @return true if any field was updated, otherwise false.
     */
    private boolean updateNonNullFields(Object source, Object target) {
        boolean isUpdated = false;

        for (var field : source.getClass().getDeclaredFields()) {
            field.setAccessible(true); // Allow access to private fields
            try {
                Object sourceValue = field.get(source);
                if (sourceValue != null) { // Update only non-null fields
                    Object targetValue = field.get(target);
                    if (!sourceValue.equals(targetValue)) { // Update if value has changed
                        field.set(target, sourceValue);
                        isUpdated = true;
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error while updating fields.", e);
            }
        }

        return isUpdated;
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id Product ID.
     * @return Success message or 404 if product is not found.
     */
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        try {
            // Convert to a mutable list to avoid UnsupportedOperationException
            List<Product> products = new ArrayList<>(productDataUtil.loadProducts());

            boolean removed = products.removeIf(product -> product.getProductId().equals(id));
            if (removed) {
                productDataUtil.saveProducts(products);
                return ResponseEntity.ok("Product deleted successfully.");
            }
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error while deleting the product.");
        }
    }
}
