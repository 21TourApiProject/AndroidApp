package com.starrynight.tourapiproject.starPage.starPageRetrofit;

import com.google.gson.annotations.SerializedName;

public class Constellation {
    @SerializedName("constId")
    private Long constId;

    @SerializedName("constName")
    private String constName;

    @SerializedName("constImage")
    private String constImage;

    @SerializedName("constStory")
    private String constStory;

    @SerializedName("springConstMtd")
    private String springConstMtd;

    @SerializedName("summerConstMtd")
    private String summerConstMtd;

    @SerializedName("fallConstMtd")
    private String fallConstMtd;

    @SerializedName("winterConstMtd")
    private String winterConstMtd;

    @SerializedName("constBestMonth")
    private String constBestMonth;

    @SerializedName("constPersonality")
    private String constPersonality;

    @SerializedName("constPeriod")
    private String constPeriod;

    @SerializedName("constFeature")
    private String constFeature;

    @SerializedName("startDate")
    private String startDate;

    @SerializedName("endDate")
    private String endDate;

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

    public String getConstImage() {
        return constImage;
    }

    public void setConstImage(String constImage) {
        this.constImage = constImage;
    }

    public String getConstStory() {
        return constStory;
    }

    public void setConstStory(String constStory) {
        this.constStory = constStory;
    }

    public String getSpringConstMtd() {
        return springConstMtd;
    }

    public void setSpringConstMtd(String springConstMtd) {
        this.springConstMtd = springConstMtd;
    }

    public String getSummerConstMtd() {
        return summerConstMtd;
    }

    public void setSummerConstMtd(String summerConstMtd) {
        this.summerConstMtd = summerConstMtd;
    }

    public String getFallConstMtd() {
        return fallConstMtd;
    }

    public void setFallConstMtd(String fallConstMtd) {
        this.fallConstMtd = fallConstMtd;
    }

    public String getWinterConstMtd() {
        return winterConstMtd;
    }

    public void setWinterConstMtd(String winterConstMtd) {
        this.winterConstMtd = winterConstMtd;
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

    public String getConstPeriod() {
        return constPeriod;
    }

    public void setConstPeriod(String constPeriod) {
        this.constPeriod = constPeriod;
    }

    public String getConstFeature() {
        return constFeature;
    }

    public void setConstFeature(String constFeature) {
        this.constFeature = constFeature;
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
}
