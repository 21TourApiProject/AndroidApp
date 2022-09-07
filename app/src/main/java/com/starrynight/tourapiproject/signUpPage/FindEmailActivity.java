package com.starrynight.tourapiproject.signUpPage;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.signUpPage.signUpRetrofit.RetrofitClient;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @className : FindEmailActivity.java
 * @description : 이메일 찾기 페이지 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
public class FindEmailActivity extends AppCompatActivity implements
        View.OnClickListener {
    private static final String TAG = "FindEmail";
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    private FirebaseAuth mAuth;

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private EditText findEmailRealName;
    private EditText mobilePhoneNumber;
    private EditText authCode;
    private Button startAuth;
    private Button resendAuth;
    private Button verify;
    private TextView showEmail;

    private Boolean isSend = false;

    String testPhoneNum = "+16505553333";

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
        setContentView(R.layout.activity_find_email);

        findEmailRealName = findViewById(R.id.findEmailRealName); //이름
        mobilePhoneNumber = findViewById(R.id.findEmailMobilePhoneNumber); //전화번호
        authCode = findViewById(R.id.authCode2); //인증코드
        startAuth = findViewById(R.id.startAuth2); //처음 문자요청
        resendAuth = findViewById(R.id.resendAuth2); //재 문자요청
        verify = findViewById(R.id.verify2); //인증요청
        showEmail = findViewById(R.id.showEmail);

        startAuth.setOnClickListener(this);
        resendAuth.setOnClickListener(this);
        verify.setOnClickListener(this);


        //뒤로 가기
        ImageView findEmailBack = findViewById(R.id.findEmailBack);
        findEmailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted:" + credential);
                mVerificationInProgress = false;
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
                mVerificationInProgress = false;

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(getApplicationContext(), "올바르지 않은 전화번호 입니다.", Toast.LENGTH_SHORT).show();
                    //mobilePhoneNumber.setError("올바르지 않은 전화번호 입니다.");

                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                Log.d(TAG, "token = " + token);
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification(mobilePhoneNumber.getText().toString());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(120L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(120L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .setForceResendingToken(token)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "인증 성공"); //인증 성공하면
                            String realName = findEmailRealName.getText().toString();

                            //이메일 찾기를 위한 get api
                            Call<String> call = RetrofitClient.getApiService().getEmail(realName, mobilePhoneNumber.getText().toString());
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    if (response.isSuccessful()) {
                                        String result = response.body();
                                        if (!result.equals("none")) {
                                            Log.d(TAG, "이메일 찾기 성공");
                                            signOut();
                                            showEmail.setText("이메일: " + result);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "해당 정보와 일치하는 계정이 없습니다.", Toast.LENGTH_SHORT).show();
                                            showEmail.setText("");
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "해당 정보와 일치하는 계정이 없습니다.", Toast.LENGTH_SHORT).show();
                                        showEmail.setText("");
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Log.e("연결실패", t.getMessage());
                                    showEmail.setText("");
                                }
                            });

                        } else {
                            Log.w(TAG, "인증 실패", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(), "인증번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                                //authCode.setError("올바르지 않은 인증번호입니다.");
                                showEmail.setText("");
                            }
                        }
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = mobilePhoneNumber.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(getApplicationContext(), "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            //mobilePhoneNumber.setError("전화번호를 입력해주세요.");
            return false;
        }
        if (phoneNumber.length() != 11) {
            Toast.makeText(getApplicationContext(), "형식에 맞는 전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            //mobilePhoneNumber.setError("형식에 맞는 전화번호를 입력해주세요.");
            return false;
        }
        return true;
    }

    //국제 번호 붙여주는 함수
    private String changePhoneNumber(String phoneNumber) {
        return "+82" + phoneNumber.substring(1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startAuth2:
                if (!validatePhoneNumber()) {
                    Log.e(TAG, "처음 문자요청했는데 전화번호가 이상함");
                    return;
                }
                isSend = true;
                Toast.makeText(getApplicationContext(), "해당 번호로 문자가 발송되었습니다. 2분 안에 인증번호를 입력해주세요.", Toast.LENGTH_LONG).show();
                startPhoneNumberVerification(changePhoneNumber(mobilePhoneNumber.getText().toString()));
                startAuth.setVisibility(View.GONE);
                resendAuth.setVisibility(View.VISIBLE);
                break;

            case R.id.verify2:
                String code = authCode.getText().toString();

                if (findEmailRealName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    //findEmailRealName.setError("이름을 입력해주세요.");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(getApplicationContext(), "인증번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    //authCode.setError("인증번호를 입력해주세요.");
                    return;
                }
                if (!isSend) {
                    Toast.makeText(getApplicationContext(), "인증 요청을 먼저 해주세요.", Toast.LENGTH_SHORT).show();
                    //authCode.setError("인증 요청을 먼저 해주세요.");
                    return;
                }
                Log.d(TAG, code + "인증코드 맞는지 확인들어감 ");
                showEmail.setText("잠시만 기다려주세요...");
                verifyPhoneNumberWithCode(mVerificationId, code);
                break;

            case R.id.resendAuth2:
                Toast.makeText(getApplicationContext(), "해당 번호로 문자가 재발송되었습니다. 2분 안에 인증번호를 입력해주세요.", Toast.LENGTH_LONG).show();
                resendVerificationCode(changePhoneNumber(mobilePhoneNumber.getText().toString()), mResendToken);
                break;
        }
    }
}