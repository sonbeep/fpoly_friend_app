package com.ltmt5.fpoly_friend_app.model;

public class Hobbies {
    private String name;
    private boolean isSelected;

    public Hobbies(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
