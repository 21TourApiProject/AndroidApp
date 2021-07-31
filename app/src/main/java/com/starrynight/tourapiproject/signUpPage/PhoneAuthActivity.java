package com.starrynight.tourapiproject.signUpPage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneAuthActivity extends AppCompatActivity implements
        View.OnClickListener {
    private static final String TAG = "PhoneAuthActivity";
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    private FirebaseAuth mAuth;

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private EditText mobilePhoneNumber;
    private EditText authCode;

    private Button startAuth;
    private Button resendAuth;
    private Button verify;

    String testPhoneNum = "+16505553333";

    UserParams userParams;
    String userId;
    boolean isDuplicate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

        Intent intent = getIntent();
        userParams = (UserParams) intent.getSerializableExtra("userParams");

        mobilePhoneNumber = findViewById(R.id.mobilePhoneNumber); //전화번호
        authCode = findViewById(R.id.authCode); //인증코드
        startAuth = findViewById(R.id.startAuth); //처음 문자요청
        resendAuth = findViewById(R.id.resendAuth); //재 문자요청
        verify = findViewById(R.id.verify); //인증요청

        startAuth.setOnClickListener(this);
        resendAuth.setOnClickListener(this);
        verify.setOnClickListener(this);
        System.out.println("here is listener create maybe.....?");

        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted:" + credential);

                mVerificationInProgress = false;
                //signInWithPhoneAuthCredential(credential); (주의)이거 살리면 오류남
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
                mVerificationInProgress = false;

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    mobilePhoneNumber.setError("올바르지 않은 전화번호 입니다.");

                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                System.out.println("token = " + token);
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("onStart 들어옴");

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
                        .setPhoneNumber(testPhoneNum)
                        .setTimeout(90L, TimeUnit.SECONDS)
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
                        .setPhoneNumber(testPhoneNum)
                        .setTimeout(90L, TimeUnit.SECONDS)
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

                           //회원가입을 위한 post api
                           userParams.setMobilePhoneNumber(mobilePhoneNumber.getText().toString());
                            Call<Void> call = RetrofitClient.getApiService().signUp(userParams);
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if(response.isSuccessful()){
                                        System.out.println("회원가입 성공");
                                        signOut();

                                        //선호 해시태그 선택 창으로 전환
                                        Intent intent = new Intent(PhoneAuthActivity.this, SelectMyHashTagActivity.class);
                                        intent.putExtra("mobilePhoneNumber", mobilePhoneNumber.getText().toString());
                                        startActivity(intent);
                                    } else{
                                        System.out.println("회원가입 실패");
                                    }
                                }
                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Log.e("연결실패", t.getMessage());
                                }
                            });

                        } else {
                            Log.w(TAG, "인증 실패", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                authCode.setError("올바르지 않은 인증번호입니다.");
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
            mobilePhoneNumber.setError("전화번호를 입력해주세요.");
            return false;
        }

        //전화번호가 중복인지 아닌지를 위한 get api
        Call<Boolean> call = RetrofitClient.getApiService().checkDuplicateMobilePhoneNumber(phoneNumber);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    System.out.println("중복 체크 성공");

                    Boolean result = response.body();
                    if (result == true){
                        System.out.println("사용가능한 전화번호");
                        isDuplicate = false;
                    } else if (result == false){
                        mobilePhoneNumber.setError("이미 가입된 전화번호입니다.");
                    }
                } else{
                    System.out.println("중복 체크 실패");
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
        return isDuplicate;
    }

    //국제 번호 붙여주는 함수
    private String changePhoneNumber(String phoneNumber){
        return "+82" + phoneNumber.substring(1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startAuth:
                if (!validatePhoneNumber()) {
                    System.out.println("처음 문자요청했는데 전화번호가 이상함");
                    return;
                }
                System.out.println("처음 문자요청됨");
                System.out.println("전화번호 = " + changePhoneNumber(mobilePhoneNumber.getText().toString()));
                startPhoneNumberVerification(changePhoneNumber(mobilePhoneNumber.getText().toString()));
                break;

            case R.id.verify:
                String code = authCode.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    authCode.setError("인증번호를 입력해주세요.");
                    return;
                }
                System.out.println("인증코드 맞는지 확인들어감 " + code);
                verifyPhoneNumberWithCode(mVerificationId, code);
                break;

            case R.id.resendAuth:
                System.out.println("문자 재요청됨");
                System.out.println("전화번호 = " + changePhoneNumber(mobilePhoneNumber.getText().toString()));
                resendVerificationCode(changePhoneNumber(mobilePhoneNumber.getText().toString()), mResendToken);
                break;
        }
    }
}