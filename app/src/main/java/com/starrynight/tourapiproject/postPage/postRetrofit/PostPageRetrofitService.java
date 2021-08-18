package com.starrynight.tourapiproject.postPage.postRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PostPageRetrofitService {
    @GET("post/{postId}")
    Call<Post> getPost(@Path("postId") Long postId);

    @GET("post/{postId}/postImage")
    Call<List<String>> getPostImage(@Path("postId")Long postId);

}
