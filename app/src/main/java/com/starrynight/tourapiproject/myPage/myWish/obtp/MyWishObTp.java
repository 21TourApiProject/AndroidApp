package com.starrynight.tourapiproject.myPage.myWish.obtp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyWishObTp {
    @SerializedName("itemId")
    private Long itemId;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("title")
    private String title;
    @SerializedName("address")
    private String address;
    @SerializedName("cat3Name")
    private String cat3;
    @SerializedName("overviewSim")
    private String overviewSim;
    @SerializedName("hashTagNames")
    private List<String> hashTagNames;

    public MyWishObTp(Long itemId, String thumbnail, String title, String address, String cat3, String overviewSim, List<String> hashTagNames) {
        this.itemId = itemId;
        this.thumbnail = thumbnail;
        this.title = title;
        this.address = address;
        this.cat3 = cat3;
        this.overviewSim = overviewSim;
        this.hashTagNames = hashTagNames;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCat3() {
        return cat3;
    }

    public void setCat3(String cat3) {
        this.cat3 = cat3;
    }

    public String getOverviewSim() {
        return overviewSim;
    }

    public void setOverviewSim(String overviewSim) {
        this.overviewSim = overviewSim;
    }

    public List<String> getHashTagNames() {
        return hashTagNames;
    }

    public void setHashTagNames(List<String> hashTagNames) {
        this.hashTagNames = hashTagNames;
    }
}
