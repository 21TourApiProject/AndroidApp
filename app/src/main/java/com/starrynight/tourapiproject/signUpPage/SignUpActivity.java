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

import com.kakao.auth.AuthType;
import com.kakao.auth.Session;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
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
        kakaoSignUp.setOnClickListener(v->{
            if (Session.getCurrentSession().checkAndImplicitOpen()) {
                Log.d("KakaoLogin", "onClick: 로그인 세션 살아있음");
                //창이안뜸, 아직 로그인 세션 살아있음
                Intent intent = new Intent(SignUpActivity.this, KakaoPhoneAuthActivity.class);
                intent.putExtra("userParams", sessionCallback.requestMe());
//                startActivity(intent);
            } else {
                Log.d("KakaoLogin", "로그인 세션 만료");
                //카카오 로그인 창 뜸
                session.open(AuthType.KAKAO_LOGIN_ALL, SignUpActivity.this);

            }
        });

        Button logout = findViewById(R.id.logout_tmp);

        logout.setOnClickListener(v -> {
            Log.d("KakaoLogin", "onCreate:click ");
            UserManagement.getInstance()
                    .requestLogout(new LogoutResponseCallback() {
                        @Override
                        public void onSessionClosed(ErrorResult errorResult) {
                            super.onSessionClosed(errorResult);
                            Log.d("KakaoLogin", "onSessionClosed: "+errorResult.getErrorMessage());

                        }
                        @Override
                        public void onCompleteLogout() {
                            if (sessionCallback != null) {
                                Session.getCurrentSession().removeCallback(sessionCallback);
                            }
                            Log.d("KakaoLogin", "onCompleteLogout:logout ");
                        }
                    });
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            Intent intent = new Intent(SignUpActivity.this, KakaoPhoneAuthActivity.class);
            intent.putExtra("userParams", sessionCallback.requestMe());
//            startActivity(intent);
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}