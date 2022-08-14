package com.starrynight.tourapiproject.postItemPage;

import android.widget.ImageView;
/**
* @className : PostHashTagItem
* @description : 게시물 관측지 or 해시태그 아이템 입니다.
* @modification : jinhyeok (2022-08-14) 주석 수정
* @author : 2022-08-14
* @date : jinhyeok
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   jinhyeok      2022-08-14       주석 수정

 */
public class PostHashTagItem {
    String hashTagname;

    ImageView hashTagPin;

    Long ObservationId;

    Long HashTagId;

    public void setHashTagPin(ImageView hashTagPin) {
        this.hashTagPin = hashTagPin;
    }

    public Long getHashTagId() {
        return HashTagId;
    }

    public void setHashTagId(Long hashTagId) {
        HashTagId = hashTagId;
    }

    public String getHashTagname() {
        return hashTagname;
    }

    public ImageView getHashTagPin() {
        return hashTagPin;
    }

    public Long getObservationId() {
        return ObservationId;
    }

    public void setHashTagname(String hashTagname) {
        this.hashTagname = hashTagname;
    }

    public PostHashTagItem(String hashTagname, ImageView hashTagPin, Long observationId, Long hashTagId) {
        this.hashTagname = hashTagname;
        this.hashTagPin = hashTagPin;
        ObservationId = observationId;
        HashTagId = hashTagId;
    }
}
