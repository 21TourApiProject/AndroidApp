package com.starrynight.tourapiproject.postWritePage.postWriteRetrofit;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PostParams implements Serializable {
    private String postContent;

    private String yearDate;

    private String time;

    private String postTitle;

    private String optionHashTag;

    private String optionObservation;

    private Long userId;

    public PostParams(){}

    public String getOptionHashTag() {
        return optionHashTag;
    }

    public String getOptionObservation() {
        return optionObservation;
    }

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


    public PostParams(String postContent, String yearDate, String time, String postTitle, String optionHashTag, String optionObservation, Long userId) {
        this.postContent = postContent;
        this.yearDate = yearDate;
        this.time = time;
        this.postTitle = postTitle;
        this.optionHashTag = optionHashTag;
        this.optionObservation = optionObservation;
        this.userId = userId;
    }

    public void setOptionHashTag(String optionHashTag) {
        this.optionHashTag = optionHashTag;
    }

    public void setOptionObservation(String optionObservation) {
        this.optionObservation = optionObservation;
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

    }
