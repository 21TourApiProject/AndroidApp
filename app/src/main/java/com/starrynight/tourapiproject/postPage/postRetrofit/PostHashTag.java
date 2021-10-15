package com.starrynight.tourapiproject.postPage.postRetrofit;

import com.google.gson.annotations.SerializedName;

public class PostHashTag {

    @SerializedName("postHashTagListId")
    private Long postHashTagListId;
    @SerializedName("post")
    private Post post;
    @SerializedName("postId")
    private Long postId;
    @SerializedName("hashTagId")
    private Long hashTagId;
    @SerializedName("hashTagName")
    private String hashTagName;

    public PostHashTag() {
    }

    public String getHashTagName() {
        return hashTagName;
    }

    public Long getHashTagId() {
        return hashTagId;
    }
}
