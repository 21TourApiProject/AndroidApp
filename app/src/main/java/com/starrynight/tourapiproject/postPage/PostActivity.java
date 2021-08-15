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
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.postPage.postRetrofit.Post;
import com.starrynight.tourapiproject.postPage.postRetrofit.PostImage;
import com.starrynight.tourapiproject.postPage.postRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.postWritePage.postWriteRetrofit.PostParams;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.BitmapFactory.decodeFile;

public class PostActivity extends AppCompatActivity{
    private ViewPager2 sliderViewPager;
    private LinearLayout indicator;
    Post post;
    Long postId;
    TextView postTitle;
    TextView postContent;
    List<PostImage>postImages;
    ImageView postImage;

    private String[] images = new String[10];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Intent intent = getIntent();
        PostParams postParams = (PostParams)intent.getSerializableExtra("postParams");
        for (int i =0;i<images.length;i++){
            images[i]="";
        }

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
        Call<List<String>>call = RetrofitClient.getApiService().getPostImage(postId);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    System.out.println("이미지 업로드 성공"+response.body());
                    List<String> result = response.body();
                    System.out.println(result);
                    for (int i=0; i< images.length;i++){
                            for (String name :result){
                                images[i]=name;
                            }
                    }
                }else{System.out.println("이미지 업로드 실패");}
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                System.out.println("이미지 업로드 실패 2");
            }
        });

        postTitle =findViewById(R.id.observeSpot);
        postContent=findViewById(R.id.postContent);

        sliderViewPager = findViewById(R.id.slider);
        indicator = findViewById(R.id.indicator);

        sliderViewPager.setOffscreenPageLimit(3);
        sliderViewPager.setAdapter(new ImageSliderAdapter(this, images));

        sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });

        setupIndicators(images.length);

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
