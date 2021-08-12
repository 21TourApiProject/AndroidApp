package com.starrynight.tourapiproject.myPage.myPageRetrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @SerializedName("userId")
    Long userId;
    @SerializedName("realName")
    String realName;
    @SerializedName("sex")
    Boolean sex;
    @SerializedName("birthDay")
    String birthDay;
    @SerializedName("mobilePhoneNumber")
    String mobilePhoneNumber;
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;
    @SerializedName("nickName")
    String nickName;
    @SerializedName("profileImage")
    String profileImage;
    @SerializedName("signUpDt")
    String signUpDt;
    @SerializedName("myHashTags")
    List<MyHashTag> myHashTags;

    public User(){};

    public Long getUserId(){
        return userId;
    }

    public String getRealName() {
        return realName;
    }

    public Boolean getSex() {
        return sex;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public List<MyHashTag> getMyHashTags() {
        return myHashTags;
    }
}
