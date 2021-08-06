package com.starrynight.tourapiproject.signUpPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.kakao.auth.Session;
import com.starrynight.tourapiproject.R;

public class SignUpActivity extends AppCompatActivity {

    private SessionCallback sessionCallback = new SessionCallback();
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button generalSignUp = findViewById(R.id.generalSignUp);
        generalSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, GeneralSingUpActivity.class);
                startActivity(intent);
            }
        });

        ImageButton kakaoSignUp = findViewById(R.id.kakaoSignUp);
        session = Session.getCurrentSession();
        session.addCallback(sessionCallback);
        kakaoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}