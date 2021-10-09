package com.starrynight.tourapiproject.searchPage.searchPageRetrofit;

import com.google.gson.annotations.SerializedName;

public class SearchFirst {

    @SerializedName("observationId")
    Long observationId;
    @SerializedName("observationName")
    String observationName;
    @SerializedName("observationImage")
    String observationImage;

    public Long getObservationId() {
        return observationId;
    }

    public void setObservationId(Long observationId) {
        this.observationId = observationId;
    }

    public String getObservationName() {
        return observationName;
    }

    public void setObservationName(String observationName) {
        this.observationName = observationName;
    }

    public String getObservationImage() {
        return observationImage;
    }

    public void setObservationImage(String observationImage) {
        this.observationImage = observationImage;
    }

    public SearchFirst(Long observationId, String observationName, String observationImage) {
        this.observationId = observationId;
        this.observationName = observationName;
        this.observationImage = observationImage;
    }
}
