package com.starrynight.tourapiproject.starPage.starPageRetrofit;

import com.starrynight.tourapiproject.starPage.constNameRetrofit.ConstellationParams2;
import com.starrynight.tourapiproject.starPage.starItemPage.StarItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StarPageRetrofitService {
    @GET("constellations")
    Call<List<StarItem>> getConstellation();

    @GET("constellations/constName")
    Call<List<ConstellationParams2>> getConstNames();

    @GET("constellation/todayConst")
    Call<List<StarItem>> getTodayConst();

    @GET("constellation/{constName}")
    Call<Constellation> getDetailConst(@Path("constName") String constName);
}
