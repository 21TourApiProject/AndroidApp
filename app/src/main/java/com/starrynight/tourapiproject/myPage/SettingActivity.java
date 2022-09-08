package com.starrynight.tourapiproject.myPage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import com.starrynight.tourapiproject.R;

/**
 * @className : SettingActivity.java
 * @description : 환경설정 페이지 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
public class SettingActivity extends AppCompatActivity {
    Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent intent = getIntent();
        userId = (Long) intent.getSerializableExtra("userId"); //전 페이지에서 받아온 사용자 id

        //뒤로 가기
        ImageView settingBack = findViewById(R.id.settingBack);
        settingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //내 정보
        LinearLayout myData = findViewById(R.id.myData);
        myData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, MyDataActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        //공지사항
        LinearLayout notice = findViewById(R.id.notice);
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, NoticeActivity.class);
                startActivity(intent);
            }
        });

//        //이용 가이드
//        LinearLayout usageGuide = findViewById(R.id.usageGuide);
//        usageGuide.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SettingActivity.this, UsageGuideActivity.class);
//                startActivity(intent);
//            }
//        });

        //고객센터
        LinearLayout customerSC = findViewById(R.id.customerSC);
        customerSC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, CustomerSCActivity.class);
                startActivity(intent);
            }
        });

        //이용약관
        LinearLayout termsAndConditions = findViewById(R.id.termsAndConditions);
        termsAndConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://luxuriant-router-7fe.notion.site/d74df94b11ce4e8f8592a77425fd403b"));
                startActivity(intent);
            }
        });

        //개인정보 처리방침
        LinearLayout personalData = findViewById(R.id.personalData);
        personalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://luxuriant-router-7fe.notion.site/3bae231c3cb34a6e9ce6585df8b96233"));
                startActivity(intent);
            }
        });

        //운영정책
        LinearLayout managementPolicy = findViewById(R.id.managementPolicy);
        managementPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://luxuriant-router-7fe.notion.site/9d25ab83064247069f7191c55b5bf671"));
                startActivity(intent);
            }
        });

        //위치기반서비스 이용약관
        LinearLayout location = findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://luxuriant-router-7fe.notion.site/f3305181536d41a9997961f6516d57ac"));
                startActivity(intent);
            }
        });

        //오픈소스 라이센스
        LinearLayout opensource = findViewById(R.id.opensource);
        opensource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, OssLicensesMenuActivity.class));
            }
        });

        //기여한 사람
        LinearLayout thanksTo = findViewById(R.id.thanksTo);
        thanksTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, ThanksToActivity.class));
            }
        });

        //로그아웃
        LinearLayout logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //팝업으로 처리
                Intent intent = new Intent(SettingActivity.this, LogoutPopActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        //탈퇴하기
        LinearLayout leave = findViewById(R.id.leave);
        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //팝업으로 처리
                Intent intent = new Intent(SettingActivity.this, LeavePopActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
    }
}