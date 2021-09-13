package com.starrynight.tourapiproject.postPage.postRetrofit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MainPost {
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
