package com.starrynight.tourapiproject.weatherPage.wtFineDustModel;

import com.google.gson.annotations.SerializedName;

public class Header {
    @SerializedName("resultMsg")
    private String resultMsg;

    @SerializedName("resultCode")
    private String resultCode;

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}
