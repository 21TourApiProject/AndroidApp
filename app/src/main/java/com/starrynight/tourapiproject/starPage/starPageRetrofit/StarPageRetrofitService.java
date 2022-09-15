package com.starrynight.tourapiproject.starPage.starPageRetrofit;

import com.starrynight.tourapiproject.starPage.constNameRetrofit.ConstellationParams2;
import com.starrynight.tourapiproject.starPage.starItemPage.StarItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @className : StarPageRetrofitService
 * @description : 별자리 관련 Retrofit 주소 설정입니다.
 * @modification : 2022-09-15 (hyeonz) 주석추가
 * @author : hyeonz
 * @date : 2022-09-15
 * @version : 1.0
====개정이력(Modification Information)====
수정일        수정자        수정내용
-----------------------------------------
2022-09-15   hyeonz      주석추가
 */
public interface StarPageRetrofitService {
    @GET("constellations")
    Call<List<StarItem>> getConstellation();

    @GET("constellations/constName")
    Call<List<ConstellationParams2>> getConstNames();

    @GET("constellation/todayConst")
    Call<List<StarItem>> getTodayConst();

    @GET("constellation/todayConstName")
    Call<List<ConstellationParams2>> getTodayConstName();

    @GET("constellation/{constName}")
    Call<Constellation> getDetailConst(@Path("constName") String constName);


}
