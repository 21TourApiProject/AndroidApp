package com.starrynight.tourapiproject.myPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.starrynight.tourapiproject.R;

public class SettingActivity extends AppCompatActivity {
    Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent intent = getIntent();
        userId = (Long) intent.getSerializableExtra("userId"); //전 페이지에서 받아온 사용자 id

        //내 정보
        TextView myData = findViewById(R.id.myData);
        myData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, MyDataActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        //공지사항
        TextView notice = findViewById(R.id.notice);
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, NoticeActivity.class);
                startActivity(intent);
            }
        });

        //이용 가이드
        TextView usageGuide = findViewById(R.id.usageGuide);
        usageGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, UsageGuideActivity.class);
                startActivity(intent);
            }
        });

        //고객센터
        TextView customerSC = findViewById(R.id.customerSC);
        customerSC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, CustomerSCActivity.class);
                startActivity(intent);
            }
        });

        //이용약관
        TextView termsAndConditions = findViewById(R.id.termsAndConditions);
        termsAndConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, TermsAndConditionsActivity.class);
                startActivity(intent);
            }
        });

        //개인정보 처리방침
        TextView personalData = findViewById(R.id.personalData);
        personalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, PersonalDataActivity.class);
                startActivity(intent);
            }
        });

        //로그아웃
        TextView logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //팝업으로 처리
            }
        });

        //탈퇴하기
        TextView leave = findViewById(R.id.leave);
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