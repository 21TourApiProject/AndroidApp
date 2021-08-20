package com.starrynight.tourapiproject.starPage.starItemPage;

import com.google.gson.annotations.SerializedName;

public class StarItem {
    @SerializedName("constId")
    private Long constId;

    @SerializedName("constName")
    private String constName;

    @SerializedName("constImage")
    private String constImage;

    public StarItem() {
    }

    public StarItem(Long constId, String constName, String constImage) {
        this.constId = constId;
        this.constName = constName;
        this.constImage = constImage;
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

    public void setConstImage(String constImage) {
        this.constImage = constImage;
    }

    public String getConstImage() {
        return constImage;
    }
}
