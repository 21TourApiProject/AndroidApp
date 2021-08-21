package com.starrynight.tourapiproject.starPage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_all);

        //recyclerView 설정
        recyclerView = findViewById(R.id.all_const_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        constAdapter = new StarViewAdapter();
        recyclerView.setAdapter(constAdapter);
//
//        constAdapter.addItem(new StarItem(0L,"별자리1", "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));
//        constAdapter.addItem(new StarItem(1L,"별자리2", "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));
//        constAdapter.addItem(new StarItem(2L,"별자리3", "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));
//        constAdapter.addItem(new StarItem(3L,"별자리4", "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));
//        constAdapter.addItem(new StarItem(4L,"별자리5", "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));
//        constAdapter.addItem(new StarItem(5L,"별자리6", "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));
//        constAdapter.addItem(new StarItem(6L,"별자리7", "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));
//        constAdapter.addItem(new StarItem(7L,"별자리8", "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));

        // 이름, 이미지를 불러오기 위한 get api
        Call<List<StarItem>> starItemCall = RetrofitClient.getApiService().getConstellation();
        starItemCall.enqueue(new Callback<List<StarItem>>() {
            @Override
            public void onResponse(Call<List<StarItem>> call, Response<List<StarItem>> response) {
                if (response.isSuccessful()) {
                    List<StarItem> result = response.body();
                    for (StarItem si : result) {
                        constAdapter.addItem(new StarItem(si.getConstId(), si.getConstName(), si.getConstImage()));
                    }
                    recyclerView.setAdapter(constAdapter);
                } else {
                    System.out.println("모든 별자리 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<List<StarItem>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });

        constAdapter.setOnItemClickListener(new OnStarItemClickListener() {
            @Override
            public void onItemClick(StarViewAdapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(getApplicationContext(), StarActivity.class);
                startActivity(intent);
            }
        });

        // 뒤로가기 버튼 클릭 이벤트
        ImageButton backBtn = findViewById(R.id.all_star_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}