package com.starrynight.tourapiproject.searchPage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.SearchFragment;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {

    SearchFragment searchFragment;

    int[] filter = {0,0};
    ArrayList<Integer> area = new ArrayList<Integer>(17);
    ArrayList<Integer> hashTag = new ArrayList<Integer>(22);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        for(int i=0; i<17; i++){
            area.set(i, 0);
        }
        for(int i=0; i<22; i++){
            hashTag.set(i, 0);
        }

        //뒤로 가기
        Button backFilter = findViewById(R.id.backFilter);
        backFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayout areaLayout = findViewById(R.id.areaLayout);
        LinearLayout hashTagLayout = findViewById(R.id.hashTagLayout);
        areaLayout.setVisibility(View.GONE);
        hashTagLayout.setVisibility(View.GONE);

        //지역
        Button areaBtn = findViewById(R.id.areaBtn);
        areaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        //여행 테마
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

        //완료 버튼
        Button submitFilter = findViewById(R.id.submitFilter);
        submitFilter.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                searchFragment = new SearchFragment();
                //getSupportFragmentManager().beginTransaction().replace(R.id.main_view, searchFragment).commit();
                Bundle bundle = new Bundle();
                bundle.putIntegerArrayList("area",area);
                bundle.putIntegerArrayList("hashTag",hashTag);
                searchFragment.setArguments(bundle);
                finish();
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
        Button hashTagBtn22 = findViewById(R.id.hashTagBtn22);

        areaBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(0) == 0) {
                    areaBtn1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area.set(0,1);
                } else{
                    areaBtn1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area.set(0,0);
                }
            }
        });
        areaBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(1) == 0) {
                    areaBtn2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area.set(1,1);
                } else{
                    areaBtn2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area.set(1,0);
                }
            }
        });
        areaBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(2) == 0) {
                    areaBtn3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area.set(2,1);
                } else{
                    areaBtn3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area.set(2,0);
                }
            }
        });
        areaBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(3) == 0) {
                    areaBtn4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area.set(3,1);
                } else{
                    areaBtn4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area.set(3,0);
                }
            }
        });
        areaBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(4) == 0) {
                    areaBtn5.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area.set(4,1);
                } else{
                    areaBtn5.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area.set(4,0);
                }
            }
        });
        areaBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(5) == 0) {
                    areaBtn6.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area.set(5,1);
                } else{
                    areaBtn6.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area.set(5,0);
                }
            }
        });
        areaBtn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(6) == 0) {
                    areaBtn7.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area.set(6,1);
                } else{
                    areaBtn7.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area.set(6,0);
                }
            }
        });
        areaBtn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(7) == 0) {
                    areaBtn8.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area.set(7,1);
                } else{
                    areaBtn8.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area.set(7,0);
                }
            }
        });
        areaBtn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(8) == 0) {
                    areaBtn9.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area.set(8,1);
                } else{
                    areaBtn9.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area.set(8,0);
                }
            }
        });
        areaBtn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(9) == 0) {
                    areaBtn10.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area.set(9,1);
                } else{
                    areaBtn10.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area.set(9,0);
                }
            }
        });
        areaBtn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(10) == 0) {
                    areaBtn11.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area.set(10,1);
                } else{
                    areaBtn11.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area.set(10,0);
                }
            }
        });
        areaBtn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(11) == 0) {
                    areaBtn12.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area.set(11,1);
                } else{
                    areaBtn12.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area.set(11,0);
                }
            }
        });
        areaBtn13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(12) == 0) {
                    areaBtn13.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area.set(12,1);
                } else{
                    areaBtn13.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area.set(12,0);
                }
            }
        });
        areaBtn14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(13) == 0) {
                    areaBtn14.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area.set(13,1);
                } else{
                    areaBtn14.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area.set(13,0);
                }
            }
        });
        areaBtn15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(14) == 0) {
                    areaBtn15.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area.set(14,1);
                } else{
                    areaBtn15.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area.set(14,0);
                }
            }
        });
        areaBtn16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(15) == 0) {
                    areaBtn16.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area.set(15,1);
                } else{
                    areaBtn16.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area.set(15,0);
                }
            }
        });
        areaBtn17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(16) == 0) {
                    areaBtn17.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    area.set(16,1);
                } else{
                    areaBtn17.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    area.set(16,0);
                }
            }
        });
        hashTagBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(0) == 0) {
                    hashTagBtn1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag.set(0,1);
                } else{
                    hashTagBtn1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag.set(0,0);
                }
            }
        });
        hashTagBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(1) == 0) {
                    hashTagBtn2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag.set(1,1);
                } else{
                    hashTagBtn2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag.set(1,0);
                }
            }
        });
        hashTagBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(2) == 0) {
                    hashTagBtn3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag.set(2,1);
                } else{
                    hashTagBtn3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag.set(2,0);
                }
            }
        });
        hashTagBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(3) == 0) {
                    hashTagBtn4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag.set(3,1);
                } else{
                    hashTagBtn4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag.set(3,0);
                }
            }
        });
        hashTagBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(4) == 0) {
                    hashTagBtn5.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag.set(4,1);
                } else{
                    hashTagBtn5.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag.set(4,0);
                }
            }
        });
        hashTagBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(5) == 0) {
                    hashTagBtn6.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag.set(5,1);
                } else{
                    hashTagBtn6.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag.set(5,0);
                }
            }
        });
        hashTagBtn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(6) == 0) {
                    hashTagBtn7.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag.set(6,1);
                } else{
                    hashTagBtn7.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag.set(6,0);
                }
            }
        });
        hashTagBtn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(7) == 0) {
                    hashTagBtn8.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag.set(7,1);
                } else{
                    hashTagBtn8.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag.set(7,0);
                }
            }
        });
        hashTagBtn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(8) == 0) {
                    hashTagBtn9.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag.set(8,1);
                } else{
                    hashTagBtn9.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag.set(8,0);
                }
            }
        });
        hashTagBtn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(9) == 0) {
                    hashTagBtn10.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag.set(9,1);
                } else{
                    hashTagBtn10.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag.set(9,0);
                }
            }
        });
        hashTagBtn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(10) == 0) {
                    hashTagBtn11.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag.set(10,1);
                } else{
                    hashTagBtn11.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag.set(10,0);
                }
            }
        });
        hashTagBtn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(11) == 0) {
                    hashTagBtn12.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag.set(11,1);
                } else{
                    hashTagBtn12.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag.set(11,0);
                }
            }
        });
        hashTagBtn13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(12) == 0) {
                    hashTagBtn13.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag.set(12,1);
                } else{
                    hashTagBtn13.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag.set(12,0);
                }
            }
        });
        hashTagBtn14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(13) == 0) {
                    hashTagBtn14.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag.set(13,1);
                } else{
                    hashTagBtn14.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag.set(13,0);
                }
            }
        });
        hashTagBtn15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(14) == 0) {
                    hashTagBtn15.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag.set(14,1);
                } else{
                    hashTagBtn15.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag.set(14,0);
                }
            }
        });
        hashTagBtn16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(15) == 0) {
                    hashTagBtn16.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag.set(15,1);
                } else{
                    hashTagBtn16.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag.set(15,0);
                }
            }
        });
        hashTagBtn17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(16) == 0) {
                    hashTagBtn17.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag.set(16,1);
                } else{
                    hashTagBtn17.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag.set(16,0);
                }
            }
        });
        hashTagBtn18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(17) == 0) {
                    hashTagBtn18.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag.set(17,1);
                } else{
                    hashTagBtn18.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag.set(17,0);
                }
            }
        });
        hashTagBtn19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(18) == 0) {
                    hashTagBtn19.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag.set(18,1);
                } else{
                    hashTagBtn19.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag.set(18,0);
                }
            }
        });
        hashTagBtn20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(19) == 0) {
                    hashTagBtn20.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag.set(19,1);
                } else{
                    hashTagBtn20.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag.set(19,0);
                }
            }
        });
        hashTagBtn21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(20) == 0) {
                    hashTagBtn21.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag.set(20,1);
                } else{
                    hashTagBtn21.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag.set(20,0);
                }
            }
        });
        hashTagBtn22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(21) == 0) {
                    hashTagBtn22.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark));
                    hashTag.set(21,1);
                } else{
                    hashTagBtn22.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bookmark_non));
                    hashTag.set(21,0);
                }
            }
        });
    }
}