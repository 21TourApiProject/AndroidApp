package com.starrynight.tourapiproject.myPage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.myPage.myWish.post.MyPost;
import com.starrynight.tourapiproject.myPage.myWish.post.MyPostAdapter;
import com.starrynight.tourapiproject.myPage.myWish.post.OnMyPostItemClickListener;
import com.starrynight.tourapiproject.postPage.PostActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPostActivity extends AppCompatActivity {

    private static final int POST = 103;
    Long userId;
    
    List<MyPost> postResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);

        Intent intent = getIntent();
        userId = (Long) intent.getSerializableExtra("userId"); //전 페이지에서 받아온 사용자 id

        //뒤로 가기
        Button myPostBack = findViewById(R.id.myPostBack);
        myPostBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RecyclerView myPostRecyclerview = findViewById(R.id.myPosts);
        LinearLayoutManager myPostLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myPostRecyclerview.setLayoutManager(myPostLayoutManager);
        myPostRecyclerview.setHasFixedSize(true);
        postResult = new ArrayList<>();

        Call<List<MyPost>> call = RetrofitClient.getApiService().getMyWishPost(userId);
        call.enqueue(new Callback<List<MyPost>>() {
            @Override
            public void onResponse(Call<List<MyPost>> call, Response<List<MyPost>> response) {
                if (response.isSuccessful()) {
                    postResult = response.body();

                    MyPostAdapter myPostAdapter = new MyPostAdapter(postResult, MyPostActivity.this);
                    myPostRecyclerview.setAdapter(myPostAdapter);
                    myPostAdapter.setOnMyWishPostItemClickListener(new OnMyPostItemClickListener() {
                        @Override
                        public void onItemClick(MyPostAdapter.ViewHolder holder, View view, int position) {
                            MyPost item = myPostAdapter.getItem(position);
                            Intent intent = new Intent(MyPostActivity.this, PostActivity.class);
                            intent.putExtra("postId", item.getItemId());
                            startActivityForResult(intent, POST);
                        }
                    });
                } else {
                    System.out.println("내 찜 게시물 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<List<MyPost>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
    }
}