package com.ltmt5.fpoly_friend_app.model;

import java.io.Serializable;
import java.util.List;

public class UserProfile implements Serializable {


    //availability = -1   ban
    //availability = 0   new
    //availability = 1   done

    private int availability;
    private String userId;
    private String name;
    private int age;
    private int phone;
    private String gender;
    private String education;
    private List<String> hobbies;
    private List<String> image;
    private String description;
    private String email;
    private String password;
    private String fcmToken;
    private String location;
    private String zodiac;
    private String personality;
    private String sexualOrientation;
    private String favoriteSong;
    private String showPriority;
    private String imageUri;
    private String token;
    private int match;

    public UserProfile() {
    }

    public UserProfile(String name, int age, String gender, String education, List<String> hobbies, List<String> image, String description, String location, String zodiac, String personality, String sexualOrientation, String favoriteSong, String showPriority, String imageUri) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.education = education;
        this.hobbies = hobbies;
        this.image = image;
        this.description = description;
        this.location = location;
        this.zodiac = zodiac;
        this.personality = personality;
        this.sexualOrientation = sexualOrientation;
        this.favoriteSong = favoriteSong;
        this.showPriority = showPriority;
        this.imageUri = imageUri;
    }


    public UserProfile(String name, int age, String gender, String education, List<String> hobbies, List<String> image, String imageUri) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.education = education;
        this.hobbies = hobbies;
        this.image = image;
        this.imageUri = imageUri;
    }

    public UserProfile(int availability, String email, String fcmToken, String imageUri, String name, String password) {
        this.availability = availability;
        this.email = email;
        this.fcmToken = fcmToken;
        this.imageUri = imageUri;
        this.name = name;
        this.password = password;
    }

    public UserProfile(String userId, String name, int age, String gender, String education, List<String> hobbies, String imageUri) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.education = education;
        this.hobbies = hobbies;
        this.imageUri = imageUri;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getZodiac() {
        return zodiac;
    }

    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public String getSexualOrientation() {
        return sexualOrientation;
    }

    public void setSexualOrientation(String sexualOrientation) {
        this.sexualOrientation = sexualOrientation;
    }

    public String getFavoriteSong() {
        return favoriteSong;
    }

    public void setFavoriteSong(String favoriteSong) {
        this.favoriteSong = favoriteSong;
    }

    public String getShowPriority() {
        return showPriority;
    }

    public void setShowPriority(String showPriority) {
        this.showPriority = showPriority;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getMatch() {
        return match;
    }

    public void setMatch(int match) {
        this.match = match;
    }
}
