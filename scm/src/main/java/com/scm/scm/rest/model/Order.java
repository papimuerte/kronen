
package com.scm.scm.rest.model;

import java.util.List;

public class Order {
    private String id;
    private String customerUsername;
    private List<OrderProduct> products; // Use OrderProduct here
    private float totalAmount;
    private String status;
    private String createdAt;

    // Newly added fields
    private String companyName;
    private String email;
    private String address;
    private String phoneNumber;
    private String notes;

    // Default Constructor
    public Order() {
    }

    // Parameterized Constructor
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
                ", companyName='" + companyName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}