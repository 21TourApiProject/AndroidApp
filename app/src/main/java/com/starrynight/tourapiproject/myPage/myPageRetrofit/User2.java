package com.starrynight.tourapiproject.myPage.myPageRetrofit;

import java.io.Serializable;

public class User2 implements Serializable {

    Long userId;
    String nickName;
    String profileImage;

    public User2(){};

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Long getUserId() {
        return userId;
    }

    public String getNickName() {
        return nickName;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
