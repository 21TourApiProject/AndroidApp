package com.starrynight.tourapiproject.observationPage.observationPageRetrofit;

import com.google.gson.annotations.SerializedName;

public class ObserveHashTag {

    @SerializedName("observeHashTagListId")
    private Long myHashTagListId;
    @SerializedName("observation")
    private Observation observation;
    @SerializedName("observationId")
    private Long observationId;
    @SerializedName("hashTagId")
    private Long hashTagId;
    @SerializedName("hashTagName")
    private String hashTagName;

    public ObserveHashTag() {
    }

    ;

    public String getHashTagName() {
        return hashTagName;
    }
}
