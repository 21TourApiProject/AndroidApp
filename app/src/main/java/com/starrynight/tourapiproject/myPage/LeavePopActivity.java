package com.starrynight.tourapiproject.myPage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.signUpPage.SignUpActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeavePopActivity extends AppCompatActivity {

    Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_leave_pop);

        Intent intent = getIntent();
        userId = (Long) intent.getSerializableExtra("userId"); //전 페이지에서 받아온 사용자 id
    }

    //탈퇴하기
    public void leave(View v){
        //delete api 호출
        Call<Void> call = RetrofitClient.getApiService().deleteUser(userId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(LeavePopActivity.this, SignUpActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    System.out.println("탈퇴하기 실패");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
    }

    //팝업 닫기
    public void closeLeave(View v){
        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);
        finish();
    }

}
