package com.starrynight.tourapiproject.observationPage.observationPageRetrofit;

import com.google.gson.annotations.SerializedName;

/**
* @className : ObserveImage.java
* @description : 관측지 이미지 DTO
* @modification : gyul chyoung (2022-08-30) 주석추가
* @author : 2022-08-30
* @date : gyul chyoung
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   gyul chyoung       2022-08-30       주석추가
 */

public class ObserveImage {

    @SerializedName("observeImageListId")
    private Long observeImageListId;
    @SerializedName("observation")
    private Observation observation;
    @SerializedName("observationId")
    private Long observationId;
    @SerializedName("image")
    private String image;
    @SerializedName("imageSource")
    private String imageSource;

    public ObserveImage() {
    }

    ;

    public String getImage() {
        return image;
    }


    public String getImageSource() {
        return imageSource;
    }
}
