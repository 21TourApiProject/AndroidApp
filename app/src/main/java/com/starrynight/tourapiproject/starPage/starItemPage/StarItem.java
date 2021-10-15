package com.starrynight.tourapiproject.starPage.starItemPage;

import com.google.gson.annotations.SerializedName;

public class StarItem {
    @SerializedName("constId")
    private Long constId;

    @SerializedName("constName")
    private String constName;

    @SerializedName("constEng")
    private String constEng;

    public StarItem(Long constId, String constName, String constEng) {
        this.constId = constId;
        this.constName = constName;
        this.constEng = constEng;
    }

    public void setConstId(Long constId) {
        this.constId = constId;
    }

    public Long getConstId() {
        return constId;
    }

    public void setConstName(String constName) {
        this.constName = constName;
    }

    public String getConstName() {
        return constName;
    }

//    public void setConstSmallImage(String constSmallImage) {
//        this.constSmallImage = constSmallImage;
//    }
//
//    public String getConstSmallImage() {
//        return constSmallImage;
//    }

    public String getConstEng() {
        return constEng;
    }

    public void setConstEng(String constEng) {
        this.constEng = constEng;
    }
}
