package com.starrynight.tourapiproject.touristPointPage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.mapPage.Activities;
import com.starrynight.tourapiproject.mapPage.BalloonObject;
import com.starrynight.tourapiproject.observationPage.RecyclerDecoration;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @className : TouristPointActivity.java
 * @description : 관광지 페이지 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
public class TouristPointActivity extends AppCompatActivity {
    Long userId;
    Boolean isWish;
    BalloonObject balloonObject = new BalloonObject();

    private static final int NEAR = 101;
    private static final String TAG = "TouristPoint";

    ImageView slider;

    Long contentId;
    String todayDate; //오늘 날짜

    TouristPoint tpData;
    Food foodData;
    Boolean isTp;

    TextView tpBanner, tpTitle, cat3Name, overview, tpAddress, tpTel, tpUseTime, tpRestDate, tpOpenTimeFood, tpRestDateFood,
            tpExpGuide, tpParking, tpChkPet, tpHomePage, tpFirstMenu, tpTreatMenu, tpPacking, tpParkingFood, overviewPop, daumMore;

    Button tpWish;
    ImageView tpCongestion, nearLine;

    LinearLayout congestionLayout, addressLayout, telLayout, useTimeLayout, restDateLayout, openTimeFoodLayout, restDateFoodLayout, expGuideLayout,
            parkingLayout, chkPetLayout, homePageLayout, firstMenuLayout, treatMenuLayout, packingLayout, parkingFoodLayout, nearLayout;

    String overviewFull; //개요 전체

    List<String> hashTagResult;
    List<Near> nearResult;

    public String daumSearchWord;
    private static final String API_KEY = "KakaoAK 8e9d0698ed2d448e4b441ff77ccef198";
    List<SearchData.Document> Listdocument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_point);

        Intent intent = getIntent();
        contentId = (Long) intent.getSerializableExtra("contentId"); //전 페이지에서 받아온 contentId

        //오늘 날짜
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        todayDate = sdf.format(cal.getTime());
        Log.d(TAG, "오늘날짜 : " + todayDate);

        //혼잡도 구하기
        CongestionThread thread = new CongestionThread();
        thread.start();

