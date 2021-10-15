package com.starrynight.tourapiproject.signUpPage.signUpRetrofit;


import java.io.Serializable;

public class KakaoUserParams implements Serializable {

    private String nickName;

    private Boolean sex;

    private String birthDay;

    private String mobilePhoneNumber;

    private String email;

    private String profileImage;

    private String ageRange;

    public KakaoUserParams() {
    }

    public KakaoUserParams(String realName, Boolean sex, String birthDay, String email) {
        this.nickName = realName;
        this.sex = sex;
        this.birthDay = birthDay;
        this.email = email;
        this.mobilePhoneNumber = "";
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }


    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public String getEmail() {
        return email;
    }
}