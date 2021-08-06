package com.starrynight.tourapiproject.MapPage;

public class BalloonObject {
    private String  name;
    private String content;
    private String id;
    private int tag;
    private double longitude;
    private double latitude;



    //말풍선 안에 들어갈 정보 클래스
    public BalloonObject(String name, String content, String id) {
        this.name = name;
        this.content = content; //상세정보
        this.id = id;//카카오맵 연결 id
    }

    public BalloonObject(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }
}
