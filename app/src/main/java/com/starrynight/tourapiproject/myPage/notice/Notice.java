package com.starrynight.tourapiproject.myPage.notice;

import com.google.gson.annotations.SerializedName;

/**
* @className : Notice
* @description : 공지 게시물 클래스 입니다.
* @modification : 2022-09-02 (jinhyeok) 주석 수정
* @author : jinhyeok
* @date : 2022-09-02
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   2022-09-02      jinhyeok       주석 수정

 */
public class Notice {
    @SerializedName("noticeId")
    private Long noticeId;
    @SerializedName("noticeTitle")
    private String noticeTitle;
    @SerializedName("noticeContent")
    private String noticeContent;
    @SerializedName("noticeDate")
    private String noticeDate;

    public Notice(Long noticeId, String noticeTitle, String noticeContent, String noticeDate) {
        this.noticeId = noticeId;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.noticeDate = noticeDate;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(String noticeDate) {
        this.noticeDate = noticeDate;
    }
}
