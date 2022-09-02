package com.starrynight.tourapiproject.observationPage.observationPageRetrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
* @className : Observation.java
* @description : 관측지 DTO
* @modification : gyul chyoung (2022-08-30) 주석추가
* @author : 2022-08-30
* @date : gyul chyoung
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   gyul chyoung       2022-08-30       주석추가
 */
public class Observation {

    @SerializedName("observationId")
    Long observationId;
    @SerializedName("observationName")
    String observationName;
    @SerializedName("link")
    String link;
    @SerializedName("intro")
    String intro;
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
    @SerializedName("courseOrder")
    int courseOrder;
    @SerializedName("areaCode")
    Long areaCode;
    @SerializedName("observeHashTags")
    List<ObserveHashTag> observeHashTags;
    @SerializedName("observeImages")
    List<ObserveImage> observeImages;
    @SerializedName("observeFee")
    List<ObserveFee> observeFees;

    public Long getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Long areaCode) {
        this.areaCode = areaCode;
    }

    public int getCourseOrder() {
        return courseOrder;
    }

    public List<ObserveFee> getObserveFees() {
        return observeFees;
    }

    public List<ObserveImage> getObserveImages() {
        return observeImages;
    }

    public String getIntro() {
        return intro;
    }

    public Observation() {
    }

    ;

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
