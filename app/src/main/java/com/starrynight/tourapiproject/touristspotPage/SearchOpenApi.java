package com.starrynight.tourapiproject.touristspotPage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
public interface SearchOpenApi {
    @GET ("v2/search/blog")
    Call<SearchData> getData(@Query("query")String query,
                                   @Query("sort")String sort,
                                   @Query("page")int page,
                                   @Query("size")int size,
                                   @Header("Authorization") String apikey);
}
