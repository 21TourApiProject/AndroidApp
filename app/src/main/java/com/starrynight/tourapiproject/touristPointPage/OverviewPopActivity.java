package com.starrynight.tourapiproject.touristPointPage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //테두리에 이상한거 안남게
        setContentView(R.layout.activity_tp_overview_pop);

//        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
//        int width = (int) (dm.widthPixels * 0.9); // Display 사이즈의 90%
//        int height = (int) (dm.heightPixels * 0.9);  //Display 사이즈의 90% 각자 원하는 사이즈로 설정하여 사용
//        getWindow().getAttributes().width = width;
//        getWindow().getAttributes().height = height;

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
