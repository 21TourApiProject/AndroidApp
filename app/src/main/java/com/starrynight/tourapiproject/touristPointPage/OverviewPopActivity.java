package com.starrynight.tourapiproject.touristPointPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.starrynight.tourapiproject.R;

public class OverviewPopActivity extends AppCompatActivity {

    String overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tp_overview_pop);

        Intent intent = getIntent();
        overview = (String) intent.getSerializableExtra("overview"); //전 페이지에서 받아온 개요
        System.out.println("overview = " + overview);

        TextView overviewFull = findViewById(R.id.overviewFull);
        overviewFull.setText(overview);
    }

    //팝업 닫기
    public void closeOverview(View v){
        finish();
    }
}
