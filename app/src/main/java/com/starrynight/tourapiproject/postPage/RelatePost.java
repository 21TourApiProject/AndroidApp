package com.starrynight.tourapiproject.postPage;

import android.graphics.Bitmap;
/**
* @className : RelatePost
* @description : 관련 게시물 클래스 입니다.
* @modification : jinhyeok (2022-08-12) 주석 수정
* @author : 2022-08-12
* @date : jinhyeok
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   jinhyeok      2022-08-12       주석 수정

 */
public class RelatePost {
    Bitmap img; //지금은 테스트를 위해 drawble에 있는 이미지를 넣으려고 int를 썼지만, db data를 이용하려면 Bitmap로 바꿔야할듯
    String title;

    public RelatePost(Bitmap img, String title) {
        this.img = img;
        this.title = title;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
