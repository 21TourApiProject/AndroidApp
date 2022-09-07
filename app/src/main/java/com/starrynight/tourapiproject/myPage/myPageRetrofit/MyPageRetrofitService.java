package com.starrynight.tourapiproject.myPage.myPageRetrofit;

import com.starrynight.tourapiproject.alarmPage.Alarm;
import com.starrynight.tourapiproject.myPage.myPost.MyPost3;
import com.starrynight.tourapiproject.myPage.myWish.MyWish;
import com.starrynight.tourapiproject.myPage.myWish.obtp.MyWishObTp;
import com.starrynight.tourapiproject.myPage.myWish.post.MyPost;
import com.starrynight.tourapiproject.myPage.notice.Notice;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * @className : MyPageRetrofitService.java
 * @description : 마이페이지 관련 레트로핏 주소 설정 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
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
    Call<Void> updateProfileImage(@Path("userId") Long userId, @Body String profileImageName);

    @PUT("user/{userId}/password/{originPwd}/{newPwd}")
    Call<Boolean> updatePassword(@Path("userId") Long userId, @Path("originPwd") String originPwd, @Path("newPwd") String newPwd);

    @GET("user/{userId}/myHashTag")
    Call<List<String>> getMyHashTag(@Path("userId") Long userId);

    @GET("user/{userId}/myHashTag/three")
    Call<List<String>> getMyHashTag3(@Path("userId") Long userId);

    @GET("myWish/{userId}/{itemId}/{wishType}")
    Call<Boolean> isThereMyWish(@Path("userId") Long userId, @Path("itemId") Long itemId, @Path("wishType") Integer wishType);

    @POST("myWish/{userId}/{itemId}/{wishType}")
    Call<Void> createMyWish(@Path("userId") Long userId, @Path("itemId") Long itemId, @Path("wishType") Integer wishType);

    @DELETE("myWish/{userId}/{itemId}/{wishType}")
    Call<Void> deleteMyWish(@Path("userId") Long userId, @Path("itemId") Long itemId, @Path("wishType") Integer wishType);

    @GET("myWish/3/{userId}")
    Call<List<MyWish>> getMyWish3(@Path("userId") Long userId);

    @GET("myWish/observation/{userId}")
    Call<List<MyWishObTp>> getMyWishObservation(@Path("userId") Long userId);

    @GET("myWish/touristPoint/{userId}")
    Call<List<MyWishObTp>> getMyWishTouristPoint(@Path("userId") Long userId);

    @GET("myWish/post/{userId}")
    Call<List<MyPost>> getMyWishPost(@Path("userId") Long userId);

    @GET("post/3/{userId}")
    Call<List<MyPost3>> getMyPost3(@Path("userId") Long userId);

    @GET("post/user/{userId}")
    Call<List<MyPost>> getMyPost(@Path("userId") Long userId);

    @GET("post/observation/{observationId}")
    Call<List<MyPost>> getRelatePost(@Path("observationId") Long observationId);

    @DELETE("user/{userId}")
    Call<Void> deleteUser(@Path("userId") Long userId);

    @GET("alarms/")
    Call<List<Alarm>> getAllAlarm();

    @GET("user/{userId}/isKakao")
    Call<Boolean> checkIsKakao(@Path("userId") Long userId);

    @GET("notice/all")
    Call<List<Notice>> getAllNotice();
}
