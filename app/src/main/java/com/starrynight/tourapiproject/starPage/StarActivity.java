package com.starrynight.tourapiproject.starPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.R;

public class StarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);

        Button back_btn = findViewById(R.id.starback_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        TextView spring_btn=findViewById(R.id.spring_btn);
        TextView summer_btn=findViewById(R.id.summer_btn);
        TextView autumn_btn=findViewById(R.id.autumn_btn);
        TextView winter_btn=findViewById(R.id.winter_btn);
        LinearLayout springLayout=findViewById(R.id.springlinear);
        LinearLayout summerLayout=findViewById(R.id.summerlinear);
        LinearLayout autumnLayout=findViewById(R.id.autumnlinear);
        LinearLayout winterLayout=findViewById(R.id.winterlinear);

        spring_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                springLayout.setVisibility(View.VISIBLE);
                summerLayout.setVisibility(View.INVISIBLE);
                autumnLayout.setVisibility(View.INVISIBLE);
                winterLayout.setVisibility(View.INVISIBLE);
                spring_btn.setSelected(true);
                summer_btn.setSelected(false);
                autumn_btn.setSelected(false);
                winter_btn.setSelected(false);
            }
        });
        summer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                springLayout.setVisibility(View.INVISIBLE);
                summerLayout.setVisibility(View.VISIBLE);
                autumnLayout.setVisibility(View.INVISIBLE);
                winterLayout.setVisibility(View.INVISIBLE);
                spring_btn.setSelected(false);
                summer_btn.setSelected(true);
                autumn_btn.setSelected(false);
                winter_btn.setSelected(false);
            }
        });
        autumn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                springLayout.setVisibility(View.INVISIBLE);
                summerLayout.setVisibility(View.INVISIBLE);
                autumnLayout.setVisibility(View.VISIBLE);
                winterLayout.setVisibility(View.INVISIBLE);
                spring_btn.setSelected(false);
                summer_btn.setSelected(false);
                autumn_btn.setSelected(true);
                winter_btn.setSelected(false);
            }
        });
        winter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                springLayout.setVisibility(View.INVISIBLE);
                summerLayout.setVisibility(View.INVISIBLE);
                autumnLayout.setVisibility(View.INVISIBLE);
                winterLayout.setVisibility(View.VISIBLE);
                spring_btn.setSelected(false);
                summer_btn.setSelected(false);
                autumn_btn.setSelected(false);
                winter_btn.setSelected(true);
            }
        });
    }
}