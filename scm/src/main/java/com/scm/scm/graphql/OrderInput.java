package com.scm.scm.graphql;

import java.util.List;

public class OrderInput {
    private String customerUsername;
    private List<ProductInput> products;

    // Getters and Setters
    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public List<ProductInput> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInput> products) {
        this.products = products;
    }
}
