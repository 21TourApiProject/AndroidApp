package com.starrynight.tourapiproject.searchPage.searchPageRetrofit;

import com.starrynight.tourapiproject.myPage.myWish.obtp.MyWishObTp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SearchPageRetrofitService {

    @GET("touristDataHashTag/search/{areaCodeList}/{hashTagIdList}")
    Call<List<MyWishObTp>> getTouristDataWithFilter(@Path("areaCodeList") List<Long> areaCodeList, @Path("hashTagIdList") List<Long> hashTagIdList);
}
