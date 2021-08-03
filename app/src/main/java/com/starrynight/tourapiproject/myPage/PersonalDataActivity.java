package com.starrynight.tourapiproject.myPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.starrynight.tourapiproject.R;

public class PersonalDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);

        //프로필 변경 페이지로 이동
        ImageView profileImage = findViewById(R.id.profileImage2);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalDataActivity.this, ChangeProfileActivity.class);
                startActivity(intent);
            }
        });

        //프로필 변경 페이지로 이동
        TextView nickName = findViewById(R.id.nickName2);
        nickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalDataActivity.this, ChangeProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}