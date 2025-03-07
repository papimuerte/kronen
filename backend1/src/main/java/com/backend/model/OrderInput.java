package com.backend.model;

import java.util.List;

public class OrderInput {
    private String customerUsername; // Username of the customer
    private String companyName; // Customer's company name (if applicable)
    private String email; // Customer's email address
    private String address; // Delivery address of the order
    private String phoneNumber; // Customer's phone number
    private String notes; // Additional notes about the order
    private List<ProductInput> products; // List of products in the order

    // Getter and setter methods
    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
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

    public List<ProductInput> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInput> products) {
        this.products = products;
    }
}
