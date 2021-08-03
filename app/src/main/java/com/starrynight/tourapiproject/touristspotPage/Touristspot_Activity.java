package com.starrynight.tourapiproject.touristspotPage;

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
import com.starrynight.tourapiproject.ObservationsiteActivity;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.postItemPage.OnPostItemClickListener;
import com.starrynight.tourapiproject.postItemPage.Post_point_item_Adapter;
import com.starrynight.tourapiproject.postItemPage.post_point_item;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

public class Touristspot_Activity extends AppCompatActivity {

    private static final String API_KEY= "KakaoAK 8e9d0698ed2d448e4b441ff77ccef198";
    List<SearchData.Document> Listdocument;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_touristspot);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_post);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        Post_point_item_Adapter adapter = new Post_point_item_Adapter();
        recyclerView.setAdapter(adapter);

        adapter.addItem(new post_point_item("관광지1","https://cdn.pixabay.com/photo/2018/08/11/20/37/cathedral-3599450_960_720.jpg"));
        adapter.addItem(new post_point_item("관광지2","https://cdn.pixabay.com/photo/2018/07/15/23/22/prague-3540883_960_720.jpg"));
        adapter.addItem(new post_point_item("관광지3","https://cdn.pixabay.com/photo/2019/12/13/07/35/city-4692432_960_720.jpg"));

        recyclerView.setAdapter((adapter));
        Post_point_item_Adapter adapter2 =new Post_point_item_Adapter();
        RecyclerView recyclerView1 = findViewById(R.id.recyclerview_post2);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(layoutManager1);

        recyclerView1.setAdapter(adapter2);

        adapter2.addItem(new post_point_item("관광지1","https://cdn.pixabay.com/photo/2019/12/13/07/35/city-4692432_960_720.jpg"));
        adapter2.addItem(new post_point_item("관광지2","https://cdn.pixabay.com/photo/2018/08/11/20/37/cathedral-3599450_960_720.jpg"));
        adapter2.addItem(new post_point_item("관광지3","https://cdn.pixabay.com/photo/2018/07/15/23/22/prague-3540883_960_720.jpg"));
        adapter2.setOnItemClicklistener(new OnPostItemClickListener() {
            @Override
            public void onItemClick(Post_point_item_Adapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(getApplicationContext(), ObservationsiteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        recyclerView1.setAdapter((adapter2));

        RecyclerView recyclerView2 = findViewById(R.id.daumrecyclerview);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setHasFixedSize(true);
        Listdocument = new ArrayList<>();

        SearchOpenApi openApi=SearchRetrofitFactory.create();
        openApi.getData("일산 카페","accuracy",1,4,API_KEY)
                .enqueue(new Callback<SearchData>() {
                    @Override
                    public void onResponse(Call<SearchData> call, Response<SearchData> response) {
                        Log.d("my tag","성공");
                        Listdocument = response.body().Searchdocuments;
                            SearchAdapter adapter1 = new SearchAdapter(Listdocument);
                            recyclerView2.setAdapter(adapter1);
                            adapter1.setOnItemClickListener(new OnSearchItemClickListener() {
                                @Override
                                public void onItemClick(SearchAdapter.ViewHolder holder, View view, int position) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(Listdocument.get(position).getUrl()));
                                    startActivity(intent);
                                }
                            });
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
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.kakao.com/"+query));
                    startActivity(intent);
                }
            });
         }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
        }
    }
