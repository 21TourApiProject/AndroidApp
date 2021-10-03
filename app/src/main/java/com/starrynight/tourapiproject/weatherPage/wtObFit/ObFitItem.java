package com.starrynight.tourapiproject.weatherPage.wtObFit;

public class ObFitItem {
    private int obFitImage;
    private String obFitTime;
    private String obFitPercent;

    public ObFitItem(int obFitImage, String obFitTime, String obFitPercent) {
        this.obFitImage = obFitImage;
        this.obFitTime = obFitTime;
        this.obFitPercent = obFitPercent;
    }

    public int getObFitImage() {
        return obFitImage;
    }

    public String getObFitPercent() {
        return obFitPercent;
    }

    public String getObFitTime() {
        return obFitTime;
    }

    public void setObFitImage(int obFitImage) {
        this.obFitImage = obFitImage;
    }

    public void setObFitPercent(String obFitPercent) {
        this.obFitPercent = obFitPercent;
    }

    public void setObFitTime(String obFitTime) {
        this.obFitTime = obFitTime;
    }
}
