package com.starrynight.tourapiproject.weatherPage.wtFineDustModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WtFineDustRetrofit {
    private static String BASE_URL = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/";

    public static WtFineDustInterface wtFineDustInterface() {
        return getFineDustInstance().create(WtFineDustInterface.class);
    }

    private static Retrofit getFineDustInstance() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
