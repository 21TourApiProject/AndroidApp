package com.starrynight.tourapiproject.starPage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.starPage.starItemPage.OnStarItemClickListener;
import com.starrynight.tourapiproject.starPage.starItemPage.StarItem;
import com.starrynight.tourapiproject.starPage.starItemPage.StarViewAdapter;
import com.starrynight.tourapiproject.starPage.starPageRetrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StarAllActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    StarViewAdapter constAdapter;
    CardView starCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_all);

        //recyclerView 설정
        recyclerView = findViewById(R.id.all_const_recycler);
        starCardView = findViewById(R.id.star_card_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        constAdapter = new StarViewAdapter();
        recyclerView.setAdapter(constAdapter);

        // 이름, 이미지를 불러오기 위한 get api
        Call<List<StarItem>> starItemCall = RetrofitClient.getApiService().getConstellation();
        starItemCall.enqueue(new Callback<List<StarItem>>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<List<StarItem>> call, Response<List<StarItem>> response) {
                if (response.isSuccessful()) {
                    List<StarItem> result = response.body();
                    for (StarItem si : result) {
                        constAdapter.addItem(new StarItem(si.getConstId(), si.getConstName(), si.getConstEng()));
                    }
                    recyclerView.setAdapter(constAdapter);
                    recyclerView.addItemDecoration(new StarRecyclerViewWidth(24));
                    recyclerView.addItemDecoration(new StarRecyclerViewHeight(16));
                } else {
                }
            }

            @Override
            public void onFailure(Call<List<StarItem>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });

        // item 클릭 시 해당 아이템 constId 넘겨주기
        constAdapter.setOnItemClickListener(new OnStarItemClickListener() {
            @Override
            public void onItemClick(StarViewAdapter.ViewHolder holder, View view, int position) {
                StarItem item = constAdapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(), StarActivity.class);
                intent.putExtra("constName", item.getConstName());
                //Log.d("constId출력", item.getConstId().toString());
                startActivity(intent);
            }
        });

        // 뒤로가기 버튼 클릭 이벤트
        ImageView backBtn = findViewById(R.id.all_star_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}