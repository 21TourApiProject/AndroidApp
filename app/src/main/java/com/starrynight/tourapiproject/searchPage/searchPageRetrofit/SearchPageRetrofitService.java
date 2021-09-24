package com.starrynight.tourapiproject.searchPage.searchPageRetrofit;

import com.starrynight.tourapiproject.myPage.myWish.obtp.MyWishObTp;
import com.starrynight.tourapiproject.myPage.myWish.post.MyPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SearchPageRetrofitService {

    @POST("touristData/search")
    Call<List<MyWishObTp>> getTouristDataWithFilter(@Body Filter filter);


    @POST("search/observation")
    Call<List<SearchParams1>> getObservationWithFilter(@Body SearchKey searchKey);

    @POST("post/search")
    Call<List<MyPost>> getPostWithFilter(@Body Filter filter);

}
