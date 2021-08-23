package com.starrynight.tourapiproject.postItemPage;

import android.media.Image;
import android.net.Uri;

import java.util.ArrayList;

public class post_item {
    String hash;
    String hash2;
    String title;
    String nickname;
    ArrayList<String> images;
    String image2;

    public post_item(String hash, String hash2, String title, String nickname,ArrayList<String> images, String image2) {
        this.hash = hash;
        this.hash2 = hash2;
        this.title = title;
        this.nickname = nickname;
        this.images = images;
        this.image2= image2;
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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHash2() {
        return hash2;
    }

    public void setHash2(String hash2) {
        this.hash2 = hash2;
    }
}
