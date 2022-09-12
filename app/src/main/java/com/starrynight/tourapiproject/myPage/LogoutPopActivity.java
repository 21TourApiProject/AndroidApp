package com.starrynight.tourapiproject.myPage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.signUpPage.SignUpActivity;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @className : LogoutPopActivity.java
 * @description : 로그아웃 팝업 페이지 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
public class LogoutPopActivity extends AppCompatActivity {

    private static final String TAG = "Logout";
    Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_logout_pop);

        Intent intent = getIntent();
        userId = (Long) intent.getSerializableExtra("userId"); //전 페이지에서 받아온 사용자 id
    }

    //로그아웃
    public void logout(View v) {

        Call<Boolean> call = RetrofitClient.getApiService().checkIsKakao(userId);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                Log.d(TAG, "카카오 가입자인지 확인");
                Boolean isKakao = response.body();
                if (isKakao != null) {
                    if (isKakao == null || isKakao) {
                        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                            @Override
                            public void onCompleteLogout() {
                                //로그아웃 성공 시 동작
                                File dir = getFilesDir();
                                File file = new File(dir, "userId");
                                boolean deleted = file.delete();
                                Intent intent = new Intent(LogoutPopActivity.this, SignUpActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
                    } else {
                        File dir = getFilesDir();
                        File file = new File(dir, "userId");
                        boolean deleted = file.delete();
                        Intent intent = new Intent(LogoutPopActivity.this, SignUpActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                } else {
                    Log.e(TAG, "사용자 타입설정 안됨");
                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e(TAG, "연결실패");
            }
        });


    }

    //팝업 닫기
    public void closeLogout(View v) {
        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);
        finish();
    }
}
