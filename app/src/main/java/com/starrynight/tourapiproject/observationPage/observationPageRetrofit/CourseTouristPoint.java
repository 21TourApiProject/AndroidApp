package com.starrynight.tourapiproject.observationPage.observationPageRetrofit;

import com.google.gson.annotations.SerializedName;

/**
* @className : CourseTouristPoint.java
* @description : 관측지 코스 관광지 가져오는 DTO
* @modification : gyul chyoung (2022-08-30) 주석추가
* @author : 2022-08-30
* @date : gyul chyoung
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   gyul chyoung       2022-08-30       주석추가
 */
public class CourseTouristPoint {

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
    @SerializedName("useTime")
    private String useTime; //이용시간(관광지)
    @SerializedName("parking")
    private String parking; //주차시설(관광지)
    @SerializedName("treatMenu")
    private String treatMenu; //취급메뉴(음식)


    public CourseTouristPoint() {
    }

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

    public String getUseTime() {
        return useTime;
    }

    public String getParking() {
        return parking;
    }

    public String getTreatMenu() {
        return treatMenu;
    }

}
