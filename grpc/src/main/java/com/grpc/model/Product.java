package com.grpc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a product in the inventory system.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private String productId; // Unique product ID
    private String name; // Product name
    private String description; // Product description
    private String category; // Product category
    private String material; // Material type
    private Double unitPrice; // Price per unit
    private String currency; // Currency type
    private Integer availableQuantity; // Stock quantity
    private Integer minimumOrderQuantity; // Minimum order amount
    private String supplier; // Supplier name
    private Integer leadTimeDays; // Delivery lead time in days
    private Integer weightGram; // Product weight in grams

    /**
     * Constructor for product and inventory management.
     */
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
