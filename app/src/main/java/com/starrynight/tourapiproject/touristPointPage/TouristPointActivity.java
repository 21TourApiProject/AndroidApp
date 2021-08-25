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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.touristPointPage.search.OnSearchItemClickListener;
import com.starrynight.tourapiproject.touristPointPage.search.SearchAdapter;
import com.starrynight.tourapiproject.touristPointPage.search.SearchData;
import com.starrynight.tourapiproject.touristPointPage.search.SearchOpenApi;
import com.starrynight.tourapiproject.touristPointPage.search.SearchRetrofitFactory;
import com.starrynight.tourapiproject.touristPointPage.touristPointRetrofit.Food;
import com.starrynight.tourapiproject.touristPointPage.touristPointRetrofit.Near;
import com.starrynight.tourapiproject.touristPointPage.touristPointRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.touristPointPage.touristPointRetrofit.TouristPoint;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

public class TouristPointActivity extends AppCompatActivity {

    private static final int NEAR = 101;

    private ViewPager2 slider;
    private String[] image = new String[1];

    Long contentId;
    String todayDate; //오늘 날짜

    TouristPoint tpData;
    Food foodData;
    Boolean isTp;

    TextView tpCongestion, tpTitle, tpOverviewSimple, cat3Name, overview, tpAddress, tpTel, tpUseTime, tpRestDate, tpOpenTimeFood, tpRestDateFood,
            tpExpGuide, tpParking, tpChkPet, tpHomePage, tpFirstMenu, tpTreatMenu, tpPacking, tpParkingFood;

    Button overviewPop;

    LinearLayout congestionLayout, addressLayout, telLayout, useTimeLayout, restDateLayout, openTimeFoodLayout, restDateFoodLayout, expGuideLayout,
            parkingLayout, chkPetLayout, homePageLayout, firstMenuLayout, treatMenuLayout, packingLayout, parkingFoodLayout;

    String overviewFull; //개요 전체

    List<Near> nearResult;
    String daumSearchWord;

