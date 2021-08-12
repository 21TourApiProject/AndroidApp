package com.starrynight.tourapiproject.postWritePage.postWriteRetrofit;

import java.io.Serializable;

public class PostHashTagParams implements Serializable {

    private String hashTagName;

    public PostHashTagParams(){}

    public void setHashTagName(String hashTagName) {
        this.hashTagName = hashTagName;
    }

    public String getHashTagName() {
        return hashTagName;
    }

    public PostHashTagParams(String hashTagName) {
        this.hashTagName = hashTagName;
    }
}
