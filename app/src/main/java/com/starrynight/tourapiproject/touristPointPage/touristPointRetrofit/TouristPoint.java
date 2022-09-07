package com.starrynight.tourapiproject.touristPointPage.touristPointRetrofit;

import com.google.gson.annotations.SerializedName;


/**
 * @className : TouristPoint.java
 * @description : 관광지 정보 클래스 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
public class TouristPoint {

    @SerializedName("contentTypeId")
    private Long contentTypeId;
    @SerializedName("firstImage")
    private String firstImage; //대표이미지 원본
    @SerializedName("title")
    private String title; //제목
    @SerializedName("cat3Name")
    private String cat3Name; //소분류 이름
    @SerializedName("overview")
    private String overview; //개요
    @SerializedName("addr")
    private String addr; //주소
    @SerializedName("tel")
    private String tel; //전화번호
    @SerializedName("useTime")
    private String useTime; //이용시간(관광지)
    @SerializedName("restDate")
    private String restDate; //휴무일(관광지)
    @SerializedName("expGuide")
    private String expGuide; //체험안내(관광지)
    @SerializedName("parking")
    private String parking; //주차시설(관광지)
    @SerializedName("chkPet")
    private String chkPet; //반려동물(관광지)
    @SerializedName("homePage")
    private String homePage; //홈페이지(관광지)
    @SerializedName("mapX")
    private Double mapX; //경도
    @SerializedName("mapY")
    private Double mapY; //위도
    @SerializedName("overviewSim")
    private String overviewSim; //한줄개요

    public TouristPoint() {
    }

    ;

    public Long getContentTypeId() {
        return contentTypeId;
    }

    public String getFirstImage() {
        return firstImage;
    }

    public String getTitle() {
        return title;
    }

    public String getCat3Name() {
        return cat3Name;
    }

    public String getOverview() {
        return overview;
    }

    public String getAddr() {
        return addr;
    }

    public String getTel() {
        return tel;
    }

    public String getUseTime() {
        return useTime;
    }

    public String getRestDate() {
        return restDate;
    }

    public String getExpGuide() {
        return expGuide;
    }

    public String getParking() {
        return parking;
    }

    public String getChkPet() {
        return chkPet;
    }

    public String getHomePage() {
        return homePage;
    }

    public Double getMapX() {
        return mapX;
    }

    public Double getMapY() {
        return mapY;
    }

    public String getOverviewSim() {
        return overviewSim;
    }
}
