package com.starrynight.tourapiproject.touristPointPage.touristPointRetrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TouristPointPageRetrofitService {

    @GET("touristData/contentType/{contentId}")
    Call<Long> getContentType(@Path("contentId") Long contentId);

    @GET("touristData/touristPoint/{contentId}")
    Call<TouristPoint> getTouristPointData(@Path("contentId") Long contentId);

    @GET("touristData/food/{contentId}")
    Call<Food> getFoodData(@Path("contentId") Long contentId);
}