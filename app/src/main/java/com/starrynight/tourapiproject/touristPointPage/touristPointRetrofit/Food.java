package com.starrynight.tourapiproject.touristPointPage.touristPointRetrofit;

import com.google.gson.annotations.SerializedName;


/**
 * @className : Food.java
 * @description : 음식점 정보 클래스 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
public class Food {

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
    @SerializedName("openTimeFood")
    private String openTimeFood; //영업시간(음식)
    @SerializedName("restDateFood")
    private String restDateFood; //휴무일(음식)
    @SerializedName("firstMenu")
    private String firstMenu; //대표메뉴(음식)
    @SerializedName("treatMenu")
    private String treatMenu; //취급메뉴(음식)
    @SerializedName("packing")
    private String packing; //포장(음식)
    @SerializedName("parkingFood")
    private String parkingFood; //주차시설(음식)
    @SerializedName("mapX")
    private Double mapX; //경도
    @SerializedName("mapY")
    private Double mapY; //위도
    @SerializedName("overviewSim")
    private String overviewSim; //한줄개요

    public Food() {
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

    public String getOpenTimeFood() {
        return openTimeFood;
    }

    public String getRestDateFood() {
        return restDateFood;
    }

    public String getFirstMenu() {
        return firstMenu;
    }

    public String getTreatMenu() {
        return treatMenu;
    }

    public String getPacking() {
        return packing;
    }

    public String getParkingFood() {
        return parkingFood;
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
