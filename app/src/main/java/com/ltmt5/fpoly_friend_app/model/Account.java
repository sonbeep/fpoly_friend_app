package com.ltmt5.fpoly_friend_app.model;

public class Account {
    private int id;
    private String username;
    private String password;
    private String userId;

    public Account(String username, String password, String userId) {
        this.username = username;
        this.password = password;
        this.userId = userId;
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
