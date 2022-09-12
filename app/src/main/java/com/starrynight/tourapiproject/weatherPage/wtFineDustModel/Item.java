package com.starrynight.tourapiproject.weatherPage.wtFineDustModel;

import com.google.gson.annotations.SerializedName;

/**
 * @className : Item
 * @description : 날씨-미세먼지 Item DTO입니다.
 * @modification : 2022-09-03 (hyeonz) 주석추가
 * @author : hyeonz
 * @date : 2022-09-03
 * @version : 1.0
====개정이력(Modification Information)====
수정일        수정자        수정내용
-----------------------------------------
hyeonz       2022-09-03   주석추가
 */
public class Item {
    @SerializedName("informCode")
    private String informCode;

    @SerializedName("informData")
    private String informData;

    @SerializedName("informGrade")
    private String informGrade;

    @SerializedName("dataTime")
    private String dataTime;

    public String getInformCode() {
        return informCode;
    }

    public void setInformCode(String informCode) {
        this.informCode = informCode;
    }

    public String getInformData() {
        return informData;
    }

    public void setInformData(String informData) {
        this.informData = informData;
    }

    public String getInformGrade() {
        return informGrade;
    }

    public void setInformGrade(String informGrade) {
        this.informGrade = informGrade;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }
}
