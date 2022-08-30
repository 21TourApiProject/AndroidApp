package com.starrynight.tourapiproject.observationPage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.starrynight.tourapiproject.R;

/**
* @className : OutlinePopActivity.java
* @description : 개요 자세히보기 창
* @modification : gyul chyoung (2022-08-30) 주석추가
* @author : 2022-08-30
* @date : gyul chyoung
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   gyul chyoung       2022-08-30       주석추가
 */

public class OutlinePopActivity extends AppCompatActivity {

    String outline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //테두리에 이상한거 안남게
        setContentView(R.layout.activity_tp_overview_pop);

        Intent intent = getIntent();
        outline = (String) intent.getSerializableExtra("outline"); //전 페이지에서 받아온 개요
        Log.d("observation_ouytline", "popup started");

        TextView overviewFull = findViewById(R.id.overviewFull);
        overviewFull.setText(outline);
    }

    //팝업 닫기
    public void closeOverview(View v) {
        finish();
    }
}
