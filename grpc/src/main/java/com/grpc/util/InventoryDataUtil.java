package com.grpc.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grpc.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Component
public class InventoryDataUtil {

    private static final Logger logger = LoggerFactory.getLogger(InventoryDataUtil.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String DATA_SERVICE_URL = "http://localhost:8090/products-data";

    /**
     * Fetches all products from the data service.
     */
    public List<Product> loadProducts() {
        logger.info("Fetching all products from {}", DATA_SERVICE_URL);

        try {
            ResponseEntity<Product[]> response = restTemplate.getForEntity(DATA_SERVICE_URL, Product[].class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                logger.info("Loaded {} products", response.getBody().length);
                return List.of(response.getBody());
            } else {
                logger.warn("Failed to fetch products, Status: {}", response.getStatusCode());
                return List.of();
            }
        } catch (Exception e) {
            logger.error("Error fetching products", e);
            return List.of();
        }
    }

    /**
     * Retrieves product details by ID.
     */
    public Optional<Product> getProductById(String productId) {
        String url = DATA_SERVICE_URL + "/" + productId;
        logger.info("Fetching product with ID: {}", productId);
    
        try {
            ResponseEntity<Product> response = restTemplate.getForEntity(url, Product.class);
    
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                logger.info("Product found: {}", response.getBody());
                return Optional.of(response.getBody());
            } else {
                logger.warn("No product found for ID: {}", productId);
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.error("Error fetching product with ID: {}", productId, e);
            return Optional.empty();
        }
    }
    
    /**
     * Retrieves available quantity of a product.
     */
    public int getAvailableQuantity(String productId) {
        return getProductById(productId)
                .map(Product::getAvailableQuantity)
                .orElse(-1);
    }

    /**
     * Updates a single product.
     */
    public void updateProduct(Product updatedProduct) {
        String url = DATA_SERVICE_URL + "/" + updatedProduct.getProductId();
        logger.info("Updating product with ID: {}", updatedProduct.getProductId());
    
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Product> request = new HttpEntity<>(updatedProduct, headers);
    
        try {
            ResponseEntity<Product> response = restTemplate.exchange(url, HttpMethod.PUT, request, Product.class);
    
            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Product {} updated successfully", updatedProduct.getProductId());
            } else {
                logger.error("Failed to update product, Status: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("Error updating product: {}", updatedProduct.getProductId(), e);
        }
    }
    
    /**
     * Saves updated product list.
     */
    private void saveUpdatedProducts(List<Product> updatedProducts) {
        logger.info("Saving {} updated products", updatedProducts.size());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Product>> request = new HttpEntity<>(updatedProducts, headers);

        try {
            ResponseEntity<Void> response = restTemplate.exchange(DATA_SERVICE_URL, HttpMethod.PUT, request, Void.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Updated product list successfully");
            } else {
                logger.error("Failed to update product list, Status: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("Error updating product list", e);
        }
    }
}
