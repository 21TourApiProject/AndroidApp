package com.starrynight.tourapiproject.weatherPage.wtTodayRetrofit;

import com.google.gson.annotations.SerializedName;

public class WtToday {
    @SerializedName("wtTodayId")
    private Long wtTodayId;

    @SerializedName("todayWtId")
    private Long todayWtId;

    @SerializedName("todayWtName1")
    private String todayWtName1;

    @SerializedName("todayWtName2")
    private String todayWtName2;

    public WtToday() {
    }

    ;
}
