package com.starrynight.tourapiproject.starPage.starPageRetrofit;

import com.starrynight.tourapiproject.starPage.starItemPage.StarItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StarPageRetrofitService {
    @GET("constellations")
    Call<List<StarItem>> getConstellation();

    @GET("constellation/todayConst")
    Call<List<StarItem>> getTodayConst();

    @GET("constellation/{constId}")
    Call<Constellation> getDetailConst(@Path("constId") Long constId);
}
