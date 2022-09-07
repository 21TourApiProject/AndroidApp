package com.starrynight.tourapiproject.touristPointPage.search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * @className : SearchOpenApi.java
 * @description : 관광지 페이지의 다음 블로그 검색 결과를 위한 api 설정 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
public interface SearchOpenApi {
    @GET("v2/search/blog")
    Call<SearchData> getData(@Query("query") String query,
                             @Query("sort") String sort,
                             @Query("page") int page,
                             @Query("size") int size,
                             @Header("Authorization") String apikey);
}
