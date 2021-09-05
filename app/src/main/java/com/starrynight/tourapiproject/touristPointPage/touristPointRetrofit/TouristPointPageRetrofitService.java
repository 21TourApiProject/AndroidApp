package com.starrynight.tourapiproject.touristPointPage.touristPointRetrofit;

import java.util.List;

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

    @GET("nearTouristData/{contentId}")
    Call<List<Near>> getNearTouristData(@Path("contentId") Long contentId);

    @GET("touristDataHashTag/{contentId}")
    Call<List<String>> getTouristDataHashTag(@Path("contentId") Long contentId);

}
