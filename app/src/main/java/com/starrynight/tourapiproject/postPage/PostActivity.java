package com.starrynight.tourapiproject.postPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.postItemPage.OnPostPointItemClickListener;
import com.starrynight.tourapiproject.postItemPage.PostHashTagItem;
import com.starrynight.tourapiproject.postItemPage.PostHashTagItemAdapter;
import com.starrynight.tourapiproject.postItemPage.Post_item_adapter;
import com.starrynight.tourapiproject.postItemPage.Post_point_item_Adapter;
import com.starrynight.tourapiproject.postItemPage.post_item;
import com.starrynight.tourapiproject.postItemPage.post_point_item;
import com.starrynight.tourapiproject.postPage.postRetrofit.Post;
import com.starrynight.tourapiproject.postPage.postRetrofit.PostImage;
import com.starrynight.tourapiproject.postPage.postRetrofit.PostObservePoint;
import com.starrynight.tourapiproject.postPage.postRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.postWritePage.postWriteRetrofit.PostParams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity{
    private ViewPager2 sliderViewPager;
    private LinearLayout indicator;
    Post post;
    Long postId;
    TextView postTitle;
    TextView postContent;
    TextView postTime;
    TextView postDate;
    TextView postObservePoint;
    List<PostImage>postImages;
    List<String>postHashTags;
    ImageView postImage;
    String[] filename2= new String[10];
    String[] relatefilename = new String[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Intent intent = getIntent();
        PostParams postParams = (PostParams)intent.getSerializableExtra("postParams");

//      앱 내부저장소에 저장된 게시글 아이디 가져오기
        String fileName = "postId";
        try{
            FileInputStream fis = openFileInput(fileName);
            String line = new BufferedReader(new InputStreamReader(fis)).readLine();
            postId = Long.parseLong(line);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } System.out.println("postId = " + postId);

        sliderViewPager = findViewById(R.id.slider);
        indicator = findViewById(R.id.indicator);
        //게시물 이미지 가져오는 get api
        Call<List<String>>call = RetrofitClient.getApiService().getPostImage(postId);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    System.out.println("이미지 업로드 성공"+response.body());
                    List<String> result = response.body();
                    ArrayList<String> FileName = new ArrayList<>();
                    for (int i=0;i<result.size();i++){
                        filename2[i]=result.get(i);
                        System.out.println(filename2[i]);
                    }
                    for (int i = 0; i <filename2.length;i++){
                        if(filename2[i] != null) {
                            System.out.println("https://starry-night.s3.ap-northeast-2.amazonaws.com/" + filename2[i]);
                            FileName.add("https://starry-night.s3.ap-northeast-2.amazonaws.com/" + filename2[i]);
                        }
                    }
                    sliderViewPager.setAdapter(new ImageSliderAdapter(getApplicationContext(),FileName));

                    sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                        @Override
                        public void onPageSelected(int position) {
                            super.onPageSelected(position);
                            setCurrentIndicator(position);
                        }
                    });
                    setupIndicators(FileName.size());
                }else{System.out.println("이미지 업로드 실패");}
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                System.out.println("이미지 업로드 실패 2");
            }
        });

        postTitle =findViewById(R.id.postTitleText);
        postContent=findViewById(R.id.postContent);
        postTime = findViewById(R.id.postTime);
        postDate = findViewById(R.id.postDate);
        postObservePoint = findViewById(R.id.postObservation);
        //게시물 정보가져오는 get api
        Call<Post>call1 = RetrofitClient.getApiService().getPost(postId);
        call1.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()){
                    System.out.println("게시물 정보 가져옴");
                    post = response.body();
                    postTitle.setText(post.getPostTitle());
                    postContent.setText(post.getPostContent());
                    postTime.setText(post.getTime());
                    postDate.setText(post.getYearDate());

                    //관련 게시물
                    Call<List<String>>call2 = RetrofitClient.getApiService().getRelatePostImageList(post.getPostObservePointId());
                    call2.enqueue(new Callback<List<String>>() {
                        @Override
                        public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                            if (response.isSuccessful()) {
                                System.out.println("관련 게시물 이미지 업로드");
                                List<String> relateImageList = response.body();
                                RecyclerView recyclerView = findViewById(R.id.relatePost);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                Post_point_item_Adapter adapter = new Post_point_item_Adapter();
                                for (int i=0;i<relateImageList.size();i++){
                                    relatefilename[i]=relateImageList.get(i);
                                    System.out.println(relatefilename[i]);
                                }
                                for (int i = 0; i <relatefilename.length;i++){
                                    if(relatefilename[i] != null) {
                                        System.out.println(relatefilename[i]);
                                        adapter.addItem(new post_point_item("","https://starry-night.s3.ap-northeast-2.amazonaws.com/" + relatefilename[i]));
                                    }
                                }
                                recyclerView.setAdapter(adapter);
                                adapter.setOnItemClicklistener(new OnPostPointItemClickListener() {
                                    @Override
                                    public void onItemClick(Post_point_item_Adapter.ViewHolder holder, View view, int position) {
                                        post_point_item item = adapter.getItem(position);
                                        Intent intent1 = new Intent(PostActivity.this,PostActivity.class);
                                        startActivity(intent1);
                                    }
                                });

                            }else{System.out.println("관련 게시물 이미지 실패");}
                        }

                        @Override
                        public void onFailure(Call<List<String>> call, Throwable t) {
                            System.out.println("관련 게시물 이미지 업로드 실패2");
                        }
                    });
                }else{System.out.println("게시물 실패");}
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                System.out.println("게시물 실패 2");
            }
        });
        Call<PostObservePoint>call2 = RetrofitClient.getApiService().getPostObservePoint(postId);
        call2.enqueue(new Callback<PostObservePoint>() {
            @Override
            public void onResponse(Call<PostObservePoint> call, Response<PostObservePoint> response) {
                if (response.isSuccessful()){
                    System.out.println("게시물 관측지 가져옴");
                    PostObservePoint postObservePoint1 = response.body();
                    postObservePoint.setText(postObservePoint1.getObservePointName());
                }else{System.out.println("게시물 관측지 실패");}
            }

            @Override
            public void onFailure(Call<PostObservePoint> call, Throwable t) {
                System.out.println("게시물 관측지 실패2");
            }
        });

        Call<List<String>>call3 = RetrofitClient.getApiService().getPostHashTagName(postId);
        call3.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()){
                    System.out.println("게시물 해시태그 가져옴"+response.body());
                    postHashTags = response.body();
                    RecyclerView hashTagRecyclerView = findViewById(R.id.hashTagRecyclerView);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
                    hashTagRecyclerView.setLayoutManager(linearLayoutManager);
                    PostHashTagItemAdapter adapter2 = new PostHashTagItemAdapter();
                    for (int i=0;i<postHashTags.size();i++){
                    adapter2.addItem(new PostHashTagItem(postHashTags.get(i)));
                    }
                    hashTagRecyclerView.setAdapter(adapter2);
                }else {System.out.println("게시물 해시태그 실패");}
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                System.out.println("게시물 해시태그 실패 2");
            }
        });

        Button button = findViewById(R.id.like);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });

        RecyclerView recyclerView = findViewById(R.id.relatePost);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        RelatePostAdapter adapter = new RelatePostAdapter();

        recyclerView.setAdapter(adapter);

        adapter.setOnPersonItemClickListener(new OnRelatePostItemClickListener() {
            @Override
            public void onItemClick(RelatePostAdapter.ViewHolder holder, View view, int position) {
                RelatePost item = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "클릭됨 : " + item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.post__indicator_inactive));
            indicators[i].setLayoutParams(params);
            indicator.addView(indicators[i]);
        }
        setCurrentIndicator(0);
    }

    private void setCurrentIndicator(int position) {
        int childCount = indicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) indicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.post__indicator_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.post__indicator_inactive
                ));
            }
        }
    }
}
