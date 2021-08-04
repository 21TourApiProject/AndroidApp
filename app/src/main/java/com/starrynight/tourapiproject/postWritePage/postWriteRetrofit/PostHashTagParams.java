package com.starrynight.tourapiproject.postWritePage.postWriteRetrofit;

import java.io.Serializable;

public class PostHashTagParams implements Serializable {
    private Long userId;

    private String hashTagName;

    public PostHashTagParams(){}

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setHashTagName(String hashTagName) {
        this.hashTagName = hashTagName;
    }

    public PostHashTagParams(Long userId, String hashTagName) {
        this.userId = userId;
        this.hashTagName = hashTagName;
    }
}
