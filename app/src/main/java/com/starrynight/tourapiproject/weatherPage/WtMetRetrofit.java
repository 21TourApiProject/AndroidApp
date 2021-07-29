package com.starrynight.tourapiproject.weatherPage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WtMetRetrofit {
    private static String BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/";

    public static WtMetInterface wtMetInterface() {
        return getWeatherInstance().create(WtMetInterface.class);
    }

    private static Retrofit getWeatherInstance() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}

