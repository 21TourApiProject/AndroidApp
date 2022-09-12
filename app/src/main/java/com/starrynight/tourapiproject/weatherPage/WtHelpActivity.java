package com.starrynight.tourapiproject.weatherPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.starrynight.tourapiproject.R;

/**
 * @className : WtHelpActivity
 * @description : 날씨 도움말 페이지입니다.
 * @modification : 2022-09-03 (hyeonz) 주석추가
 * @author : hyeonz
 * @date : 2022-09-03
 * @version : 1.0
====개정이력(Modification Information)====
수정일        수정자        수정내용
-----------------------------------------
hyeonz       2022-09-03   주석추가
 */
public class WtHelpActivity extends AppCompatActivity {

    // 아이템 열기 터치 영역
    ConstraintLayout lightPolOpen;
    ConstraintLayout moonPhaseOpen;
    ConstraintLayout cloudOpen;
    ConstraintLayout windOpen;
    ConstraintLayout tempOpen;
    ConstraintLayout humidityOpen;
    ConstraintLayout precipitationOpen;
    ConstraintLayout fineDustOpen;


    // 아이템 내용
    TextView lightPolContent;
    TextView moonPhaseContent;
    TextView cloudContent;
    TextView windContent;
    TextView tempContent;
    TextView humidityContent;
    TextView precipitationContent;
    TextView fineDustContent;

    // 아이템 화살표
    ImageView lightPolArrow;
    ImageView moonPhaseArrow;
    ImageView cloudArrow;
    ImageView windArrow;
    ImageView tempArrow;
    ImageView humidityArrow;
    ImageView precipitationArrow;
    ImageView fineDustArrow;

    ImageView wtHelpBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wt_help);

        lightPolOpen = findViewById(R.id.help_light_pol_open);
        moonPhaseOpen = findViewById(R.id.help_moon_phase_open);
        cloudOpen = findViewById(R.id.help_cloud_open);
        windOpen = findViewById(R.id.help_wind_open);
        tempOpen = findViewById(R.id.help_temp_open);
        humidityOpen = findViewById(R.id.help_humidity_open);
        precipitationOpen = findViewById(R.id.help_precipitation_open);
        fineDustOpen = findViewById(R.id.help_fine_dust_open);

        lightPolContent = findViewById(R.id.help_light_pol_content);
        moonPhaseContent = findViewById(R.id.help_moon_phase_content);
        cloudContent = findViewById(R.id.help_cloud_content);
        windContent = findViewById(R.id.help_wind_content);
        tempContent = findViewById(R.id.help_temp_content);
        humidityContent = findViewById(R.id.help_humidity_content);
        precipitationContent = findViewById(R.id.help_precipitation_content);
        fineDustContent = findViewById(R.id.help_fine_dust_content);

        lightPolArrow = findViewById(R.id.help_light_pol_arrow);
        moonPhaseArrow = findViewById(R.id.help_moon_phase_arrow);
        cloudArrow = findViewById(R.id.help_cloud_arrow);
        windArrow = findViewById(R.id.help_wind_arrow);
        tempArrow = findViewById(R.id.help_temp_arrow);
        humidityArrow = findViewById(R.id.help_humidity_arrow);
        precipitationArrow = findViewById(R.id.help_precipitation_arrow);
        fineDustArrow = findViewById(R.id.help_fine_dust_arrow);

        wtHelpBackBtn = findViewById(R.id.wt_help_back_btn);

        onClickWtBackBtn();
        setItemClickEvent();

    }
    public void onClickWtBackBtn() {
        wtHelpBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setItemClickEvent() {
        lightPolOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 닫혀있으면 열기
                if (lightPolContent.getVisibility() == View.GONE) {
                    lightPolArrow.setRotation(90);
                    lightPolContent.setVisibility(View.VISIBLE);
                }
                // 열려있으면 닫기
                else {
                    lightPolArrow.setRotation(360);
                    lightPolContent.setVisibility(View.GONE);
                }
            }
        });

        moonPhaseOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moonPhaseContent.getVisibility() == View.GONE) {
                    moonPhaseArrow.setRotation(90);
                    moonPhaseContent.setVisibility(View.VISIBLE);
                } else {
                    moonPhaseArrow.setRotation(360);
                    moonPhaseContent.setVisibility(View.GONE);
                }
            }
        });

        cloudOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cloudContent.getVisibility() == View.GONE) {
                    cloudArrow.setRotation(90);
                    cloudContent.setVisibility(View.VISIBLE);
                } else {
                    cloudArrow.setRotation(360);
                    cloudContent.setVisibility(View.GONE);
                }
            }
        });

        windOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (windContent.getVisibility() == View.GONE) {
                    windArrow.setRotation(90);
                    windContent.setVisibility(View.VISIBLE);
                } else {
                    windArrow.setRotation(360);
                    windContent.setVisibility(View.GONE);
                }
            }
        });

        tempOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tempContent.getVisibility() == View.GONE) {
                    tempArrow.setRotation(90);
                    tempContent.setVisibility(View.VISIBLE);
                } else {
                    tempArrow.setRotation(360);
                    tempContent.setVisibility(View.GONE);
                }
            }
        });

        humidityOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (humidityContent.getVisibility() == View.GONE) {
                    humidityArrow.setRotation(90);
                    humidityContent.setVisibility(View.VISIBLE);
                } else {
                    humidityArrow.setRotation(360);
                    humidityContent.setVisibility(View.GONE);
                }
            }
        });

        precipitationOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (precipitationContent.getVisibility() == View.GONE) {
                    precipitationArrow.setRotation(90);
                    precipitationContent.setVisibility(View.VISIBLE);
                } else {
                    precipitationArrow.setRotation(360);
                    precipitationContent.setVisibility(View.GONE);
                }
            }
        });

        fineDustOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fineDustContent.getVisibility() == View.GONE) {
                    fineDustArrow.setRotation(90);
                    fineDustContent.setVisibility(View.VISIBLE);
                } else {
                    fineDustArrow.setRotation(360);
                    fineDustContent.setVisibility(View.GONE);
                }
            }
        });
    }
}