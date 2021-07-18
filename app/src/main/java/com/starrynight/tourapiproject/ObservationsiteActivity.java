package com.starrynight.tourapiproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.starrynight.tourapiproject.TouristspotPage.Touristspot_Activity;

public class ObservationsiteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observationsite);

        Button heart_btn = findViewById(R.id.button6);
        heart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });
        Button back_btn =findViewById(R.id.button);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        ImageView obv_btn = findViewById(R.id.obvimagebutton);
        ImageView obv_btn2 = findViewById(R.id.obvimagebutton2);
        ImageView obv_btn3 = findViewById(R.id.obvimagebutton3);
        ImageView obv_btn4 = findViewById(R.id.obvimagebutton4);
        LinearLayout linearLayout = findViewById(R.id.obvlayout);
        LinearLayout linearLayout2 = findViewById(R.id.obvlayout2);
        LinearLayout linearLayout3 = findViewById(R.id.obvlayout3);
        LinearLayout linearLayout4 = findViewById(R.id.obvlayout4);

        obv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                linearLayout.setVisibility(View.VISIBLE);
                linearLayout2.setVisibility(View.INVISIBLE);
                linearLayout3.setVisibility(View.INVISIBLE);
                linearLayout4.setVisibility(View.INVISIBLE);
                obv_btn.setSelected(true);
                obv_btn2.setSelected(false);
                obv_btn3.setSelected(false);
                obv_btn4.setSelected(false);
            }
        });
        obv_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                linearLayout.setVisibility(View.INVISIBLE);
                linearLayout2.setVisibility(View.VISIBLE);
                linearLayout3.setVisibility(View.INVISIBLE);
                linearLayout4.setVisibility(View.INVISIBLE);
                obv_btn.setSelected(false);
                obv_btn2.setSelected(true);
                obv_btn3.setSelected(false);
                obv_btn4.setSelected(false);
            }
        });
        obv_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                linearLayout.setVisibility(View.INVISIBLE);
                linearLayout2.setVisibility(View.INVISIBLE);
                linearLayout3.setVisibility(View.VISIBLE);
                linearLayout4.setVisibility(View.INVISIBLE);
                obv_btn.setSelected(false);
                obv_btn2.setSelected(false);
                obv_btn3.setSelected(true);
                obv_btn4.setSelected(false);
            }
        });
        obv_btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                linearLayout.setVisibility(View.INVISIBLE);
                linearLayout2.setVisibility(View.INVISIBLE);
                linearLayout3.setVisibility(View.INVISIBLE);
                linearLayout4.setVisibility(View.VISIBLE);
                obv_btn.setSelected(false);
                obv_btn2.setSelected(false);
                obv_btn3.setSelected(false);
                obv_btn4.setSelected(true);
            }
        });

    }
}