package com.starrynight.tourapiproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.starrynight.tourapiproject.postItemPage.Post_point_item_Adapter;
import com.starrynight.tourapiproject.postItemPage.post_point_item;

public class Touristspot_Activity extends AppCompatActivity {

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


        Button button = findViewById(R.id.heart_button);
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
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
