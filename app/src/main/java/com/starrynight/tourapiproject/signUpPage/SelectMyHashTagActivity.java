package com.starrynight.tourapiproject.signUpPage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.signUpPage.signUpRetrofit.MyHashTagParams;
import com.starrynight.tourapiproject.signUpPage.signUpRetrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectMyHashTagActivity extends AppCompatActivity {
    List<MyHashTagParams> myHashTagParams = new ArrayList<>();
    String mobilePhoneNumber;
    String[] clicked = new String[22];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_my_hash_tag);

        Intent intent = getIntent();
        mobilePhoneNumber = (String) intent.getSerializableExtra("mobilePhoneNumber"); //전 페이지에서 받아온 사용자 전화번호

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
                        myHashTagParam.setMobilePhoneNumber(mobilePhoneNumber);
                        myHashTagParams.add(myHashTagParam);
                    }
                }
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

        if(button.getTag() == "isClicked"){
            button.setTag("");
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.select_hash_tag_unclicked));

            String viewId = view.getResources().getResourceEntryName(view.getId());
            int id = Integer.parseInt(viewId.substring(2));
            clicked[id-1] = "";
        }
        else{
            button.setTag("isClicked");
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.select_hash_tag_clicked));

            String viewId = view.getResources().getResourceEntryName(view.getId());
            int id = Integer.parseInt(viewId.substring(2));
            clicked[id-1] = button.getText().toString();
        }
    }
}