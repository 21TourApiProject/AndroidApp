package com.starrynight.tourapiproject.myPage;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @className : ChangePasswordActivity.java
 * @description : 비밀번호 변경 페이지 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
public class ChangePasswordActivity extends AppCompatActivity {

    private static final String TAG = "ChangePassword";

    Long userId;
    EditText originPwd;
    EditText newPwd;
    EditText newPwdCheck;
    TextView newPwdGuide;
    TextView newPwdCheckGuide;

    String pwd = "";
    String pwdCheck = "";
    Boolean isPwd = false; //형식에 맞는 비밀번호인지
    Boolean isSame = false; //같은 비밀번호인지

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Intent intent = getIntent();
        userId = (Long) intent.getSerializableExtra("userId"); //전 페이지에서 받아온 사용자 id

        originPwd = findViewById(R.id.originPwd);
        newPwd = findViewById(R.id.newPwd);
        newPwdCheck = findViewById(R.id.newPwdCheck);
        newPwdGuide = findViewById(R.id.newPwdGuide);
        newPwdCheckGuide = findViewById(R.id.newPwdCheckGuide);

        //뒤로 가기
        ImageView passwordBack = findViewById(R.id.passwordBack);
        passwordBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        newPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력란에 변화가 있을 때
                pwd = s.toString();
                showPwdGuide(pwd);
                if (!pwdCheck.isEmpty()) {
                    showPwdCheckGuide(pwd, pwdCheck);
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                pwd = arg0.toString();
                showPwdGuide(pwd);
                if (!pwdCheck.isEmpty()) {
                    showPwdCheckGuide(pwd, pwdCheck);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });


        newPwdCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력란에 변화가 있을 때
                pwdCheck = s.toString();
                showPwdCheckGuide(pwd, pwdCheck);
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                pwdCheck = arg0.toString();
                showPwdCheckGuide(pwd, pwdCheck);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });

        //완료 버튼
        Button pwdSubmit = findViewById(R.id.pwdSubmit);
        pwdSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oriPwd = originPwd.getText().toString();
                if (oriPwd.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (pwd.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (!isPwd) {
                    Toast.makeText(getApplicationContext(), "비밀번호 형식이 올바르지 않습니다", Toast.LENGTH_SHORT).show();
                } else if (!isSame) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                } else {
                    //비밀번호 변경을 위한 put api
                    Call<Boolean> call = RetrofitClient.getApiService().updatePassword(userId, oriPwd, pwd);
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if (response.isSuccessful()) {
                                Boolean result = response.body();
                                if (result) {
                                    //비밀번호 성공적으로 변경되면 이전 페이지로
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.d(TAG, "중복 체크 실패");
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Log.e("연결실패", t.getMessage());
                        }
                    });
                }
            }
        });
    }

    //비밀번호 두개가 맞는지 실시간으로
    private void showPwdCheckGuide(String pwd, String pwdCheck) {
        if (!pwd.equals(pwdCheck)) {
            isSame = false;
            newPwdCheckGuide.setText("비밀번호가 일치하지 않습니다.");
        } else {
            isSame = true;
            newPwdCheckGuide.setText("");
        }
    }

    //비밀번호 규칙 맞는지 실시간으로
    private void showPwdGuide(String pwd) {
        if (!isCorrectPwdRule(pwd)) {
            newPwdGuide.setText("비밀번호 형식이 올바르지 않습니다. (특수문자, 영문, 숫자 조합해서 8자 이상)");
            isPwd = false;
        } else {
            newPwdGuide.setText("");
            isPwd = true;
        }
    }

    //비밀번호 규칙 함수
    private Boolean isCorrectPwdRule(String pwd) {
        String pattern = "^.*(?=^.{8,}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!\\\"\\[\\]\\\\\\/#$%&'()*+,.;:<=>?@^_`{|}^`_~-]).*$";
        return Pattern.matches(pattern, pwd);
    }

}