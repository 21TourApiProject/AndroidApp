package com.starrynight.tourapiproject.postItemPage;
/**
* @className : post_point_item
* @description : 검색 페이지, 게시물 페이지(관련 게시물)에 띄울 게시물 아이템 입니다.
* @modification : jinhyeok (2022-08-14) 주석 수정
* @author : 2022-08-14
* @date : jinhyeok
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   jinhyeok      2022-08-14       주석 수정

 */
public class post_point_item {
    String tourname;
    String tourimage;

    public post_point_item(String tourname, String tourimage) {
        this.tourname = tourname;
        this.tourimage = tourimage;
    }

    public String getTourimage() {
        return tourimage;
    }

    public void setTourimage(String tourimage) {
        this.tourimage = tourimage;
    }

    public String getTourname() {
        return tourname;
    }

    public void setTourname(String tourname) {
        this.tourname = tourname;
    }

}
