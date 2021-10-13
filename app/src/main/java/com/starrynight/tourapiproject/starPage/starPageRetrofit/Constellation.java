package com.starrynight.tourapiproject.starPage.starPageRetrofit;

import com.google.gson.annotations.SerializedName;

public class Constellation {
    @SerializedName("constId")
    private Long constId;

    @SerializedName("constName")
    private String constName;

    @SerializedName("constStory")
    private String constStory;

    @SerializedName("constMtd")
    private String constMtd;

    @SerializedName("constBestMonth")
    private String constBestMonth;

    @SerializedName("constPersonality")
    private String constPersonality;

    @SerializedName("constTravel")
    private String constTravel;

    @SerializedName("constPeriod")
    private String constPeriod;

    @SerializedName("constFeature1")
    private String constFeature1;

    @SerializedName("constFeature2")
    private String constFeature2;

    @SerializedName("constFeature3")
    private String constFeature3;

    @SerializedName("startDate")
    private String startDate;

    @SerializedName("endDate")
    private String endDate;

    @SerializedName("constGuard")
    private String constGuard;

    @SerializedName("startDate1")
    private String startDate1;

    @SerializedName("endDate2")
    private String endDat2;

    @SerializedName("constEng")
    private String constEng;

    public Constellation() {
    }

    public Long getConstId() {
        return constId;
    }

    public void setConstId(Long constId) {
        this.constId = constId;
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName = constName;
    }

//    public String getConstBigImage() {
//        return constBigImage;
//    }
//
//    public void setConstBigImage(String constBigImage) {
//        this.constBigImage = constBigImage;
//    }
//
//    public String getConstSmallImage() {
//        return constSmallImage;
//    }
//
//    public void setConstSmallImage(String constSmallImage) {
//        this.constSmallImage = constSmallImage;
//    }

    public String getConstStory() {
        return constStory;
    }

    public void setConstStory(String constStory) {
        this.constStory = constStory;
    }

    public String getConstMtd() {
        return constMtd;
    }

    public void setConstMtd(String constMtd) {
        this.constMtd = constMtd;
    }

    public String getConstBestMonth() {
        return constBestMonth;
    }

    public void setConstBestMonth(String constBestMonth) {
        this.constBestMonth = constBestMonth;
    }

    public String getConstPersonality() {
        return constPersonality;
    }

    public void setConstPersonality(String constPersonality) {
        this.constPersonality = constPersonality;
    }

    public String getConstTravel() {
        return constTravel;
    }

    public void setConstTravel(String constTravel) {
        this.constTravel = constTravel;
    }

    public String getConstPeriod() {
        return constPeriod;
    }

    public void setConstPeriod(String constPeriod) {
        this.constPeriod = constPeriod;
    }

    public String getConstFeature1() {
        return constFeature1;
    }

    public String getConstFeature2() {
        return constFeature2;
    }

    public String getConstFeature3() {
        return constFeature3;
    }

    public void setConstFeature1(String constFeature1) {
        this.constFeature1 = constFeature1;
    }

    public void setConstFeature2(String constFeature2) {
        this.constFeature2 = constFeature2;
    }

    public void setConstFeature3(String constFeature3) {
        this.constFeature3 = constFeature3;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getConstGuard() {
        return constGuard;
    }

    public void setConstGuard(String constGuard) {
        this.constGuard = constGuard;
    }

    public String getEndDat2() {
        return endDat2;
    }

    public String getStartDate1() {
        return startDate1;
    }

    public void setEndDat2(String endDat2) {
        this.endDat2 = endDat2;
    }

    public void setStartDate1(String startDate1) {
        this.startDate1 = startDate1;
    }

    public String getConstEng() {
        return constEng;
    }

    public void setConstEng(String constEng) {
        this.constEng = constEng;
    }
}
