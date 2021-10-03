package com.starrynight.tourapiproject.weatherPage.wtTodayRetrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WtTodayRetrofitService {
    @GET("wtToday/{todayWtId}")
    Call <WtTodayParams> getTodayWeatherInfo(@Path("todayWtId") int todayWtId);
}
