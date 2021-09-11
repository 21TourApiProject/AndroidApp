package com.starrynight.tourapiproject.postPage.postRetrofit;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Post {
    @SerializedName("postId")
    Long postId;
    @SerializedName("postContent")
    String postContent;
    @SerializedName("postTitle")
    String postTitle;
    @SerializedName("optionHashTag")
    String optionHashTag;
    @SerializedName("optionObservation")
    String optionObservation;
    @SerializedName("yearDate")
    String yearDate;
    @SerializedName("time")
    String time;
    @SerializedName("postHashTag")
    List<PostHashTag> postHashTags;
    @SerializedName("postImage")
    List<PostImage> postImages;
    @SerializedName("userId")
    Long userId;
    @SerializedName("observationId")
    Long observationId;

    public Post(){}

    public String getOptionHashTag() {
        return optionHashTag;
    }

    public String getOptionObservation() {
        return optionObservation;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public Long getPostId() {
        return postId;
    }

    public Long getObservationId() {
        return observationId;
    }

    public String getYearDate() {
        return yearDate;
    }

    public String getTime() {
        return time;
    }

    public List<PostImage> getPostImages() {
        return postImages;
    }

    public Long getUserId() {
        return userId;
    }
    public List<PostHashTag> getPostHashTags() {return postHashTags;}
    public List<PostImage>getPostImage(){return postImages;}

}
