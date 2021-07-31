package com.starrynight.tourapiproject.signUpPage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GeneralSignUpRetrofitService {

    @POST("user")
    Call<Void> signUp(@Body UserParams params);

    @GET("user/duplicate/email/{email}")
    Call<Boolean> checkDuplicateEmail(@Path("email")String email);

    @GET("user/duplicate/mobilePhoneNumber/{mobilePhoneNumber}")
    Call<Boolean> checkDuplicateMobilePhoneNumber(@Path("mobilePhoneNumber")String mobilePhoneNumber);

    @GET("hashTags")
    Call<List<HashTagResult>> getAllHashTag();

    @POST("myHashTag")
    Call<Void> createMyHashTag(@Body List<MyHashTagParams> myHashTagParams);
}
