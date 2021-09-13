package com.starrynight.tourapiproject.postPage.postRetrofit;

import com.starrynight.tourapiproject.observationPage.observationPageRetrofit.Observation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PostPageRetrofitService {
    @GET("post/{postId}")
    Call<Post> getPost(@Path("postId") Long postId);

    @GET("post/{postId}/postImage")
    Call<List<String>> getPostImage(@Path("postId")Long postId);

    @GET("post/{observationId}/observation")
    Call<Observation> getObservation(@Path("observationId")Long observationId);

    @GET("postHashTagName/{postId}")
    Call<List<String>> getPostHashTagName(@Path("postId")Long postId);

    @GET("postImage/{postObservePointId}")
    Call<List<String>> getRelatePostImageList(@Path("postObservePointId")Long postObservePointId);

    @DELETE("post/{userId}")
    Call<Void> deletePost(@Path("userId")Long userId);

    @GET("post/")
    Call<List<MainPost>> getMainPosts();
}
