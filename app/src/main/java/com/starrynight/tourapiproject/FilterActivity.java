package com.starrynight.tourapiproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class FilterActivity extends AppCompatActivity {

    int[] filter = {0,0};
    int[] area = new int[17];
    int[] hashTag = new int[21];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        for(int i=0; i<17; i++){
            area[i] = 0;
        }
        for(int i=0; i<21; i++){
            hashTag[i] = 0;
        }

        Button areaBtn = findViewById(R.id.areaBtn);
        areaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout areaLayout = findViewById(R.id.areaLayout);
                if (filter[0] == 0) {
                    filter[0] = 1;
                    areaBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart_btn1));
                    areaLayout.setVisibility(View.VISIBLE);
                } else{
                    filter[0] = 0;
                    areaBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart_btn2));
                    areaLayout.setVisibility(View.GONE);
                }
            }
        });

        Button hashTagBtn = findViewById(R.id.hashTagBtn);
        hashTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout hashTagLayout = findViewById(R.id.hashTagLayout);
                if (filter[1] == 0) {
                    filter[1] = 1;
                    hashTagBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart_btn1));
                    hashTagLayout.setVisibility(View.VISIBLE);
                } else{
                    filter[1] = 0;
                    hashTagBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart_btn2));
                    hashTagLayout.setVisibility(View.GONE);
                }
            }
        });

        Button submitFilter = findViewById(R.id.submitFilter);
        submitFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i<17; i++){
                    if (area[i] == 1)
                        System.out.println(i+1 + " ");
                }
                for(int i=0; i<21; i++){
                    if (hashTag[i] == 1)
                        System.out.println(i+1 + " ");
                }
            }
        });

        Button areaBtn1 = findViewById(R.id.areaBtn1);
        Button areaBtn2 = findViewById(R.id.areaBtn2);
        Button areaBtn3 = findViewById(R.id.areaBtn3);
        Button areaBtn4 = findViewById(R.id.areaBtn4);
        Button areaBtn5 = findViewById(R.id.areaBtn5);
        Button areaBtn6 = findViewById(R.id.areaBtn6);
        Button areaBtn7 = findViewById(R.id.areaBtn7);
        Button areaBtn8 = findViewById(R.id.areaBtn8);
        Button areaBtn9 = findViewById(R.id.areaBtn9);
        Button areaBtn10 = findViewById(R.id.areaBtn10);
        Button areaBtn11 = findViewById(R.id.areaBtn11);
        Button areaBtn12 = findViewById(R.id.areaBtn12);
        Button areaBtn13 = findViewById(R.id.areaBtn13);
        Button areaBtn14 = findViewById(R.id.areaBtn14);
        Button areaBtn15 = findViewById(R.id.areaBtn15);
        Button areaBtn16 = findViewById(R.id.areaBtn16);
        Button areaBtn17 = findViewById(R.id.areaBtn17);

        Button hashTagBtn1 = findViewById(R.id.hashTagBtn1);
        Button hashTagBtn2 = findViewById(R.id.hashTagBtn2);
        Button hashTagBtn3 = findViewById(R.id.hashTagBtn3);
        Button hashTagBtn4 = findViewById(R.id.hashTagBtn4);
        Button hashTagBtn5 = findViewById(R.id.hashTagBtn5);
        Button hashTagBtn6 = findViewById(R.id.hashTagBtn6);
        Button hashTagBtn7 = findViewById(R.id.hashTagBtn7);
        Button hashTagBtn8 = findViewById(R.id.hashTagBtn8);
        Button hashTagBtn9 = findViewById(R.id.hashTagBtn9);
        Button hashTagBtn10 = findViewById(R.id.hashTagBtn10);
        Button hashTagBtn11 = findViewById(R.id.hashTagBtn11);
        Button hashTagBtn12 = findViewById(R.id.hashTagBtn12);
        Button hashTagBtn13 = findViewById(R.id.hashTagBtn13);
        Button hashTagBtn14 = findViewById(R.id.hashTagBtn14);
        Button hashTagBtn15 = findViewById(R.id.hashTagBtn15);
        Button hashTagBtn16 = findViewById(R.id.hashTagBtn16);
        Button hashTagBtn17 = findViewById(R.id.hashTagBtn17);
        Button hashTagBtn18 = findViewById(R.id.hashTagBtn18);
        Button hashTagBtn19 = findViewById(R.id.hashTagBtn19);
        Button hashTagBtn20 = findViewById(R.id.hashTagBtn20);
        Button hashTagBtn21 = findViewById(R.id.hashTagBtn21);

        areaBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area[0] == 0) {
                    areaBtn1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area[0] = 1;
                } else{
                    areaBtn1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area[0] = 0;
                }
            }
        });
        areaBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area[1] == 0) {
                    areaBtn2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area[1] = 1;
                } else{
                    areaBtn2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area[1] = 0;
                }
            }
        });
        areaBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area[2] == 0) {
                    areaBtn3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area[2] = 1;
                } else{
                    areaBtn3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area[2] = 0;
                }
            }
        });
        areaBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area[3] == 0) {
                    areaBtn4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area[3] = 1;
                } else{
                    areaBtn4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area[3] = 0;
                }
            }
        });
        areaBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area[4] == 0) {
                    areaBtn5.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area[4] = 1;
                } else{
                    areaBtn5.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area[4] = 0;
                }
            }
        });
        areaBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area[5] == 0) {
                    areaBtn6.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area[5] = 1;
                } else{
                    areaBtn6.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area[5] = 0;
                }
            }
        });
        areaBtn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area[6] == 0) {
                    areaBtn7.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area[6] = 1;
                } else{
                    areaBtn7.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area[6] = 0;
                }
            }
        });
        areaBtn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area[7] == 0) {
                    areaBtn8.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area[7] = 1;
                } else{
                    areaBtn8.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area[7] = 0;
                }
            }
        });
        areaBtn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area[8] == 0) {
                    areaBtn9.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area[8] = 1;
                } else{
                    areaBtn9.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area[8] = 0;
                }
            }
        });
        areaBtn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area[9] == 0) {
                    areaBtn10.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area[9] = 1;
                } else{
                    areaBtn10.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area[9] = 0;
                }
            }
        });
        areaBtn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area[10] == 0) {
                    areaBtn11.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area[10] = 1;
                } else{
                    areaBtn11.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area[10] = 0;
                }
            }
        });
        areaBtn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area[11] == 0) {
                    areaBtn12.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area[11] = 1;
                } else{
                    areaBtn12.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area[11] = 0;
                }
            }
        });
        areaBtn13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area[12] == 0) {
                    areaBtn13.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area[12] = 1;
                } else{
                    areaBtn13.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area[12] = 0;
                }
            }
        });
        areaBtn14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area[13] == 0) {
                    areaBtn14.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area[13] = 1;
                } else{
                    areaBtn14.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area[13] = 0;
                }
            }
        });
        areaBtn15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area[14] == 0) {
                    areaBtn15.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area[14] = 1;
                } else{
                    areaBtn15.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area[14] = 0;
                }
            }
        });
        areaBtn16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area[15] == 0) {
                    areaBtn16.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area[15] = 1;
                } else{
                    areaBtn16.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area[15] = 0;
                }
            }
        });
        areaBtn17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area[16] == 0) {
                    areaBtn17.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area[16] = 1;
                } else{
                    areaBtn17.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area[16] = 0;
                }
            }
        });
        hashTagBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag[0] == 0) {
                    hashTagBtn1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag[0] = 1;
                } else{
                    hashTagBtn1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag[0] = 0;
                }
            }
        });
        hashTagBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag[1] == 0) {
                    hashTagBtn2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag[1] = 1;
                } else{
                    hashTagBtn2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag[1] = 0;
                }
            }
        });
        hashTagBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag[2] == 0) {
                    hashTagBtn3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag[2] = 1;
                } else{
                    hashTagBtn3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag[2] = 0;
                }
            }
        });
        hashTagBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag[3] == 0) {
                    hashTagBtn4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag[3] = 1;
                } else{
                    hashTagBtn4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag[3] = 0;
                }
            }
        });
        hashTagBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag[4] == 0) {
                    hashTagBtn5.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag[4] = 1;
                } else{
                    hashTagBtn5.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag[4] = 0;
                }
            }
        });
        hashTagBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag[5] == 0) {
                    hashTagBtn6.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag[5] = 1;
                } else{
                    hashTagBtn6.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag[5] = 0;
                }
            }
        });
        hashTagBtn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag[6] == 0) {
                    hashTagBtn7.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag[6] = 1;
                } else{
                    hashTagBtn7.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag[6] = 0;
                }
            }
        });
        hashTagBtn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag[7] == 0) {
                    hashTagBtn8.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag[7] = 1;
                } else{
                    hashTagBtn8.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag[7] = 0;
                }
            }
        });
        hashTagBtn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag[8] == 0) {
                    hashTagBtn9.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag[8] = 1;
                } else{
                    hashTagBtn9.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag[8] = 0;
                }
            }
        });
        hashTagBtn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag[9] == 0) {
                    hashTagBtn10.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag[9] = 1;
                } else{
                    hashTagBtn10.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag[9] = 0;
                }
            }
        });
        hashTagBtn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag[10] == 0) {
                    hashTagBtn11.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag[10] = 1;
                } else{
                    hashTagBtn11.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag[10] = 0;
                }
            }
        });
        hashTagBtn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag[11] == 0) {
                    hashTagBtn12.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag[11] = 1;
                } else{
                    hashTagBtn12.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag[11] = 0;
                }
            }
        });
        hashTagBtn13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag[12] == 0) {
                    hashTagBtn13.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag[12] = 1;
                } else{
                    hashTagBtn13.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag[12] = 0;
                }
            }
        });
        hashTagBtn14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag[13] == 0) {
                    hashTagBtn14.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag[13] = 1;
                } else{
                    hashTagBtn14.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag[13] = 0;
                }
            }
        });
        hashTagBtn15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag[14] == 0) {
                    hashTagBtn15.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag[14] = 1;
                } else{
                    hashTagBtn15.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag[14] = 0;
                }
            }
        });
        hashTagBtn16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag[15] == 0) {
                    hashTagBtn16.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag[15] = 1;
                } else{
                    hashTagBtn16.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag[15] = 0;
                }
            }
        });
        hashTagBtn17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag[16] == 0) {
                    hashTagBtn17.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag[16] = 1;
                } else{
                    hashTagBtn17.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag[16] = 0;
                }
            }
        });
        hashTagBtn18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag[17] == 0) {
                    hashTagBtn18.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag[17] = 1;
                } else{
                    hashTagBtn18.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag[17] = 0;
                }
            }
        });
        hashTagBtn19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag[18] == 0) {
                    hashTagBtn19.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag[18] = 1;
                } else{
                    hashTagBtn19.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag[18] = 0;
                }
            }
        });
        hashTagBtn20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag[19] == 0) {
                    hashTagBtn20.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag[19] = 1;
                } else{
                    hashTagBtn20.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag[19] = 0;
                }
            }
        });
        hashTagBtn21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag[20] == 0) {
                    hashTagBtn21.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag[20] = 1;
                } else{
                    hashTagBtn21.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag[20] = 0;
                }
            }
        });

    }
}