package com.starrynight.tourapiproject.myPage.myPageRetrofit;

import com.starrynight.tourapiproject.myPage.myPost.MyPost;
import com.starrynight.tourapiproject.myPage.myWish.post.MyPostWish;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MyPageRetrofitService {

    @GET("user/{userId}")
    Call<User> getUser(@Path("userId") Long userId);

    @GET("user2/{userId}")
    Call<User2> getUser2(@Path("userId") Long userId);

    @GET("user/duplicate/nickName/{nickName}")
    Call<Boolean> checkDuplicateNickName(@Path("nickName") String nickName);

    @PUT("user/{userId}/nickName/{nickName}")
    Call<Void> updateNickName(@Path("userId") Long userId, @Path("nickName") String nickName);

    @PUT("user/{userId}/profileImage")
    Call<Void> updateProfileImage(@Path("userId") Long userId, @Body User3 profileImage);

    @PUT("user/{userId}/password/{originPwd}/{newPwd}")
    Call<Boolean> updatePassword(@Path("userId") Long userId, @Path("originPwd") String originPwd, @Path("newPwd") String newPwd);

    @GET("user/{userId}/myHashTag")
    Call<List<String>> getMyHashTag(@Path("userId") Long userId);

    @GET("myWishPost/{userId}")
    Call<List<MyPostWish>> getMyWishPost(@Path("userId") Long userId);

    @GET("post/user/{userId}")
    Call<List<MyPost>> getMyPost(@Path("userId") Long userId);

    @DELETE("user/{userId}")
    Call<Void> deleteUser(@Path("userId") Long userId);

}
