package com.starrynight.tourapiproject.postPage.postRetrofit;

import com.google.gson.annotations.SerializedName;
/**
* @className : PostHashTag
* @description : 게시물 해시태그 클래스 입니다.
* @modification : 2022-09-02 (jinhyeok) 주석 수정
* @author : jinhyeok
* @date : 2022-09-02
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   2022-09-02      jinhyeok       주석 수정

 */
public class PostHashTag {

    @SerializedName("postHashTagListId")
    private Long postHashTagListId;
    @SerializedName("post")
    private Post post;
    @SerializedName("postId")
    private Long postId;
    @SerializedName("hashTagId")
    private Long hashTagId;
    @SerializedName("hashTagName")
    private String hashTagName;

    public PostHashTag() {
    }

    public String getHashTagName() {
        return hashTagName;
    }

    public Long getHashTagId() {
        return hashTagId;
    }
}
