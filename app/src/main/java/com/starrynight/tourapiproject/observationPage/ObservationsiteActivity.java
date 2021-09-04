package com.starrynight.tourapiproject.observationPage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.observationPage.observationPageRetrofit.CourseTouristPoint;
import com.starrynight.tourapiproject.observationPage.observationPageRetrofit.Observation;
import com.starrynight.tourapiproject.observationPage.observationPageRetrofit.ObserveFee;
import com.starrynight.tourapiproject.observationPage.observationPageRetrofit.ObserveImage;
import com.starrynight.tourapiproject.observationPage.observationPageRetrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ObservationsiteActivity extends AppCompatActivity {

    private static final String TAG = "observation page";
    Observation observation;
    List<String> observeHashTags;
    private RecyclerHashTagAdapter recyclerHashTagAdapter;
    TextView outline;
    TextView link;

    private ViewPager2 obs_slider;
    private LinearLayout obs_indicator;
    private String[] obs_images;
    private List<ObserveImage> obs_images_list;

    private ViewPager2 course_slider;
    private LinearLayout course_circle_indicator;
    private LinearLayout course_txt_indicator;
    private List<CourseTouristPoint> touristPointList;

    private RecyclerFeeAdapter recyclerFeeAdapter;
    private List<ObserveFee> obs_fee_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observationsite);

        Intent intent = getIntent();
        long observationId = (Long) intent.getSerializableExtra("observationId"); //전 페이지에서 받아온 contentId
