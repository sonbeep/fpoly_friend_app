package com.ltmt5.fpoly_friend_app.model;

public class Chat {
    private String avatar;
    private String name;
    private String description;
    private String time;
    private String count;

    public Chat(String avatar, String name, String description, String time, String count) {
        this.avatar = avatar;
        this.name = name;
        this.description = description;
        this.time = time;
        this.count = count;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
