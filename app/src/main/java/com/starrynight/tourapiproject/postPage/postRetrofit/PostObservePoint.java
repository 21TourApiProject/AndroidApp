package com.starrynight.tourapiproject.postPage.postRetrofit;

import com.google.gson.annotations.SerializedName;

public class PostObservePoint {
    @SerializedName("postObservePointId")
    private Long postObservePointId;
    @SerializedName("observePointName")
    private String observePointName;

    public Long getPostObservePointId() {
        return postObservePointId;
    }

    public String getObservePointName() {
        return observePointName;
    }
}
