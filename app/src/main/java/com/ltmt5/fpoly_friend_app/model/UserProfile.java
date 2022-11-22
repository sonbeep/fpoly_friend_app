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
    private int id;
    private String name;
    private String image;
    private int age;
    private String location;
    private String description;
    private String gender;
    private String major;
    private String hobbies;

    public UserProfile(String name, String image, int age, String location, String description, String gender, String major, String hobbies) {
        this.name = name;
        this.image = image;
        this.age = age;
        this.location = location;
        this.description = description;
        this.gender = gender;
        this.major = major;
        this.hobbies = hobbies;
    }

    protected UserProfile(Parcel in) {
        id = in.readInt();
        name = in.readString();
        image = in.readString();
        age = in.readInt();
        location = in.readString();
        description = in.readString();
        gender = in.readString();
        major = in.readString();
        hobbies = in.readString();
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

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(image);
        parcel.writeInt(age);
        parcel.writeString(location);
        parcel.writeString(description);
        parcel.writeString(gender);
        parcel.writeString(major);
        parcel.writeString(hobbies);
    }
}
