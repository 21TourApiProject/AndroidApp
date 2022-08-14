package com.starrynight.tourapiproject.postPage.postRetrofit;

import com.google.gson.annotations.SerializedName;
/**
* @className : PostImage
* @description : 게시물 이미지 클래스 입니다.
* @modification : jinhyeok (2022-08-12) 주석 수정
* @author : 2022-08-12
* @date : jinhyeok
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   jinhyeok      2022-08-12       주석 수정

 */
public class PostImage {
    @SerializedName("postImageListId")
    private Long postImageListId;
    @SerializedName("post")
    private Post post;
    @SerializedName("postId")
    private Long postId;
    @SerializedName("imageName")
    private String imageName;
    @SerializedName("postObservePointId")
    private Long postObservePointId;

    public PostImage() {
    }

    public Long getPostImageListId() {
        return postImageListId;
    }

    public Post getPost() {
        return post;
    }

    public Long getPostId() {
        return postId;
    }

    public String getImageName() {
        return imageName;
    }

    public Long getPostObservePointId() {
        return postObservePointId;
    }
}
