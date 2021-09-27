package com.starrynight.tourapiproject.weatherPage.wtTodayRetrofit;

import com.google.gson.annotations.SerializedName;

public class WtTodayParams {
    @SerializedName("todayWtName1")
    private String todayWtName1;

    @SerializedName("todayWtName2")
    private String todayWtName2;

    public WtTodayParams(){};

    public String getTodayWtName1() {
        return todayWtName1;
    }

    public String getTodayWtName2() {
        return todayWtName2;
    }
}
