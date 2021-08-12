package com.starrynight.tourapiproject.signUpPage.signUpRetrofit;

import java.io.Serializable;

public class MyHashTagParams implements Serializable {

    private String hashTagName;

    public MyHashTagParams(){}

    public MyHashTagParams(String hashTagName) {
        this.hashTagName = hashTagName;
    }

    public void setHashTagName(String hashTagName) {
        this.hashTagName = hashTagName;
    }

}
