package com.scm.scm.model;

import java.util.List;

public class Order {
    private String id;
    private String customerUsername;
    private List<OrderProduct> products; // Use OrderProduct here
    private float totalAmount;
    private String status;
    private String createdAt;

    // Default Constructor
    public Order() {
    }

    // Parameterized Constructor
    public Order(String id, String customerUsername, List<OrderProduct> products, float totalAmount, String status, String createdAt) {
        this.id = id;
        this.customerUsername = customerUsername;
        this.products = products;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public List<OrderProduct> getProducts() {
        return products;
    }

    public void setProducts(List<OrderProduct> products) {
        this.products = products;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // toString method (optional for debugging)
    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", customerUsername='" + customerUsername + '\'' +
                ", products=" + products +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
