package com.starrynight.tourapiproject.postPage.postRetrofit;

import com.google.gson.annotations.SerializedName;

public class PostImage {
    @SerializedName("postImageListId")
    private Long postImageListId;
    @SerializedName("post")
    private Post post;
    @SerializedName("postId")
    private Long postId;
    @SerializedName("imageName")
    private String imageName;
    @SerializedName("postObservePointId")
    private Long postObservePointId;
    public PostImage(){}
}
