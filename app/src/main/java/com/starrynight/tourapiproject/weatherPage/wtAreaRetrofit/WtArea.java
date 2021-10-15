package com.starrynight.tourapiproject.weatherPage.wtAreaRetrofit;

import com.google.gson.annotations.SerializedName;

public class WtArea {
    @SerializedName("wtAreaId")
    private Long wtAreaId;

    @SerializedName("cityName")
    private String cityName;

    @SerializedName("provName")
    private String provName;

    @SerializedName("latitude")
    private Double latitude;

    @SerializedName("longitude")
    private Double longitude;

    public WtArea() {
    }
}
