package com.starrynight.tourapiproject.weatherPage.wtMetModel;

import com.starrynight.tourapiproject.weatherPage.wtMetModel.WtMetModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WtMetInterface {
    @GET("onecall")
    Call<WtMetModel> getMetData(
            @Query("lat") Double lat,
            @Query("lon") Double lon,
            @Query("exclude") String exclude,
            @Query("appid") String appid,
            @Query("units") String units
    );
}
