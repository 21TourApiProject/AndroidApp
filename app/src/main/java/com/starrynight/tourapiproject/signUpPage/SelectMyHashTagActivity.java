package com.starrynight.tourapiproject.signUpPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
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
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @className : SelectMyHashTagActivity.java
 * @description :  회원가입 시 선호 해시태그를 선택하는 페이지 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
public class SelectMyHashTagActivity extends AppCompatActivity {
    List<MyHashTagParams> myHashTagParams = new ArrayList<>();
    String email;
    Long userId;
    ArrayList<String> hashtag;
    String[] clicked = new String[22];

    HashMap<String, Integer> hashTagMap = new HashMap<String, Integer>();
    String[] hashTagName = {"공기 좋은", "깔끔한", "감성적인", "이색적인", "인생샷", "전문적인", "캠핑", "차박", "뚜벅이", "드라이브",
            "반려동물", "한적한", "근교", "도심 속", "연인", "가족", "친구", "혼자", "가성비", "소확행", "럭셔리한", "경치 좋은"};

    Button[] buttons = new Button[22];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_my_hash_tag);

        Intent intent = getIntent();
        email = (String) intent.getSerializableExtra("email"); //전 페이지에서 받아온 사용자 이메일
        userId = (Long) intent.getSerializableExtra("userId"); //전 페이지에서 받아온 사용자 id
        hashtag = (ArrayList<String>) intent.getSerializableExtra("hashtag"); //전 페이지에서 받아온 사용자 해시태그

        for (int i = 0; i < 22; i++) {
            clicked[i] = "";
        }

        if (hashtag != null) {
            int i = 0;
            for (String name : hashTagName) {
                hashTagMap.put(name, i);
                i++;
            }

            for (String h : hashtag) {
                clicked[hashTagMap.get(h)] = h;
            }
        }

        Button ht1 = findViewById(R.id.ht1);
        Button ht2 = findViewById(R.id.ht2);
        Button ht3 = findViewById(R.id.ht3);
        Button ht4 = findViewById(R.id.ht4);
        Button ht5 = findViewById(R.id.ht5);
        Button ht6 = findViewById(R.id.ht6);
        Button ht7 = findViewById(R.id.ht7);
        Button ht8 = findViewById(R.id.ht8);
        Button ht9 = findViewById(R.id.ht9);
        Button ht10 = findViewById(R.id.ht10);
        Button ht11 = findViewById(R.id.ht11);
        Button ht12 = findViewById(R.id.ht12);
        Button ht13 = findViewById(R.id.ht13);
        Button ht14 = findViewById(R.id.ht14);
        Button ht15 = findViewById(R.id.ht15);
        Button ht16 = findViewById(R.id.ht16);
        Button ht17 = findViewById(R.id.ht17);
        Button ht18 = findViewById(R.id.ht18);
        Button ht19 = findViewById(R.id.ht19);
        Button ht20 = findViewById(R.id.ht20);
        Button ht21 = findViewById(R.id.ht21);
        Button ht22 = findViewById(R.id.ht22);
        buttons[0] = ht1;
        buttons[1] = ht2;
        buttons[2] = ht3;
        buttons[3] = ht4;
        buttons[4] = ht5;
        buttons[5] = ht6;
        buttons[6] = ht7;
        buttons[7] = ht8;
        buttons[8] = ht9;
        buttons[9] = ht10;
        buttons[10] = ht11;
        buttons[11] = ht12;
        buttons[12] = ht13;
        buttons[13] = ht14;
        buttons[14] = ht15;
        buttons[15] = ht16;
        buttons[16] = ht17;
        buttons[17] = ht18;
        buttons[18] = ht19;
        buttons[19] = ht20;
        buttons[20] = ht21;
        buttons[21] = ht22;

        for (int i = 0; i < 22; i++) {
            if (!clicked[i].equals("")) {
                buttons[i].setTag("isClicked");
                buttons[i].setBackground(ContextCompat.getDrawable(this, R.drawable.selectmyhashtag_hashtag));
            }
        }

        FrameLayout selectHashTagBack = findViewById(R.id.selectHashTagBack); //뒤로가기

        if (email == null) {
            selectHashTagBack.setVisibility(View.VISIBLE);
        }
        selectHashTagBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button finSelectHt = findViewById(R.id.finSelectHt);
        finSelectHt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < 22; i++) {
                    if (!clicked[i].isEmpty()) {
                        MyHashTagParams myHashTagParam = new MyHashTagParams();
                        myHashTagParam.setHashTagName(clicked[i]);
                        myHashTagParams.add(myHashTagParam);
                    }
                }
                //마이페이지에서 넘어왔다면
                if (userId != null) {
                    Call<Void> call = RetrofitClient.getApiService().changeMyHashTag(userId, myHashTagParams);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("연결실패", t.getMessage());
                        }
                    });

                } else {
                    //선호 해시태그 입력을 위한 post api
                    Call<Long> call = RetrofitClient.getApiService().createMyHashTag(email, myHashTagParams);
                    call.enqueue(new Callback<Long>() {
                        @Override
                        public void onResponse(Call<Long> call, Response<Long> response) {
                            if (response.isSuccessful()) {
                                Long result = response.body();
                                if (result != -1L) {

                                    //앱 내부 저장소에 userId란 이름으로 사용자 id 저장
                                    String fileName = "userId";
                                    String userId = result.toString();
                                    try {
                                        FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                                        fos.write(userId.getBytes());
                                        fos.close();
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    if (email == null) {
                                        finish();
                                    } else {
                                        Intent intent = new Intent(SelectMyHashTagActivity.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP); //액티비티 스택제거
                                        startActivity(intent);
                                    }

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

        if (button.getTag() == "isClicked") {
            button.setTag("");
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.selectmyhashtag_hashtag_non));

            String viewId = view.getResources().getResourceEntryName(view.getId());
            int id = Integer.parseInt(viewId.substring(2));
            clicked[id - 1] = "";
        } else {
            button.setTag("isClicked");
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.selectmyhashtag_hashtag));

            String viewId = view.getResources().getResourceEntryName(view.getId());
            int id = Integer.parseInt(viewId.substring(2));
            clicked[id - 1] = button.getText().toString();
        }
    }

    @Override
    public void onBackPressed() {
        if (email == null) {
            finish();
        }
    }

}