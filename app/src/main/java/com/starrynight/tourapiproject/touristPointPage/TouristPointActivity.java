package com.starrynight.tourapiproject.touristPointPage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.starrynight.tourapiproject.ObservationsiteActivity;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.postItemPage.OnPostItemClickListener;
import com.starrynight.tourapiproject.postItemPage.Post_point_item_Adapter;
import com.starrynight.tourapiproject.postItemPage.post_point_item;
import com.starrynight.tourapiproject.touristPointPage.search.OnSearchItemClickListener;
import com.starrynight.tourapiproject.touristPointPage.search.SearchAdapter;
import com.starrynight.tourapiproject.touristPointPage.search.SearchData;
import com.starrynight.tourapiproject.touristPointPage.search.SearchOpenApi;
import com.starrynight.tourapiproject.touristPointPage.search.SearchRetrofitFactory;
import com.starrynight.tourapiproject.touristPointPage.touristPointRetrofit.Food;
import com.starrynight.tourapiproject.touristPointPage.touristPointRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.touristPointPage.touristPointRetrofit.TouristPoint;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

public class TouristPointActivity extends AppCompatActivity {

    private ViewPager2 slider;
    private LinearLayout indicator;
    private String[] images = new String[1];
    Long contentId = 127480L; //나중에 수정
    TouristPoint tpData;
    Food foodData;
    Boolean isTp;

