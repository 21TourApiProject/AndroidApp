package com.starrynight.tourapiproject.observationPage.observationPageRetrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.starrynight.tourapiproject.retrofitConfig.TaskServer;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
* @className : RetrofitClient.java
* @description : 관측지관련 레트로핏 설정
* @modification : gyul chyoung (2022-08-30) 주석추가
* @author : 2022-08-30
* @date : gyul chyoung
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   gyul chyoung       2022-08-30       주석추가
 */

public class RetrofitClient {


    private static final String BASE_URL = TaskServer.v1Server;

    public static ObservationPageRetrofitService getApiService() {
        return getInstance().create(ObservationPageRetrofitService.class);
    }

    private static Retrofit getInstance() {
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
