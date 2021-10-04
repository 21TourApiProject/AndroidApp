package com.starrynight.tourapiproject.myPage.myWish.post;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyPost {

    @SerializedName("postId")
    private Long postId;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("title")
    private String title;
    @SerializedName("writer")
    private String nickName;
    @SerializedName("profileImage")
    private String profileImage;
    @SerializedName("hashTagNames")
    private List<String> hashTagNames;

    public MyPost(Long postId, String thumbnail, String title, String nickName, String profileImage, List<String> hashTagNames) {
        this.postId = postId;
        this.thumbnail = thumbnail;
        this.title = title;
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.hashTagNames = hashTagNames;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public List<String> getHashTagNames() {
        return hashTagNames;
    }

    public void setHashTagNames(List<String> hashTagNames) {
        this.hashTagNames = hashTagNames;
    }
}
