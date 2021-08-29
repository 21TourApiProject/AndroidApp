package com.starrynight.tourapiproject.starPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.starPage.starPageRetrofit.Constellation;
import com.starrynight.tourapiproject.starPage.starPageRetrofit.RetrofitClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StarActivity extends AppCompatActivity {
    Context context;

    // 별자리 상세정보 제목
    TextView constStoryTl, constObInfoTl, constBestMonthTl, constPersonalityName, constPersonalityPeriod;

    // 별자리 상세정보 내용
    TextView constName, constStory, constObInfo, constBestMonth, constPersonality;

    // 별자리 상세정보 뷰
    View constStoryTv, constObInfoTv, constBestMonthTv, constPersonalityTv;
    ImageView constImage, constFeature;

    Long constId;
    Constellation constData;

    String spring = "0321";
    String summer = "0622";
    String fall = "0923";
    String winter = "1221";
    String yearEnd = "1231";
    String yearStart = "0101";

    Integer compareDataSpring, compareDataSummer, compareDataFall, compareDataWinter, compareDataYearEnd, compareDataYearStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);

        // 넘겨준 constId 받기
        Intent intent = getIntent();
        constId = (Long) intent.getSerializableExtra("constId");
        //Log.d("constId받아오기", constId.toString());

        constName = findViewById(R.id.detail_const_name);
        constImage = findViewById(R.id.detail_const_image);
        constFeature = findViewById(R.id.const_feature);

        constStoryTv = findViewById(R.id.const_story_textView);
        constStory = constStoryTv.findViewById(R.id.content_text);

        constObInfoTv = findViewById(R.id.const_ob_info_textView);
        constObInfo = constObInfoTv.findViewById(R.id.content_text);

        constBestMonthTv = findViewById(R.id.const_best_month_textView);
        constBestMonth = constBestMonthTv.findViewById(R.id.content_text);

        constPersonalityTv = findViewById(R.id.const_personality_textView);
        constPersonality = constPersonalityTv.findViewById(R.id.content_per_text);
        constPersonalityName = constPersonalityTv.findViewById(R.id.const_per_text);
        constPersonalityPeriod = constPersonalityTv.findViewById(R.id.period_per_text);

        constStoryTl = constStoryTv.findViewById(R.id.title_text);
        constStoryTl.setText("별자리 이야기");

        constObInfoTl = constObInfoTv.findViewById(R.id.title_text);
        constObInfoTl.setText("별자리 관측 정보");

        constBestMonthTl = constBestMonthTv.findViewById(R.id.title_text);
        constBestMonthTl.setText("가장 보기 좋은 달");

        // 별자리 클릭 후 상세 정보 불러오는 api
        Call<Constellation> detailConstCall = RetrofitClient.getApiService().getDetailConst(constId);
        detailConstCall.enqueue(new Callback<Constellation>() {
            @Override
            public void onResponse(Call<Constellation> call, Response<Constellation> response) {
                if (response.isSuccessful()) {
                    //계절별 별자리 관측법 불러오기

                    Date springDate = null;
                    Date summerDate = null;
                    Date fallDate = null;
                    Date winterDate = null;
                    Date todayDate = null;
                    Date yearEndDate = null;
                    Date yearStartDate = null;

                    SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
                    try {
                        Date today = new Date();
                        springDate = sdf.parse(spring);
                        summerDate = sdf.parse(summer);
                        fallDate = sdf.parse(fall);
                        winterDate = sdf.parse(winter);
                        yearEndDate = sdf.parse(yearEnd);
                        yearStartDate = sdf.parse(yearStart);
                        todayDate = sdf.parse(sdf.format(today));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    constData = response.body();
                    Glide.with(StarActivity.this).load(constData.getConstImage()).fitCenter().into(constImage);
                    constName.setText(constData.getConstName());
                    constStory.setText(constData.getConstStory());

                    compareDataSpring = todayDate.compareTo(springDate);            //3월 21일
                    compareDataSummer = todayDate.compareTo(summerDate);            //6월 22일
                    compareDataFall = todayDate.compareTo(fallDate);                //9월 23일
                    compareDataWinter = todayDate.compareTo(winterDate);            //12월 21일
                    compareDataYearStart = todayDate.compareTo(yearStartDate);      //1월 1일
                    compareDataYearEnd = todayDate.compareTo(yearEndDate);          //12월 31일

                    // 봄(3/21 ~ 6/21)
                    if ((compareDataSpring == 1 || compareDataSpring == 0) && compareDataSummer == -1) {
                        constObInfo.setText(constData.getSpringConstMtd());
                    }
                    // 여름(6/22 ~ 9/22)
                    else if ((compareDataSummer == 1 || compareDataSummer == 0) && compareDataFall == -1) {
                        constObInfo.setText(constData.getSummerConstMtd());
                    }
                    // 가을(9/23 ~ 12/20)
                    else if ((compareDataFall == 1 || compareDataFall == 0) && compareDataWinter == -1) {
                        constObInfo.setText(constData.getFallConstMtd());
                    }
                    // 겨울(12/21 ~ 12/31)
                    else if ((compareDataWinter == 1 || compareDataWinter == 0) && compareDataYearEnd == -1) {
                        constObInfo.setText(constData.getWinterConstMtd());
                    }
                    // 겨울(01/01 ~ 03/20)
                    else if ((compareDataYearStart == 1 || compareDataYearStart == 0) && compareDataSpring == -1) {
                        constObInfo.setText(constData.getWinterConstMtd());
                    }

                    constBestMonth.setText(constData.getConstBestMonth());

                    if (constData.getConstPersonality() == null) {
                        constPersonalityTv.setVisibility(View.GONE);
                    }
                    constPersonality.setText(constData.getConstPersonality());
                    constPersonalityName.setText(constData.getConstName());
                    constPersonalityPeriod.setText(constData.getConstPeriod());

                    if (constData.getConstFeature() == null) {
                        constFeature.setVisibility(View.GONE);
                    }
                    Glide.with(StarActivity.this).load(constData.getConstFeature()).fitCenter().into(constFeature);

                } else {
                    System.out.println("별자리 정보 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<Constellation> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });

        // 뒤로 가기 버튼 이벤트
        ImageButton backBtn = findViewById(R.id.detail_star_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}