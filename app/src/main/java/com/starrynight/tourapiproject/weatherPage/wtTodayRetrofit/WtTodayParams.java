package com.starrynight.tourapiproject.weatherPage.wtTodayRetrofit;

import com.google.gson.annotations.SerializedName;

/**
 * @className : WtTodayParams
 * @description : 오늘의 날씨 DTO입니다.
 * @modification : 2022-09-03 (hyeonz) 주석추가
 * @author : hyeonz
 * @date : 2022-09-03
 * @version : 1.0
====개정이력(Modification Information)====
수정일        수정자        수정내용
-----------------------------------------
hyeonz       2022-09-03   주석추가
 */
public class WtTodayParams {
    @SerializedName("todayWtName1")
    private String todayWtName1;

    @SerializedName("todayWtName2")
    private String todayWtName2;

    public WtTodayParams() {
    }

    ;

    public String getTodayWtName1() {
        return todayWtName1;
    }

    public String getTodayWtName2() {
        return todayWtName2;
    }
}
