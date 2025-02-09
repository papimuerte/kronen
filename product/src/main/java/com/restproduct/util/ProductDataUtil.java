package com.restproduct.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restproduct.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class for handling product data operations by interacting with an external data service.
 */
@Component
public class ProductDataUtil {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();
    
    // API endpoint for fetching and storing product data
    private static final String DATA_SERVICE_URL = "http://localhost:8090/products-data"; 

    /**
     * Loads the list of products from the external data service.
     *
     * @return List of products retrieved from the service.
     * @throws IOException if an error occurs while fetching the data.
     */
    public List<Product> loadProducts() throws IOException {
        ResponseEntity<Product[]> response = restTemplate.getForEntity(DATA_SERVICE_URL, Product[].class);
        return Arrays.asList(response.getBody());
    }

    /**
     * Saves the list of products by sending a POST request to the external data service.
     *
     * @param products List of products to be saved.
     * @throws IOException if an error occurs while saving the products.
     */
    public void saveProducts(List<Product> products) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        
        HttpEntity<List<Product>> request = new HttpEntity<>(products, headers);
        
        // Send the HTTP POST request to save the product list
        restTemplate.exchange(DATA_SERVICE_URL, HttpMethod.POST, request, Void.class);
    }
}
