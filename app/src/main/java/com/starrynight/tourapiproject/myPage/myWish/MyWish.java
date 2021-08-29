package com.starrynight.tourapiproject.myPage.myWish;

import com.google.gson.annotations.SerializedName;

public class MyWish {

    @SerializedName("thumbnail")
    String thumbnail;
    @SerializedName("title")
    String title;
    @SerializedName("wishType")
    Integer wishType;

    public MyWish(String thumbnail, String title, Integer wishType) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.wishType = wishType;
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

    public Integer getWishType() {
        return wishType;
    }

    public void setWishType(Integer wishType) {
        this.wishType = wishType;
    }
}
