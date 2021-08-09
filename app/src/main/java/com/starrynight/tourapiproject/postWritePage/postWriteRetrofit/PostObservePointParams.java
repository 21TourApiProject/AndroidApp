package com.starrynight.tourapiproject.postWritePage.postWriteRetrofit;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostObservePointParams implements Serializable {
    private Long postId;

    private String observePointName;

    public PostObservePointParams() {}

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setObservePointName(String observePointName) {
        this.observePointName = observePointName;
    }

    public PostObservePointParams(Long postId, String observePointName) {
        this.postId = postId;
        this.observePointName = observePointName;
    }
}
