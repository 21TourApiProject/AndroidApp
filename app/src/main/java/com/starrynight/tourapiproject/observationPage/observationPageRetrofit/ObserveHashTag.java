package com.starrynight.tourapiproject.observationPage.observationPageRetrofit;

import com.google.gson.annotations.SerializedName;

/**
* @className : ObserveHashTag.java
* @description : 관측지 해쉬태그 DTO
* @modification : gyul chyoung (2022-08-30) 주석추가
* @author : 2022-08-30
* @date : gyul chyoung
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   gyul chyoung       2022-08-30       주석추가
 */

public class ObserveHashTag {

    @SerializedName("observeHashTagListId")
    private Long myHashTagListId;
    @SerializedName("observation")
    private Observation observation;
    @SerializedName("observationId")
    private Long observationId;
    @SerializedName("hashTagId")
    private Long hashTagId;
    @SerializedName("hashTagName")
    private String hashTagName;

    public ObserveHashTag() {
    }

    ;

    public String getHashTagName() {
        return hashTagName;
    }
}
