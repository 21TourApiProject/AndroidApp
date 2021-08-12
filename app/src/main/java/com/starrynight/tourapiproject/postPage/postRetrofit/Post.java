package com.starrynight.tourapiproject.postPage.postRetrofit;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.List;

public class Post {
    @SerializedName("postContent")
    private String postContent;
    @SerializedName("observeFit")
    private String observeFit;
    @SerializedName("yearDate")
    private LocalDateTime yearDate;
    @SerializedName("time")
    private LocalDateTime time;
    @SerializedName("userId")
    private Long userId;
    @SerializedName("postHashTag")
    List<PostHashTag> postHashTags;
    @SerializedName("postImage")
    List<PostImage> postImages;

    public Post(){}

    public String getPostContent() {
        return postContent;
    }

    public String getObserveFit() {
        return observeFit;
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
