package com.starrynight.tourapiproject.starPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.starPage.starPageRetrofit.Constellation;
import com.starrynight.tourapiproject.starPage.starPageRetrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StarActivity extends AppCompatActivity {
    Context context;

    // 별자리 상세정보 제목
    TextView constMtdTl, constBestMonthTl;

    // 별자리 상세정보 내용
    TextView constName, constStory, constMtd, constBestMonth;

    // 별자리 상세정보 뷰
    View constMtdTv, constBestMonthTv;
    ImageView constImage, constFeature1, constFeature2, constFeature3;

    String intentConstName;
    Constellation constData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);

        // 넘겨준 constId 받기
        Intent intent = getIntent();
        intentConstName = (String) intent.getSerializableExtra("constName");
        Log.d("constName 받아오기", intentConstName);

        constName = findViewById(R.id.detail_const_name);
        constImage = findViewById(R.id.detail_const_image);

        constFeature1 = findViewById(R.id.const_feature1);
        constFeature2 = findViewById(R.id.const_feature2);
        constFeature3 = findViewById(R.id.const_feature3);

        constStory = findViewById(R.id.const_story);

        constMtdTv = findViewById(R.id.const_mtd_tv);
        constMtdTl = constMtdTv.findViewById(R.id.star_title_text);
        constMtd = constMtdTv.findViewById(R.id.star_content_text);

        constBestMonthTv = findViewById(R.id.const_best_month_tv);
        constBestMonth = constBestMonthTv.findViewById(R.id.star_content_text);
        constBestMonthTl = constBestMonthTv.findViewById(R.id.star_title_text);

        constMtdTl.setText("별자리 관측 정보");
        constBestMonthTl.setText("가장 보기 좋은 달");

        // 별자리 클릭 후 상세 정보 불러오는 api
        Call<Constellation> detailConstCall = RetrofitClient.getApiService().getDetailConst(intentConstName);
        detailConstCall.enqueue(new Callback<Constellation>() {
            @Override
            public void onResponse(Call<Constellation> call, Response<Constellation> response) {
                if (response.isSuccessful()) {

                    constData = response.body();
                    Glide.with(StarActivity.this).load("https://starry-night.s3.ap-northeast-2.amazonaws.com/constDetailImage/b_" + constData.getConstEng() + ".png").fitCenter().into(constImage);
                    constName.setText(constData.getConstName());
                    constStory.setText(constData.getConstStory());
                    constMtd.setText(constData.getConstMtd());
                    constBestMonth.setText(constData.getConstBestMonth());

                    if (constData.getConstFeature1() == null) {
                        constFeature1.setVisibility(View.GONE);
                    } else {
                        Glide.with(StarActivity.this).load(constData.getConstFeature1()).fitCenter().into(constFeature1);
                    }

                    if (constData.getConstFeature2() == null) {
                        constFeature2.setVisibility(View.GONE);
                    } else {
                        Glide.with(StarActivity.this).load(constData.getConstFeature2()).fitCenter().into(constFeature2);
                    }

                    if (constData.getConstFeature3() == null) {
                        constFeature3.setVisibility(View.GONE);
                    } else {
                        Glide.with(StarActivity.this).load(constData.getConstFeature3()).fitCenter().into(constFeature3);
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<Constellation> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });

        // 뒤로 가기 버튼 이벤트
        ImageView backBtn = findViewById(R.id.detail_star_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}