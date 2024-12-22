package com.scm.scm.model;

public class User {
    private String username;
    private String password;
    private String role;
    private String email;    // Neu hinzugefügt
    private String phone;    // Neu hinzugefügt
    private String address;  // Neu hinzugefügt

    // Default constructor
    public User() {
    }

    // Parameterized constructor
    public User(String username, String password, String role, String email, String phone, String address) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.address = address;
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

    public String getPhone() {  // Getter für phone
        return phone;
    }

    public void setPhone(String phone) {  // Setter für phone
        this.phone = phone;
    }

    public String getAddress() {  // Getter für address
        return address;
    }

    public void setAddress(String address) {  // Setter für address
        this.address = address;
    }

    // toString method (optional for debugging)
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
