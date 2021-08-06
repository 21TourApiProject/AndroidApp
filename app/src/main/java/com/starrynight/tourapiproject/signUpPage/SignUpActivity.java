package com.starrynight.tourapiproject.signUpPage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.kakao.auth.Session;

import androidx.appcompat.app.AppCompatActivity;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.signUpPage.signUpRetrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private SessionCallback sessionCallback = new SessionCallback();
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //로그인 버튼
        Button logIn = findViewById(R.id.logIn);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText) (findViewById(R.id.logInEmail))).getText().toString();
                String password = ((EditText) (findViewById(R.id.logInPwd))).getText().toString();
                //로그인을 위한 get api
                Call<Long> call = RetrofitClient.getApiService().logIn(email, password);
                call.enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {
                        if (response.isSuccessful()) {
                            Long result = response.body();
                            if (result != -1L) {
                                System.out.println("로그인 성공");

                            } else {
                                Toast.makeText(getApplicationContext(), "로그인 정보가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            System.out.println("오류가 발생했습니다. 다시 시도해주세요.");
                        }
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });
            }
        });

        //이메일 찾기 버튼
        TextView findEmail = findViewById(R.id.findEmail);
        findEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, FindEmailActivity.class);
                startActivity(intent);
            }
        });

        //비밀번호 찾기 버튼
        TextView findPwd = findViewById(R.id.findPwd);
        findPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, FindPasswordActivity.class);
                startActivity(intent);
            }
        });


        //일반 회원가입
        Button generalSignUp = findViewById(R.id.generalSignUp);
        generalSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, GeneralSingUpActivity.class);
                startActivity(intent);
            }
        });

        //카카오 회원가입
        ImageButton kakaoSignUp = findViewById(R.id.kakaoSignUp);
        session = Session.getCurrentSession();
        session.addCallback(sessionCallback);
        kakaoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}