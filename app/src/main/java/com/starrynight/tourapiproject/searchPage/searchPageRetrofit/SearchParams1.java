package com.starrynight.tourapiproject.searchPage.searchPageRetrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchParams1 {
    //검색결과에 쓸 관측지 params
    @SerializedName("itemId")
    Long itemId; //관측지id
    @SerializedName("thumbnail")
    String thumbnail; //썸네일
    @SerializedName("title")
    String title; //제목
    @SerializedName("address")
    String address; //주소
    @SerializedName("contentType")
    String contentType; //분류
    @SerializedName("intro")
    String intro; //짧은 개요
    @SerializedName("longitude")
    Double longitude;  //경도
    @SerializedName("latitude")
    Double latitude; //위도
    @SerializedName("light")
    Double light; //광공해
    @SerializedName("hashTagNames")
    List<String> hashTagNames; //해시태그 배열

    public SearchParams1(Long itemId, String thumbnail, String title, String address, String contentType, String intro, Double longitude, Double latitude, Double light, List<String> hashTagNames) {
        this.itemId = itemId;
        this.thumbnail = thumbnail;
        this.title = title;
        this.address = address;
        this.contentType = contentType;
        this.intro = intro;
        this.longitude = longitude;
        this.latitude = latitude;
        this.light = light;
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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String observeType) {
        this.contentType = observeType;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLight() {
        return light;
    }

    public void setLight(Double light) {
        this.light = light;
    }

    public List<String> getHashTagNames() {
        return hashTagNames;
    }

    public void setHashTagNames(List<String> hashTagNames) {
        this.hashTagNames = hashTagNames;
    }
}
