package com.starrynight.tourapiproject.myPage.myPageRetrofit;

import com.google.gson.annotations.SerializedName;

/**
 * @className : MyHashTag.java
 * @description : 사용자 선호 해시태그 정보 클래스 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
public class MyHashTag {

    @SerializedName("myHashTagListId")
    private Long myHashTagListId;
    @SerializedName("user")
    private User user;
    @SerializedName("userId")
    private Long userId;
    @SerializedName("hashTagId")
    private Long hashTagId;
    @SerializedName("hashTagName")
    private String hashTagName;

    public MyHashTag() {
    }

    ;

}
