package com.starrynight.tourapiproject.observationPage.observationPageRetrofit;

import com.google.gson.annotations.SerializedName;

public class ObserveFee {
    public Long getObserveFeeListId() {
        return observeFeeListId;
    }

    public String getFeeName() {
        return feeName;
    }

    public String getEntranceFee() {
        return entranceFee;
    }

    @SerializedName("observeFeeListId")
    private Long observeFeeListId;
    @SerializedName("observation")
    private Observation observation;
    @SerializedName("observationId")
    private Long observationId;
    @SerializedName("feeName")
    private String feeName;
    @SerializedName("entranceFee")
    private String entranceFee;

    public ObserveFee() { };
}
