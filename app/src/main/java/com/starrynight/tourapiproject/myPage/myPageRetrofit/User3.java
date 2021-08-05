package com.starrynight.tourapiproject.myPage.myPageRetrofit;

import java.io.Serializable;

public class User3 implements Serializable {
    String profileImage;

    public User3(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
