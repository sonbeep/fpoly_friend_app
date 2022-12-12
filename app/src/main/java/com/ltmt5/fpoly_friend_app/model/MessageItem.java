package com.ltmt5.fpoly_friend_app.model;

public class MessageItem {

    private int id;
    private String name;
    private String content;
    private int count;
    private int picture;


    public MessageItem() {
    }

    public MessageItem(int id, String name, String content, int count, int picture) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.count = count;
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int id) {
        this.id = id;
    }
}

//myImageView.setImageResource(R.drawable.icon);