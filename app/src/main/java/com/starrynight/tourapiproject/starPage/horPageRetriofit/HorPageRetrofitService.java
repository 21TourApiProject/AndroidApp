package com.starrynight.tourapiproject.starPage.horPageRetriofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HorPageRetrofitService {
    @GET("horoscope/{horId}")
    Call<Horoscope> getHoroscopes(@Path("horId") Long horId);
}
