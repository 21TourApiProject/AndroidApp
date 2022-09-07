package com.starrynight.tourapiproject.myPage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.myPage.myWish.obtp.MyWishObTp;
import com.starrynight.tourapiproject.myPage.myWish.obtp.MyWishObTpAdapter;
import com.starrynight.tourapiproject.myPage.myWish.obtp.OnMyWishObTpItemClickListener;
import com.starrynight.tourapiproject.myPage.myWish.post.MyPost;
import com.starrynight.tourapiproject.myPage.myWish.post.MyPostAdapter;
import com.starrynight.tourapiproject.myPage.myWish.post.OnMyPostItemClickListener;
import com.starrynight.tourapiproject.observationPage.ObservationsiteActivity;
import com.starrynight.tourapiproject.postPage.PostActivity;
import com.starrynight.tourapiproject.touristPointPage.TouristPointActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @className : MyWishActivity.java
 * @description : 북마크 페이지 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
public class MyWishActivity extends AppCompatActivity {

    private static final int WISH = 102;

    Long userId;

    TextView myWishOb;
    TextView myWishTp;
    TextView myWishPost;
    ImageView myWishObLine;
    ImageView myWishTpLine;
    ImageView myWishPostLine;
    RecyclerView myWishRecyclerview;

    List<MyWishObTp> obResult; //찜 관측지
    List<MyWishObTp> tpResult; //찜 관광지
    List<MyPost> postResult; //찜 게시물

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wish);

        Intent intent = getIntent();
        userId = (Long) intent.getSerializableExtra("userId"); //전 페이지에서 받아온 사용자 id

        myWishOb = findViewById(R.id.myWishOb);
        myWishTp = findViewById(R.id.myWishTp);
        myWishPost = findViewById(R.id.myWishPost);
        myWishObLine = findViewById(R.id.myWishObLine);
        myWishTpLine = findViewById(R.id.myWishTpLine);
        myWishPostLine = findViewById(R.id.myWishPostLine);
        myWishRecyclerview = findViewById(R.id.myWishs);

        //뒤로 가기
        ImageView myWishBack = findViewById(R.id.myWishBack);
        myWishBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayoutManager myWishLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myWishRecyclerview.setLayoutManager(myWishLayoutManager);
        obResult = new ArrayList<>();
        tpResult = new ArrayList<>();
        postResult = new ArrayList<>();


        Call<List<MyWishObTp>> call = RetrofitClient.getApiService().getMyWishObservation(userId);
        call.enqueue(new Callback<List<MyWishObTp>>() {
            @Override
            public void onResponse(Call<List<MyWishObTp>> call, Response<List<MyWishObTp>> response) {
                if (response.isSuccessful()) {
                    tpResult = response.body();
                    MyWishObTpAdapter myWishObAdapter = new MyWishObTpAdapter(tpResult, MyWishActivity.this);
                    myWishRecyclerview.setAdapter(myWishObAdapter);
                    myWishObAdapter.setOnMyWishObTpItemClickListener(new OnMyWishObTpItemClickListener() {
                        @Override
                        public void onItemClick(MyWishObTpAdapter.ViewHolder holder, View view, int position) {
                            MyWishObTp item = myWishObAdapter.getItem(position);
                            Intent intent = new Intent(MyWishActivity.this, ObservationsiteActivity.class);
                            intent.putExtra("observationId", item.getItemId());
                            startActivityForResult(intent, WISH);
                        }
                    });
                } else {
                    Log.d("myWishOb", "내 찜 관측지 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<List<MyWishObTp>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WISH) {
            //액티비티 새로고침
            Intent intent = getIntent();
            finish();
            overridePendingTransition(0, 0);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    public void clickOb(View v) {
        myWishObLine.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.mywish_tapclick));
        myWishTpLine.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.mywish_tap));
        myWishPostLine.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.mywish_tap));
        Call<List<MyWishObTp>> call = RetrofitClient.getApiService().getMyWishObservation(userId);
        call.enqueue(new Callback<List<MyWishObTp>>() {
            @Override
            public void onResponse(Call<List<MyWishObTp>> call, Response<List<MyWishObTp>> response) {
                if (response.isSuccessful()) {
                    tpResult = response.body();

                    MyWishObTpAdapter myWishObAdapter = new MyWishObTpAdapter(tpResult, MyWishActivity.this);
                    myWishRecyclerview.setAdapter(myWishObAdapter);
                    myWishObAdapter.setOnMyWishObTpItemClickListener(new OnMyWishObTpItemClickListener() {
                        @Override
                        public void onItemClick(MyWishObTpAdapter.ViewHolder holder, View view, int position) {
                            MyWishObTp item = myWishObAdapter.getItem(position);
                            Intent intent = new Intent(MyWishActivity.this, ObservationsiteActivity.class);
                            intent.putExtra("observationId", item.getItemId());
                            startActivityForResult(intent, WISH);
                        }
                    });
                } else {
                    Log.d("myWishOb", "내 찜 관측지 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<List<MyWishObTp>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
    }

    public void clickTp(View v) {
        myWishObLine.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.mywish_tap));
        myWishTpLine.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.mywish_tapclick));
        myWishPostLine.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.mywish_tap));
        Call<List<MyWishObTp>> call = RetrofitClient.getApiService().getMyWishTouristPoint(userId);
        call.enqueue(new Callback<List<MyWishObTp>>() {
            @Override
            public void onResponse(Call<List<MyWishObTp>> call, Response<List<MyWishObTp>> response) {
                if (response.isSuccessful()) {
                    obResult = response.body();

                    MyWishObTpAdapter myWishObAdapter = new MyWishObTpAdapter(obResult, MyWishActivity.this);
                    myWishRecyclerview.setAdapter(myWishObAdapter);
                    myWishObAdapter.setOnMyWishObTpItemClickListener(new OnMyWishObTpItemClickListener() {
                        @Override
                        public void onItemClick(MyWishObTpAdapter.ViewHolder holder, View view, int position) {
                            MyWishObTp item = myWishObAdapter.getItem(position);
                            Intent intent = new Intent(MyWishActivity.this, TouristPointActivity.class);
                            intent.putExtra("contentId", item.getItemId());
                            startActivityForResult(intent, WISH);
                        }
                    });
                } else {
                    Log.d("myWishTp", "내 찜 관광지 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<List<MyWishObTp>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
    }

    public void clickPost(View v) {
        myWishObLine.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.mywish_tap));
        myWishTpLine.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.mywish_tap));
        myWishPostLine.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.mywish_tapclick));
        Call<List<MyPost>> call = RetrofitClient.getApiService().getMyWishPost(userId);
        call.enqueue(new Callback<List<MyPost>>() {
            @Override
            public void onResponse(Call<List<MyPost>> call, Response<List<MyPost>> response) {
                if (response.isSuccessful()) {
                    postResult = response.body();

                    MyPostAdapter myPostAdapter = new MyPostAdapter(postResult, MyWishActivity.this);
                    myWishRecyclerview.setAdapter(myPostAdapter);
                    myPostAdapter.setOnMyWishPostItemClickListener(new OnMyPostItemClickListener() {
                        @Override
                        public void onItemClick(MyPostAdapter.ViewHolder holder, View view, int position) {
                            MyPost item = myPostAdapter.getItem(position);
                            Intent intent = new Intent(MyWishActivity.this, PostActivity.class);
                            intent.putExtra("postId", item.getPostId());
                            startActivityForResult(intent, WISH);
                        }
                    });
                } else {
                    Log.d("myPost", "내 찜 게시물 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<List<MyPost>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
    }
}