package com.starrynight.tourapiproject.postPage.postRetrofit;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.List;

public class Post {
    @SerializedName("postId")
    private Long postId;
    @SerializedName("postContent")
    private String postContent;
    @SerializedName("yearDate")
    private LocalDateTime yearDate;
    @SerializedName("time")
    private LocalDateTime time;
    @SerializedName("userId")
    private Long userId;
    @SerializedName("postObservePointId")
    private Long postObservePointId;
    @SerializedName("postHashTag")
    List<PostHashTag> postHashTags;
    @SerializedName("postImage")
    List<PostImage> postImages;

    public Post(){}

    public String getPostContent() {
        return postContent;
    }

    public Long getPostId() {
        return postId;
    }

    public Long getPostObservePointId() {
        return postObservePointId;
    }

    public LocalDateTime getYearDate() {
        return yearDate;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Long getUserId() {
        return userId;
    }
    public List<PostHashTag> getPostHashTags() {return postHashTags;}
    public List<PostImage>getPostImage(){return postImages;}

}
