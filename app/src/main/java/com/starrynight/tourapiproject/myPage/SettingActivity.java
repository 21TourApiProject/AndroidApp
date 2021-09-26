package com.starrynight.tourapiproject.myPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.signUpPage.LocationInfoActivity;

public class SettingActivity extends AppCompatActivity {
    Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent intent = getIntent();
        userId = (Long) intent.getSerializableExtra("userId"); //전 페이지에서 받아온 사용자 id

        //뒤로 가기
        Button settingBack = findViewById(R.id.settingBack);
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

        //이용 가이드
        LinearLayout usageGuide = findViewById(R.id.usageGuide);
        usageGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, UsageGuideActivity.class);
                startActivity(intent);
            }
        });

        //고객센터
        LinearLayout customerSC = findViewById(R.id.customerSC);
        customerSC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, CustomerSCActivity.class);
                startActivity(intent);
            }
        });

//        //이용약관
//        LinearLayout termsAndConditions = findViewById(R.id.termsAndConditions);
//        termsAndConditions.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SettingActivity.this, TermsAndConditionsActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        //개인정보 처리방침
//        LinearLayout personalData = findViewById(R.id.personalData);
//        personalData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SettingActivity.this, PersonalDataActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        //운영정책
//        LinearLayout managementPolicy = findViewById(R.id.managementPolicy);
//        managementPolicy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SettingActivity.this, ManagementPolicyActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        //위치기반서비스 이용약관
//        LinearLayout location = findViewById(R.id.location);
//        location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SettingActivity.this, LocationInfoActivity.class);
//                startActivity(intent);
//            }
//        });

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