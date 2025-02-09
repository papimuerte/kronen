package com.dataservice.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class User {
    private String username;
    private String password;
    private String role = "USER";
    private String email;    // Neu hinzugefügt

    @JsonAlias("phone") // Accepts both "phone" and "phoneNumber"
    private String phoneNumber;    // Neu hinzugefügt
    
    private String address;  // Neu hinzugefügt
    private String companyName;

    // Default constructor
    public User() {
    }

    // Parameterized constructor
    public User(String username, String password, String role, String email, String phoneNumber, String address, String companyName) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.companyName = companyName;
    }

    // Getters and Setters
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

    public String getEmail() {  // Getter für email
        return email;
    }

    public void setEmail(String email) {  // Setter für email
        this.email = email;
    }

    public String getPhoneNumber() {  // Getter für phone
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {  // Setter für phone
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {  // Getter für address
        return address;
    }

    public void setAddress(String address) {  // Setter für address
        this.address = address;
    }

    public String getcompanyName() {  // Getter für companyName
        return companyName;
    }

    public void setcompanyName(String companyName) {  // Setter für companyName
        this.companyName = companyName;
    }

    // toString method (optional for debugging)
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

