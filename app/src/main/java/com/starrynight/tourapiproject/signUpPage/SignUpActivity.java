package com.starrynight.tourapiproject.signUpPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.weatherPage.WeatherActivity;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button test = findViewById(R.id.signUp);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SelectHashTagActivity.class);
                startActivity(intent);
            }
        });
    }
}