package com.starrynight.tourapiproject.touristPointPage.search;

import com.starrynight.tourapiproject.retrofitConfig.TaskServer;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchRetrofitFactory {

    private static String BASE_URL = TaskServer.kkoMapURL;

    public static SearchOpenApi create() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(SearchOpenApi.class);
    }
}
