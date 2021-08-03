package com.starrynight.tourapiproject.myPage.myPageRetrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MyPageRetrofitService {

    @GET("user/{userId}")
    Call<User> getUser(@Path("userId") Long userId);

    @GET("user/duplicate/nickName/{nickName}")
    Call<Boolean> checkDuplicateNickName(@Path("nickName") String nickName);

    @PUT("user/{userId}/nickName/{nickName}")
    Call<Void> updateNickName(@Path("userId") Long userId, @Path("nickName") String nickName);

}