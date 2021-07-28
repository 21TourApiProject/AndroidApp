package com.starrynight.tourapiproject.weatherPage;

import com.starrynight.tourapiproject.weatherPage.weatherMetModel.WtMetModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WtMetInterface {
    @GET("/getVilageFcst")
    Call<WtMetModel> getMetData(
                            @Query("serviceKey") String serviceKey,
                            @Query("numOfRows") int numOfRows,
                            @Query("pageNo") int pageNo,
                            @Query("dataType") String dataType,
                            @Query("base_date") String base_date,
                            @Query("base_time") String base_time,
                            @Query("nx") int nx,
                            @Query("ny") int ny
    );
}
