package com.starrynight.tourapiproject.weatherPage.wtAreaRetrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @className : WtAreaRetrofitService.java
 * @description : 날씨-지역 관련 Retrofit 주소 설정입니다.
 * @modification : 2022-09-03 (hyeonz) 주석추가
 * @author : hyeonz
 * @date : 2022-09-03
 * @version : 1.0
    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
        hyeonz       2022-09-03   주석추가
 */
public interface WtAreaRetrofitService {
    @GET("wtAreas/{cityName}/{provName}")
    Call<WtAreaParams> getAreaInfo(@Path("cityName") String cityName, @Path("provName") String provName);
}
