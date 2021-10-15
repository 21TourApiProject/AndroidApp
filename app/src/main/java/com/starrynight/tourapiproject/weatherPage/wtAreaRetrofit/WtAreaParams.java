package com.starrynight.tourapiproject.weatherPage.wtAreaRetrofit;

import com.google.gson.annotations.SerializedName;

public class WtAreaParams {

    @SerializedName("latitude")
    private Double latitude;

    @SerializedName("longitude")
    private Double longitude;

    @SerializedName("minLightPol")
    private Double minLightPol;

    @SerializedName("maxLightPol")
    private Double maxLightPol;

    public WtAreaParams() {
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getMaxLightPol() {
        return maxLightPol;
    }

    public Double getMinLightPol() {
        return minLightPol;
    }
}
