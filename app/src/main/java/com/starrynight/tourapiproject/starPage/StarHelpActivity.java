package com.starrynight.tourapiproject.starPage;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.starrynight.tourapiproject.R;

/**
 * @className : StarHelpActivity
 * @description : 별자리 도움말 페이지입니다.
 * @modification : 2022-09-15 (hyeonz) 주석추가
 * @author : hyeonz
 * @date : 2022-09-15
 * @version : 1.0
====개정이력(Modification Information)====
수정일        수정자        수정내용
-----------------------------------------
2022-09-15   hyeonz      주석추가
 */
public class StarHelpActivity extends AppCompatActivity {
    // 아이템 열기 터치 영역
    ConstraintLayout observeEasilyOpen;
    ConstraintLayout lookMilkyWayOpen;
    ConstraintLayout starPhotoOpen;
    ConstraintLayout observePreparationOpen;
    ConstraintLayout observeEtiquetteOpen;

    // 아이템 내용
    ImageView observeEasilyContent;
    ImageView lookMilkyWayContent;
    ImageView starPhotoContent;
    ImageView observePreparationContent;
    ImageView observeEtiquetteContent;

    // 아이템 화살표
    ImageView observeEasilyArrow;
    ImageView lookMilkyWayArrow;
    ImageView starPhotoArrow;
    ImageView observePreparationArrow;
    ImageView observeEtiquetteArrow;

    ImageView helpBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_help);

        observeEasilyOpen = findViewById(R.id.observe_easily_open);
        lookMilkyWayOpen = findViewById(R.id.look_milkyWay_open);
        starPhotoOpen = findViewById(R.id.star_photo_open);
        observePreparationOpen = findViewById(R.id.observe_preparation_open);
        observeEtiquetteOpen = findViewById(R.id.observe_etiquette_open);

        observeEasilyContent = findViewById(R.id.observe_easily_content);
        lookMilkyWayContent = findViewById(R.id.look_milkyWay_content);
        starPhotoContent = findViewById(R.id.star_photo_content);
        observePreparationContent = findViewById(R.id.observe_preparation_content);
        observeEtiquetteContent = findViewById(R.id.observe_etiquette_content);

        observeEasilyArrow = findViewById(R.id.observe_easily_arrow);
        lookMilkyWayArrow = findViewById(R.id.look_milkyWay_arrow);
        starPhotoArrow = findViewById(R.id.star_photo_arrow);
        observePreparationArrow = findViewById(R.id.observe_preparation_arrow);
        observeEtiquetteArrow = findViewById(R.id.observe_etiquette_arrow);

        helpBackBtn = findViewById(R.id.star_help_back_btn);

        onClickBackBtn();
        setItemClickEvent();

    }

    public void onClickBackBtn() {
        helpBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setItemClickEvent() {
        observeEasilyOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 닫혀있으면 열기
                if (observeEasilyContent.getVisibility() == View.GONE) {
                    observeEasilyArrow.setRotation(90);
                    observeEasilyContent.setVisibility(View.VISIBLE);
                }
                // 열려있으면 닫기
                else {
                    observeEasilyArrow.setRotation(360);
                    observeEasilyContent.setVisibility(View.GONE);
                }
            }
        });

        lookMilkyWayOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lookMilkyWayContent.getVisibility() == View.GONE) {
                    lookMilkyWayArrow.setRotation(90);
                    lookMilkyWayContent.setVisibility(View.VISIBLE);
                } else {
                    lookMilkyWayArrow.setRotation(360);
                    lookMilkyWayContent.setVisibility(View.GONE);
                }
            }
        });

        starPhotoOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (starPhotoContent.getVisibility() == View.GONE) {
                    starPhotoArrow.setRotation(90);
                    starPhotoContent.setVisibility(View.VISIBLE);
                } else {
                    starPhotoArrow.setRotation(360);
                    starPhotoContent.setVisibility(View.GONE);
                }
            }
        });

        observePreparationOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (observePreparationContent.getVisibility() == View.GONE) {
                    observePreparationArrow.setRotation(90);
                    observePreparationContent.setVisibility(View.VISIBLE);
                } else {
                    observePreparationArrow.setRotation(360);
                    observePreparationContent.setVisibility(View.GONE);
                }
            }
        });

        observeEtiquetteOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (observeEtiquetteContent.getVisibility() == View.GONE) {
                    observeEtiquetteArrow.setRotation(90);
                    observeEtiquetteContent.setVisibility(View.VISIBLE);
                } else {
                    observeEtiquetteArrow.setRotation(360);
                    observeEtiquetteContent.setVisibility(View.GONE);
                }
            }
        });
    }

}