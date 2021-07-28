package com.starrynight.tourapiproject.weatherPage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WtMetRetrofit {
    private static String baseUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/";


    public static WtMetInterface create() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(WtMetInterface.class);
    }
}

