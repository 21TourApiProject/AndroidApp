package com.starrynight.tourapiproject.starPage.starPageRetrofit;

import com.google.gson.annotations.SerializedName;

/**
 * @className : Constellation
 * @description : 별자리 DTO입니다.
 * @modification : 2022-09-15 (hyeonz) 주석추가
 * @author : hyeonz
 * @date : 2022-09-15
 * @version : 1.0
====개정이력(Modification Information)====
수정일        수정자        수정내용
-----------------------------------------
2022-09-15   hyeonz      주석추가
 */
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

    @SerializedName("constFeature1")
    private String constFeature1;

    @SerializedName("constFeature2")
    private String constFeature2;

    @SerializedName("constFeature3")
    private String constFeature3;

    @SerializedName("startDate1")
    private String startDate1;

    @SerializedName("endDate1")
    private String endDate1;

    @SerializedName("startDate2")
    private String startDate2;

    @SerializedName("endDate2")
    private String endDate2;

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

    public String getStartDate1() {
        return startDate1;
    }

    public void setStartDate1(String startDate1) {
        this.startDate1 = startDate1;
    }

    public String getEndDate1() {
        return endDate1;
    }

    public void setEndDate1(String endDate1) {
        this.endDate1 = endDate1;
    }

    public String getEndDate2() {
        return endDate2;
    }

    public String getStartDate2() {
        return startDate2;
    }

    public void setEndDate2(String endDate2) {
        this.endDate2 = endDate2;
    }

    public void setStartDate2(String startDate2) {
        this.startDate2 = startDate2;
    }

    public String getConstEng() {
        return constEng;
    }

    public void setConstEng(String constEng) {
        this.constEng = constEng;
    }
}
