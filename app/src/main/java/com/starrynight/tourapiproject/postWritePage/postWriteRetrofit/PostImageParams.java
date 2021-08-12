package com.starrynight.tourapiproject.postWritePage.postWriteRetrofit;

import java.io.Serializable;

public class PostImageParams implements Serializable {
    private Long postId;

    private String imageName;

    public PostImageParams(){}

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public PostImageParams(Long postId, String imageName) {
        this.postId = postId;
        this.imageName = imageName;
    }
}
