package com.starrynight.tourapiproject.TouristspotPage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.postItemPage.Post_point_item_Adapter;
import com.starrynight.tourapiproject.postItemPage.post_point_item;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Touristspot_Activity extends AppCompatActivity {

    private static final String API_KEY="KakaoAK 8e9d0698ed2d448e4b441ff77ccef198";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_touristspot);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_post);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        Post_point_item_Adapter adapter = new Post_point_item_Adapter();
        recyclerView.setAdapter(adapter);

        adapter.addItem(new post_point_item("관광지1"));
        adapter.addItem(new post_point_item("관광지2"));
        adapter.addItem(new post_point_item("관광지3"));

        recyclerView.setAdapter((adapter));

        RecyclerView recyclerView1 = findViewById(R.id.recyclerview_post2);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(layoutManager1);

        recyclerView1.setAdapter(adapter);

        adapter.addItem(new post_point_item("관광지1"));
        adapter.addItem(new post_point_item("관광지2"));
        adapter.addItem(new post_point_item("관광지3"));

        recyclerView1.setAdapter((adapter));

        RecyclerView recyclerView2 = findViewById(R.id.daumrecyclerview);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(layoutManager2);

        SearchOpenApi openApi=SearchRetrofitFactory.create();
        openApi.getData("집짓기","accuracy",1,3,API_KEY)
                .enqueue(new Callback<SearchData>() {
                    @Override
                    public void onResponse(Call<SearchData> call, Response<SearchData> response) {
                        Log.d("my tag","성공");
                            SearchAdapter adapter1 = new SearchAdapter(response.body().meta.body);
                            recyclerView2.setAdapter(adapter1);
                    }

                    @Override
                    public void onFailure(Call<SearchData> call, Throwable t) {
                        Log.e("my tag","에러");
                    }
                });


        Button button = findViewById(R.id.like);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setSelected(!v.isSelected());
                }
            });

            Button postback_btn = findViewById(R.id.postbackbutton);
            postback_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            Button morespot_btn = findViewById(R.id.morespotbutton);
            morespot_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),MoretouristspotActivity.class);
                    startActivity(intent);
                }
            });
            Button review_btn =findViewById(R.id.reviewmore_button);
            review_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),TouristreviewActivity.class);
                    startActivity(intent);
                }
            });
            Button daum_btn=findViewById(R.id.daum_btn);
            daum_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.kakao.com/"));
                    startActivity(intent);
                }
            });
         }
    }
