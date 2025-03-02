package com.dataservice.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String productId;
    private String name;
    private String description;
    private Double unitPrice;
    private String currency;

    private Map<String, Double> unitPrices; // Größenabhängige Preise

    public void setUnitPrices(Map<String, Double> unitPrices) {
        this.unitPrices = unitPrices;
        // Falls vorhanden, nimm den kleinsten Preis als Standard
        if (unitPrices != null && !unitPrices.isEmpty()) {
            this.unitPrice = unitPrices.values().stream().min(Double::compare).orElse(null);
        }
    }

    // Explicit Getter and Setter for productId
    public String getproductId() {
        return productId;
    }

    public void setproductId(String productId) {
        this.productId = productId;
    }

    // Constructor for general inventory use
    public Product(String productId, String name, Double unitPrice, String description, 
                   String category, String material, String currency, Integer availableQuantity, 
                   Integer minimumOrderQuantity, String supplier, Integer leadTimeDays, Integer weightGram) {
        this.productId = productId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.description = description;
        this.currency = currency;
    }
}
