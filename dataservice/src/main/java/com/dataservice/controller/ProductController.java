package com.dataservice.controller;

// REST controller for managing product data stored in a JSON file.

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

    // File path where product data is stored in JSON format.
    private static final String FILE_PATH = "data/products.json";
    private final JsonFileUtil jsonFileUtil;

    // Constructor to initialize JsonFileUtil dependency.
    public ProductController(JsonFileUtil jsonFileUtil) {
        this.jsonFileUtil = jsonFileUtil;
    }

    // Retrieves all products from the JSON file.
    @GetMapping
    public List<Product> getAllProducts() throws IOException {
        return jsonFileUtil.readJsonFile(FILE_PATH, new TypeReference<List<Product>>() {});
    }

    // Adds multiple new products to the JSON file and returns the updated product list.
    @PostMapping
    public List<Product> addProducts(@RequestBody List<Product> newProducts) throws IOException {
        List<Product> products = jsonFileUtil.readJsonFile(FILE_PATH, new TypeReference<List<Product>>() {});
        products.addAll(newProducts);
        jsonFileUtil.writeJsonFile(FILE_PATH, products);
        return products;
    }

    @PostMapping("/add")
    public void addProduct(@RequestBody Product newProduct) throws IOException {
        List<Product> products = jsonFileUtil.readJsonFile(FILE_PATH, new TypeReference<List<Product>>() {});
        products.add(newProduct); // Add only the new product
        jsonFileUtil.writeJsonFile(FILE_PATH, products); // Save the updated list
    }


    // Deletes a product by its ID and returns the updated product list.
    @DeleteMapping("/{productId}")
    public List<Product> deleteProduct(@PathVariable String productId) throws IOException {
        List<Product> products = new ArrayList<>(jsonFileUtil.readJsonFile(FILE_PATH, new TypeReference<List<Product>>() {}));
    
        boolean removed = products.removeIf(product -> product.getproductId().equals(productId));
    
        if (!removed) {
            throw new RuntimeException("Product with ID " + productId + " not found.");
        }
    
        jsonFileUtil.writeJsonFile(FILE_PATH, products);
    
        return products;
    }


    // Retrieves a specific product by its ID from the JSON file.
    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable String productId) throws IOException {
        List<Product> products = jsonFileUtil.readJsonFile(FILE_PATH, new TypeReference<List<Product>>() {});
        return products.stream()
                .filter(product -> product.getproductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product with ID " + productId + " not found."));
    }

    // Updates an existing product by its ID and returns the updated product.
    @PutMapping("/{productId}")
    public Product updateProduct(@PathVariable String productId, @RequestBody Product updatedProduct) throws IOException {
        List<Product> products = jsonFileUtil.readJsonFile(FILE_PATH, new TypeReference<List<Product>>() {});
    
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getproductId().equals(productId)) {
                products.set(i, updatedProduct);
                jsonFileUtil.writeJsonFile(FILE_PATH, products);
                return updatedProduct;
            }
        }
    
        throw new RuntimeException("Product with ID " + productId + " not found.");
    }

    // Updates only the available quantity of a product by ID
    @PutMapping("/{productId}/quantity/{newQuantity}")
    public Product updateProductQuantity(@PathVariable String productId, @PathVariable int newQuantity) throws IOException {
        List<Product> products = jsonFileUtil.readJsonFile(FILE_PATH, new TypeReference<List<Product>>() {});
    
        for (Product product : products) {
            if (product.getproductId().equals(productId)) {
                product.setAvailableQuantity(newQuantity); // Update quantity
                jsonFileUtil.writeJsonFile(FILE_PATH, products);
                return product; // Return updated product
            }
        }
    
        throw new RuntimeException("Product with ID " + productId + " not found.");
    }

}