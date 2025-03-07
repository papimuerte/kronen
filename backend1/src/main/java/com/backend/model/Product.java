package com.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String productId; 
    private String name; 
    private String description; 
    private String category; 
    private String material; 
    private Double unitPrice; 
    private Map<String, Double> unitPrices;  // ✅ Map statt Array
    private String currency; 
    private Integer availableQuantity; 
    private Integer minimumOrderQuantity; 
    private String supplier; 
    private Integer leadTimeDays; 
    private Integer weightGram;
}

