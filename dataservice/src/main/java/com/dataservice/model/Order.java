
package com.dataservice.model;

// Model class representing an order, including customer details, product list, and order status.

import java.util.List;

public class Order {
    private String id;
    private String customerUsername;
    private List<OrderProduct> products; // List of products in the order
    private float totalAmount;
    private String status;
    private String createdAt;

    // Additional fields for company and contact details
    private String companyName;
    private String email;
    private String address;
    private String phoneNumber;
    private String notes;

    // Default constructor for object initialization
    public Order() {
    }

    // Parameterized constructor for creating order objects with all details
    public Order(String id, String customerUsername, List<OrderProduct> products, float totalAmount,
                 String status, String createdAt, String companyName, String email,
                 String address, String phoneNumber, String notes) {
        this.id = id;
        this.customerUsername = customerUsername;
        this.products = products;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
        this.companyName = companyName;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
    }

    // Getters and setters for accessing and modifying order properties
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // toString method for debugging and logging purposes
    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", customerUsername='" + customerUsername + '\'' +
                ", products=" + products +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", companyName='" + companyName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}