package com.starrynight.tourapiproject.postItemPage;

import android.widget.ImageView;
/**
* @className : PostWriteHashTagItem2
* @description : 게시물 작성 페이지에 필요한 해시태그 아이템입니다.
* @modification : jinhyeok (2022-08-14) 주석 수정
* @author : 2022-08-14
* @date : jinhyeok
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   jinhyeok      2022-08-14       주석 수정

 */
public class PostWriteHashTagItem2 {
    String hashTagname;
    ImageView hashTagDelete;

    public ImageView getHashTagDelete() {
        return hashTagDelete;
    }

    public void setHashTagDelete(ImageView hashTagDelete) {
        this.hashTagDelete = hashTagDelete;
    }

    public String getHashTagname() {
        return hashTagname;
    }

    public void setHashTagname(String hashTagname) {
        this.hashTagname = hashTagname;
    }

    public PostWriteHashTagItem2(String hashTagname) {
        this.hashTagname = hashTagname;
    }
}
