package com.starrynight.tourapiproject.myPage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
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

/**
 * @className : MyPostActivity.java
 * @description : 내가 쓴 게시물 페이지 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
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
        ImageView myPostBack = findViewById(R.id.myPostBack);
        myPostBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RecyclerView myPostRecyclerview = findViewById(R.id.myPosts);
        LinearLayoutManager myPostLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myPostRecyclerview.setLayoutManager(myPostLayoutManager);
        postResult = new ArrayList<>();

        Call<List<MyPost>> call = RetrofitClient.getApiService().getMyPost(userId);
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
                            intent.putExtra("postId", item.getPostId());
                            startActivityForResult(intent, POST);
                        }
                    });
                } else {
                    Log.d("myPost", "내 게시물 찜 가져오기 실패");
                }
            }

            @Override
            public void onFailure(Call<List<MyPost>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == POST) {
            //액티비티 새로고침
            Intent intent = getIntent();
            finish();
            overridePendingTransition(0, 0);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }
}