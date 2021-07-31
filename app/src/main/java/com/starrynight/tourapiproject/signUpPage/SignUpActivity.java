package com.starrynight.tourapiproject.signUpPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.starrynight.tourapiproject.R;

public class SignUpActivity extends AppCompatActivity {

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

        Button kakaoSignUp = findViewById(R.id.kakaoSignUp);
        kakaoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}