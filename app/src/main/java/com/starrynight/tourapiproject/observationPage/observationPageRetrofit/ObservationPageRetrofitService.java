package com.starrynight.tourapiproject.observationPage.observationPageRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ObservationPageRetrofitService {

    @GET("observation/{observationId}")
    Call<Observation> getObservation(@Path("observationId") Long observationId);

    @GET("observation/{observationId}/observeHashTag")
    Call<List<String>> getObserveHashTags(@Path("observationId") Long observationId);

    @GET("observation/{observationId}/observeImage")
    Call<List<String>> getObserveImagePath(@Path("observationId") Long observationId);

    @GET("observation/{observationId}/observeFee")
    Call<List<ObserveFee>> getObserveFeeList(@Path("observationId") Long observationId);
}
