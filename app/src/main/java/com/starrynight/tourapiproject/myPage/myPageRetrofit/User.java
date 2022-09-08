package com.starrynight.tourapiproject.myPage.myPageRetrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @className : User.java
 * @description : 마이페이지에서 사용되는 사용자 정보 클래스 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
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

    public User() {
    }

    ;

    public Long getUserId() {
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
