package com.starrynight.tourapiproject.postWritePage.postWriteRetrofit;

import android.graphics.Bitmap;

import com.starrynight.tourapiproject.postPage.postRetrofit.PostImage;

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

    @POST ("postImage/{postId}")
    Call <Void> createPostImage(@Path("postId")Long postId,@Body List<PostImageParams> postImageParams);

    @GET ("post/write/{postContent}")
    Call<String> addContent(@Path("postContent")String postContent);

    @GET("postImage/{postImageListId")
    Call<String> getPostImageName(@Path("postImageListId")Long postImageListId);

    @POST("postObservePoint")
    Call<Void> createPostObservePoint(@Body PostObservePointParams postObservePointParams);

    @POST ("postHashTag/{postId}")
    Call<Long> createPostHashTag(@Path("postId")Long postId,@Body List<PostHashTagParams> postHashTagParams);
}
