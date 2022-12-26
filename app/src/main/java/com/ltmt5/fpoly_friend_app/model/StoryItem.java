package com.ltmt5.fpoly_friend_app.model;

public class StoryItem {
    private int storyId;
    private String title;
    private String image;
    private int userId;

    public StoryItem(String title, String image, int userId) {
        this.title = title;
        this.image = image;
        this.userId = userId;
    }

    public StoryItem() {
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
