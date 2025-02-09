package com.graphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String productId; // Unique identifier for the product
    private String name; // Name of the product
    private String description; // Description of the product
    private String category; // Category of the product
    private String material; // Material used in the product
    private Double unitPrice; // Price per unit of the product
    private String currency; // Currency of the price
    private Integer availableQuantity; // Quantity available in stock
    private Integer minimumOrderQuantity; // Minimum quantity required for an order
    private String supplier; // Supplier of the product
    private Integer leadTimeDays; // Lead time in days for delivery
    private Integer weightGram; // Weight of the product in grams

    // Constructor for general inventory use
    public Product(String productId, String name, Double unitPrice, String description, 
                   String category, String material, String currency, Integer availableQuantity, 
                   Integer minimumOrderQuantity, String supplier, Integer leadTimeDays, Integer weightGram) {
        this.productId = productId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.description = description;
        this.category = category;
        this.material = material;
        this.currency = currency;
        this.availableQuantity = availableQuantity;
        this.minimumOrderQuantity = minimumOrderQuantity;
        this.supplier = supplier;
        this.leadTimeDays = leadTimeDays;
        this.weightGram = weightGram;
    }
}