    private static final String API_KEY= "KakaoAK 8e9d0698ed2d448e4b441ff77ccef198";
    List<SearchData.Document> Listdocument;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_tourist_point);

        //이미지 슬라이더
        slider = findViewById(R.id.tpSlider);
        indicator = findViewById(R.id.tpIndicator);

        slider.setOffscreenPageLimit(3);
        slider.setAdapter(new TpImageSliderAdapter(this, images));

        slider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });
        setupIndicators(images.length);


        //관광지 정보 불러오기
        Call<Long> call = RetrofitClient.getApiService().getContentType(contentId);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful()) {
                    Long result = response.body();
                    if (result == 12L){
                        System.out.println("관광지");
                        Call<TouristPoint> call2 = RetrofitClient.getApiService().getTouristPointData(contentId);
                        call2.enqueue(new Callback<TouristPoint>() {
                            @Override
                            public void onResponse(Call<TouristPoint> call, Response<TouristPoint> response) {
                                if (response.isSuccessful()) {
                                    tpData = response.body();
                                    isTp = true;

                                } else {
                                    System.out.println("tp 정보 불러오기 실패");
                                }
                            }
                            @Override
                            public void onFailure(Call<TouristPoint> call, Throwable t) {
                                Log.e("연결실패", t.getMessage());
                            }
                        });
                    }
                    else if(result == 39L){
                        System.out.println("음식");
                        Call<Food> call2 = RetrofitClient.getApiService().getFoodData(contentId);
                        call2.enqueue(new Callback<Food>() {
                            @Override
                            public void onResponse(Call<Food> call, Response<Food> response) {
                                if (response.isSuccessful()) {
                                    foodData = response.body();
                                    isTp = false;

                                } else {
                                    System.out.println("food 정보 불러오기 실패");
                                }
                            }
                            @Override
                            public void onFailure(Call<Food> call, Throwable t) {
                                Log.e("연결실패", t.getMessage());
                            }
                        });
                    }
                } else {
                    System.out.println("관광지 정보 불러오기 실패");
                }
            }
            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });

        TextView tpTitle = findViewById(R.id.tpTitle);
        TextView cat3Name = findViewById(R.id.cat3Name);
        TextView overview= findViewById(R.id.overview);
        TextView tpAddress = findViewById(R.id.tpAddress);
        TextView tpTel = findViewById(R.id.tpTel);
        TextView tpUseTime = findViewById(R.id.tpUseTime);
        TextView tpRestDate = findViewById(R.id.tpRestDate);
        TextView tpOpenTimeFood = findViewById(R.id.tpOpenTimeFood);
        TextView tpRestDateFood = findViewById(R.id.tpRestDateFood);
        TextView tpExpGuide = findViewById(R.id.tpExpGuide);
        TextView tpParking = findViewById(R.id.tpParking);
        TextView tpChkPet = findViewById(R.id.tpChkPet);
        TextView tpHomePage = findViewById(R.id.tpHomePage);
        TextView tpFirstMenu = findViewById(R.id.tpFirstMenu);
        TextView tpTreatMenu = findViewById(R.id.tpTreatMenu);
        TextView tpPacking = findViewById(R.id.tpPacking);
        TextView tpParkingFood = findViewById(R.id.tpParkingFood);

        if(isTp){
            tpTitle.setText(tpData.getTitle());
            cat3Name.setText(tpData.getCat3Name());
            if (!tpData.getOverview().isEmpty()){
                overview.setText(tpData.getOverview());
            }
            if (!tpData.getAddr1().isEmpty()){
                tpAddress.setText(tpData.getAddr1());
            }
            tpTel.setText(tpData.getTel());
            tpUseTime.setText(tpData.getUseTime());
            tpRestDate.setText(tpData.getRestDate());
            tpExpGuide.setText(tpData.getExpGuide());
            tpParking.setText(tpData.getParking());
            tpChkPet.setText(tpData.getChkPet());
            tpHomePage.setText(tpData.getHomePage());
        }
        else{
            tpTitle.setText(foodData.getTitle());
            cat3Name.setText(foodData.getCat3Name());
            overview.setText(foodData.getOverview());
            tpAddress.setText(foodData.getAddr1());
            tpTel.setText(foodData.getTel());
            tpOpenTimeFood.setText(foodData.getOpenTimeFood());
            tpRestDateFood.setText(foodData.getRestDateFood());
            tpFirstMenu.setText(foodData.getFirstMenu());
            tpTreatMenu.setText(foodData.getTreatMenu());
            tpPacking.setText(foodData.getPacking());
            tpParkingFood.setText(foodData.getParkingFood());
        }



        Post_point_item_Adapter adapter2 =new Post_point_item_Adapter();
        RecyclerView recyclerView1 = findViewById(R.id.nearRecyclerview);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(layoutManager1);

        recyclerView1.setAdapter(adapter2);

        adapter2.addItem(new post_point_item("관광지1","https://cdn.pixabay.com/photo/2019/12/13/07/35/city-4692432_960_720.jpg"));
        adapter2.addItem(new post_point_item("관광지2","https://cdn.pixabay.com/photo/2018/08/11/20/37/cathedral-3599450_960_720.jpg"));
        adapter2.addItem(new post_point_item("관광지3","https://cdn.pixabay.com/photo/2018/07/15/23/22/prague-3540883_960_720.jpg"));
        adapter2.setOnItemClicklistener(new OnPostItemClickListener() {
            @Override
            public void onItemClick(Post_point_item_Adapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(getApplicationContext(), ObservationsiteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        recyclerView1.setAdapter((adapter2));

        RecyclerView recyclerView2 = findViewById(R.id.daumRecyclerview);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setHasFixedSize(true);
        Listdocument = new ArrayList<>();

        SearchOpenApi openApi= SearchRetrofitFactory.create();
        openApi.getData("일산 카페","accuracy",1,3,API_KEY)
                .enqueue(new Callback<SearchData>() {
                    @Override
                    public void onResponse(Call<SearchData> call, Response<SearchData> response) {
                        Log.d("my tag","성공");
                        Listdocument = response.body().Searchdocuments;
                            SearchAdapter adapter1 = new SearchAdapter(Listdocument);
                            recyclerView2.setAdapter(adapter1);
                            adapter1.setOnItemClickListener(new OnSearchItemClickListener() {
                                @Override
                                public void onItemClick(SearchAdapter.ViewHolder holder, View view, int position) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(Listdocument.get(position).getUrl()));
                                    startActivity(intent);
                                }
                            });
                    }

                    @Override
                    public void onFailure(Call<SearchData> call, Throwable t) {
                        Log.e("my tag","에러");
                    }
                });


            Button daum_btn=findViewById(R.id.daumMore);
            daum_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.kakao.com/"+query));
                    startActivity(intent);
                }
            });
         }

    private void setupIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.post__indicator_inactive));
            indicators[i].setLayoutParams(params);
            indicator.addView(indicators[i]);
        }
        setCurrentIndicator(0);
    }


    private void setCurrentIndicator(int position) {
        int childCount = indicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) indicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.post__indicator_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.post__indicator_inactive
                ));
            }
        }
    }

}
