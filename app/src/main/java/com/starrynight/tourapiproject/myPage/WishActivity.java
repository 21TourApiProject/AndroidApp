package com.starrynight.tourapiproject.myPage;

import android.app.FragmentTransaction;
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
import com.starrynight.tourapiproject.myPage.myWish.obTp.MyWishObTp;
import com.starrynight.tourapiproject.myPage.myWish.obTp.MyWishObTpAdapter;
import com.starrynight.tourapiproject.myPage.myWish.post.MyWishPost;
import com.starrynight.tourapiproject.myPage.myWish.obTp.OnMyWishObTpItemClickListener;
import com.starrynight.tourapiproject.myPage.myWish.post.OnMyWishPostItemClickListener;
import com.starrynight.tourapiproject.myPage.myWish.post.MyWishPostAdapter;
import com.starrynight.tourapiproject.observationPage.ObservationsiteActivity;
import com.starrynight.tourapiproject.postPage.PostActivity;
import com.starrynight.tourapiproject.touristPointPage.TouristPointActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishActivity extends AppCompatActivity {

    private static final int WISH = 102;

    Long userId;

    Button myWishOb;
    Button myWishTp;
    Button myWishPost;
    List<MyWishObTp> obResult;
    List<MyWishObTp> tpResult;
    List<MyWishPost> postResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish);

        Intent intent = getIntent();
        userId = (Long) intent.getSerializableExtra("userId"); //전 페이지에서 받아온 사용자 id

        myWishOb = findViewById(R.id.myWishOb);
        myWishTp = findViewById(R.id.myWishTp);
        myWishPost = findViewById(R.id.myWishPost);


        RecyclerView myWishsRecyclerview = findViewById(R.id.myWishs);
        LinearLayoutManager myWishsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myWishsRecyclerview.setLayoutManager(myWishsLayoutManager);
        myWishsRecyclerview.setHasFixedSize(true);
        obResult = new ArrayList<>();
        tpResult = new ArrayList<>();
        postResult = new ArrayList<>();


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

                            MyWishObTpAdapter myWishObAdapter = new MyWishObTpAdapter(tpResult, WishActivity.this);
                            myWishsRecyclerview.setAdapter(myWishObAdapter);
                            myWishObAdapter.setOnMyWishObTpItemClickListener(new OnMyWishObTpItemClickListener() {
                                @Override
                                public void onItemClick(MyWishObTpAdapter.ViewHolder holder, View view, int position) {
                                    MyWishObTp item = myWishObAdapter.getItem(position);
                                    Intent intent = new Intent(WishActivity.this, ObservationsiteActivity.class);
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

                            MyWishObTpAdapter myWishObAdapter = new MyWishObTpAdapter(obResult, WishActivity.this);
                            myWishsRecyclerview.setAdapter(myWishObAdapter);
                            myWishObAdapter.setOnMyWishObTpItemClickListener(new OnMyWishObTpItemClickListener() {
                                @Override
                                public void onItemClick(MyWishObTpAdapter.ViewHolder holder, View view, int position) {
                                    MyWishObTp item = myWishObAdapter.getItem(position);
                                    Intent intent = new Intent(WishActivity.this, TouristPointActivity.class);
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
                Call<List<MyWishPost>> call = RetrofitClient.getApiService().getMyWishPost(userId);
                call.enqueue(new Callback<List<MyWishPost>>() {
                    @Override
                    public void onResponse(Call<List<MyWishPost>> call, Response<List<MyWishPost>> response) {
                        if (response.isSuccessful()) {
                            postResult = response.body();

                            MyWishPostAdapter myWishPostAdapter = new MyWishPostAdapter(postResult, WishActivity.this);
                            myWishsRecyclerview.setAdapter(myWishPostAdapter);
                            myWishPostAdapter.setOnMyWishPostItemClickListener(new OnMyWishPostItemClickListener() {
                                @Override
                                public void onItemClick(MyWishPostAdapter.ViewHolder holder, View view, int position) {
                                    MyWishPost item = myWishPostAdapter.getItem(position);
                                    Intent intent = new Intent(WishActivity.this, PostActivity.class);
                                    intent.putExtra("postId", item.getItemId());
                                    startActivityForResult(intent, WISH);
                                }
                            });
                        } else {
                            System.out.println("내 찜 게시물 불러오기 실패");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<MyWishPost>> call, Throwable t) {
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