package com.starrynight.tourapiproject.weatherPage.wtFineDustModel;

import com.google.gson.annotations.SerializedName;

public class WtFineDustModel {
    @SerializedName("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
