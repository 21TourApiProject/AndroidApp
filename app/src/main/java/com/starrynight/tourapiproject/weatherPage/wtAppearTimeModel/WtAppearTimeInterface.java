package com.starrynight.tourapiproject.weatherPage.wtAppearTimeModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WtAppearTimeInterface {
    @GET("getAreaRiseSetInfo")
    Call<WtAppearTimeModel> getAppearTimeData(
            @Query("serviceKey") String serviceKey,
            @Query("location") String location,
            @Query("locdate") String locdate
    );
}

