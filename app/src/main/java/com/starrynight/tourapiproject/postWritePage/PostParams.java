package com.starrynight.tourapiproject.postWritePage;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PostParams implements Serializable {

    private String postContent;

    private String postImage;

    private String observeFit;

    private LocalDateTime yearDate;

    private LocalDateTime time;

    private Long userId;

    public PostParams(){}

    public PostParams(String postContent, String postImage, String observeFit, LocalDateTime yearDate, LocalDateTime time, Long userId) {
        this.postContent = postContent;
        this.postImage = postImage;
        this.observeFit = observeFit;
        this.yearDate = yearDate;
        this.time = time;
        this.userId = userId;
    }
}
