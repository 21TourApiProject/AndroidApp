package com.starrynight.tourapiproject.starPage;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.kakao.sdk.newtoneapi.TextToSpeechClient;
import com.kakao.sdk.newtoneapi.TextToSpeechListener;
import com.kakao.sdk.newtoneapi.TextToSpeechManager;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.starPage.starPageRetrofit.Constellation;
import com.starrynight.tourapiproject.starPage.starPageRetrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @className : StarActivity
 * @description : 별자리 상세페이지입니다.
 * @modification : 2022-09-15 (hyeonz) 주석추가
 * @author : hyeonz
 * @date : 2022-09-15
 * @version : 1.0
====개정이력(Modification Information)====
수정일        수정자        수정내용
-----------------------------------------
2022-09-15   hyeonz      주석추가
 */
public class StarActivity extends AppCompatActivity {
    private static final String TAG = "Star page";
    TextView constName;

    // 별자리 상세정보 뷰
    TextView constMtdTv, constBestMonthTv, constStoryTv;
    ImageView constImage, constFeature1, constFeature2, constFeature3;
    ImageView story_play_btn;

    String intentConstName;
    Constellation constData;

    private TextToSpeechClient ttsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);

        TextToSpeechManager.getInstance().initializeLibrary(getApplicationContext());   //카카오 음성

        // 넘겨준 constId 받기
        Intent intent = getIntent();
        intentConstName = (String) intent.getSerializableExtra("constName");
        Log.d("constName 받아오기", intentConstName);

        constName = findViewById(R.id.detail_const_name);
        constImage = findViewById(R.id.detail_const_image);

        constFeature1 = findViewById(R.id.const_feature1);
        constFeature2 = findViewById(R.id.const_feature2);
        constFeature3 = findViewById(R.id.const_feature3);

        constStoryTv = findViewById(R.id.const_story);
        constMtdTv = findViewById(R.id.const_mtd_tv);
        constBestMonthTv = findViewById(R.id.const_best_month_tv);

        story_play_btn = findViewById(R.id.story_play_btn);

        ttsClient = new TextToSpeechClient.Builder()
                .setSpeechMode(TextToSpeechClient.NEWTONE_TALK_1)     // 음성합성방식
                .setSpeechSpeed(1.0)            // 발음 속도(0.5~4.0)
                .setSpeechVoice(TextToSpeechClient.VOICE_WOMAN_READ_CALM)  //TTS 음색 모드 설정(여성 차분한 낭독체)
                .setListener(ttsListener)
                .build();

        // 별자리 클릭 후 상세 정보 불러오는 api
        Call<Constellation> detailConstCall = RetrofitClient.getApiService().getDetailConst(intentConstName);
        detailConstCall.enqueue(new Callback<Constellation>() {
            @Override
            public void onResponse(Call<Constellation> call, Response<Constellation> response) {
                if (response.isSuccessful()) {

                    constData = response.body();
                    Glide.with(StarActivity.this).load("https://starry-night.s3.ap-northeast-2.amazonaws.com/constDetailImage/b_" + constData.getConstEng() + ".png").fitCenter().into(constImage);
                    constName.setText(constData.getConstName());
                    constStoryTv.setText(constData.getConstStory());
                    constMtdTv.setText(constData.getConstMtd());
                    constBestMonthTv.setText(constData.getConstBestMonth());

                    ttsClient.setSpeechText(constData.getConstStory());   //뉴톤톡 하고자 하는 문자열을 미리 세팅.

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

        story_play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ttsClient.isPlaying()) {
                    ttsClient.stop();
                } else {
                    ttsClient.play();
                }
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

    private TextToSpeechListener ttsListener = new TextToSpeechListener() {
        @Override
        public void onFinished() {
            int intSentSize = ttsClient.getSentDataSize();      //세션 중에 전송한 데이터 사이즈
            int intRecvSize = ttsClient.getReceivedDataSize();  //세션 중에 전송받은 데이터 사이즈

            final String strInacctiveText = "handleFinished() SentSize : " + intSentSize + "  RecvSize : " + intRecvSize;

            Log.i(TAG, strInacctiveText);
        }

        @Override
        public void onError(int code, String message) {
            Log.e(TAG, "카카오음성오류: " + message);
        }
    };

    public void onDestroy() {
        super.onDestroy();
        TextToSpeechManager.getInstance().finalizeLibrary();
    }

    public void onPause() {
        super.onPause();
        if (ttsClient.isPlaying())
            ttsClient.stop();
    }
}