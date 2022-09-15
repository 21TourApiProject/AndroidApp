package com.starrynight.tourapiproject.starPage.horPageRetriofit;

import com.starrynight.tourapiproject.starPage.horItemPage.HorItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @className : HorPageRetrofitService
 * @description : 별자리 운세 관련 Retrofit 주소 설정입니다.
 * @modification : 2022-09-15 (hyeonz) 주석추가
 * @author : hyeonz
 * @date : 2022-09-15
 * @version : 1.0
====개정이력(Modification Information)====
수정일        수정자        수정내용
-----------------------------------------
2022-09-15   hyeonz    주석추가
 */
public interface HorPageRetrofitService {
    @GET("horoscope/{todayMonth}")
    Call <List<HorItem>> getHoroscopes(@Path("todayMonth") int todayMonth);
}
