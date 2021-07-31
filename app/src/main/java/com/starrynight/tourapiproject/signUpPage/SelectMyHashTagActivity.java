package com.starrynight.tourapiproject.signUpPage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.starrynight.tourapiproject.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectMyHashTagActivity extends AppCompatActivity {
    List<MyHashTagParams> myHashTagParams = new ArrayList<>();
    String mobilePhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_my_hash_tag);

        Intent intent = getIntent();
        mobilePhoneNumber = (String) intent.getSerializableExtra("mobilePhoneNumber"); //전 페이지에서 받아온 사용자 전화번호

        Button finSelectHt = findViewById(R.id.finSelectHt);
        finSelectHt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //post api
                Call<Void> call = RetrofitClient.getApiService().createMyHashTag(myHashTagParams);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            System.out.println("post 성공");
                        } else{
                            System.out.println("post 실패");
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });
            }
        });
    }

    public void clickEvent(View view) {
        Button button = (Button) view;

        MyHashTagParams myHashTagParam = new MyHashTagParams();
        myHashTagParam.setHashTagName(button.getText().toString());
        System.out.println("선택한 해시태그 = " + button.getText().toString());
        myHashTagParam.setMobilePhoneNumber(mobilePhoneNumber);
        myHashTagParams.add(myHashTagParam);
    }
}