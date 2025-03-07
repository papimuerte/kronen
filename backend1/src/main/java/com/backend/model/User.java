package com.backend.model;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * Model class representing a User entity with authentication-related fields.
 */
public class User {
    
    private String username;
    private String password;
    private String role = "USER";  // Default role for new users
    private String email;    // Newly added field

    @JsonAlias("phone") // Accepts both "phone" and "phoneNumber"
    private String phoneNumber;    // Newly added field
    
    private String address;  // Newly added field
    private String companyName;

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Parameterized constructor for creating a User instance with all attributes.
     * 
     * @param username    The username of the user.
     * @param password    The password for authentication.
     * @param role        The role assigned to the user.
     * @param email       The user's email address.
     * @param phoneNumber The user's phone number.
     * @param address     The user's address.
     * @param companyName The company name associated with the user.
     */
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

    public String getEmail() {  // Getter for email
        return email;
    }

    public void setEmail(String email) {  // Setter for email
        this.email = email;
    }

    public String getPhoneNumber() {  // Getter for phone
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {  // Setter for phone
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {  // Getter for address
        return address;
    }

    public void setAddress(String address) {  // Setter for address
        this.address = address;
    }

    public String getCompanyName() {  // Getter for companyName
        return companyName;
    }

    public void setCompanyName(String companyName) {  // Setter for companyName
        this.companyName = companyName;
    }

    /**
     * Overrides the toString method for debugging purposes.
     * 
     * @return A string representation of the User object.
     */
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
