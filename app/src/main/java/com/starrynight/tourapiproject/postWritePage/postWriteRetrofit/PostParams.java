package com.starrynight.tourapiproject.postWritePage.postWriteRetrofit;

import java.io.Serializable;
/**
 * className :   PostParams
 * description : 게시물 페이지의 게시물 param 입니다.
 * modification : 2022.08.01(박진혁) 주석 수정
 * author : jinhyeok
 * date : 2022-08-01
 * version : 1.0
 * ====개정이력(Modification Information)====
 * 수정일        수정자        수정내용
 * -----------------------------------------
 * 2022-08-01      jinhyeok      주석 수정
 */
public class PostParams implements Serializable {
    private String postContent;

    private String yearDate;

    private String time;

    private String postTitle;

    private String optionHashTag;
    private String optionHashTag2;
    private String optionHashTag3;
    private String optionHashTag4;
    private String optionHashTag5;
    private String optionHashTag6;
    private String optionHashTag7;
    private String optionHashTag8;
    private String optionHashTag9;
    private String optionHashTag10;

    private String optionObservation;

    private Long userId;

    public PostParams() {
    }

    public String getOptionHashTag() {
        return optionHashTag;
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

    public String getOptionHashTag4() {
        return optionHashTag4;
    }

    public void setOptionHashTag4(String optionHashTag4) {
        this.optionHashTag4 = optionHashTag4;
    }

    public String getOptionHashTag5() {
        return optionHashTag5;
    }

    public void setOptionHashTag5(String optionHashTag5) {
        this.optionHashTag5 = optionHashTag5;
    }

    public String getOptionHashTag6() {
        return optionHashTag6;
    }

    public void setOptionHashTag6(String optionHashTag6) {
        this.optionHashTag6 = optionHashTag6;
    }

    public String getOptionHashTag7() {
        return optionHashTag7;
    }

    public void setOptionHashTag7(String optionHashTag7) {
        this.optionHashTag7 = optionHashTag7;
    }

    public String getOptionHashTag8() {
        return optionHashTag8;
    }

    public void setOptionHashTag8(String optionHashTag8) {
        this.optionHashTag8 = optionHashTag8;
    }

    public String getOptionHashTag9() {
        return optionHashTag9;
    }

    public void setOptionHashTag9(String optionHashTag9) {
        this.optionHashTag9 = optionHashTag9;
    }

    public String getOptionHashTag10() {
        return optionHashTag10;
    }

    public void setOptionHashTag10(String optionHashTag10) {
        this.optionHashTag10 = optionHashTag10;
    }

    public String getOptionObservation() {
        return optionObservation;
    }

    public String getPostContent() {
        return postContent;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getYearDate() {
        return yearDate;
    }

    public String getTime() {
        return time;
    }

    public Long getUserId() {
        return userId;
    }

    public void setOptionHashTag(String optionHashTag) {
        this.optionHashTag = optionHashTag;
    }

    public void setOptionObservation(String optionObservation) {
        this.optionObservation = optionObservation;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public void setYearDate(String yearDate) {
        this.yearDate = yearDate;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }


    public PostParams(String postContent, String yearDate, String time, String postTitle, String optionHashTag, String optionObservation, Long userId) {
        this.postContent = postContent;
        this.yearDate = yearDate;
        this.time = time;
        this.postTitle = postTitle;
        this.optionHashTag = optionHashTag;
        this.optionObservation = optionObservation;
        this.userId = userId;
    }


}
