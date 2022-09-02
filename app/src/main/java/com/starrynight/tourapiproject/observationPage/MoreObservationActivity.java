package com.starrynight.tourapiproject.observationPage;

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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
* @className : MoreObservationActivity
* @description : 관측지 관련 게시물 더보기 페이지입니다.
* @modification : jinhyeok (2022-08-16) 주석 수정
* @author : 2022-08-16
* @date : jinhyeok
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   jinhyeok      2022-08-16       주석 수정

 */
public class MoreObservationActivity extends AppCompatActivity {
    private static final int POST = 103;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_observation);

        Intent intent = getIntent();
        Long observationId= (Long)intent.getSerializableExtra("observationId");
        Button myPostBack = findViewById(R.id.morePost_back_btn);
        myPostBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.morePostRecycler);
        LinearLayoutManager myPostLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(myPostLayoutManager);

        Call<List<MyPost>>call = RetrofitClient.getApiService().getRelatePost(observationId);
        call.enqueue(new Callback<List<MyPost>>() {
            @Override
            public void onResponse(Call<List<MyPost>> call, Response<List<MyPost>> response) {
               if (response.isSuccessful()){
                   Log.d("relatePost","관련 게시물 업로드");
                   List<MyPost> result = response.body();
                   MyPostAdapter relatePostAdapter = new MyPostAdapter(result, MoreObservationActivity.this);
                   recyclerView.setAdapter(relatePostAdapter);
                   relatePostAdapter.setOnMyWishPostItemClickListener(new OnMyPostItemClickListener() {
                       @Override
                       public void onItemClick(MyPostAdapter.ViewHolder holder, View view, int position) {
                           MyPost item = relatePostAdapter.getItem(position);
                           Log.d("postId",""+item.getPostId());
                           Intent intent = new Intent(MoreObservationActivity.this, PostActivity.class);
                           intent.putExtra("postId", item.getPostId());
                           startActivityForResult(intent, POST);
                       }
                   });
               }else{Log.d("relatePost","관련 게시물 업로드 실패");}

            }

            @Override
            public void onFailure(Call<List<MyPost>> call, Throwable t) {
                 Log.d("relatePost","인터넷 오류");
            }
        });
    }
}