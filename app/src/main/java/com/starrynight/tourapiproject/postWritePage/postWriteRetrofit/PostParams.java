package com.starrynight.tourapiproject.postWritePage.postWriteRetrofit;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PostParams implements Serializable {
    private String postContent;

    private Bitmap postImage;

    private String observeFit;

    private String yearDate;

    private String time;

    private Long userId;

    public PostParams(){}

    public PostParams(String postContent, Bitmap postImage, String observeFit, String yearDate, String time) {
        this.postContent = postContent;
        this.postImage = postImage;
        this.observeFit = observeFit;
        this.yearDate = yearDate;
        this.time = time;
    }

    public void setObserveFit(String observeFit) {
        this.observeFit = observeFit;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
