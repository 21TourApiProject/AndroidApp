package com.starrynight.tourapiproject.observationPage.observationPageRetrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
  
    private static final String BASE_URL = "http://172.30.1.41:8080/v1/";

    public static ObservationPageRetrofitService getApiService(){return getInstance().create(ObservationPageRetrofitService.class);}

    private static Retrofit getInstance(){
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
