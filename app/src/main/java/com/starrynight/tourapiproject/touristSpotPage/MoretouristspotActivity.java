package com.starrynight.tourapiproject.touristSpotPage;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import com.starrynight.tourapiproject.R;

public class MoretouristspotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moretouristspot);

        Button button = findViewById(R.id.backbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Touristspot_Activity.class);
                startActivity(intent);
            }
        });
        Button post_btn = findViewById(R.id.morebutton1);
        ScrollView scrollView = findViewById(R.id.scrollview1);
        ScrollView scrollView1 = findViewById(R.id.scrollview2);
        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.setVisibility(View.INVISIBLE);
                scrollView1.setVisibility(View.VISIBLE);
                post_btn.setVisibility(View.INVISIBLE);
            }
        });
    }
}