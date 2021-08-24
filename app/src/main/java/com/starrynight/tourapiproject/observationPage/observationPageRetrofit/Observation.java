package com.starrynight.tourapiproject.observationPage.observationPageRetrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Observation {

    @SerializedName("observationId")
    Long observationId;
    @SerializedName("observationName")
    String observationName;
    @SerializedName("link")
    String link;
    @SerializedName("latitude")
    double latitude;
    @SerializedName("longitude")
    double longitude;
    @SerializedName("address")
    String address;
    @SerializedName("phoneNumber")
    String phoneNumber;
    @SerializedName("operatingHour")
    String operatingHour;
    @SerializedName("entranceFee")
    String entranceFee;
    @SerializedName("parking")
    String parking;
    @SerializedName("observeType")
    String observeType;
    @SerializedName("outline")
    String outline;
    @SerializedName("guide")
    String guide;
    @SerializedName("closedDay")
    String closedDay;
    @SerializedName("light")
    double light;
    @SerializedName("nature")
    boolean nature;
    @SerializedName("myHashTags")
    List<ObserveHashTag> observeHashTags;

    public Observation(){};

    public double getLight() {
        return light;
    }

    public boolean getNature() {
        return nature;
    }

    public List<ObserveHashTag> getObserveHashTag() {
        return observeHashTags;
    }

    public Long getObservationId() {
        return observationId;
    }

    public String getObservationName() {
        return observationName;
    }

    public String getLink() {
        return link;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getOperatingHour() {
        return operatingHour;
    }

    public String getEntranceFee() {
        return entranceFee;
    }

    public String getParking() {
        return parking;
    }

    public String getObserveType() {
        return observeType;
    }

    public String getOutline() {
        return outline;
    }

    public String getGuide() {
        return guide;
    }

    public String getClosedDay() {
        return closedDay;
    }
}
