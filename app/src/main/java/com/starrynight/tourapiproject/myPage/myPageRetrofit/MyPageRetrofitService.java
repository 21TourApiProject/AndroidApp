package com.starrynight.tourapiproject.myPage.myPageRetrofit;

import com.starrynight.tourapiproject.myPage.myPost.MyPost;
import com.starrynight.tourapiproject.myPage.myWish.MyWish;
import com.starrynight.tourapiproject.myPage.myWish.obTp.MyWishObTp;
import com.starrynight.tourapiproject.myPage.myWish.post.MyWishPost;

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

    @GET("myWish/{userId}")
    Call<List<MyWish>> getMyWish(@Path("userId") Long userId);

    @GET("myWish/observation/{userId}")
    Call<List<MyWishObTp>> getMyWishObservation(@Path("userId") Long userId);

    @GET("myWish/touristPoint/{userId}")
    Call<List<MyWishObTp>> getMyWishTouristPoint(@Path("userId") Long userId);

    @GET("myWish/post/{userId}")
    Call<List<MyWishPost>> getMyWishPost(@Path("userId") Long userId);

    @GET("post/user/{userId}")
    Call<List<MyPost>> getMyPost(@Path("userId") Long userId);

    @DELETE("user/{userId}")
    Call<Void> deleteUser(@Path("userId") Long userId);

}
