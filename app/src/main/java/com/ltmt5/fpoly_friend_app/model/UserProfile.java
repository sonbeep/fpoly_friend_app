package com.ltmt5.fpoly_friend_app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserProfile implements Parcelable {

    public static final Creator<UserProfile> CREATOR = new Creator<UserProfile>() {
        @Override
        public UserProfile createFromParcel(Parcel in) {
            return new UserProfile(in);
        }

        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };
    private int userId;
    private String name;
    private String image;
    private int age;
    private int phoneNumber;
    private String email;
    private String location;
    private String description;
    private String gender;
    private String education;
    private String hobbies;
    private String zodiac;
    private String personality;
    private String sexualOrientation;
    private String favoriteSong;
    private String showPriority;

    public UserProfile(String name, String image, int age, int phoneNumber, String email, String location, String description, String gender, String education, String hobbies, String zodiac, String personality, String sexualOrientation, String favoriteSong, String showPriority) {
        this.name = name;
        this.image = image;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.location = location;
        this.description = description;
        this.gender = gender;
        this.education = education;
        this.hobbies = hobbies;
        this.zodiac = zodiac;
        this.personality = personality;
        this.sexualOrientation = sexualOrientation;
        this.favoriteSong = favoriteSong;
        this.showPriority = showPriority;
    }


    public UserProfile() {
    }

    protected UserProfile(Parcel in) {
        userId = in.readInt();
        name = in.readString();
        image = in.readString();
        age = in.readInt();
        phoneNumber = in.readInt();
        email = in.readString();
        location = in.readString();
        description = in.readString();
        gender = in.readString();
        education = in.readString();
        hobbies = in.readString();
        zodiac = in.readString();
        personality = in.readString();
        sexualOrientation = in.readString();
        favoriteSong = in.readString();
        showPriority = in.readString();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(userId);
        parcel.writeString(name);
        parcel.writeString(image);
        parcel.writeInt(age);
        parcel.writeInt(phoneNumber);
        parcel.writeString(email);
        parcel.writeString(location);
        parcel.writeString(description);
        parcel.writeString(gender);
        parcel.writeString(education);
        parcel.writeString(hobbies);
        parcel.writeString(zodiac);
        parcel.writeString(personality);
        parcel.writeString(sexualOrientation);
        parcel.writeString(favoriteSong);
        parcel.writeString(showPriority);
    }
}
