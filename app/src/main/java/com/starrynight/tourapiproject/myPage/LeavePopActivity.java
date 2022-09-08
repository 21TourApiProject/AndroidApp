package com.starrynight.tourapiproject.myPage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.signUpPage.SignUpActivity;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @className : LeavePopActivity.java
 * @description : 탈퇴 팝업 페이지 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
public class LeavePopActivity extends AppCompatActivity {

    private static final String TAG = "Leave";
    Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_leave_pop);

        Intent intent = getIntent();
        userId = (Long) intent.getSerializableExtra("userId"); //전 페이지에서 받아온 사용자 id
    }

    //탈퇴하기
    public void leave(View v) {

        Call<Boolean> call1 = RetrofitClient.getApiService().checkIsKakao(userId);
        call1.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                Log.d(TAG, "카카오 가입자인지 확인");
                Boolean isKakao = response.body();
                if (isKakao != null) {
                    if (isKakao) {
                        //카카오 회원가입인 경우
                        UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() { //회원탈퇴 실행
                            @Override
                            public void onFailure(ErrorResult errorResult) {
                                //회원탈퇴 실패 시
                                int result = errorResult.getErrorCode();

                                if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                                    Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "회원탈퇴에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onSessionClosed(ErrorResult errorResult) { //로그인 세션이 닫혀있을 시
                                //다시 로그인해달라는 Toast 메세지를 띄우고 로그인 창으로 이동함
                                Toast.makeText(getApplicationContext(), "로그인 세션이 닫혔습니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LeavePopActivity.this, SignUpActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onNotSignedUp() { //가입되지 않은 계정에서 회원탈퇴 요구 시
                                //가입되지 않은 계정이라는 Toast 메세지를 띄우고 로그인 창으로 이동함
                                Toast.makeText(getApplicationContext(), "가입되지 않은 계정입니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LeavePopActivity.this, SignUpActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onSuccess(Long result) { //회원탈퇴에 성공하면
                                //"회원탈퇴에 성공했습니다."라는 Toast 메세지를 띄우고 로그인 창으로 이동함
                                //delete api 호출

                                Call<Void> call3 = RetrofitClient.getApiService().deleteUser(userId);
                                call3.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()) {
                                            File dir = getFilesDir();
                                            File file = new File(dir, "userId");
                                            boolean deleted = file.delete();
                                            Toast.makeText(getApplicationContext(), "회원탈퇴에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(LeavePopActivity.this, SignUpActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Log.e(TAG, "탈퇴하기 실패");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Log.e("연결실패", t.getMessage());
                                    }
                                });

                            }
                        });


                    } else {
                        //일반회원가입인 경우
                        //delete api 호출

                        Call<Void> call2 = RetrofitClient.getApiService().deleteUser(userId);
                        call2.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    File dir = getFilesDir();
                                    File file = new File(dir, "userId");
                                    boolean deleted = file.delete();
                                    Intent intent = new Intent(LeavePopActivity.this, SignUpActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else {
                                    Log.e(TAG, "탈퇴하기 실패");
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("연결실패", t.getMessage());
                            }
                        });
                    }
                }


            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e(TAG, "연결실패");
            }
        });

    }

    //팝업 닫기
    public void closeLeave(View v) {
        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);
        finish();
    }

}
