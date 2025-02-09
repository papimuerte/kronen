package com.graphql.model;

public class ProductInput {
    private String productId; // Unique identifier for the product
    private int quantity; // Quantity of the product ordered
    private double unitPrice; // Price per unit of the product
    private String name; // Name of the product

    // Getters
    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

