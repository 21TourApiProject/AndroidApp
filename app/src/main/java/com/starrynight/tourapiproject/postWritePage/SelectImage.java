package com.starrynight.tourapiproject.postWritePage;

import android.graphics.Bitmap;

public class SelectImage {
    Bitmap img;
    int id;

    public SelectImage(Bitmap img, int id) {
        this.img = img;
        this.id = id;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
