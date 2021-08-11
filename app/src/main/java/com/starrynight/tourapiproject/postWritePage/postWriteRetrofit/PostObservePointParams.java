package com.starrynight.tourapiproject.postWritePage.postWriteRetrofit;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostObservePointParams implements Serializable {

    private String observePointName;

    public PostObservePointParams() {}

    public void setObservePointName(String observePointName) {
        this.observePointName = observePointName;
    }

    public String getObservePointName() {
        return observePointName;
    }

    public PostObservePointParams(String observePointName) {
        this.observePointName = observePointName;
    }
}
