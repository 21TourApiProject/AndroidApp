package com.starrynight.tourapiproject.postWritePage;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.postWritePage.postWriteRetrofit.PostHashTagParams;
import com.starrynight.tourapiproject.postWritePage.postWriteRetrofit.PostImageParams;
import com.starrynight.tourapiproject.postWritePage.postWriteRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.signUpPage.SelectMyHashTagActivity;
import com.starrynight.tourapiproject.starPage.StarActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    String postContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hash_tag);
        findHashTag = findViewById(R.id.findHashTag);
        dynamicLayout2 = (LinearLayout)findViewById(R.id.dynamicLayout2);
        Intent intent= getIntent();
        postContent = (String) intent.getSerializableExtra("postContent");
        List<PostImageParams>postImageParams = (List<PostImageParams>)intent.getSerializableExtra("postImageParams");

        Button addHashTag = findViewById(R.id.addHashTag);
        addHashTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (findHashTag != null){
                    addHashTag(findHashTag.getText().toString());
                }
                PostHashTags = ((TextView)(findViewById(R.id.findHashTag))).getText().toString();
                PostHashTagParams postHashTagParam= new PostHashTagParams();
                postHashTagParam.setHashTagName(PostHashTags);
                postHashTagParams.add(postHashTagParam);
//                Call<Long>call =RetrofitClient.getApiService().createPostHashTag(,postHashTagParams);
//                call.enqueue(new Callback<Long>() {
//                    @Override
//                    public void onResponse(Call<Long> call, Response<Long> response) {
//                        if (response.isSuccessful()) {
//                            Long result =response.body();
//                            if (result != -1L){
//                            System.out.println("해시태그 생성");
//                                //앱 내부 저장소에 postId란 이름으로 사용자 id 저장
//                                String fileName = "postId";
//                                String postId = result.toString();
//                                try{
//                                    FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
//                                    fos.write(postId.getBytes());
//                                    fos.close();
//                                } catch (FileNotFoundException e) {
//                                    e.printStackTrace();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }else{System.out.println("시스템 오류");}
//                        }else {System.out.println("해시태그 생성 실패");}
//                    }
//
//                    @Override
//                    public void onFailure(Call<Long> call, Throwable t) {
//                        System.out.println("해시태그 생성 실패2");
//                    }
//                });
//                Call<Void>call1 = RetrofitClient.getApiService().createPostImage(,postImageParams);
//                call1.enqueue(new Callback<Void>() {
//                    @Override
//                    public void onResponse(Call<Void> call, Response<Void> response) {
//                        if (response.isSuccessful()) {
//                            System.out.println("이미지 업로드 성공");
//                        }else {System.out.println("이미지 업로드 실패");}
//                    }
//
//                    @Override
//                    public void onFailure(Call<Void> call, Throwable t) {
//                        System.out.println("이미지 업로드 실패 2");
//                    }
//                });
                intent.putExtra("postHashTagParams", (Serializable) postHashTagParams);
                setResult(3,intent);
                finish();
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