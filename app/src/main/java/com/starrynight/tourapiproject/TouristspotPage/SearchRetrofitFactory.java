package com.starrynight.tourapiproject.TouristspotPage;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchRetrofitFactory {
    private static String BASE_URL="https://dapi.kakao.com/";
    public static SearchOpenApi create(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(SearchOpenApi.class);
    }
}
