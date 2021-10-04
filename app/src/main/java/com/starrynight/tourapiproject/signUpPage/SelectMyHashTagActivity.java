package com.starrynight.tourapiproject.signUpPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.signUpPage.signUpRetrofit.MyHashTagParams;
import com.starrynight.tourapiproject.signUpPage.signUpRetrofit.RetrofitClient;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectMyHashTagActivity extends AppCompatActivity {
    List<MyHashTagParams> myHashTagParams = new ArrayList<>();
    String email;
    Long userId;
    String[] clicked = new String[22];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_my_hash_tag);

        Intent intent = getIntent();
        email = (String) intent.getSerializableExtra("email"); //전 페이지에서 받아온 사용자 이메일
        userId = (Long) intent.getSerializableExtra("userId"); //전 페이지에서 받아온 사용자 id

        for(int i=0; i<22; i++){
            clicked[i]="";
        }

        Button finSelectHt = findViewById(R.id.finSelectHt);
        finSelectHt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0; i<22; i++){
                    if (!clicked[i].isEmpty()){
                        MyHashTagParams myHashTagParam = new MyHashTagParams();
                        myHashTagParam.setHashTagName(clicked[i]);
                        myHashTagParams.add(myHashTagParam);
                    }
                }
                //마이페이지에서 넘어왔다면
                if(userId != null){
                    System.out.println("마이페이지에서 넘어옴");
                    Call<Void> call = RetrofitClient.getApiService().changeMyHashTag(userId, myHashTagParams);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                finish();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("연결실패", t.getMessage());
                        }
                    });

                } else{
                    //선호 해시태그 입력을 위한 post api
                    Call<Long> call = RetrofitClient.getApiService().createMyHashTag(email, myHashTagParams);
                    call.enqueue(new Callback<Long>() {
                        @Override
                        public void onResponse(Call<Long> call, Response<Long> response) {
                            if (response.isSuccessful()) {
                                Long result = response.body();
                                if (result != -1L) {
                                    System.out.println("선호 해시태그 선택 성공");

                                    //앱 내부 저장소에 userId란 이름으로 사용자 id 저장
                                    String fileName = "userId";
                                    String userId = result.toString();
                                    try{
                                        FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                                        fos.write(userId.getBytes());
                                        fos.close();
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    Intent intent = new Intent(SelectMyHashTagActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP); //액티비티 스택제거
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Long> call, Throwable t) {
                            Log.e("연결실패", t.getMessage());
                        }
                    });
                }

            }
        });
    }

    public void clickEvent(View view) {
        Button button = (Button) view;

        if(button.getTag() == "isClicked"){
            button.setTag("");
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.selectmyhashtag_hashtag_non));

            String viewId = view.getResources().getResourceEntryName(view.getId());
            int id = Integer.parseInt(viewId.substring(2));
            clicked[id-1] = "";
        }
        else{
            button.setTag("isClicked");
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.selectmyhashtag_hashtag));

            String viewId = view.getResources().getResourceEntryName(view.getId());
            int id = Integer.parseInt(viewId.substring(2));
            clicked[id-1] = button.getText().toString();
        }
    }

    @Override public void onBackPressed() {}

}