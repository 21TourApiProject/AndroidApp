package com.starrynight.tourapiproject.alarmPage;

public class AlarmItem {
    String alarmName;
    String alarmDate;
    String alarmContent;

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(String alarmDate) {
        this.alarmDate = alarmDate;
    }

    public String getAlarmContent() {
        return alarmContent;
    }

    public void setAlarmContent(String alarmContent) {
        this.alarmContent = alarmContent;
    }

    public AlarmItem(String alarmName, String alarmDate, String alarmContent) {
        this.alarmName = alarmName;
        this.alarmDate = alarmDate;
        this.alarmContent = alarmContent;
    }
}
