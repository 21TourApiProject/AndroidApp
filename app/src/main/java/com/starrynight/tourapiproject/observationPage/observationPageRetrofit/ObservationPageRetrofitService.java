package com.starrynight.tourapiproject.observationPage.observationPageRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
* @className : ObservationPageRetrofitService.java
* @description : 관측지페이지 관련 레트로핏 주소설정
* @modification : gyul chyoung (2022-08-30) 내용
* @author : 2022-08-30
* @date : gyul chyoung
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   gyul chyoung       2022-08-30       최초생성
 */

public interface ObservationPageRetrofitService {

    @GET("observation/{observationId}")
    Call<Observation> getObservation(@Path("observationId") Long observationId);

    @GET("observation/{observationId}/observeHashTag")
    Call<List<String>> getObserveHashTags(@Path("observationId") Long observationId);

    @GET("observation/{observationId}/observeImage")
    Call<List<String>> getObserveImagePath(@Path("observationId") Long observationId);

    @GET("observation/{observationId}/observeImageInfo")
    Call<List<ObserveImageInfo>> getObserveImageInfo(@Path("observationId") Long observationId);

    @GET("observation/{observationId}/observeFee")
    Call<List<ObserveFee>> getObserveFeeList(@Path("observationId") Long observationId);

    @GET("observation/{observationId}/courseTouristPoint")
    Call<List<CourseTouristPoint>> getCourseTouristPointList(@Path("observationId") Long observationId);

    @GET("observation/{observationId}/courseNames")
    Call<List<String>> getCourseNameList(@Path("observationId") Long observationId);

    @GET("observations")
    Call<List<Observation>> getAllObservation();
}
