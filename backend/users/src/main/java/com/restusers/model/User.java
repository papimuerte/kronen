package com.restusers.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class User {
    private String username;
    private String password;
    private String role = "USER";
    private String email;    // Newly added field for storing user's email

    @JsonAlias("phone") // Allows both "phone" and "phoneNumber" as input field names
    private String phoneNumber;    // Newly added field for storing user's phone number
    
    private String address;  // Newly added field for storing user's address

    // Default constructor (required for object mapping and serialization)
    public User() {
    }

    // Parameterized constructor for creating a user with all attributes
    public User(String username, String password, String role, String email, String phoneNumber, String address, String companyName) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters and Setters

    // Gets the username of the user
    public String getUsername() {
        return username;
    }

    // Sets the username of the user
    public void setUsername(String username) {
        this.username = username;
    }

    // Gets the user's password
    public String getPassword() {
        return password;
    }

    // Sets the user's password
    public void setPassword(String password) {
        this.password = password;
    }

    // Gets the user's role (e.g., USER, ADMIN)
    public String getRole() {
        return role;
    }

    // Sets the user's role
    public void setRole(String role) {
        this.role = role;
    }

    // Gets the user's email
    public String getEmail() {  
        return email;
    }

    // Sets the user's email
    public void setEmail(String email) {  
        this.email = email;
    }

    // Gets the user's phone number
    public String getPhoneNumber() {  
        return phoneNumber;
    }

    // Sets the user's phone number
    public void setPhoneNumber(String phoneNumber) {  
        this.phoneNumber = phoneNumber;
    }

    // Gets the user's address
    public String getAddress() {  
        return address;
    }

    // Sets the user's address
    public void setAddress(String address) {  
        this.address = address;
    }


    // Overrides toString() method to provide a string representation of the User object
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
