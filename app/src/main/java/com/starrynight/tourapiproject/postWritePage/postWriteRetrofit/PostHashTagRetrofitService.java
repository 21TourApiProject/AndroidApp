package com.starrynight.tourapiproject.postWritePage.postWriteRetrofit;

import android.graphics.Bitmap;

import java.time.LocalDateTime;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PostHashTagRetrofitService {

    @POST("post/{observePointName}")
    Call<Void> postup(@Path("observePointName")String observePointName,@Body PostParams params);

    @GET ("post/write/{postImage}")
    Call <Bitmap> addImage(@Path("postImage")String postImage);

    @GET ("post/write/{postContent}")
    Call<String> addContent(@Path("postContent")String postContent);

    @POST("postObservePoint")
    Call<Void> createPostObservePoint(@Body PostObservePointParams postObservePointParams);

    @GET ("post/write/{yearDate}/{time}")
    Call<LocalDateTime> addTime(@Path("yearDate")LocalDateTime yearDate,@Path("time")LocalDateTime time);

    @POST ("postHashTag")
    Call<Void> createPostHashTag(@Body List<PostHashTagParams> postHashTagParams);
}
