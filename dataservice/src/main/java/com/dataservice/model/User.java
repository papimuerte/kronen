
package com.dataservice.model;

// Represents a user entity with authentication details, contact information, and role assignment.

import com.fasterxml.jackson.annotation.JsonAlias;

public class User {
    private String username;
    private String password;
    private String role = "USER";
    private String email;    // Newly added field

    @JsonAlias("phone") // Allows both "phone" and "phoneNumber" as input keys
    private String phoneNumber;    // Newly added field
    
    private String address;  // Newly added field
    private String companyName;

    // Default constructor
    public User() {
    }

    // Constructor to initialize user attributes
    public User(String username, String password, String role, String email, String phoneNumber, String address, String companyName) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.companyName = companyName;
    }

    // Getters and setters for accessing and modifying user properties
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {  
        return email;
    }

    public void setEmail(String email) {  
        this.email = email;
    }

    public String getPhoneNumber() {  
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {  
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {  
        return address;
    }

    public void setAddress(String address) {  
        this.address = address;
    }

    public String getcompanyName() {  
        return companyName;
    }

    public void setcompanyName(String companyName) {  
        this.companyName = companyName;
    }

    // toString method for logging and debugging
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
