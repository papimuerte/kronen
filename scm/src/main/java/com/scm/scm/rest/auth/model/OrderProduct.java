package com.scm.scm.rest.auth.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderProduct {
    private String productId;
    private String name;
    private int quantity;
    private Double unitPrice;

    // Constructor for order-specific products
    public OrderProduct(String productId, String name, int quantity, Double unitPrice) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}
