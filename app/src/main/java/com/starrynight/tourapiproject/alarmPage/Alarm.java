package com.starrynight.tourapiproject.alarmPage;

import com.google.gson.annotations.SerializedName;

public class Alarm {
    @SerializedName("alarmTitle")
    private String alarmTitle;
    @SerializedName("yearDate")
    private String yearDate;
    @SerializedName("alarmContent")
    private String alarmContent;

    public Alarm(){}

    public String getAlarmTitle() {
        return alarmTitle;
    }

    public void setAlarmTitle(String alarmTitle) {
        this.alarmTitle = alarmTitle;
    }

    public String getYearDate() {
        return yearDate;
    }

    public void setYearDate(String yearDate) {
        this.yearDate = yearDate;
    }

    public String getAlarmContent() {
        return alarmContent;
    }

    public void setAlarmContent(String alarmContent) {
        this.alarmContent = alarmContent;
    }

    public Alarm(String alarmTitle, String yearDate, String alarmContent) {
        this.alarmTitle = alarmTitle;
        this.yearDate = yearDate;
        this.alarmContent = alarmContent;
    }
}