//        long observationId = 2;

        Call<Observation> call1 = RetrofitClient.getApiService().getObservation(observationId);
        call1.enqueue(new Callback<Observation>() {
            @Override
            public void onResponse(Call<Observation> call, Response<Observation> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "관측지 호출 성공");
                    observation = response.body();

                    obs_images_list = observation.getObserveImages();
                    obs_images_list.get(0).getImageSource();

                    obs_fee_list = observation.getObserveFees();

                    Call<List<String>> call3 = RetrofitClient.getApiService().getObserveImagePath(observationId);
                    call3.enqueue(new Callback<List<String>>() {
                        @Override
                        public void onResponse(Call<List<String>> call, Response<List<String>> response) {

                            if (response.isSuccessful()) {
                                if (response != null) {
                                    Log.d(TAG, "관측지 이미지 호출 성공");
                                    List<String> imageList = response.body();
                                    obs_images = imageList.toArray(new String[imageList.size()]);

                                    //관측지 이미지 슬라이더 설정
                                    obs_slider = findViewById(R.id.obs_Img_slider);
                                    obs_indicator = findViewById(R.id.obs_Img_indicator);
                                    obs_slider.setAdapter(new ObserveImageSliderAdapter(ObservationsiteActivity.this, obs_images));
                                    obs_slider.setOffscreenPageLimit(1);

                                    obs_slider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                        @Override
                                        public void onPageSelected(int position) {
                                            super.onPageSelected(position);
                                            setObserveCurrentIndicator(position);
                                        }
                                    });
                                    setupObserveIndicators(obs_images.length);
                                }

                            } else {
                                Log.e(TAG, "관측지 이미지 호출 실패");
                            }
                        }

                        @Override
                        public void onFailure(Call<List<String>> call, Throwable t) {

                        }
                    });


                    TextView name = findViewById(R.id.obs_name_txt);
                    name.setText(observation.getObservationName());
                    TextView obs_type = findViewById(R.id.obs_type_txt);
                    obs_type.setText(observation.getObserveType());
                    outline = findViewById(R.id.obs_outline_txt);
                    outline.setText(observation.getOutline());
                    setOutlineButton(); //개요 더보기 설정

                    if (observation.getNature()) {
                        //자연관측지일 경우 레이아웃 구성
                        RelativeLayout nature_layout = findViewById(R.id.obs_fornature_layout);
                        nature_layout.setVisibility(View.VISIBLE);

                        LinearLayout operating_layout = findViewById(R.id.obs_foroperating_layout);
                        operating_layout.setVisibility(View.GONE);

                        TextView nature_parking = findViewById(R.id.obs_nature_parking_txt);
                        nature_parking.setText(observation.getParking());
                        TextView nature_address = findViewById(R.id.obs_nature_address_txt);
                        nature_address.setText(observation.getAddress());
                        TextView nature_guide = findViewById(R.id.obs_nature_guide_txt);
                        nature_guide.setText(observation.getGuide());


                    } else {
                        //운영관측지일 경우 레이아웃 구성
                        TextView address = findViewById(R.id.obs_address_txt);
                        address.setText(observation.getAddress());
                        TextView phonenumber = findViewById(R.id.obs_phonenumber_txt);
                        phonenumber.setText(observation.getPhoneNumber());
                        TextView operatinghour = findViewById(R.id.obs_address_txt);
                        operatinghour.setText(observation.getOperatingHour());
                        TextView closedday = findViewById(R.id.obs_closedday_txt);
                        closedday.setText(observation.getClosedDay());

                        //이용요금 리사이클러 설정
                        initFeeRecycler();
                        Call<List<ObserveFee>> call4 = RetrofitClient.getApiService().getObserveFeeList(observationId);
                        call4.enqueue(new Callback<List<ObserveFee>>() {

                            @Override
                            public void onResponse(Call<List<ObserveFee>> call, Response<List<ObserveFee>> response) {
                                if (response.isSuccessful()) {
                                    Log.d(TAG, "관측지 이용요금 호출 성공");
                                    obs_fee_list = response.body();

                                    for (ObserveFee p : obs_fee_list) {
                                        RecyclerFeeItem item = new RecyclerFeeItem();
                                        item.setEntranceFee(p.getEntranceFee());
                                        item.setFeeName(p.getFeeName());

                                        recyclerFeeAdapter.addItem(item);
                                    }
                                    recyclerFeeAdapter.notifyDataSetChanged();

                                } else {
                                    Log.e(TAG, "관측지 이용요금 호출 실패");
                                }
                            }

                            @Override
                            public void onFailure(Call<List<ObserveFee>> call, Throwable t) {
                                Log.e(TAG, "연결실패" + t.getMessage());
                            }
                        });

                        TextView guide = findViewById(R.id.obs_guide_txt);
                        guide.setText(observation.getGuide());
                        TextView parking = findViewById(R.id.obs_parking_txt);
                        parking.setText(observation.getParking());
                        link = findViewById(R.id.obs_url_txt);
                        setLinkText();  //링크텍스트, 클릭이벤트 설정

                        ToggleButton moreinfo_btn = findViewById(R.id.obs_moreinfo_btn);
                        //더보기 버튼 구현
                        RelativeLayout moreinfo_layout = findViewById(R.id.obd_moreinfo_layout);
                        moreinfo_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    moreinfo_layout.setVisibility(View.VISIBLE);
                                } else {
                                    moreinfo_layout.setVisibility(View.GONE);
                                }
                            }
                        });
                    }


                    //해쉬태그 리사이클러 설정
                    initHashtagRecycler();

                    Call<List<String>> call2 = RetrofitClient.getApiService().getObserveHashTags(observationId);
                    call2.enqueue(new Callback<List<String>>() {

                        @Override
                        public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                            if (response.isSuccessful()) {
                                Log.d(TAG, "관측지 해쉬태그 호출 성공");
                                observeHashTags = response.body();

                                for (String p : observeHashTags) {
                                    RecyclerHashTagItem item = new RecyclerHashTagItem();
                                    item.setHashtagName(p);

                                    recyclerHashTagAdapter.addItem(item);
                                }
                                recyclerHashTagAdapter.notifyDataSetChanged();

                            } else {
                                Log.e(TAG, "관측지 해쉬태그 호출 실패");
                            }
                        }

                        @Override
                        public void onFailure(Call<List<String>> call, Throwable t) {
                            Log.e(TAG, "연결실패" + t.getMessage());

                        }
                    });

                    //코스설정
                    course_slider = findViewById(R.id.obs_course_slider);
                    course_circle_indicator = findViewById(R.id.obs_course_circle_indicator);
                    course_txt_indicator = findViewById(R.id.obs_course_name_indicator);
                    Call<List<String>> call6 = RetrofitClient.getApiService().getCourseNameList(observationId);
                    call6.enqueue(new Callback<List<String>>() {
                        @Override
                        public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                            if (response.isSuccessful()) {
                                //코스 인디케이터에 넣을 코스 이름 받아오기
                                List<String> course_name_list = response.body();

                                Call<List<CourseTouristPoint>> call5 = RetrofitClient.getApiService().getCourseTouristPointList(observationId);
                                call5.enqueue(new Callback<List<CourseTouristPoint>>(){
                                    @Override
                                    public void onResponse(Call<List<CourseTouristPoint>> call, Response<List<CourseTouristPoint>> response) {
                                        if (response.isSuccessful()) {
                                            //코스 viewpager에 적용할 관광지 정보 가져오기
                                            if (response != null) {
                                                Log.d(TAG, "관측지 코스 관광지 호출 성공");
                                                touristPointList=response.body();

                                                List<String> course_names = new ArrayList<>();
                                                for (CourseTouristPoint p : touristPointList) {
                                                    course_names.add(p.getTitle());
                                                }

                                                course_slider.setAdapter(new ObserveCourseViewAdapter(ObservationsiteActivity.this, touristPointList));
                                                course_slider.setOffscreenPageLimit(1);

                                                course_slider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                                    @Override
                                                    public void onPageSelected(int position) {
                                                        super.onPageSelected(position);
                                                        //관측지 빠고 나머지만 선택가능하게 설정
                                                        if (position < observation.getCourseOrder()) {
                                                            setCourseCurrentIndicator(position);
                                                        } else {
                                                            setCourseCurrentIndicator(position+1);
                                                        }
                                                    }
                                                });
                                                setupCourseIndicators(touristPointList.size(), course_name_list);
                                            }

                                        } else {
                                            Log.e(TAG, "관측지 코스 호출 실패");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<CourseTouristPoint>> call, Throwable t) {
                                        Log.e(TAG, "관측지 코스 연결실패");
                                    }
                                });
                            } else {
                                Log.e(TAG, "관측지 코스이름 호출 실패");
                            }
                        }

                        @Override
                        public void onFailure(Call<List<String>> call, Throwable t) {
                            Log.e(TAG, "관측지 코스 이름 연결 실패");
                        }
                    });


                } else {
                    Log.e(TAG, "관측지 호출 실패");
                }
            }
            @Override
            public void onFailure(Call<Observation> call, Throwable t) {
                Log.e(TAG, "연결실패" + t.getMessage());
            }

        });

