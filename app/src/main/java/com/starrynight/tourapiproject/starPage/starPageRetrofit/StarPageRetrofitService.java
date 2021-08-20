package com.starrynight.tourapiproject.starPage.starPageRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StarPageRetrofitService {
    @GET("constellation")
    Call<List<Constellation>> getConstellation();

    @GET("constellation/todayConst")
    Call<List<Constellation>> getTodayConst();

    @GET("constellation/{constId}")
    Call<Constellation> getDetailConst(@Path("constId") Long constId);
}
