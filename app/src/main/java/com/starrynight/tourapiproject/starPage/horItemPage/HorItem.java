package com.starrynight.tourapiproject.starPage.horItemPage;

public class HorItem {
    private String horImage;
    private String horEngTitle;
    private String horKrTitle;
    private String horPeriod;
    private String horDesc;
    private String horGuard;
    private String horPersonality;
    private String horTravel;

    public HorItem() {

    }

    public String getHorImage() {
        return horImage;
    }

    public void setHorImage(String horImage) {
        this.horImage = horImage;
    }

    public String getHorEngTitle() {
        return horEngTitle;
    }

    public void setHorEngTitle(String horEngTitle) {
        this.horEngTitle = horEngTitle;
    }

    public String getHorKrTitle() {
        return horKrTitle;
    }

    public void setHorKrTitle(String horKrTitle) {
        this.horKrTitle = horKrTitle;
    }

    public String getHorPeriod() {
        return horPeriod;
    }

    public void setHorPeriod(String horPeriod) {
        this.horPeriod = horPeriod;
    }

    public String getHorDesc() {
        return horDesc;
    }

    public void setHorDesc(String horDesc) {
        this.horDesc = horDesc;
    }

    public String getHorTravel() {
        return horTravel;
    }

    public String getHorPersonality() {
        return horPersonality;
    }

    public String getHorGuard() {
        return horGuard;
    }

    public HorItem(String horImage, String horEngTitle, String horKrTitle, String horPeriod, String horDesc, String horGuard, String horPersonality, String horTravel) {
        this.horImage = horImage;
        this.horEngTitle = horEngTitle;
        this.horKrTitle = horKrTitle;
        this.horPeriod = horPeriod;
        this.horDesc = horDesc;
        this.horGuard = horGuard;
        this.horPersonality = horPersonality;
        this.horTravel = horTravel;
    }
}
