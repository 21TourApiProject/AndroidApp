package com.starrynight.tourapiproject.weatherPage.wtMetModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.starrynight.tourapiproject.retrofitConfig.TaskServer;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WtMetRetrofit {
    private static String BASE_URL = TaskServer.openWeatherURL;

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

