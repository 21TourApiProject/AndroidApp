package com.starrynight.tourapiproject.postPage.postRetrofit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
/**
* @className : MainPost
* @description : 메인페이지 게시물 클래스입니다.
* @modification : jinhyeok (2022-08-08) 주석 수정
* @author : 2022-08-08
* @date : jinhyeok
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   jinhyeok      2022-08-08       주석 수정

 */
public class MainPost {
    @SerializedName("observationId")
    private Long observationId;
    @SerializedName("postId")
    private Long postId;
    @SerializedName("mainObservation")
    private String mainObservation;
    @SerializedName("optionObservation")
    private String optionObservation;
    @SerializedName("mainTitle")
    private String mainTitle;
    @SerializedName("mainNickName")
    private String mainNickName;
    @SerializedName("images")
    private ArrayList<String> images;
    @SerializedName("hashTags")
    private List<String> hashTags;
    @SerializedName("optionHashTag")
    private String optionHashTag;
    @SerializedName("optionHashTag2")
    private String optionHashTag2;
    @SerializedName("optionHashTag3")
    private String optionHashTag3;
    @SerializedName("profileImage")
    private String profileImage;

    public MainPost(String mainObservation, String optionObservation, String mainTitle, String mainNickName, ArrayList<String> images, List<String> hashTags, String optionHashTag, String profileImage) {
        this.mainObservation = mainObservation;
        this.optionObservation = optionObservation;
        this.mainTitle = mainTitle;
        this.mainNickName = mainNickName;
        this.images = images;
        this.hashTags = hashTags;
        this.optionHashTag = optionHashTag;
        this.profileImage = profileImage;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getObservationId() {
        return observationId;
    }

    public void setObservationId(Long observationId) {
        this.observationId = observationId;
    }

    public String getOptionHashTag2() {
        return optionHashTag2;
    }

    public void setOptionHashTag2(String optionHashTag2) {
        this.optionHashTag2 = optionHashTag2;
    }

    public String getOptionHashTag3() {
        return optionHashTag3;
    }

    public void setOptionHashTag3(String optionHashTag3) {
        this.optionHashTag3 = optionHashTag3;
    }

    public String getMainObservation() {
        return mainObservation;
    }

    public void setMainObservation(String mainObservation) {
        this.mainObservation = mainObservation;
    }

    public String getOptionObservation() {
        return optionObservation;
    }

    public void setOptionObservation(String optionObservation) {
        this.optionObservation = optionObservation;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getMainNickName() {
        return mainNickName;
    }

    public void setMainNickName(String mainNickName) {
        this.mainNickName = mainNickName;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public List<String> getHashTags() {
        return hashTags;
    }

    public void setHashTags(List<String> hashTags) {
        this.hashTags = hashTags;
    }

    public String getOptionHashTag() {
        return optionHashTag;
    }

    public void setOptionHashTag(String optionHashTag) {
        this.optionHashTag = optionHashTag;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
