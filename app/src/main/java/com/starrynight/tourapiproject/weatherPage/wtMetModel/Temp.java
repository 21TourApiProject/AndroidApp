package com.starrynight.tourapiproject.weatherPage.wtMetModel;

import com.google.gson.annotations.SerializedName;

/**
 * @className : Temp
 * @description : 날씨-기온 DTO입니다.
 * @modification : 2022-09-03 (hyeonz) 주석추가
 * @author : hyeonz
 * @date : 2022-09-03
 * @version : 1.0
====개정이력(Modification Information)====
수정일        수정자        수정내용
-----------------------------------------
hyeonz       2022-09-03   주석추가
 */
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