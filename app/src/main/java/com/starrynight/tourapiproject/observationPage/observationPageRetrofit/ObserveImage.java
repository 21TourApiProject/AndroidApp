package com.starrynight.tourapiproject.observationPage.observationPageRetrofit;

import com.google.gson.annotations.SerializedName;

public class ObserveImage {

    @SerializedName("observeImageListId")
    private Long observeImageListId;
    @SerializedName("observation")
    private Observation observation;
    @SerializedName("observationId")
    private Long observationId;
    @SerializedName("image")
    private String image;
    @SerializedName("imageSource")
    private String imageSource;

    public ObserveImage(){};

    public String getImage() {
        return image;
    }


    public String getImageSource() {
        return imageSource;
    }
}
