package com.starrynight.tourapiproject.myPage.myWish.post;

import com.google.gson.annotations.SerializedName;

import java.util.List;
/**
* @className : MyPost
* @description : 찜목록 내 게시물, 내가 쓴 게시물 더 보기, 관측지 관련 게시물 아이템 클래스 입니다.
* @modification : jinhyeok (2022-08-16) 주석 수정
* @author : 2022-08-16
* @date : jinhyeok
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   jinhyeok      2022-08-16       주석 수정

 */
public class MyPost {

    @SerializedName("itemId")
    private Long postId;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("title")
    private String title;
    @SerializedName("nickName")
    private String nickName;
    @SerializedName("profileImage")
    private String profileImage;
    @SerializedName("hashTagNames")
    private List<String> hashTagNames;

    public MyPost(Long postId, String thumbnail, String title, String nickName, String profileImage, List<String> hashTagNames) {
        this.postId = postId;
        this.thumbnail = thumbnail;
        this.title = title;
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.hashTagNames = hashTagNames;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public List<String> getHashTagNames() {
        return hashTagNames;
    }

    public void setHashTagNames(List<String> hashTagNames) {
        this.hashTagNames = hashTagNames;
    }
}
