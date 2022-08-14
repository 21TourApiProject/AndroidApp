package com.starrynight.tourapiproject.postPage.postRetrofit;

import com.google.gson.annotations.SerializedName;
/**
* @className : PostHashTag
* @description : 설명
* @modification : jinhyeok (2022-08-12) 주석 수정
* @author : 2022-08-12
* @date : jinhyeok
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   jinhyeok      2022-08-12       주석 수정

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
