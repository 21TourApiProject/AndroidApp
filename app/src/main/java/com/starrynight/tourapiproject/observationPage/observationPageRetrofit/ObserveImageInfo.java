package com.starrynight.tourapiproject.observationPage.observationPageRetrofit;

import java.io.Serializable;

/**
* @className : ObserveImageInfo.java
* @description : 관측지 이미지 DTO
* @modification : gyul chyoung (2022-08-30) 주석추가
* @author : 2022-08-30
* @date : gyul chyoung  
* @version : 1.0 
     ====개정이력(Modification Information)====      
  수정일        수정자        수정내용    ----------------------------------------- 
   gyul chyoung       2022-08-30       주석추가
 */

public class ObserveImageInfo implements Serializable {
    private String image;
    private String imageSource;

    public ObserveImageInfo(String image, String imageSource) {
        this.image = image;
        this.imageSource = imageSource;
    }

    public ObserveImageInfo() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }
}
