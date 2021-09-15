package com.starrynight.tourapiproject.weatherPage.wtFineDustModel;

import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("informCode")
    private String informCode;

    @SerializedName("informData")
    private String informData;

    @SerializedName("informGrade")
    private String informGrade;

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
}
