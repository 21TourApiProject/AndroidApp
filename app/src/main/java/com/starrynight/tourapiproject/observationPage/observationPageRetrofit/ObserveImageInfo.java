package com.starrynight.tourapiproject.observationPage.observationPageRetrofit;

import java.io.Serializable;

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
