package com.starrynight.tourapiproject.touristPointPage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.starrynight.tourapiproject.R;

public class TPReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_point_review);

        Button button = findViewById(R.id.morebutton);
        LinearLayout linearLayout = findViewById(R.id.LinearLayout2);
        LinearLayout linearLayout1 = findViewById(R.id.LinearLayout1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.VISIBLE);
                linearLayout1.setVisibility(View.INVISIBLE);
                button.setVisibility(View.INVISIBLE);
            }
        });
    }
}