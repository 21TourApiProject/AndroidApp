package com.starrynight.tourapiproject.searchPage.searchPageRetrofit;

import com.starrynight.tourapiproject.myPage.myWish.post.MyPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SearchPageRetrofitService {

    @POST("search/touristPointForMap")
    Call<List<SearchParams1>> getTouristPointWithFilterforMap(@Body SearchKey searchKey);

    @POST("search/touristPoint")
    Call<List<SearchParams1>> getTouristPointWithFilter(@Body SearchKey searchKey);

    @POST("search/observation")
    Call<List<SearchParams1>> getObservationWithFilter(@Body SearchKey searchKey);

    @POST("search/post")
    Call<List<MyPost>> getPostWithFilter(@Body SearchKey searchKey);

    @GET("searchFirst/{typeName}")
    Call<List<SearchFirst>> getSearchFirst(@Path("typeName") String typeName);

}
