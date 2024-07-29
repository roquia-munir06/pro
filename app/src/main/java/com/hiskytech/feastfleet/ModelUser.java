package com.hiskytech.feastfleet;

public class ModelUser {
    private String name;
    private String email;
    private String password;
    private String userId;

    // Default constructor
    public ModelUser() {
        this.name = "";
        this.email = "";
        this.password = "";
        this.userId = "";
    }

    // Parameterized constructor
    public ModelUser(String name, String email, String password, String userId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userId = userId;
    }

    // Getter and setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public ModelUser(String name, String email) {
        this.name = name;
        this.email = email;
        this.password = "";
        this.userId = "";
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
