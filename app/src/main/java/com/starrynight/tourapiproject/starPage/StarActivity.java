package com.starrynight.tourapiproject.starPage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

import static android.graphics.BitmapFactory.decodeFile;

public class StarActivity extends AppCompatActivity {
    Context context;

    TextView constName, constStory, constObInfo, constBestMonth, constPersonality;
    View constStoryTv, constObInfoTv, constBestMonthTv, constPersonalityTv;
    ImageView constImage;

    Long constId;
    Constellation constData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);

        Intent intent = getIntent();
        constId = (Long) intent.getSerializableExtra("constId");
        Log.d("cosntId받아오기", constId.toString());

        constName = findViewById(R.id.detail_const_name);
        constImage = findViewById(R.id.detail_const_image);

        constStoryTv = findViewById(R.id.const_story_textView);
        constStory = constStoryTv.findViewById(R.id.content_text);

        constObInfoTv = findViewById(R.id.const_ob_info_textView);
        constObInfo = constObInfoTv.findViewById(R.id.content_text);

        constBestMonthTv = findViewById(R.id.const_best_month_textView);
        constBestMonth = constBestMonthTv.findViewById(R.id.content_text);

        constPersonalityTv = findViewById(R.id.const_personality_textView);
        constPersonality = constPersonalityTv.findViewById(R.id.content_text);


        // 별자리 클릭 후 상세 정보 불러오는 api
        Call<Constellation> detailConstCall = RetrofitClient.getApiService().getDetailConst(constId);
        detailConstCall.enqueue(new Callback<Constellation>() {
            @Override
            public void onResponse(Call<Constellation> call, Response<Constellation> response) {
                if (response.isSuccessful()) {
                    constData = response.body();
                    Glide.with(StarActivity.this).load(constData.getConstImage()).into(constImage);
                    constName.setText(constData.getConstName());
                    constStory.setText(constData.getConstStory());
                    constObInfo.setText(constData.getSpringConstMtd());
                    constBestMonth.setText(constData.getConstBestMonth());
                    constPersonality.setText(constData.getConstPersonality());
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
        ImageButton backBtn = findViewById(R.id.all_star_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}