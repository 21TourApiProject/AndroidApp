package com.starrynight.tourapiproject.weatherPage.wtAreaRetrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WtAreaRetrofitService {
    @GET("wtAreas/{cityName}/{provName}")
    Call<WtAreaParams> getAreaInfo(@Path("cityName") String cityName, @Path("provName") String provName);
}