//        RecyclerView recyclerView = findViewById(R.id.obv_recyclerview);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        Post_point_item_Adapter adapter = new Post_point_item_Adapter();
//        recyclerView.setAdapter(adapter);
//
//        adapter.addItem(new post_point_item("게시글1", "https://cdn.pixabay.com/photo/2018/08/11/20/37/cathedral-3599450_960_720.jpg"));
//        adapter.addItem(new post_point_item("게시글2", "https://cdn.pixabay.com/photo/2018/07/15/23/22/prague-3540883_960_720.jpg"));
//        adapter.addItem(new post_point_item("게시글3", "https://cdn.pixabay.com/photo/2019/12/13/07/35/city-4692432_960_720.jpg"));
//
//        adapter.setOnItemClicklistener(new OnPostPointItemClickListener() {
//            @Override
//            public void onItemClick(Post_point_item_Adapter.ViewHolder holder, View view, int position) {
//                Intent intent = new Intent(getApplicationContext(), PostActivity.class);
//                startActivity(intent);
//            }
//        });

        Button heart_btn = findViewById(R.id.obs_save_btn);
        heart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });
        Button back_btn = findViewById(R.id.obs_back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }



    private void initHashtagRecycler(){
        //해쉬태그 리사이클러 초기화
        RecyclerView hashTagsrecyclerView = findViewById(R.id.obs_hashtags_layout);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        hashTagsrecyclerView.setLayoutManager(linearLayoutManager);

        recyclerHashTagAdapter = new RecyclerHashTagAdapter();
        hashTagsrecyclerView.setAdapter(recyclerHashTagAdapter);
    }

    private void initFeeRecycler(){
        //이용요금 리사이클러 초기화
        RecyclerView feeRecyclerView = findViewById(R.id.obs_entrancefee_layout);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        feeRecyclerView.setLayoutManager(linearLayoutManager);

        recyclerFeeAdapter = new RecyclerFeeAdapter();
        feeRecyclerView.setAdapter(recyclerFeeAdapter);
    }

    private void setOutlineButton() {
        TextView outline_btn = findViewById(R.id.obs_outline_btn);
        Layout l= outline.getLayout();

        //개요가 4줄 이상일 경우만 자세히 보기 버튼 생성
        if (l != null) {
            int lines= l.getLineCount();
            if (lines > 0)
                if (l.getEllipsisCount(lines-1) > 0) {
                    outline_btn.setVisibility(View.VISIBLE);
                    Log.d(TAG, "텍스트 줄넘침");
                }
        }

        //개요 더보기 버튼 클릭시 팝업띄움
        outline_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ObservationsiteActivity.this, OutlinePopActivity.class);
                intent.putExtra("outline", observation.getOutline());
                startActivity(intent);
            }
        });
    }

    private void setLinkText() {
        //홈페이지 링크 설정

        //하이퍼 링크인줄 알도록 밑줄추가
        SpannableString content = new SpannableString(observation.getLink());
        content.setSpan(new UnderlineSpan(),0,content.length(),0);
        link.setText(content);

        link.setOnClickListener(new View.OnClickListener() {
            //홈페이지 바로가기 설정
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(observation.getLink()));
                startActivity(intent);
            }
        });
    }

    private void setupObserveIndicators(int count) {
        //이비지 슬라이더 인디케이터 걸정
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

//        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.post__indicator_inactive));
            indicators[i].setLayoutParams(params);
            obs_indicator.addView(indicators[i]);
        }
        setObserveCurrentIndicator(0);
    }

    private void setObserveCurrentIndicator(int position) {
        int childCount = obs_indicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) obs_indicator.getChildAt(i);
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

    private void setupCourseIndicators(int count, List<String> names) {
        //코스 인디케이터 걸정
        ImageView[] img_indicators = new ImageView[count+1];
        TextView[] txt_indicators = new TextView[count+1];
        LinearLayout.LayoutParams img_params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
        LinearLayout.LayoutParams txt_params = new LinearLayout.LayoutParams(
                30, ViewGroup.LayoutParams.WRAP_CONTENT,1);

        ViewGroup.LayoutParams test_params = new ViewGroup.LayoutParams(30, ViewGroup.LayoutParams.WRAP_CONTENT);



//        img_params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < img_indicators.length; i++) {
            img_indicators[i] = new ImageView(this);
            img_indicators[i].setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.post__indicator_inactive));
            img_indicators[i].setLayoutParams(img_params);
            course_circle_indicator.addView(img_indicators[i]);

            txt_indicators[i] = new TextView(this);
            txt_indicators[i].setText(names.get(i));
            txt_indicators[i].setLayoutParams(txt_params);
            txt_indicators[i].setEllipsize(TextUtils.TruncateAt.END);
            txt_indicators[i].setTextSize(10);
            txt_indicators[i].setMaxLines(2);
            txt_indicators[i].setGravity(Gravity.CENTER_HORIZONTAL);
            course_txt_indicator.addView(txt_indicators[i]);

        }
        setCourseCurrentIndicator(0);
    }

    private void setCourseCurrentIndicator(int position) {
        //코스 동그라미 인디케이터 설정
        int childCount = course_circle_indicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) course_circle_indicator.getChildAt(i);
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