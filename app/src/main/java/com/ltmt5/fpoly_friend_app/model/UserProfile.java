package com.ltmt5.fpoly_friend_app.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class UserProfile implements Parcelable {

    private int userId;
    private String name;
    private int age;
    private String gender;
    private String education;
    private List<String> hobbies;
    private List<String> image;

    private String description;
    private String location;
    private String zodiac;
    private String personality;
    private String sexualOrientation;
    private String favoriteSong;
    private String showPriority;

    private Bitmap avt;


    public UserProfile() {
    }


    public UserProfile(String name, int age, String gender, String education, List<String> hobbies, List<String> image, String description, String location, String zodiac, String personality, String sexualOrientation, String favoriteSong, String showPriority) {
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
    }

    public UserProfile(String name, int age, String gender, String education, List<String> hobbies, List<String> image) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.education = education;
        this.hobbies = hobbies;
        this.image = image;
    }

    protected UserProfile(Parcel in) {
        userId = in.readInt();
        name = in.readString();
        age = in.readInt();
        gender = in.readString();
        education = in.readString();
        hobbies = in.createStringArrayList();
        image = in.createStringArrayList();
        description = in.readString();
        location = in.readString();
        zodiac = in.readString();
        personality = in.readString();
        sexualOrientation = in.readString();
        favoriteSong = in.readString();
        showPriority = in.readString();
        avt = in.readParcelable(Bitmap.class.getClassLoader());
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(userId);
        parcel.writeString(name);
        parcel.writeInt(age);
        parcel.writeString(gender);
        parcel.writeString(education);
        parcel.writeStringList(hobbies);
        parcel.writeStringList(image);
        parcel.writeString(description);
        parcel.writeString(location);
        parcel.writeString(zodiac);
        parcel.writeString(personality);
        parcel.writeString(sexualOrientation);
        parcel.writeString(favoriteSong);
        parcel.writeString(showPriority);
        parcel.writeParcelable(avt, i);
    }

    public Bitmap getAvt() {
        return avt;
    }

    public void setAvt(Bitmap avt) {
        this.avt = avt;
    }
}
