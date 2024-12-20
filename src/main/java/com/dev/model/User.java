package com.dev.model;

public class User {
    private int id;
    private String username;
    private String password;
    private String role;

    // Constructor with username and password
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = "Employee"; // Default role (you can modify as needed)
    }

    // Default constructor
    public User() {}

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}
