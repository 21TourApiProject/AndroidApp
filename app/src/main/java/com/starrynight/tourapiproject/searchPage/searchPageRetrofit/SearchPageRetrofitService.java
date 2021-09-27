package com.starrynight.tourapiproject.searchPage.searchPageRetrofit;

import com.starrynight.tourapiproject.myPage.myWish.post.MyPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SearchPageRetrofitService {

    @POST("search/touristPoint")
    Call<List<SearchParams1>> getTouristPointWithFilter(@Body SearchKey searchKey);


    @POST("search/observation")
    Call<List<SearchParams1>> getObservationWithFilter(@Body SearchKey searchKey);

    @POST("search/post")
    Call<List<MyPost>> getPostWithFilter(@Body SearchKey searchKey);

}