    private static final String API_KEY= "KakaoAK 8e9d0698ed2d448e4b441ff77ccef198";
    List<SearchData.Document> Listdocument;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_tourist_point);

        Intent intent = getIntent();
        contentId = (Long) intent.getSerializableExtra("contentId"); //전 페이지에서 받아온 contentId

        //오늘 날짜
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        todayDate = sdf.format(cal.getTime());
        System.out.println(todayDate);

        //혼잡도 구하기
        CongestionThread thread = new CongestionThread();
        thread.start();

        tpCongestion = findViewById(R.id.tpCongestion);
        tpTitle = findViewById(R.id.tpTitle);
        tpOverviewSimple = findViewById(R.id.tpOverviewSimple);
        cat3Name = findViewById(R.id.cat3Name);
        overview= findViewById(R.id.overview);
        overviewPop = findViewById(R.id.overviewPop);
        tpAddress = findViewById(R.id.tpAddress);
        tpTel = findViewById(R.id.tpTel);
        tpUseTime = findViewById(R.id.tpUseTime);
        tpRestDate = findViewById(R.id.tpRestDate);
        tpOpenTimeFood = findViewById(R.id.tpOpenTimeFood);
        tpRestDateFood = findViewById(R.id.tpRestDateFood);
        tpExpGuide = findViewById(R.id.tpExpGuide);
        tpParking = findViewById(R.id.tpParking);
        tpChkPet = findViewById(R.id.tpChkPet);
        tpHomePage = findViewById(R.id.tpHomePage);
        tpFirstMenu = findViewById(R.id.tpFirstMenu);
        tpTreatMenu = findViewById(R.id.tpTreatMenu);
        tpPacking = findViewById(R.id.tpPacking);
        tpParkingFood = findViewById(R.id.tpParkingFood);

        congestionLayout = findViewById(R.id.congestionLayout);
        addressLayout = findViewById(R.id.addressLayout);
        telLayout = findViewById(R.id.telLayout);
        useTimeLayout = findViewById(R.id.useTimeLayout);
        restDateLayout = findViewById(R.id.restDateLayout);
        openTimeFoodLayout = findViewById(R.id.openTimeFoodLayout);
        restDateFoodLayout = findViewById(R.id.restDateFoodLayout);
        expGuideLayout = findViewById(R.id.expGuideLayout);
        parkingLayout = findViewById(R.id.parkingLayout);
        chkPetLayout = findViewById(R.id.chkPetLayout);
        homePageLayout = findViewById(R.id.homePageLayout);
        firstMenuLayout = findViewById(R.id.firstMenuLayout);
        treatMenuLayout = findViewById(R.id.treatMenuLayout);
        packingLayout = findViewById(R.id.packingLayout);
        parkingFoodLayout = findViewById(R.id.parkingFoodLayout);


        //이미지 슬라이더
        slider = findViewById(R.id.tpSlider);
        slider.setOffscreenPageLimit(image.length);


        //관광지 정보 불러오기
        LinearLayout tpInfo1 = findViewById(R.id.tpInfo1);
        LinearLayout foodInfo1 = findViewById(R.id.foodInfo1);

        Call<Long> call = RetrofitClient.getApiService().getContentType(contentId);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful()) {
                    Long result = response.body();
                    if (result == 12L){
                        System.out.println("타입 : 관광지");
                        Call<TouristPoint> call2 = RetrofitClient.getApiService().getTouristPointData(contentId);
                        call2.enqueue(new Callback<TouristPoint>() {
                            @Override
                            public void onResponse(Call<TouristPoint> call, Response<TouristPoint> response) {
                                if (response.isSuccessful()) {
                                    tpData = response.body();
                                    isTp = true;
                                    tpInfo1.setVisibility(View.VISIBLE);

                                    //이미지
                                    if (!tpData.getFirstImage().equals("null")){
                                        image[0] = tpData.getFirstImage();
                                        slider.setAdapter(new TpImageSliderAdapter(TouristPointActivity.this, image));

                                        slider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                            @Override
                                            public void onPageSelected(int position) {
                                                super.onPageSelected(position);
                                            }
                                        });
                                    }
                                    tpTitle.setText(tpData.getTitle());
                                    daumSearchWord = tpData.getTitle();
                                    cat3Name.setText(tpData.getCat3Name());

                                    if (!tpData.getOverview().equals("null")){
                                        overview.setText(tpData.getOverview().substring(0,120) + "...");
                                        tpOverviewSimple.setText(tpData.getOverview().substring(0,15) + "...");
                                        overviewFull = tpData.getOverview();
                                    }else{
                                        overview.setVisibility(View.GONE);
                                        overviewPop.setVisibility(View.GONE);
                                        tpOverviewSimple.setVisibility(View.GONE);
                                    }
                                    if (!tpData.getAddr1().equals("null")){
                                        tpAddress.setText(tpData.getAddr1());
                                    }else{
                                        addressLayout.setVisibility(View.GONE);
                                    }
                                    if (!tpData.getTel().equals("null")){
                                        tpTel.setText(tpData.getTel());
                                    }else{
                                        telLayout.setVisibility(View.GONE);
                                    }
                                    if (!tpData.getUseTime().equals("null")){
                                        tpUseTime.setText(tpData.getUseTime());
                                    }else{
                                        useTimeLayout.setVisibility(View.GONE);
                                    }
                                    if (!tpData.getRestDate().equals("null")){
                                        tpRestDate.setText(tpData.getRestDate());
                                    }else{
                                        restDateLayout.setVisibility(View.GONE);
                                    }
                                    if (!tpData.getExpGuide().equals("null")){
                                        tpExpGuide.setText(tpData.getExpGuide());
                                    }else{
                                        expGuideLayout.setVisibility(View.GONE);
                                    }
                                    if (!tpData.getParking().equals("null")){
                                        tpParking.setText(tpData.getParking());
                                    }else{
                                        parkingLayout.setVisibility(View.GONE);
                                    }
                                    if (!tpData.getChkPet().equals("null")){
                                        tpChkPet.setText(tpData.getChkPet());
                                    }else{
                                        chkPetLayout.setVisibility(View.GONE);
                                    }
                                    if (!tpData.getHomePage().equals("null")){
                                        String cleanHomepage = tpData.getHomePage();
                                        tpHomePage.setText(cleanHomepage);
                                        tpHomePage.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(cleanHomepage));
                                                startActivity(intent);
                                            }
                                        });
                                    }else{
                                        homePageLayout.setVisibility(View.GONE);
                                    }

                                } else {
                                    System.out.println("관광지 타입 불러오기 실패");
                                }
                            }
                            @Override
                            public void onFailure(Call<TouristPoint> call, Throwable t) {
                                Log.e("연결실패", t.getMessage());
                            }
                        });
                    }
                    else if(result == 39L){
                        System.out.println("타입 : 음식");
                        Call<Food> call2 = RetrofitClient.getApiService().getFoodData(contentId);
                        call2.enqueue(new Callback<Food>() {
                            @Override
                            public void onResponse(Call<Food> call, Response<Food> response) {
                                if (response.isSuccessful()) {
                                    foodData = response.body();
                                    isTp = false;
                                    foodInfo1.setVisibility(View.VISIBLE);

                                    //이미지
                                    if (!foodData.getFirstImage().equals("null")){
                                        image[0] = foodData.getFirstImage();
                                        slider.setAdapter(new TpImageSliderAdapter(TouristPointActivity.this, image));

                                        slider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                            @Override
                                            public void onPageSelected(int position) {
                                                super.onPageSelected(position);
                                            }
                                        });
                                    }
                                    tpTitle.setText(foodData.getTitle());
                                    daumSearchWord = foodData.getTitle();
                                    cat3Name.setText(foodData.getCat3Name());

                                    if (!foodData.getOverview().equals("null")){
                                        overview.setText(foodData.getOverview().substring(0,120) + "...");
                                        tpOverviewSimple.setText(foodData.getOverview().substring(0,15) + "...");
                                        overviewFull = foodData.getOverview();
                                    }else{
                                        overview.setVisibility(View.GONE);
                                        overviewPop.setVisibility(View.GONE);
                                        tpOverviewSimple.setVisibility(View.GONE);
                                    }
                                    if (!foodData.getAddr1().equals("null")){
                                        tpAddress.setText(foodData.getAddr1());
                                    }else{
                                        addressLayout.setVisibility(View.GONE);
                                    }
                                    if (!foodData.getTel().equals("null")){
                                        tpTel.setText(foodData.getTel());
                                    }else{
                                        telLayout.setVisibility(View.GONE);
                                    }
                                    if (!foodData.getOpenTimeFood().equals("null")){
                                        tpOpenTimeFood.setText(foodData.getOpenTimeFood());
                                    }else{
                                        openTimeFoodLayout.setVisibility(View.GONE);
                                    }
                                    if (!foodData.getRestDateFood().equals("null")){
                                        tpRestDateFood.setText(foodData.getRestDateFood());
                                    }else{
                                        restDateFoodLayout.setVisibility(View.GONE);
                                    }
                                    if (!foodData.getFirstMenu().equals("null")){
                                        tpFirstMenu.setText(foodData.getFirstMenu());
                                    }else{
                                        firstMenuLayout.setVisibility(View.GONE);
                                    }
                                    if (!foodData.getTreatMenu().equals("null")){
                                        tpTreatMenu.setText(foodData.getTreatMenu());
                                    }else{
                                        treatMenuLayout.setVisibility(View.GONE);
                                    }
                                    if (!foodData.getPacking().equals("null")){
                                        tpPacking.setText(foodData.getPacking());
                                    }else{
                                        packingLayout.setVisibility(View.GONE);
                                    }
                                    if (!foodData.getParkingFood().equals("null")){
                                        tpParkingFood.setText(foodData.getParkingFood());
                                    }else{
                                        parkingFoodLayout.setVisibility(View.GONE);
                                    }

                                } else {
                                    System.out.println("음식 타입 불러오기 실패");
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


        //펼치기
        LinearLayout tpInfo2 = findViewById(R.id.tpInfo2);
        LinearLayout foodInfo2 = findViewById(R.id.foodInfo2);
        Button moreInfo = findViewById(R.id.moreInfo);
        moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreInfo.setVisibility(View.GONE);
                if (isTp){
                    tpInfo2.setVisibility(View.VISIBLE);
                }else{
                    foodInfo2.setVisibility(View.VISIBLE);
                }
            }
        });

        //자세히 보기 팝업
        Button overviewPop = findViewById(R.id.overviewPop);
        overviewPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TouristPointActivity.this, OverviewPopActivity.class);
                intent.putExtra("overview", overviewFull);
                startActivity(intent);
            }
        });

        //주변에 가볼만한 곳
        RecyclerView nearRecyclerview = findViewById(R.id.nearRecyclerview);
        LinearLayoutManager nearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        nearRecyclerview.setLayoutManager(nearLayoutManager);
        nearRecyclerview.setHasFixedSize(true);
        nearResult = new ArrayList<>();

        Call<List<Near>> call2 = RetrofitClient.getApiService().getNearTouristData(contentId);
        call2.enqueue(new Callback<List<Near>>() {
            @Override
            public void onResponse(Call<List<Near>> call, Response<List<Near>> response) {
                if (response.isSuccessful()) {
                    nearResult = response.body();
                    int len = nearResult.size();
                    String[] nearImages = new String[len];
                    for(int i=0; i<len; i++){
                        nearImages[i] = nearResult.get(i).getFirstImage();
                    }

                    NearAdapter nearAdapter = new NearAdapter(nearResult, nearImages,TouristPointActivity.this);
                    nearRecyclerview.setAdapter(nearAdapter);
                    nearAdapter.setOnNearItemClickListener(new OnNearItemClickListener(){
                        @Override
                        public void onItemClick(NearAdapter.ViewHolder holder, View view, int position) {
                            Near item = nearAdapter.getItem(position);
                            Intent intent = new Intent(TouristPointActivity.this, TouristPointActivity.class);
                            intent.putExtra("contentId", item.getContentId());
                            startActivityForResult(intent, NEAR);
                        }
                    });
                } else {
                    System.out.println("주변 관광지 불러오기 실패");
                }
            }
            @Override
            public void onFailure(Call<List<Near>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });


        //다음 블로그 검색결과
        RecyclerView daumRecyclerview = findViewById(R.id.daumRecyclerview);
        LinearLayoutManager daumLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        daumRecyclerview.setLayoutManager(daumLayoutManager);
        daumRecyclerview.setHasFixedSize(true);
        Listdocument = new ArrayList<>();

        SearchOpenApi openApi= SearchRetrofitFactory.create();
        openApi.getData("카페","accuracy",1,3,API_KEY)
                .enqueue(new Callback<SearchData>() {
                    @Override
                    public void onResponse(Call<SearchData> call, Response<SearchData> response) {
                        Log.d("my tag","성공");
                        Listdocument = response.body().Searchdocuments;
                            SearchAdapter adapter1 = new SearchAdapter(Listdocument);
                        daumRecyclerview.setAdapter(adapter1);
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
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://blog.daum.net/"+query));
                    startActivity(intent);
                }
            });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEAR){
            //새로고침
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    //예측혼잡도구분코드를 얻기 위한 open api 호출
    private class CongestionThread extends Thread {
        private static final String TAG = "CongestionThread";
        public CongestionThread() {}
        public void run() {
            String key = "?ServiceKey=VQ0keALnEea3BkQdEGgwgCD8XNDNR%2Fg98L9D4GzWryl4UYHnGfUUUI%2BHDA6DdzYjjzJmuHT1UmuJZ7wJHoGfuA%3D%3D"; //인증키
            String result;

            try {
                URL url = new URL("http://api.visitkorea.or.kr/openapi/service/rest/DataLabService/tarDecoList" + key + "&startYmd=" + todayDate + "&endYmd=" + todayDate + "&contentId=" + contentId + "&MobileOS=AND&MobileApp=tourApiProject&_type=json");
                BufferedReader bf; //빠른 속도로 데이터를 처리하기 위해
                bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
                result = bf.readLine(); //api로 받아온 결과

                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
                JSONObject response = (JSONObject) jsonObject.get("response");
                JSONObject body = (JSONObject) response.get("body");
                Long count = (Long)body.get("totalCount");

                if (count == 0){
                    System.out.println("혼잡도 데이터 없음");
                }
                else {
                    JSONObject items = (JSONObject) body.get("items");
                    JSONObject item = (JSONObject) items.get("item");
                    Long code = (Long) item.get("estiDecoDivCd");
                    bf.close();
                    System.out.println("code = " + code);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            congestionLayout.setVisibility(View.VISIBLE);
                            tpCongestion.setText(Long.toString(code));
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}