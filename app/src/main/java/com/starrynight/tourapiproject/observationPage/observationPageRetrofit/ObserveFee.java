package com.starrynight.tourapiproject.observationPage.observationPageRetrofit;

import com.google.gson.annotations.SerializedName;

/**
* @className : ObserveFee.java
* @description : 관측지 요금 DTO
* @modification : gyul chyoung (2022-08-30) 주석수정
* @author : 2022-08-30
* @date : gyul chyoung
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   gyul chyoung       2022-08-30       주석수정
 */

public class ObserveFee {
    public Long getObserveFeeListId() {
        return observeFeeListId;
    }

    public String getFeeName() {
        return feeName;
    }

    public String getEntranceFee() {
        return entranceFee;
    }

    @SerializedName("observeFeeListId")
    private Long observeFeeListId;
    @SerializedName("observation")
    private Observation observation;
    @SerializedName("observationId")
    private Long observationId;
    @SerializedName("feeName")
    private String feeName;
    @SerializedName("entranceFee")
    private String entranceFee;

    public ObserveFee() {
    }

    ;
}
