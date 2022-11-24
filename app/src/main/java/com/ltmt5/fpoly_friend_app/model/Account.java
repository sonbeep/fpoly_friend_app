package com.ltmt5.fpoly_friend_app.model;

public class Account {
    private int accountId;
    private String username;
    private String password;
    private int userId;
    private boolean isVipMember;
    private boolean isBan;

    public Account(String username, String password, int userId, boolean isVipMember, boolean isBan) {
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.isVipMember = isVipMember;
        this.isBan = isBan;
    }

    public Account() {
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isVipMember() {
        return isVipMember;
    }

    public void setVipMember(boolean vipMember) {
        isVipMember = vipMember;
    }

    public boolean isBan() {
        return isBan;
    }

    public void setBan(boolean ban) {
        isBan = ban;
    }
}
