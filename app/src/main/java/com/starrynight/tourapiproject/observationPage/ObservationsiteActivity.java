package com.starrynight.tourapiproject.observationPage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.mapPage.Activities;
import com.starrynight.tourapiproject.mapPage.BalloonObject;
import com.starrynight.tourapiproject.observationPage.observationPageRetrofit.CourseTouristPoint;
import com.starrynight.tourapiproject.observationPage.observationPageRetrofit.Observation;
import com.starrynight.tourapiproject.observationPage.observationPageRetrofit.ObserveFee;
import com.starrynight.tourapiproject.observationPage.observationPageRetrofit.ObserveImageInfo;
import com.starrynight.tourapiproject.observationPage.observationPageRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.postPage.PostActivity;
import com.starrynight.tourapiproject.postPage.postRetrofit.PostImage;
import com.starrynight.tourapiproject.postWritePage.PostWriteActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
* @className : ObservationsiteActivity.java
* @description : 관측지페이지
* @modification : gyul chyoung (2022-08-30) 주석추가
* @author : 2022-08-30
* @date : gyul chyoung
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   gyul chyoung       2022-08-30       주석추가
 */

public class ObservationsiteActivity extends AppCompatActivity {
    private Long userId;
    private Long postId;
    private Boolean isWish;

    private static final String TAG = "observation page";
    private Observation observation;
    private List<String> observeHashTags;
    private RecyclerHashTagAdapter recyclerHashTagAdapter;
    TextView outline;
    TextView link;
    ImageView relateImage1;
    ImageView relateImage2;
    ImageView relateImage3;

    private ViewPager2 obs_slider;
    private LinearLayout obs_indicator;
    private TextView imageSource_txt;
    private String[] obs_images;
    private List<String> imageSources;

    private ViewPager2 course_slider;
    private LinearLayout course_circle_indicator;
    private LinearLayout course_txt_indicator;
    private List<CourseTouristPoint> touristPointList;
    private FrameLayout relateImageFrame;

    private RecyclerFeeAdapter recyclerFeeAdapter;
    private List<ObserveFee> obs_fee_list;
    private String[] relatefilename = new String[5];


    private BalloonObject balloonObject = new BalloonObject();   //mapfragment bundle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observationsite);
        relateImage1 = findViewById(R.id.relateImage);
        relateImage2 = findViewById(R.id.relateImage2);
        relateImage3 = findViewById(R.id.relateImage3);
        relateImageFrame = findViewById(R.id.relateImageFrame);

        Intent intent = getIntent();

        Long observationId = (Long) intent.getSerializableExtra("observationId"); //전 페이지에서 받아온 contentId

        postId = (Long) intent.getSerializableExtra("postId");


