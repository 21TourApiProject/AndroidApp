package com.starrynight.tourapiproject.postPage.postRetrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.starrynight.tourapiproject.retrofitConfig.TaskServer;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
* @className : RetrofitClient
* @description : 게시물 RetrofitClient 입니다.
* @modification : jinhyeok (2022-08-12) 주석 수정
* @author : 2022-08-12
* @date : jinhyeok
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   jinhyeok      2022-08-12       주석 수정

 */
public class RetrofitClient {
    private static final String BASE_URL = TaskServer.v1Server;


    public static PostPageRetrofitService getApiService() {
        return getInstance().create(PostPageRetrofitService.class);
    }

    private static Retrofit getInstance() {
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
