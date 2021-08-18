package com.starrynight.tourapiproject.postWritePage.postWriteRetrofit;

import java.io.Serializable;

public class PostImageParams implements Serializable {

    private String imageName;

    public PostImageParams(){}

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public PostImageParams(String imageName) {
        this.imageName = imageName;
    }
}
