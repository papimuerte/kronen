package com.scm.scm.graphql.model;

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
    private String category;
    private String material;
    private Double unitPrice;
    private String currency;
    private Integer availableQuantity;
    private Integer minimumOrderQuantity;
    private String supplier;
    private Integer leadTimeDays;
    private Integer weightGram;

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

