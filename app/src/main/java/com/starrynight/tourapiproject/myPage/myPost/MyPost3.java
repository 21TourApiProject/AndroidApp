package com.starrynight.tourapiproject.myPage.myPost;

import com.google.gson.annotations.SerializedName;

public class MyPost3 {
    @SerializedName("thumbnail")
    String thumbnail;
    @SerializedName("title")
    String title;

    public MyPost3(String thumbnail, String title) {
        this.thumbnail = thumbnail;
        this.title = title;
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

}
