package com.graphql.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@Component
public class ProductServiceUtil {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceUtil.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String PRODUCT_SERVICE_URL = "http://localhost:8085/api/products"; // ✅ Product Service Base URL

    // ✅ Check if stock is available
    public boolean isStockAvailable(String productId, int requestedQty) {
    String url = PRODUCT_SERVICE_URL + "/" + productId;
    logger.info("Checking stock for product: {}", productId);

    try {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        logger.info("Product Service Response: {}", response.getBody());  // Log entire response

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(response.getBody());

        if (!jsonResponse.has("availableQuantity")) {
            logger.warn("Product {} response does not contain quantity: {}", productId, response.getBody());
            return false;
        }

        int availableQty = jsonResponse.get("availableQuantity").asInt();
        logger.info("Product {} has {} in stock. Requested: {}", productId, availableQty, requestedQty);
        return availableQty >= requestedQty;
    } catch (Exception e) {
        logger.error("Error checking stock for {}: {}", productId, e.getMessage());
        return false;
    }
}


    // ✅ Deduct stock from product inventory
    public boolean deductStock(String productId, int qtyToReduce) {
        String url = PRODUCT_SERVICE_URL + "/admin/" + productId;
        logger.info("Reducing stock for product {} by {}", productId, qtyToReduce);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = Map.of("availableQuantity", -qtyToReduce); // Reduce qty
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Stock updated successfully for product: {}", productId);
                return true;
            } else {
                logger.error("Failed to update stock for {}. Response: {}", productId, response);
                return false;
            }
        } catch (Exception e) {
            logger.error("Error updating stock for {}: {}", productId, e.getMessage());
            return false;
        }
    }
}

