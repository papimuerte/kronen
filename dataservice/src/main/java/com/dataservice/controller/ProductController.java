package com.dataservice.controller;


import com.dataservice.model.Product;
import com.dataservice.JsonFileUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products-data")
public class ProductController {
    private static final String FILE_PATH = "data/products.json";
    private final JsonFileUtil jsonFileUtil;

    public ProductController(JsonFileUtil jsonFileUtil) {
        this.jsonFileUtil = jsonFileUtil;
    }

    @GetMapping
    public List<Product> getAllProducts() throws IOException {
        return jsonFileUtil.readJsonFile(FILE_PATH, new TypeReference<List<Product>>() {});
    }

    @PostMapping
    public List<Product> addProducts(@RequestBody List<Product> newProducts) throws IOException {
        List<Product> products = jsonFileUtil.readJsonFile(FILE_PATH, new TypeReference<List<Product>>() {});
        products.addAll(newProducts);  // ✅ Add the new products
        jsonFileUtil.writeJsonFile(FILE_PATH, products);
        return products;  // ✅ Return the updated product list
    }

    @DeleteMapping("/{id}")
    public List<Product> deleteProduct(@PathVariable String id) throws IOException {
        // ✅ Ensure a mutable list
        List<Product> products = new ArrayList<>(jsonFileUtil.readJsonFile(FILE_PATH, new TypeReference<List<Product>>() {}));
    
        // Remove the product with the given ID
        boolean removed = products.removeIf(product -> product.getproductId().equals(id));
    
        if (!removed) {
            throw new RuntimeException("Product with ID " + id + " not found.");
        }
    
        // ✅ Save the updated product list
        jsonFileUtil.writeJsonFile(FILE_PATH, products);
    
        return products;
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable String productId) throws IOException {
        List<Product> products = jsonFileUtil.readJsonFile(FILE_PATH, new TypeReference<List<Product>>() {});
        return products.stream()
                .filter(product -> product.getproductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product with ID " + productId + " not found."));
    }

    @PutMapping("/{productId}")
    public Product updateProduct(@PathVariable String productId, @RequestBody Product updatedProduct) throws IOException {
        List<Product> products = jsonFileUtil.readJsonFile(FILE_PATH, new TypeReference<List<Product>>() {});
    
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getproductId().equals(productId)) {
                products.set(i, updatedProduct);  // ✅ Replace existing product
                jsonFileUtil.writeJsonFile(FILE_PATH, products); // ✅ Save updated list
                return updatedProduct;
            }
        }
    
        throw new RuntimeException("Product with ID " + productId + " not found.");
    }


    
    
}
