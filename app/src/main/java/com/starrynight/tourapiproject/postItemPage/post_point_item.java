package com.starrynight.tourapiproject.postItemPage;

public class post_point_item {
    String tourname;
    String tourimage;

    public post_point_item(String tourname, String tourimage) {
        this.tourname = tourname;
        this.tourimage = tourimage;
    }

    public String getTourimage() {
        return tourimage;
    }

    public void setTourimage(String tourimage) {
        this.tourimage = tourimage;
    }

    public String getTourname() {
        return tourname;
    }

    public void setTourname(String tourname) {
        this.tourname = tourname;
    }

}
