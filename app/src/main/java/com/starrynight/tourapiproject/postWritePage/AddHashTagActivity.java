package com.starrynight.tourapiproject.postWritePage;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.postWritePage.postWriteRetrofit.PostHashTagParams;
import com.starrynight.tourapiproject.postWritePage.postWriteRetrofit.RetrofitClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddHashTagActivity extends AppCompatActivity {
    List<PostHashTagParams>postHashTagParams = new ArrayList<>();
    TextView findHashTag;
    LinearLayout dynamicLayout2;
    int numOfHT = 0;
    String PostHashTags;
    Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hash_tag);
        Intent intent = getIntent();
        userId = (Long) intent.getSerializableExtra("userId");

        findHashTag = findViewById(R.id.findHashTag);
        dynamicLayout2 = (LinearLayout)findViewById(R.id.dynamicLayout2);

        Button addHashTag = findViewById(R.id.addHashTag);
        addHashTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (findHashTag != null){
                    addHashTag(findHashTag.getText().toString());
                }
                PostHashTags = ((TextView)(findViewById(R.id.findHashTag))).getText().toString();
                PostHashTagParams postHashTagParam= new PostHashTagParams(userId,PostHashTags);
                postHashTagParams.add(postHashTagParam);
                Call<Void>call = RetrofitClient.getApiService().createPostHashTag(postHashTagParams);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        System.out.println("해시태그 추가");
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        System.out.println("해시태그 추가 실패");
                    }
                });
                Intent intent1 = new Intent(getApplicationContext(), PostWriteActivity.class);
                startActivity(intent1);
                finish();
            }
        });
        Call <Void> call = RetrofitClient.getApiService().createPostHashTag(postHashTagParams);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    System.out.println("post 성공");
                }else{
                    System.out.println("post 실패");
                }

            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("로그 실패",t.getMessage());
            }
        });

    }

    private void addHashTag(String data) {
        numOfHT ++;
        TextView textView = new TextView(this);
        textView.setText(data);
        //String id = "@+id/hashTag"+ String.valueOf(numOfHT);
        textView.setId(numOfHT);
        textView.setBackground(ContextCompat.getDrawable(this, R.drawable.post_write__edge));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        final int height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
        dynamicLayout2.addView(textView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
    }
}