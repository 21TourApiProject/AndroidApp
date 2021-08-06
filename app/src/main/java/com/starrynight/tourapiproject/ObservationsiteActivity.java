package com.starrynight.tourapiproject;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starrynight.tourapiproject.TouristspotPage.Touristspot_Activity;
import com.starrynight.tourapiproject.postItemPage.OnPostItemClickListener;
import com.starrynight.tourapiproject.postItemPage.Post_point_item_Adapter;
import com.starrynight.tourapiproject.postItemPage.post_point_item;
import com.starrynight.tourapiproject.postPage.PostActivity;

import io.perfmark.Link;

public class ObservationsiteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observationsite);
        RecyclerView recyclerView = findViewById(R.id.obv_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        Post_point_item_Adapter adapter = new Post_point_item_Adapter();
        recyclerView.setAdapter(adapter);

        adapter.addItem(new post_point_item("게시글1","https://cdn.pixabay.com/photo/2018/08/11/20/37/cathedral-3599450_960_720.jpg"));
        adapter.addItem(new post_point_item("게시글2","https://cdn.pixabay.com/photo/2018/07/15/23/22/prague-3540883_960_720.jpg"));
        adapter.addItem(new post_point_item("게시글3","https://cdn.pixabay.com/photo/2019/12/13/07/35/city-4692432_960_720.jpg"));

        adapter.setOnItemClicklistener(new OnPostItemClickListener() {
            @Override
            public void onItemClick(Post_point_item_Adapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                startActivity(intent);
            }
        });

        Button heart_btn = findViewById(R.id.obvheart_btn);
        heart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });
        Button back_btn =findViewById(R.id.obvback_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        Button link_btn = findViewById(R.id.obvlink_btn);
        link_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.kasi.re.kr/kor/index"));
                startActivity(intent);
            }
        });

        ImageView obv_btn = findViewById(R.id.obvimagebutton);
        ImageView obv_btn2 = findViewById(R.id.obvimagebutton2);
        ImageView obv_btn3 = findViewById(R.id.obvimagebutton3);
        ImageView obv_btn4 = findViewById(R.id.obvimagebutton4);
        LinearLayout linearLayout = findViewById(R.id.obvlayout);
        LinearLayout linearLayout2 = findViewById(R.id.obvlayout2);
        LinearLayout linearLayout3 = findViewById(R.id.obvlayout3);
        LinearLayout linearLayout4 = findViewById(R.id.obvlayout4);

        obv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                linearLayout.setVisibility(View.VISIBLE);
                linearLayout2.setVisibility(View.INVISIBLE);
                linearLayout3.setVisibility(View.INVISIBLE);
                linearLayout4.setVisibility(View.INVISIBLE);
                obv_btn.setSelected(true);
                obv_btn2.setSelected(false);
                obv_btn3.setSelected(false);
                obv_btn4.setSelected(false);
            }
        });
        obv_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                linearLayout.setVisibility(View.INVISIBLE);
                linearLayout2.setVisibility(View.VISIBLE);
                linearLayout3.setVisibility(View.INVISIBLE);
                linearLayout4.setVisibility(View.INVISIBLE);
                obv_btn.setSelected(false);
                obv_btn2.setSelected(true);
                obv_btn3.setSelected(false);
                obv_btn4.setSelected(false);
            }
        });
        obv_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                linearLayout.setVisibility(View.INVISIBLE);
                linearLayout2.setVisibility(View.INVISIBLE);
                linearLayout3.setVisibility(View.VISIBLE);
                linearLayout4.setVisibility(View.INVISIBLE);
                obv_btn.setSelected(false);
                obv_btn2.setSelected(false);
                obv_btn3.setSelected(true);
                obv_btn4.setSelected(false);
            }
        });
        obv_btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                linearLayout.setVisibility(View.INVISIBLE);
                linearLayout2.setVisibility(View.INVISIBLE);
                linearLayout3.setVisibility(View.INVISIBLE);
                linearLayout4.setVisibility(View.VISIBLE);
                obv_btn.setSelected(false);
                obv_btn2.setSelected(false);
                obv_btn3.setSelected(false);
                obv_btn4.setSelected(true);
            }
        });

    }
}