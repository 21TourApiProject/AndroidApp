package com.starrynight.tourapiproject.signUpPage.signUpRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @className : SignUpRetrofitService.java
 * @description : 회원가입 관련 Retrofit 주소 설정 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
public interface SignUpRetrofitService {

    @POST("user")
    Call<Void> signUp(@Body UserParams params);

    @POST("user/kakao")
    Call<Void> kakaoSignUp(@Body KakaoUserParams params);

    @DELETE("user/email/{email}")
    Call<Void> cancelSignUp(@Path("email") String email);

    @GET("user/duplicate/email/{email}")
    Call<Boolean> checkDuplicateEmail(@Path("email") String email);

    @GET("user/duplicate/mobilePhoneNumber/{mobilePhoneNumber}")
    Call<Boolean> checkDuplicateMobilePhoneNumber(@Path("mobilePhoneNumber") String mobilePhoneNumber);

    @POST("myHashTag/{email}")
    Call<Long> createMyHashTag(@Path("email") String email, @Body List<MyHashTagParams> myHashTagParams);

    @GET("user/login/{email}/{password}")
    Call<Long> logIn(@Path("email") String email, @Path("password") String password);

    @GET("user/kakaologin/{email}")
    Call<Long> kakaoLogIn(@Path("email") String email);

    @GET("user/login/email/{realName}/{mobilePhoneNumber}")
    Call<String> getEmail(@Path("realName") String realName, @Path("mobilePhoneNumber") String mobilePhoneNumber);

    @GET("user/login/password/{email}/{realName}/{mobilePhoneNumber}")
    Call<Boolean> getPassword(@Path("email") String email, @Path("realName") String realName, @Path("mobilePhoneNumber") String mobilePhoneNumber);

    @POST("myHashTag/change/{userId}")
    Call<Void> changeMyHashTag(@Path("userId") Long userId, @Body List<MyHashTagParams> myHashTagParams);
}
