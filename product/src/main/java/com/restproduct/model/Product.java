package com.restproduct.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class representing a product entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    
    private String productId;             // Unique identifier for the product
    private String name;                  // Product name
    private String description;           // Product description
    private String category;              // Product category (e.g., electronics, clothing)
    private String material;              // Material composition of the product
    private Double unitPrice;             // Price per unit
    private String currency;              // Currency of the price (e.g., USD, EUR)
    private Integer availableQuantity;    // Number of units available in stock
    private Integer minimumOrderQuantity; // Minimum order quantity for purchase
    private String supplier;              // Supplier of the product
    private Integer leadTimeDays;         // Number of days required for delivery
    private Integer weightGram;           // Product weight in grams

    /**
     * Parameterized constructor for creating a product instance.
     * 
     * @param productId             Unique identifier for the product.
     * @param name                  Product name.
     * @param unitPrice             Price per unit.
     * @param description           Product description.
     * @param category              Product category.
     * @param material              Material composition of the product.
     * @param currency              Currency of the price.
     * @param availableQuantity     Available stock quantity.
     * @param minimumOrderQuantity  Minimum quantity required for an order.
     * @param supplier              Supplier of the product.
     * @param leadTimeDays          Delivery lead time in days.
     * @param weightGram            Weight of the product in grams.
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
