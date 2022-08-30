package com.starrynight.tourapiproject.observationPage;

/**
* @className : RecyclerFeeItem.java
* @description : 관측지 입장료 리사이클러 아이템
* @modification : gyul chyoung (2022-08-30) 주석추가
* @author : 2022-08-30
* @date : gyul chyoung
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   gyul chyoung       2022-08-30       주석추가
 */

public class RecyclerFeeItem {
    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    public String getEntranceFee() {
        return entranceFee;
    }

    public void setEntranceFee(String entranceFee) {
        this.entranceFee = entranceFee;
    }

    private String feeName;
    private String entranceFee;
}

