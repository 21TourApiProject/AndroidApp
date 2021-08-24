package com.starrynight.tourapiproject.postItemPage;

import android.media.Image;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class post_item {
    String observation;
    String title;
    String nickname;
    ArrayList<String> images;
    List<String> hashTags;
    String image2;

    public post_item(String observation, String title, String nickname,ArrayList<String> images,List<String> hashTags, String image2) {
        this.observation = observation;
        this.hashTags = hashTags;
        this.title = title;
        this.nickname = nickname;
        this.images = images;
        this.image2= image2;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public List<String> getHashTags() {
        return hashTags;
    }

    public void setHashTags(List<String> hashTags) {
        this.hashTags = hashTags;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String>images) {
        this.images = images;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
