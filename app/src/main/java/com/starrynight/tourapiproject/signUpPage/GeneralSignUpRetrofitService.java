package com.starrynight.tourapiproject.signUpPage;

import com.squareup.okhttp.ResponseBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GeneralSignUpRetrofitService {

    @POST("user")
    Call<Void> signUp(@Body UserParams params);
}
