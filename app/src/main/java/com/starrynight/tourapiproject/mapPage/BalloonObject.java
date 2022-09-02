package com.starrynight.tourapiproject.mapPage;

import java.io.Serializable;
import java.util.List;

/**
* @className : BalloonObject.java
* @description : 지도 하단의 장소설명 클래스
* @modification : gyul chyoung (2022-08-30) 주석추가
* @author : 2022-08-30
* @date : gyul chyoung
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   gyul chyoung       2022-08-30       주석추가
 */

public class BalloonObject implements Serializable {
    public BalloonObject() {
    }

    private String kakaoId; //카카오 맵으로 이동 할 id(안쓸수도)
    private int tag;    //1관측지, 2관광지
    private double longitude;   //경도
    private double latitude;    //위도
    //말풍선 속 내용
    private String name;
    private String address;
    private String point_type;  //관광지, 관측지타입
    private String intro;   //한줄소개
    private Long id;    //관측지나 관광지 id
    private String image;
    private List<String> hashtags;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    public String getKakaoId() {
        return kakaoId;
    }

    public void setKakaoId(String kakaoId) {
        this.kakaoId = kakaoId;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPoint_type() {
        return point_type;
    }

    public void setPoint_type(String point_type) {
        this.point_type = point_type;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }


    public BalloonObject(Long id, int tag, double longitude, double latitude, String name, String address, String point_type, String intro) {
        this.id = id;
        this.tag = tag;
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.address = address;
        this.point_type = point_type;
        this.intro = intro;
    }

    //말풍선 안에 들어갈 정보 클래스


}
