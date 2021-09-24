package com.starrynight.tourapiproject.weatherPage.wtFineDustModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WtFineDustInterface {
    @GET("getMinuDustFrcstDspth")
    Call<WtFineDustModel> getFineDustData(
            @Query("serviceKey") String serviceKey,
            @Query("returnType") String returnType,
            @Query("searchDate") String searchDate,
            @Query("informCode") String informCode
    );
}
