package com.starrynight.tourapiproject.myPage.myPageRetrofit;

import java.io.Serializable;

public class User2 implements Serializable {

    String nickName;
    String profileImage;

    public User2(){};

    public User2(String nickName, String profileImage) {
        this.nickName = nickName;
        this.profileImage = profileImage;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getNickName() {
        return nickName;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
