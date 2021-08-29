package com.starrynight.tourapiproject.myPage.myPageRetrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
  
    private static final String BASE_URL = "http://172.30.1.9:8080/v1/";

    public static MyPageRetrofitService getApiService(){return getInstance().create(MyPageRetrofitService.class);}

    private static Retrofit getInstance(){
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
