package com.starrynight.tourapiproject.weatherPage.wtFineDustModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @className : WtFineDustInterface
 * @description : 날씨-미세먼지 관련 Retrofit 주소 설정입니다.
 * @modification : 2022-09-03 (hyeonz) 주석추가
 * @author : hyeonz
 * @date : 2022-09-03
 * @version : 1.0
====개정이력(Modification Information)====
수정일        수정자        수정내용
-----------------------------------------
hyeonz       2022-09-03   주석추가
 */
public interface WtFineDustInterface {
    @GET("getMinuDustFrcstDspth")
    Call<WtFineDustModel> getFineDustData(
            @Query("serviceKey") String serviceKey,
            @Query("returnType") String returnType,
            @Query("searchDate") String searchDate,
            @Query("informCode") String informCode
    );
}
