package com.starrynight.tourapiproject.myPage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

public class MyWishActivity extends AppCompatActivity {

    private static final int WISH = 102;

    Long userId;

    Button myWishOb;
    Button myWishTp;
    Button myWishPost;
    List<MyWishObTp> obResult;
    List<MyWishObTp> tpResult;
    List<MyPost> postResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wish);

        Intent intent = getIntent();
        userId = (Long) intent.getSerializableExtra("userId"); //전 페이지에서 받아온 사용자 id

        myWishOb = findViewById(R.id.myWishOb);
        myWishTp = findViewById(R.id.myWishTp);
        myWishPost = findViewById(R.id.myWishPost);

        //뒤로 가기
        Button myWishBack = findViewById(R.id.myWishBack);
        myWishBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RecyclerView myWishRecyclerview = findViewById(R.id.myWishs);
        LinearLayoutManager myWishLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myWishRecyclerview.setLayoutManager(myWishLayoutManager);
        myWishRecyclerview.setHasFixedSize(true);
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
                    System.out.println("내 찜 관측지 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<List<MyWishObTp>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });


        //내 찜 관측지
        myWishOb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            System.out.println("내 찜 관측지 불러오기 실패");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<MyWishObTp>> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });
            }
        });


        //내 찜 관광지
        myWishTp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            System.out.println("내 찜 관광지 불러오기 실패");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<MyWishObTp>> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });
            }
        });


        //내 찜 게시물
        myWishPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                    intent.putExtra("postId", item.getItemId());
                                    startActivityForResult(intent, WISH);
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
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == WISH){
            //액티비티 새로고침

        }
    }
}