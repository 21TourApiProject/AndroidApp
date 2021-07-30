package com.starrynight.tourapiproject.signUpPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.postPage.OnRelatePostItemClickListener;
import com.starrynight.tourapiproject.postPage.RelatePost;
import com.starrynight.tourapiproject.postPage.RelatePostAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectHashTagActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_hash_tag);

        RecyclerView recyclerView = findViewById(R.id.selectHashTag);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        HashTagAdapter adapter = new HashTagAdapter();

        //전체 해시태그 불러오는 get api
        Call<List<HashTagResult>> call = RetrofitClient.getApiService().getAllHashTag();
        call.enqueue(new Callback<List<HashTagResult>>() {
            @Override
            public void onResponse(Call<List<HashTagResult>> call, Response<List<HashTagResult>> response) {
                if(response.isSuccessful()){
                    List<HashTagResult>hashTags = response.body();

                    for(HashTagResult hashTag: hashTags){
                        adapter.addItem(new HashTag(hashTag.getHashTagId(), hashTag.getHashTagName()));
                    }
                    recyclerView.setAdapter(adapter);
                    System.out.println("해시태그 불러오기 성공");
                } else{
                    System.out.println("해시태그 불러오기 실패");
                }
            }
            @Override
            public void onFailure(Call<List<HashTagResult>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });

        adapter.setOnHashTagItemClickListener(new OnHashTagItemClickListener() {
            @Override
            public void onItemClick(HashTagAdapter.ViewHolder holder, View view, int position) {
                HashTag item = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "클릭됨 : " + item.getName() + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}