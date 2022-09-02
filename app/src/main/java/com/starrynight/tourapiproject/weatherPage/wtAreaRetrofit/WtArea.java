package com.starrynight.tourapiproject.weatherPage.wtAreaRetrofit;

import com.google.gson.annotations.SerializedName;

/**
 * @className : WtArea
 * @description : 날씨-지역 DTO 입니다.
 * @modification : 2022-09-03 (hyeonz) 주석추가
 * @author : hyeonz
 * @date : 2022-09-03
 * @version : 1.0
====개정이력(Modification Information)====
수정일        수정자        수정내용
-----------------------------------------
hyeonz       2022-09-03   주석추가
 */
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
