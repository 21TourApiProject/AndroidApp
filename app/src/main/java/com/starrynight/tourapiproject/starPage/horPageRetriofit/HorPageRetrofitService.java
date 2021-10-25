package com.starrynight.tourapiproject.starPage.horPageRetriofit;

import com.starrynight.tourapiproject.starPage.horItemPage.HorItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HorPageRetrofitService {
    @GET("horoscope/{todayMonth}")
    Call <List<HorItem>> getHoroscopes(@Path("todayMonth") int todayMonth);
}
