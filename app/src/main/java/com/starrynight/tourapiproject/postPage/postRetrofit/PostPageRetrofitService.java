package com.starrynight.tourapiproject.postPage.postRetrofit;

import com.starrynight.tourapiproject.myPage.myPageRetrofit.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PostPageRetrofitService {
    @GET("post/{postId}")
    Call<Post> getPost(@Path("postId") Long postId);

}
