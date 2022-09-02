package com.starrynight.tourapiproject.postPage.postRetrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;
/**
* @className : Post
* @description : 게시물 클래스 입니다.
* @modification : 2022-09-02 (jinhyeok) 주석 수정
* @author : jinhyeok
* @date : 2022-09-02
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   2022-09-02      jinhyeok       주석 수정

 */
public class Post {
    @SerializedName("postId")
    Long postId;
    @SerializedName("postContent")
    String postContent;
    @SerializedName("postTitle")
    String postTitle;
    @SerializedName("optionHashTag")
    String optionHashTag;
    @SerializedName("optionHashTag2")
    String optionHashTag2;
    @SerializedName("optionHashTag3")
    String optionHashTag3;
    @SerializedName("optionHashTag4")
    String optionHashTag4;
    @SerializedName("optionHashTag5")
    String optionHashTag5;
    @SerializedName("optionHashTag6")
    String optionHashTag6;
    @SerializedName("optionHashTag7")
    String optionHashTag7;
    @SerializedName("optionHashTag8")
    String optionHashTag8;
    @SerializedName("optionHashTag9")
    String optionHashTag9;
    @SerializedName("optionHashTag10")
    String optionHashTag10;
    @SerializedName("optionObservation")
    String optionObservation;
    @SerializedName("yearDate")
    String yearDate;
    @SerializedName("time")
    String time;
    @SerializedName("postHashTag")
    List<PostHashTag> postHashTags;
    @SerializedName("postImage")
    List<PostImage> postImages;
    @SerializedName("userId")
    Long userId;
    @SerializedName("observationId")
    Long observationId;
    @SerializedName("areaCode")
    Long areaCode;

    public Post() {
    }

    public String getOptionHashTag() {
        return optionHashTag;
    }

    public String getOptionObservation() {
        return optionObservation;
    }

    public String getOptionHashTag2() {
        return optionHashTag2;
    }

    public String getOptionHashTag3() {
        return optionHashTag3;
    }

    public String getOptionHashTag4() {
        return optionHashTag4;
    }

    public String getOptionHashTag5() {
        return optionHashTag5;
    }

    public String getOptionHashTag6() {
        return optionHashTag6;
    }

    public String getOptionHashTag7() {
        return optionHashTag7;
    }

    public String getOptionHashTag8() {
        return optionHashTag8;
    }

    public String getOptionHashTag9() {
        return optionHashTag9;
    }

    public String getOptionHashTag10() {
        return optionHashTag10;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public Long getPostId() {
        return postId;
    }

    public Long getObservationId() {
        return observationId;
    }

    public String getYearDate() {
        return yearDate;
    }

    public String getTime() {
        return time;
    }

    public List<PostImage> getPostImages() {
        return postImages;
    }

    public Long getUserId() {
        return userId;
    }

    public List<PostHashTag> getPostHashTags() {
        return postHashTags;
    }

    public List<PostImage> getPostImage() {
        return postImages;
    }

    public Long getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Long areaCode) {
        this.areaCode = areaCode;
    }
}
