package com.starrynight.tourapiproject.touristPointPage.touristPointRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * @className : TouristPointPageRetrofitService.java
 * @description : 관광지 관련 레트로핏 주소 설정 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
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
