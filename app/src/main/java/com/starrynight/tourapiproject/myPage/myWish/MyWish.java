package com.starrynight.tourapiproject.myPage.myWish;

public class MyWish {

    String thumbnail;
    String title;
    Long id;

    public MyWish(String thumbnail, String title, Long id) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
