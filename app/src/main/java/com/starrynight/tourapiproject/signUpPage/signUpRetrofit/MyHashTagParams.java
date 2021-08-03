package com.starrynight.tourapiproject.signUpPage.signUpRetrofit;

import java.io.Serializable;

public class MyHashTagParams implements Serializable {
    private String mobilePhoneNumber;

    private String hashTagName;

    public MyHashTagParams() {
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public void setHashTagName(String hashTagName) {
        this.hashTagName = hashTagName;
    }

    public MyHashTagParams(String mobilePhoneNumber, String hashTagName) {
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.hashTagName = hashTagName;
    }
}
