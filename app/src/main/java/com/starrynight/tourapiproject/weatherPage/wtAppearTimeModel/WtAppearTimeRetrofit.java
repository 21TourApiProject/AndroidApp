package com.starrynight.tourapiproject.weatherPage.wtAppearTimeModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.starrynight.tourapiproject.weatherPage.wtMetModel.WtMetInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WtAppearTimeRetrofit {
    private static String BASE_URL = "http://apis.data.go.kr/B090041/openapi/service/RiseSetInfoService/";

    public static WtAppearTimeInterface wtAppearTimeInterface() {
        return getAppearTimeInstance().create(WtAppearTimeInterface.class);
    }

    private static Retrofit getAppearTimeInstance() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
