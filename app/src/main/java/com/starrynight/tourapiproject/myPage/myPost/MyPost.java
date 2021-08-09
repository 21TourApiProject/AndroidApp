package com.starrynight.tourapiproject.myPage.myPost;

import com.google.gson.annotations.SerializedName;

public class MyPost {
    @SerializedName("thumbnail")
    String thumbnail;
    @SerializedName("title")
    String title;
    @SerializedName("postId")
    Long postId;

    public MyPost(String thumbnail, String title, Long postId) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.postId = postId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }


}
