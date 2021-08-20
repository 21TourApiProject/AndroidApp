package com.starrynight.tourapiproject.starPage.starPageRetrofit;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

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

    @SerializedName("fallConstId")
    private String fallConstId;

    @SerializedName("winterConstId")
    private String winterConstId;

    @SerializedName("constBestMonth")
    private String constBestMonth;

    @SerializedName("constPersonality")
    private String constPersonality;

    @SerializedName("startDate")
    private LocalDate startDate;

    @SerializedName("endDate")
    private LocalDate endDate;

    public Constellation() {
    }

    ;

//    public Constellation(Long constId, String constName, String constImage){
//        this.constId = constId;
//        this.constName = constName;
//        this.constImage = constImage;
//    };

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

    public String getFallConstId() {
        return fallConstId;
    }

    public void setFallConstId(String fallConstId) {
        this.fallConstId = fallConstId;
    }

    public String getWinterConstId() {
        return winterConstId;
    }

    public void setWinterConstId(String winterConstId) {
        this.winterConstId = winterConstId;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
