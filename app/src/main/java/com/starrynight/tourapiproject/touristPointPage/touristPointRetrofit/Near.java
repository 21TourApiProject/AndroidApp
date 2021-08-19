package com.starrynight.tourapiproject.touristPointPage.touristPointRetrofit;

public class Near {
    private Long contentId; //콘텐츠 ID
    private String firstImage; //대표이미지 원본
    private String title; //제목
    private String addr; //주소
    private String cat3Name; //소분류 이름
    private String overviewSimple; //개요 한줄

    public Near(){};

    public Near(Long contentId, String firstImage, String title, String addr, String overviewSimple, String cat3Name) {
        this.contentId = contentId;
        this.firstImage = firstImage;
        this.title = title;
        this.addr = addr;
        this.cat3Name = cat3Name;
        this.overviewSimple = overviewSimple;

    }

    public String getAddr() {
        return addr;
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

    public String getOverviewSimple() {
        return overviewSimple;
    }

    public String getCat3Name() {
        return cat3Name;
    }
}
