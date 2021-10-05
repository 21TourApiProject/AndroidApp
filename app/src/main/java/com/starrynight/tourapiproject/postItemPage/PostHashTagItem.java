package com.starrynight.tourapiproject.postItemPage;

import android.widget.ImageView;

public class PostHashTagItem {
    String hashTagname;

    ImageView hashTagPin;

    Long ObservationId;

    Long HashTagId;

    public void setHashTagPin(ImageView hashTagPin) {
        this.hashTagPin = hashTagPin;
    }

    public Long getHashTagId() {
        return HashTagId;
    }

    public void setHashTagId(Long hashTagId) {
        HashTagId = hashTagId;
    }

    public String getHashTagname() {
        return hashTagname;
    }

    public ImageView getHashTagPin() {
        return hashTagPin;
    }

    public Long getObservationId() {
        return ObservationId;
    }

    public void setHashTagname(String hashTagname) {
        this.hashTagname = hashTagname;
    }

    public PostHashTagItem(String hashTagname, ImageView hashTagPin, Long observationId, Long hashTagId) {
        this.hashTagname = hashTagname;
        this.hashTagPin = hashTagPin;
        ObservationId = observationId;
        HashTagId = hashTagId;
    }
}
