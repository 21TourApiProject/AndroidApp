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

import static com.starrynight.tourapiproject.R.layout.activity_tp_overview_pop;

/**
 * @className : OverviewPopActivity.java
 * @description : 관광지 페이지의 개요 팝업 페이지 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
public class OverviewPopActivity extends AppCompatActivity {

    String overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //테두리에 이상한거 안남게
        setContentView(activity_tp_overview_pop);

//        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
//        int width = (int) (dm.widthPixels * 0.9); // Display 사이즈의 90%
//        int height = (int) (dm.heightPixels * 0.9);  //Display 사이즈의 90% 각자 원하는 사이즈로 설정하여 사용
//        getWindow().getAttributes().width = width;
//        getWindow().getAttributes().height = height;

        Intent intent = getIntent();
        overview = (String) intent.getSerializableExtra("overview"); //전 페이지에서 받아온 개요

        TextView overviewFull = findViewById(R.id.overviewFull);
        overviewFull.setText(overview);
    }

    //팝업 닫기
    public void closeOverview(View v) {
        finish();
    }
}
