package com.graphql.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderProduct {
    private String productId; // Unique identifier for the product
    private String name; // Name of the product
    private int quantity; // Quantity of the product ordered
    private Double unitPrice; // Price per unit of the product

    // Constructor for order-specific products
    public OrderProduct(String productId, String name, int quantity, Double unitPrice) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}

