package com.starrynight.tourapiproject.postWritePage.postWriteRetrofit;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PostParams implements Serializable {
    private String postContent;

    private String observeFit;

    private String yearDate;

    private String time;

    private Long userId;

    public PostParams(){}

    public String getPostContent() {
        return postContent;
    }

    public String getYearDate() {
        return yearDate;
    }

    public String getTime() {
        return time;
    }

    public Long getUserId() {
        return userId;
    }

    public String getObserveFit() {
        return observeFit;
    }

    public PostParams(String postContent, String observeFit, String yearDate, String time,Long userId) {
        this.postContent = postContent;
        this.observeFit = observeFit;
        this.yearDate = yearDate;
        this.time = time;
        this.userId = userId;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public void setYearDate(String yearDate) {
        this.yearDate = yearDate;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setObserveFit(String observeFit) {
        this.observeFit = observeFit;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
