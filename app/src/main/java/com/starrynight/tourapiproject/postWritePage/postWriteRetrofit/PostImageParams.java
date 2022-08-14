package com.starrynight.tourapiproject.postWritePage.postWriteRetrofit;

import java.io.Serializable;
/**
 * className :   PostImageParams
 * description : 게시물 이미지 param 입니다.
 * modification : 2022.08.01(박진혁) 주석 수정
 * author : jinhyeok
 * date : 2022-08-01
 * version : 1.0
 * ====개정이력(Modification Information)====
 * 수정일        수정자        수정내용
 * -----------------------------------------
 * 2022-08-01      jinhyeok      주석 수정
 */
public class PostImageParams implements Serializable {

    private String imageName;

    public PostImageParams() {
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public PostImageParams(String imageName) {
        this.imageName = imageName;
    }
}
