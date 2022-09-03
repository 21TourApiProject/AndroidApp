package com.starrynight.tourapiproject.weatherPage.wtFineDustModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.starrynight.tourapiproject.retrofitConfig.TaskServer;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @className : WtFineDustRetrofit
 * @description : 날씨-미세먼지 관련 Retrofit 설정입니다.
 * @modification : 2022-09-03 (hyeonz) 주석추가
 * @author : hyeonz
 * @date : 2022-09-03
 * @version : 1.0
====개정이력(Modification Information)====
수정일        수정자        수정내용
-----------------------------------------
hyeonz       2022-09-03   주석추가
 */
public class WtFineDustRetrofit {
    private static String BASE_URL = TaskServer.fineDustURL;

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
