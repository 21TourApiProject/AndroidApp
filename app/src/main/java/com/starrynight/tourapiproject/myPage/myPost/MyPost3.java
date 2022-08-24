package com.starrynight.tourapiproject.myPage.myPost;

import com.google.gson.annotations.SerializedName;
/**
* @className : MyPost3
* @description : 내가 쓴 게시물 3개 미리보기 4아이템 클래스 입니다.
* @modification : jinhyeok (2022-08-16) 주석 수정
* @author : 2022-08-16
* @date : jinhyeok
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   jinhyeok      2022-08-16       주석 수정

 */
public class MyPost3 {
    @SerializedName("thumbnail")
    String thumbnail;
    @SerializedName("title")
    String title;

    public MyPost3(String thumbnail, String title) {
        this.thumbnail = thumbnail;
        this.title = title;
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

}
