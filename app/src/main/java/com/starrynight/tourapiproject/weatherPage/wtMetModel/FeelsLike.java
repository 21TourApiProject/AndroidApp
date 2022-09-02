package com.starrynight.tourapiproject.weatherPage.wtMetModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @className : FeelsLike
 * @description : 체감 날씨 DTO입니다.
 * @modification : 2022-09-03 (hyeonz) 주석추가
 * @author : hyeonz
 * @date : 2022-09-03
 * @version : 1.0
====개정이력(Modification Information)====
수정일        수정자        수정내용
-----------------------------------------
hyeonz       2022-09-03   주석추가
 */
public class FeelsLike {

    @SerializedName("day")
    @Expose
    private String day;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
