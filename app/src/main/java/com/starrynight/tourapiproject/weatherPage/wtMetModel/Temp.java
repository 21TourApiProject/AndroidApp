package com.starrynight.tourapiproject.weatherPage.wtMetModel;

import com.google.gson.annotations.SerializedName;

public class Temp {
    @SerializedName("day")
    private Double day;

    @SerializedName("min")
    private Double min;

    @SerializedName("max")
    private Double max;

    public Double getDay() {
        return day;
    }

    public void setDay(Double day) {
        this.day = day;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }
}