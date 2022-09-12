package com.starrynight.tourapiproject.weatherPage.wtMetModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @className : WtMetInterface
 * @description : 날씨 상세정보 관련 Retrofit 주소 설정입니다.
 * @modification : 2022-09-03 (hyeonz) 주석추가
 * @author : hyeonz
 * @date : 2022-09-03
 * @version : 1.0
====개정이력(Modification Information)====
수정일        수정자        수정내용
-----------------------------------------
hyeonz       2022-09-03   주석추가
 */
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
