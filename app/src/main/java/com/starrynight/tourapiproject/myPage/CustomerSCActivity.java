package com.starrynight.tourapiproject.myPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.starrynight.tourapiproject.R;

/**
 * @className : CustomerSCActivity.java
 * @description : 고객센터 페이지 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
public class CustomerSCActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sc);

        Button sendEmail = findViewById(R.id.sendEmail);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                String[] address = {"starsufers@gmail.com"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_SUBJECT, "[고객문의] 제목을 입력해 주세요.");
                email.putExtra(Intent.EXTRA_TEXT, "정확한 답변 전달을 위해 고객님의 개인정보가 필요합니다. 수집된 정보는 본인 확인 및 문제 해결 이외의 다른 용도로 절대 사용하지 않습니다.\n" +
                        "이름 :\n" +
                        "ID(Email) :\n" +
                        "연락처(휴대폰) :\n" +
                        "문의 내용 : 화면 캡쳐, 문제 발생 일시 등과 함께 문제 상황과  필요하신 사항에 대해 상세히 기재해 주시면 보다 신속하고 정확한 처리가 가능합니다. ");
                startActivity(email);
            }
        });

        ImageView csBack = findViewById(R.id.csBack);
        csBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}