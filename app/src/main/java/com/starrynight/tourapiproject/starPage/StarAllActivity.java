package com.starrynight.tourapiproject.starPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.starPage.starItemPage.OnStarItemClickListener;
import com.starrynight.tourapiproject.starPage.starItemPage.StarItem;
import com.starrynight.tourapiproject.starPage.starItemPage.StarViewAdapter;

public class StarAllActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_all);

        //recyclerView 설정
        recyclerView = findViewById(R.id.full_cel_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        StarViewAdapter adapter = new StarViewAdapter();
        recyclerView.setAdapter(adapter);

        adapter.addItem(new StarItem("별자리1", "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));
        adapter.addItem(new StarItem("별자리2", "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));
        adapter.addItem(new StarItem("별자리3", "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));
        adapter.addItem(new StarItem("별자리4", "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));
        adapter.addItem(new StarItem("별자리5", "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));
        adapter.addItem(new StarItem("별자리6", "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));
        adapter.addItem(new StarItem("별자리7", "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));
        adapter.addItem(new StarItem("별자리8", "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnStarItemClickListener() {
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