//        tpBanner = findViewById(R.id.tpBanner);
        tpWish = findViewById(R.id.tpWish);
        tpCongestion = findViewById(R.id.tpCongestion);
        tpTitle = findViewById(R.id.tpTitle);
        cat3Name = findViewById(R.id.cat3Name);
        overview = findViewById(R.id.overview);
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
        nearLayout = findViewById(R.id.nearLayout);
        nearLine = findViewById(R.id.nearLine);
        daumMore = findViewById(R.id.daumMore);
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

        ImageView tpBack = findViewById(R.id.tpBack);
        tpBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //앱 내부 저장소의 userId 데이터 읽기
        String fileName = "userId";
        try {
            FileInputStream fis = this.openFileInput(fileName);
            String line = new BufferedReader(new InputStreamReader(fis)).readLine();
            userId = Long.parseLong(line);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //이미 찜한건지 확인
        Call<Boolean> call0 = com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient.getApiService().isThereMyWish(userId, contentId, 1);
        call0.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    if (response.body()) {
                        isWish = true;
                        tpWish.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    } else {
                        isWish = false;
                    }
                } else {
                    Log.e(TAG, "내 찜 조회하기 실패");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });

        //저장 버튼
        tpWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isWish) { //찜 안한 상태일때
                    Call<Void> call = com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient.getApiService().createMyWish(userId, contentId, 1);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                //버튼 디자인 바뀌게 구현하기
                                isWish = true;
                                tpWish.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                                Toast.makeText(getApplicationContext(), "나의 여행버킷리스트에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e(TAG, "관광지 찜 실패");
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("연결실패", t.getMessage());
                        }
                    });
                } else {
                    Call<Void> call = com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient.getApiService().deleteMyWish(userId, contentId, 1);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                isWish = false;
                                tpWish.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                                Toast.makeText(getApplicationContext(), "나의 여행버킷리스트에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e(TAG, "관광지 찜 삭제 실패");
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("연결실패", t.getMessage());
                        }
                    });
                }
            }
        });

        balloonObject.setId(contentId);
        balloonObject.setTag(2);

        //이미지 슬라이더
        slider = findViewById(R.id.tpSlider);

        //관광지 정보 불러오기
        Call<Long> call = RetrofitClient.getApiService().getContentType(contentId);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful()) {
                    Long result = response.body();
                    if (result == 12L) {
                        Log.d(TAG, "타입 : 관광지");
                        Call<TouristPoint> call1 = RetrofitClient.getApiService().getTouristPointData(contentId);
                        call1.enqueue(new Callback<TouristPoint>() {
                            @Override
                            public void onResponse(Call<TouristPoint> call, Response<TouristPoint> response) {
                                if (response.isSuccessful()) {
                                    tpData = response.body();
                                    isTp = true;

                                    balloonObject.setLongitude(tpData.getMapX());
                                    balloonObject.setLatitude(tpData.getMapY());
                                    balloonObject.setName(tpData.getTitle());

                                    //주소를 두단어까지 줄임
                                    String address = tpData.getAddr();
                                    int i = address.indexOf(' ');
                                    if (i != -1) {
                                        int j = address.indexOf(' ', i + 1);
                                        if (j != -1) {
                                            balloonObject.setAddress(address.substring(0, j));
                                        } else {
                                            balloonObject.setAddress(address);
                                        }
                                    } else {
                                        balloonObject.setAddress(address);
                                    }

                                    balloonObject.setPoint_type(tpData.getCat3Name());
                                    balloonObject.setIntro(tpData.getOverviewSim());

                                    //이미지
                                    if (tpData.getFirstImage() != null) {
                                        balloonObject.setImage(tpData.getFirstImage());
                                        Glide.with(getApplicationContext()).load(tpData.getFirstImage()).into(slider);
                                    }
//                                    tpBanner.setText(tpData.getTitle());
                                    tpTitle.setText(tpData.getTitle());
                                    daumSearchWord = tpData.getTitle();

                                    //다음 블로그 검색결과
                                    RecyclerView daumRecyclerview = findViewById(R.id.daumRecyclerview);
                                    LinearLayoutManager daumLayoutManager = new LinearLayoutManager(TouristPointActivity.this, LinearLayoutManager.VERTICAL, false);
                                    daumRecyclerview.setLayoutManager(daumLayoutManager);
                                    Listdocument = new ArrayList<>();

                                    SearchOpenApi openApi = SearchRetrofitFactory.create();
                                    openApi.getData(daumSearchWord, "accuracy", 1, 3, API_KEY)
                                            .enqueue(new Callback<SearchData>() {
                                                @Override
                                                public void onResponse(Call<SearchData> call, Response<SearchData> response) {
                                                    if (response.isSuccessful()) {
                                                        Log.d(TAG, "다음 검색 성공");
                                                        Listdocument = response.body().Searchdocuments;
                                                        SearchAdapter searchAdapter = new SearchAdapter(Listdocument);
                                                        daumRecyclerview.setAdapter(searchAdapter);
                                                        searchAdapter.setOnItemClickListener(new OnSearchItemClickListener() {
                                                            @Override
                                                            public void onItemClick(SearchAdapter.ViewHolder holder, View view, int position) {
                                                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Listdocument.get(position).getUrl()));
                                                                startActivity(intent);
                                                            }
                                                        });
                                                    } else {
                                                        Log.e(TAG, "다음 검색 실패");
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<SearchData> call, Throwable t) {
                                                    Log.e(TAG, "연결실패");
                                                }
                                            });

                                    daumMore.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String encode = null;
                                            try {
                                                encode = URLEncoder.encode(daumSearchWord, "UTF-8");
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://search.daum.net/search?w=blog&f=section&m=&SA=daumsec&lpp=10&nil_profile=vsearch&nil_src=blog&q=" + encode));
                                            startActivity(intent);
                                        }
                                    });

                                    cat3Name.setText(tpData.getCat3Name());

                                    if (tpData.getOverview() != null) {
                                        String tpO = tpData.getOverview();
                                        if (tpO.length() > 120)
                                            overview.setText(tpO.substring(0, 120) + "...");
                                        else {
                                            overview.setText(tpO);
                                            overviewPop.setVisibility(View.GONE);
                                        }
                                        overviewFull = tpO;
                                    } else {
                                        overview.setVisibility(View.GONE);
                                        overviewPop.setVisibility(View.GONE);
                                    }
                                    if (tpData.getAddr() != null) {
                                        tpAddress.setText(tpData.getAddr());
                                        addressLayout.setVisibility(View.VISIBLE);
                                    }
                                    if (tpData.getTel() != null) {
                                        tpTel.setText(tpData.getTel());
                                        telLayout.setVisibility(View.VISIBLE);
                                    }
                                    if (tpData.getUseTime() != null) {
                                        tpUseTime.setText(tpData.getUseTime());
                                        useTimeLayout.setVisibility(View.VISIBLE);
                                    }
                                    if (tpData.getRestDate() != null) {
                                        tpRestDate.setText(tpData.getRestDate());
                                        restDateLayout.setVisibility(View.VISIBLE);
                                    }
                                    if (tpData.getExpGuide() != null) {
                                        tpExpGuide.setText(tpData.getExpGuide());
                                        expGuideLayout.setVisibility(View.VISIBLE);
                                    }
                                    if (tpData.getParking() != null) {
                                        tpParking.setText(tpData.getParking());
                                        parkingLayout.setVisibility(View.VISIBLE);
                                    }
                                    if (tpData.getChkPet() != null) {
                                        tpChkPet.setText(tpData.getChkPet());
                                        chkPetLayout.setVisibility(View.VISIBLE);
                                    }
                                    if (tpData.getHomePage() != null) {
                                        String cleanHomepage = tpData.getHomePage();
                                        tpHomePage.setText(cleanHomepage);
                                        homePageLayout.setVisibility(View.VISIBLE);
                                        tpHomePage.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(cleanHomepage));
                                                startActivity(intent);
                                            }
                                        });
                                    }

                                } else {
                                    Log.e(TAG, "관광지 타입 불러오기 실패");
                                }
                            }

                            @Override
                            public void onFailure(Call<TouristPoint> call, Throwable t) {
                                Log.e("연결실패", t.getMessage());
                            }
                        });
                    } else if (result == 39L) {
                        Log.d(TAG, "타입 : 음식");
                        Call<Food> call2 = RetrofitClient.getApiService().getFoodData(contentId);
                        call2.enqueue(new Callback<Food>() {
                            @Override
                            public void onResponse(Call<Food> call, Response<Food> response) {
                                if (response.isSuccessful()) {
                                    foodData = response.body();
                                    isTp = false;

                                    balloonObject.setLongitude(foodData.getMapX());
                                    balloonObject.setLatitude(foodData.getMapY());
                                    balloonObject.setName(foodData.getTitle());

                                    //주소를 두단어까지 줄임
                                    String address = foodData.getAddr();
                                    int i = address.indexOf(' ');
                                    if (i != -1) {
                                        int j = address.indexOf(' ', i + 1);
                                        if (j != -1) {
                                            balloonObject.setAddress(address.substring(0, j));
                                        } else {
                                            balloonObject.setAddress(address);
                                        }
                                    } else {
                                        balloonObject.setAddress(address);
                                    }

                                    balloonObject.setPoint_type(foodData.getCat3Name());
                                    balloonObject.setIntro(foodData.getOverviewSim());

                                    //이미지
                                    if (foodData.getFirstImage() != null) {
                                        balloonObject.setImage(foodData.getFirstImage());
                                        Glide.with(getApplicationContext()).load(foodData.getFirstImage()).into(slider);
                                    }
//                                    tpBanner.setText(foodData.getTitle());
                                    tpTitle.setText(foodData.getTitle());
                                    daumSearchWord = foodData.getTitle();
                                    //다음 블로그 검색결과
                                    RecyclerView daumRecyclerview = findViewById(R.id.daumRecyclerview);
                                    LinearLayoutManager daumLayoutManager = new LinearLayoutManager(TouristPointActivity.this, LinearLayoutManager.VERTICAL, false);
                                    daumRecyclerview.setLayoutManager(daumLayoutManager);
                                    Listdocument = new ArrayList<>();

                                    SearchOpenApi openApi = SearchRetrofitFactory.create();
                                    openApi.getData(daumSearchWord, "accuracy", 1, 3, API_KEY)
                                            .enqueue(new Callback<SearchData>() {
                                                @Override
                                                public void onResponse(Call<SearchData> call, Response<SearchData> response) {
                                                    Log.d("my tag", "성공");
                                                    Listdocument = response.body().Searchdocuments;
                                                    SearchAdapter adapter1 = new SearchAdapter(Listdocument);
                                                    daumRecyclerview.setAdapter(adapter1);
                                                    adapter1.setOnItemClickListener(new OnSearchItemClickListener() {
                                                        @Override
                                                        public void onItemClick(SearchAdapter.ViewHolder holder, View view, int position) {
                                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Listdocument.get(position).getUrl()));
                                                            startActivity(intent);
                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onFailure(Call<SearchData> call, Throwable t) {
                                                    Log.e("my tag", "에러");
                                                }
                                            });

                                    daumMore.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String encode = null;
                                            try {
                                                encode = URLEncoder.encode(daumSearchWord, "UTF-8");
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://search.daum.net/search?w=blog&f=section&m=&SA=daumsec&lpp=10&nil_profile=vsearch&nil_src=blog&q=" + encode));
                                            startActivity(intent);
                                        }
                                    });

                                    cat3Name.setText(foodData.getCat3Name());

                                    if (foodData.getOverview() != null) {
                                        String foodO = foodData.getOverview();
                                        if (foodO.length() > 120)
                                            overview.setText(foodO.substring(0, 120) + "...");
                                        else {
                                            overview.setText(foodO);
                                            overviewPop.setVisibility(View.GONE);
                                        }
                                        overviewFull = foodO;
                                    } else {
                                        overview.setVisibility(View.GONE);
                                        overviewPop.setVisibility(View.GONE);
                                    }
                                    if (foodData.getAddr() != null) {
                                        tpAddress.setText(foodData.getAddr());
                                        addressLayout.setVisibility(View.VISIBLE);
                                    }
                                    if (foodData.getTel() != null) {
                                        tpTel.setText(foodData.getTel());
                                        telLayout.setVisibility(View.VISIBLE);
                                    }
                                    if (foodData.getOpenTimeFood() != null) {
                                        tpOpenTimeFood.setText(foodData.getOpenTimeFood());
                                        openTimeFoodLayout.setVisibility(View.VISIBLE);
                                    }
                                    if (foodData.getRestDateFood() != null) {
                                        tpRestDateFood.setText(foodData.getRestDateFood());
                                        restDateFoodLayout.setVisibility(View.VISIBLE);
                                    }
                                    if (foodData.getFirstMenu() != null) {
                                        tpFirstMenu.setText(foodData.getFirstMenu());
                                        firstMenuLayout.setVisibility(View.VISIBLE);
                                    }
                                    if (foodData.getTreatMenu() != null) {
                                        tpTreatMenu.setText(foodData.getTreatMenu());
                                        treatMenuLayout.setVisibility(View.VISIBLE);
                                    }
                                    if (foodData.getPacking() != null) {
                                        tpPacking.setText(foodData.getPacking());
                                        packingLayout.setVisibility(View.VISIBLE);
                                    }
                                    if (foodData.getParkingFood() != null) {
                                        tpParkingFood.setText(foodData.getParkingFood());
                                        parkingFoodLayout.setVisibility(View.VISIBLE);
                                    }

                                } else {
                                    Log.e(TAG, "음식 타입 불러오기 실패");
                                }
                            }

                            @Override
                            public void onFailure(Call<Food> call, Throwable t) {
                                Log.e("연결실패", t.getMessage());
                            }
                        });
                    }
                } else {
                    Log.e(TAG, "관광지 정보 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });


        //관광지 해시태그 리사이클러 뷰
        RecyclerView hashTagRecyclerview = findViewById(R.id.tpHashTag);
        LinearLayoutManager hashTagLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        hashTagRecyclerview.setLayoutManager(hashTagLayoutManager);
        hashTagResult = new ArrayList<>();

        //관광지 해시태그 불러오기
        Call<List<String>> call2 = RetrofitClient.getApiService().getTouristDataHashTag(contentId);
        call2.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    RecyclerDecoration hashtagDecoration = new RecyclerDecoration(16);
                    hashTagResult = response.body();
                    if (hashTagResult.isEmpty())
                        hashTagRecyclerview.setVisibility(View.GONE);
                    HashTagAdapter hashTagAdapter = new HashTagAdapter(hashTagResult);
                    hashTagRecyclerview.setAdapter(hashTagAdapter);
                    hashTagRecyclerview.addItemDecoration(hashtagDecoration);
                    balloonObject.setHashtags(hashTagResult);
                } else {
                    Log.e(TAG, "관광지 해시태그 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });


        //펼치기
        LinearLayout tpInfo2 = findViewById(R.id.tpInfo2);
        LinearLayout foodInfo2 = findViewById(R.id.foodInfo2);
        TextView moreInfo = findViewById(R.id.moreInfo);
        moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moreInfo.getText() == "더 보기") {
                    moreInfo.setText("축소하기");
                    if (isTp) {
                        tpInfo2.setVisibility(View.VISIBLE);
                    } else {
                        foodInfo2.setVisibility(View.VISIBLE);
                    }
                } else {
                    moreInfo.setText("더 보기");
                    if (isTp) {
                        tpInfo2.setVisibility(View.GONE);
                    } else {
                        foodInfo2.setVisibility(View.GONE);
                    }
                }
            }
        });

        //자세히 보기 팝업
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
//        LinearLayoutManager nearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager nearLayoutManager = new LinearLayoutManager(getApplicationContext()){
            @Override public boolean canScrollVertically() {
                return false;
            }
        };

        nearRecyclerview.setLayoutManager(nearLayoutManager);
        nearResult = new ArrayList<>();

        Call<List<Near>> call3 = RetrofitClient.getApiService().getNearTouristData(contentId);
        call3.enqueue(new Callback<List<Near>>() {
            @Override
            public void onResponse(Call<List<Near>> call, Response<List<Near>> response) {
                if (response.isSuccessful()) {
                    nearResult = response.body();
                    int len = nearResult.size();
                    if (len == 0) {
                        nearLayout.setVisibility(View.GONE);
                        nearLine.setVisibility(View.GONE);
                    }
                    else {
                        String[] nearImages = new String[len];
                        for (int i = 0; i < len; i++) {
                            nearImages[i] = nearResult.get(i).getFirstImage();
                        }

                        NearAdapter nearAdapter = new NearAdapter(nearResult, nearImages, TouristPointActivity.this);
                        nearRecyclerview.setAdapter(nearAdapter);
                        nearAdapter.setOnNearItemClickListener(new OnNearItemClickListener() {
                            @Override
                            public void onItemClick(NearAdapter.ViewHolder holder, View view, int position) {
                                Near item = nearAdapter.getItem(position);
                                Intent intent = new Intent(TouristPointActivity.this, TouristPointActivity.class);
                                intent.putExtra("contentId", item.getContentId());
                                startActivityForResult(intent, NEAR);
                            }
                        });
                    }
                } else {
                    Log.e(TAG, "주변 관광지 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<List<Near>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });


        //지도 버튼
        ImageButton tpGps = findViewById(R.id.tpGps);
        tpGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //스택 중간에 있던 액티비티들 삭제
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);   //액티비티가 스택 맨위에 있으면 재활용
                intent.putExtra("FromWhere", Activities.OBSERVATION);
                intent.putExtra("BalloonObject", balloonObject);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEAR) {
            //새로고침
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    //예측혼잡도구분코드를 얻기 위한 open api 호출
    private class CongestionThread extends Thread {
        private static final String TAG = "CongestionThread";

        public CongestionThread() {
        }

        public void run() {
            String key = "?ServiceKey=BdxNGWQJQFutFYE6DkjePTmerMbwG2fzioTf6sr69ecOAdLGMH4iiukF8Ex93YotSgkDOHe1VxKNOr8USSN6EQ%3D%3D"; //인증키
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
                Long count = (Long) body.get("totalCount");

                if (count == 0) {
                    Log.d(TAG, "혼잡도 데이터 없음");
                } else {
                    JSONObject items = (JSONObject) body.get("items");
                    JSONObject item = (JSONObject) items.get("item");
                    Long code = (Long) item.get("estiDecoDivCd");
                    bf.close();
                    Log.d(TAG, "혼잡도 : " + code);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            congestionLayout.setVisibility(View.VISIBLE);
                            if (code == 1) {
                                tpCongestion.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.tp__1));
                            } else if (code == 2) {
                                tpCongestion.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.tp__2));
                            } else if (code == 3) {
                                tpCongestion.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.tp__3));
                            } else if (code == 4) {
                                tpCongestion.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.tp__4));
                            } else if (code == 5) {
                                tpCongestion.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.tp__5));
                            }
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}