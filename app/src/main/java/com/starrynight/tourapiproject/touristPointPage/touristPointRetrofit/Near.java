package com.starrynight.tourapiproject.touristPointPage.touristPointRetrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Near {

    @SerializedName("contentId")
    private Long contentId; //콘텐츠 ID
    @SerializedName("firstImage")
    private String firstImage; //대표이미지 원본
    @SerializedName("title")
    private String title; //제목
    @SerializedName("addr")
    private String addr; //주소
    @SerializedName("cat3Name")
    private String cat3Name; //소분류 이름
    @SerializedName("overviewSim")
    private String overviewSim; //개요 한줄
    @SerializedName("hashTagNames")
    private List<String> hashTagNames; //개요 한줄

    public Near(Long contentId, String firstImage, String title, String addr, String cat3Name, String overviewSim, List<String> hashTagNames) {
        this.contentId = contentId;
        this.firstImage = firstImage;
        this.title = title;
        this.addr = addr;
        this.cat3Name = cat3Name;
        this.overviewSim = overviewSim;
        this.hashTagNames = hashTagNames;
    }

    public Long getContentId() {
        return contentId;
    }

    public String getFirstImage() {
        return firstImage;
    }

    public String getTitle() {
        return title;
    }

    public String getAddr() {
        return addr;
    }

    public String getCat3Name() {
        return cat3Name;
    }

    public String getOverviewSim() {
        return overviewSim;
    }

    public List<String> getHashTagNames() {
        return hashTagNames;
    }
}
