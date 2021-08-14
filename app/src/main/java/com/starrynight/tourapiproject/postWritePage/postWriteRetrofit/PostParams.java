package com.starrynight.tourapiproject.postWritePage.postWriteRetrofit;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PostParams implements Serializable {
    private String postContent;

    private String yearDate;

    private String time;

    private String postTitle;

    private Long userId;

    private Long postObservePointId;

    public PostParams(){}

    public String getPostContent() {
        return postContent;
    }

    public String getPostTitle() {
        return postTitle;
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

    public Long getPostObservePointId() {
        return postObservePointId;
    }

    public PostParams(String postContent, String yearDate, String time, String postTitle, Long userId, Long postObservePointId) {
        this.postContent = postContent;
        this.yearDate = yearDate;
        this.time = time;
        this.postTitle = postTitle;
        this.userId = userId;
        this.postObservePointId = postObservePointId;
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

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public void setPostObservePointId(Long postObservePointId) {
        this.postObservePointId = postObservePointId;
    }
}
