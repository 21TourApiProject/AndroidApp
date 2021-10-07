package com.starrynight.tourapiproject.postItemPage;

import android.widget.ImageView;

public class PostWriteHashTagItem2 {
    String hashTagname;
    ImageView hashTagDelete;

    public ImageView getHashTagDelete() {
        return hashTagDelete;
    }

    public void setHashTagDelete(ImageView hashTagDelete) {
        this.hashTagDelete = hashTagDelete;
    }

    public String getHashTagname() {
        return hashTagname;
    }

    public void setHashTagname(String hashTagname) {
        this.hashTagname = hashTagname;
    }

    public PostWriteHashTagItem2(String hashTagname) {
        this.hashTagname = hashTagname;
    }
}
