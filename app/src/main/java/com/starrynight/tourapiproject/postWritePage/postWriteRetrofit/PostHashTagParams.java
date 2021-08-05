package com.starrynight.tourapiproject.postWritePage.postWriteRetrofit;

import java.io.Serializable;

public class PostHashTagParams implements Serializable {
    private Long postId;

    private String hashTagName;

    public PostHashTagParams(){}

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setHashTagName(String hashTagName) {
        this.hashTagName = hashTagName;
    }

    public PostHashTagParams(Long postId, String hashTagName) {
        this.postId = postId;
        this.hashTagName = hashTagName;
    }
}
