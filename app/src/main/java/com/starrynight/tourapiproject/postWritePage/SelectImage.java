package com.starrynight.tourapiproject.postWritePage;

import android.graphics.Bitmap;
/**
* @className : SelectImage
* @description : 게시물 작성 시 선택한 이미지 클래스입니다.
* @modification : jinhyeok (2022-08-14) 주석 수정
* @author : 2022-08-14
* @date : jinhyeok
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   jinhyeok      2022-08-14       주석 수정

 */
public class SelectImage {
    Bitmap img;
    int id;

    public SelectImage(Bitmap img, int id) {
        this.img = img;
        this.id = id;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
