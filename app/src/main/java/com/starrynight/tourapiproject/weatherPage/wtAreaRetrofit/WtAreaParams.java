package com.starrynight.tourapiproject.weatherPage.wtAreaRetrofit;

import com.google.gson.annotations.SerializedName;

public class WtAreaParams {

    @SerializedName("latitude")
    private Double latitude;

    @SerializedName("longitude")
    private Double longitude;

    public WtAreaParams(){}

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
