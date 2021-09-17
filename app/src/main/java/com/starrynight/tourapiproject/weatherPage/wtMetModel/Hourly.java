package com.starrynight.tourapiproject.weatherPage.wtMetModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hourly {
    @SerializedName("dt")
    private String dt;

    @SerializedName("temp")
    private String temp;

    @SerializedName("humidity")
    private String humidity;

    @SerializedName("clouds")
    private String clouds;

    @SerializedName("visibility")
    @Expose
    private String visibility;

    @SerializedName("wind_speed")
    private String windSpeed;

    @SerializedName("weather")
    private List<Weather> weather;

    @SerializedName("pop")
    private String pop;

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

}