        Call<Observation> call1 = RetrofitClient.getApiService().getObservation(observationId);
        call1.enqueue(new Callback<Observation>() {
            @Override
            public void onResponse(Call<Observation> call, Response<Observation> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "관측지 호출 성공");
                    observation = response.body();

                    Call<List<ObserveImageInfo>> call3 = RetrofitClient.getApiService().getObserveImageInfo(observationId);
                    call3.enqueue(new Callback<List<ObserveImageInfo>>() {
                        @Override
                        public void onResponse(Call<List<ObserveImageInfo>> call, Response<List<ObserveImageInfo>> response) {
                            if (response.isSuccessful()) {
                                if (!response.body().isEmpty()) {
                                    Log.d(TAG, "관측지 이미지 호출 성공");
                                    List<ObserveImageInfo> observeImageInfos = response.body();
                                    List<String> imageList = new ArrayList<>();
                                    imageSources = new ArrayList<>();

                                    for (ObserveImageInfo info : observeImageInfos) {
                                        imageList.add(info.getImage());
                                        imageSources.add(info.getImageSource());
                                    }
                                    obs_images = imageList.toArray(new String[imageList.size()]);
                                    balloonObject.setImage(imageList.get(0));   //map위한 bundle

                                    obs_slider = findViewById(R.id.obs_Img_slider);
                                    obs_indicator = findViewById(R.id.obs_Img_indicator);
                                    obs_slider.setAdapter(new ObserveImageSliderAdapter(ObservationsiteActivity.this, obs_images));
                                    obs_slider.setOffscreenPageLimit(10);

                                    obs_slider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                        @Override
                                        public void onPageSelected(int position) {
                                            super.onPageSelected(position);
                                            setObserveCurrentIndicator(position);
                                        }
                                    });
                                    imageSource_txt = findViewById(R.id.obs_image_source);
                                    setupObserveIndicators(obs_images.length);
                                }
                            } else {
                                Log.e(TAG, "관측지 이미지 호출 실패");
                            }

                        }

                        @Override
                        public void onFailure(Call<List<ObserveImageInfo>> call, Throwable t) {
                            Log.e(TAG, "관측지 이미지 연결결 실패");
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

                        double lighttxt = observation.getLight();
                        TextView nature_light = findViewById(R.id.obs_nature_light_txt);
                        nature_light.setText(Double.toString(observation.getLight()));
                        ImageView nature_light_icon = findViewById(R.id.obs_nature_light_icon);
                        if (lighttxt <= 1)
                            nature_light_icon.setImageResource(R.drawable.observation__light_great);
                        else if (lighttxt > 1 && lighttxt <= 15)
                            nature_light_icon.setImageResource(R.drawable.observation__light_good);
                        else if (lighttxt > 15 && lighttxt <= 45)
                            nature_light_icon.setImageResource(R.drawable.observation__light_common);
                        else if (lighttxt > 45 && lighttxt <= 80)
                            nature_light_icon.setImageResource(R.drawable.observation__light_bad);
                        else if (lighttxt > 80)
                            nature_light_icon.setImageResource(R.drawable.observation__light_worst);
                        TextView nature_parking = findViewById(R.id.obs_nature_parking_txt);
                        nature_parking.setText(observation.getParking());
                        TextView nature_address = findViewById(R.id.obs_nature_address_txt);
                        nature_address.setText(observation.getAddress());
                        TextView nature_guide = findViewById(R.id.obs_nature_guide_txt);
                        nature_guide.setText(Html.fromHtml(observation.getGuide(), HtmlCompat.FROM_HTML_MODE_LEGACY));

                    } else {
                        //운영관측지일 경우 레이아웃 구성
                        double lighttxt = observation.getLight();
                        TextView light = findViewById(R.id.obs_light_txt);
                        light.setText(Double.toString(observation.getLight()));
                        ImageView light_icon = findViewById(R.id.obs_light_icon);
                        if (lighttxt <= 1)
                            light_icon.setImageResource(R.drawable.observation__light_great);
                        else if (lighttxt > 1 && lighttxt <= 15)
                            light_icon.setImageResource(R.drawable.observation__light_good);
                        else if (lighttxt > 15 && lighttxt <= 45)
                            light_icon.setImageResource(R.drawable.observation__light_common);
                        else if (lighttxt > 45 && lighttxt <= 80)
                            light_icon.setImageResource(R.drawable.observation__light_bad);
                        else if (lighttxt > 80)
                            light_icon.setImageResource(R.drawable.observation__light_worst);

                        TextView address = findViewById(R.id.obs_address_txt);
                        address.setText(observation.getAddress());
                        TextView phonenumber = findViewById(R.id.obs_phonenumber_txt);
                        phonenumber.setText(observation.getPhoneNumber());
                        TextView operatinghour = findViewById(R.id.obs_operatinghour_txt);
                        operatinghour.setText(Html.fromHtml(observation.getOperatingHour(), HtmlCompat.FROM_HTML_MODE_LEGACY));
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
                        guide.setText(Html.fromHtml(observation.getGuide(), HtmlCompat.FROM_HTML_MODE_LEGACY));
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
                                balloonObject.setHashtags(observeHashTags); //지도에 넣을 bundle
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

                    //지도버튼 설정
                    ImageButton map_btn = findViewById(R.id.obs_location_btn);
                    //Long id, int tag, double longitude, double latitude, String name, String address, String point_type, String intro
                    //BallonObject에 내용넣음

                    balloonObject.setId(observationId);
                    balloonObject.setTag(1);    //1관측지 2관광지
                    balloonObject.setLongitude(observation.getLongitude());
                    balloonObject.setLatitude(observation.getLatitude());
                    balloonObject.setName(observation.getObservationName());

                    //주소를 두단어까지 줄임
                    String address = observation.getAddress();
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

                    balloonObject.setPoint_type(observation.getObserveType());
                    balloonObject.setIntro(observation.getIntro());
                    //사진이랑 해쉬태그는 따로 불러와서 그거 각자 call에서 추가해야함 (아래두줄참고)
//                    balloonObject.setHashtags(observeHashTags);   //259에 구현현
//                    balloonObjec.setImage(이미지); //이미지지한장 129에 구현
                    map_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ObservationsiteActivity.this, MainActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //스택 중간에 있던 액티비티들 삭제
//                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);   //액티비티가 스택 맨위에 있으면 재활용
                            intent.putExtra("FromWhere", Activities.OBSERVATION);
                            intent.putExtra("BalloonObject", balloonObject);
                            intent.putExtra("isWished", isWish);
                            startActivity(intent);

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
                                call5.enqueue(new Callback<List<CourseTouristPoint>>() {
                                    @Override
                                    public void onResponse(Call<List<CourseTouristPoint>> call, Response<List<CourseTouristPoint>> response) {
                                        if (response.isSuccessful()) {
                                            //코스 viewpager에 적용할 관광지 정보 가져오기
                                            if (response != null) {
                                                Log.d(TAG, "관측지 코스 관광지 호출 성공");
                                                touristPointList = response.body();

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
                                                            setCourseCurrentIndicator(position + 1);
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

        //찜버튼 설정
        Button save_btn = findViewById(R.id.obs_save_btn);

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
        Call<Boolean> call0 = com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient.getApiService().isThereMyWish(userId, observationId, 0);
        call0.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    if (response.body()) {
                        isWish = true;
                        save_btn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    } else {
                        isWish = false;
                    }
                } else {
                    Log.d("isWish", "내 찜 조회하기 실패");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });

        // 짐버튼 클릭 설정정
       save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isWish) { //찜 안한 상태일때
                    Call<Void> call = com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient.getApiService().createMyWish(userId, observationId, 0);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                //버튼 디자인 바뀌게 구현하기
                                isWish = true;
                                save_btn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                                Toast.makeText(getApplicationContext(), "나의 여행버킷리스트에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d("isWish", "관광지 찜 실패");
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("연결실패", t.getMessage());
                        }
                    });
                } else {
                    Call<Void> call = com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient.getApiService().deleteMyWish(userId, observationId, 0);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                isWish = false;
                                save_btn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                                Toast.makeText(getApplicationContext(), "나의 여행버킷리스트에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d("isWishDelete", "관광지 찜 삭제 실패");
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
        ImageButton back_btn = findViewById(R.id.obs_back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView postwrite_btn = findViewById(R.id.writePost_btn);
        postwrite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), PostWriteActivity.class);
                startActivity(intent1);
            }
        });

        //게시물 이미지 가져오기
        Call<List<PostImage>> call = com.starrynight.tourapiproject.postPage.postRetrofit.RetrofitClient.getApiService().getRelatePostImageList(observationId);
        call.enqueue(new Callback<List<PostImage>>() {
            @Override
            public void onResponse(Call<List<PostImage>> call, Response<List<PostImage>> response) {
                if (response.isSuccessful()) {
                    Log.d("relatePostImage", "관련 게시물 이미지 업로드");
                    List<PostImage> relateImageList = response.body();
                    for (int i = 0; i < relateImageList.size(); i++) {
                        relatefilename[i] = relateImageList.get(i).getImageName();
                        if (relateImageList.size() > 4) {
                            break;
                        }
                    }
                    if (relatefilename[0] != null) {
                        Glide.with(getApplicationContext())
                                .load("https://starry-night.s3.ap-northeast-2.amazonaws.com/postImage/" + relatefilename[0])
                                .into(relateImage1);
                    }
                    if (relatefilename[1] != null) {
                        relateImage2.setVisibility(View.VISIBLE);
                        Glide.with(getApplicationContext())
                                .load("https://starry-night.s3.ap-northeast-2.amazonaws.com/postImage/" + relatefilename[1])
                                .into(relateImage2);
                    }
                    if (relatefilename[2] != null) {
                        relateImageFrame.setVisibility(View.VISIBLE);
                        Glide.with(getApplicationContext())
                                .load("https://starry-night.s3.ap-northeast-2.amazonaws.com/postImage/" + relatefilename[2])
                                .into(relateImage3);
                    }
                    relateImage1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!relateImageList.isEmpty()) {
                                Intent intent01 = new Intent(getApplicationContext(), PostActivity.class);
                                intent01.putExtra("postId", relateImageList.get(0).getPostId());
                                startActivity(intent01);
                            }
                        }
                    });
                    relateImage2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent2 = new Intent(getApplicationContext(), PostActivity.class);
                            intent2.putExtra("postId", relateImageList.get(1).getPostId());
                            startActivity(intent2);
                        }
                    });
                    relateImage3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent2 = new Intent(getApplicationContext(), MoreObservationActivity.class);
                            intent2.putExtra("observationId", observationId);
                            startActivity(intent2);
                        }
                    });
                } else {
                    Log.d("relatePostImage", "관련 게시물 이미지 업로드 실패");
                }
            }

            @Override
            public void onFailure(Call<List<PostImage>> call, Throwable t) {
                Log.d("relatePostImage", "관련 게시물 이미지 업로드 인터넷 오류");
            }
        });

    }


    private void initHashtagRecycler() {
        //해쉬태그 리사이클러 초기화
        RecyclerDecoration hashtagDecoration = new RecyclerDecoration(16);
        RecyclerView hashTagsrecyclerView = findViewById(R.id.obs_hashtags_layout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        hashTagsrecyclerView.setLayoutManager(linearLayoutManager);
        hashTagsrecyclerView.addItemDecoration(hashtagDecoration);
        recyclerHashTagAdapter = new RecyclerHashTagAdapter();
        hashTagsrecyclerView.setAdapter(recyclerHashTagAdapter);
    }

    private void initFeeRecycler() {
        //이용요금 리사이클러 초기화
        RecyclerView feeRecyclerView = findViewById(R.id.obs_entrancefee_layout);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        feeRecyclerView.setLayoutManager(linearLayoutManager);

        recyclerFeeAdapter = new RecyclerFeeAdapter();
        feeRecyclerView.setAdapter(recyclerFeeAdapter);
    }

    private void setOutlineButton() {
        TextView outline_btn = findViewById(R.id.obs_outline_btn);
        Layout l = outline.getLayout();

        //개요가 4줄 이상일 경우만 자세히 보기 버튼 생성
        if (l != null) {
            int lines = l.getLineCount();
            if (lines > 0)
                if (l.getEllipsisCount(lines - 1) > 0) {
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
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
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
        //이비지 슬라이더 인디케이터 설정
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(15, 0, 15, 0);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.observe__indicator_inactive));
            indicators[i].setLayoutParams(params);
            obs_indicator.addView(indicators[i]);
        }
        setObserveCurrentIndicator(0);
    }

    private void setObserveCurrentIndicator(int position) {
        imageSource_txt.setText(imageSources.get(position));
        int childCount = obs_indicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) obs_indicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.observe__indicator_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.observe__indicator_inactive
                ));
            }
        }
    }

    private void setupCourseIndicators(int count, List<String> names) {
        //코스 인디케이터 설설정
        ImageView[] img_indicators = new ImageView[count + 1];
        TextView[] txt_indicators = new TextView[count + 1];
        LinearLayout.LayoutParams img_params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        LinearLayout.LayoutParams txt_params = new LinearLayout.LayoutParams(
                30, ViewGroup.LayoutParams.WRAP_CONTENT, 1);

        ViewGroup.LayoutParams test_params = new ViewGroup.LayoutParams(30, ViewGroup.LayoutParams.WRAP_CONTENT);


//        img_params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < img_indicators.length; i++) {
            img_indicators[i] = new ImageView(this);
            img_indicators[i].setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.observation__course_inactive));
            img_indicators[i].setLayoutParams(img_params);
            course_circle_indicator.addView(img_indicators[i]);

            txt_indicators[i] = new TextView(this);
            txt_indicators[i].setText(names.get(i));
            txt_indicators[i].setLayoutParams(txt_params);
            txt_indicators[i].setEllipsize(TextUtils.TruncateAt.END);
            txt_indicators[i].setTextSize(9);
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
                        R.drawable.observation__course_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.observation__course_inactive
                ));
            }
        }
    }

}