package com.starrynight.tourapiproject.weatherPage.wtMetModel;

import com.google.gson.annotations.SerializedName;

public class Temp {
    @SerializedName("day")
    private String day;

    @SerializedName("min")
    private String min;

    @SerializedName("max")
    private String max;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }
}