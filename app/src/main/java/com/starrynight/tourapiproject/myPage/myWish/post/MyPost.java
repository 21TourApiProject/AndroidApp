package com.starrynight.tourapiproject.myPage.myWish.post;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyPost {

    @SerializedName("itemId")
    private Long itemId;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("title")
    private String title;
    @SerializedName("nickName")
    private String nickName;
    @SerializedName("profileImage")
    private String profileImage;
    @SerializedName("hashTagName")
    private List<String> hashTagName;

    public MyPost(Long itemId, String thumbnail, String title, String nickName, String profileImage, List<String> hashTagName) {
        this.itemId = itemId;
        this.thumbnail = thumbnail;
        this.title = title;
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.hashTagName = hashTagName;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

    public List<String> getHashTagName() {
        return hashTagName;
    }

    public void setHashTagName(List<String> hashTagName) {
        this.hashTagName = hashTagName;
    }
}
