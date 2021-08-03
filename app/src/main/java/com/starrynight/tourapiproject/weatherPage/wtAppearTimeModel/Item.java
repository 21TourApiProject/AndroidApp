package com.starrynight.tourapiproject.weatherPage.wtAppearTimeModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Item")
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {
    private String locdate;

    private String location;

    private String longitudeNum;

    private String latitudeNum;

    private String sunrise;

    private String sunset;

    private String moonrise;

    private String moonset;

    public String getLocdate() {
        return locdate;
    }

    public void setLocdate(String locdate) {
        this.locdate = locdate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLongitudeNum() {
        return longitudeNum;
    }

    public void setLongitudeNum(String longitudeNum) {
        this.longitudeNum = longitudeNum;
    }

    public String getLatitudeNum() {
        return latitudeNum;
    }

    public void setLatitudeNum(String latitudeNum) {
        this.latitudeNum = latitudeNum;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getMoonrise() {
        return moonrise;
    }

    public void setMoonrise(String moonrise) {
        this.moonrise = moonrise;
    }

    public String getMoonset() {
        return moonset;
    }

    public void setMoonset(String moonset) {
        this.moonset = moonset;
    }
}
