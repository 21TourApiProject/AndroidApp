package com.starrynight.tourapiproject.signUpPage;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import androidx.core.content.ContextCompat;

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
import com.starrynight.tourapiproject.signUpPage.signUpRetrofit.KakaoUserParams;
import com.starrynight.tourapiproject.signUpPage.signUpRetrofit.RetrofitClient;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KakaoPhoneAuthActivity extends AppCompatActivity implements
        View.OnClickListener {
    private static final String TAG = "KakaoPhoneAuthActivity";
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";
    private static final int SELECT_HASH_TAG = 0;

    private FirebaseAuth mAuth;

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private EditText mobilePhoneNumber;
    private TextView phoneGuide; //전화번호 칸 바로 밑에 글칸
    private EditText authCode;
    private Button startAuth;
    private Button resendAuth;
    private Button verify;

    private Button ageLimit;
    Boolean isAge;
    Button phoneAgree;
    private Boolean isPhoneAgree = true;

    String testPhoneNum = "+16505553333";

    KakaoUserParams userParams;
    private Boolean isSend = false;

    String phoneNumber;
    private Boolean isPhoneEmpty = true; //전화번호이 비어있는지
    private Boolean isNotPhone = false; //올바른 전화번호 형식이 아닌지
    private Boolean isPhoneDuplicate = true; //전화번호이 중복인지

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
        setContentView(R.layout.activity_kakao_phone_auth);

//        TextView skip_btn = findViewById(R.id.kko_pass);
//        skip_btn.setVisibility(View.VISIBLE);
//        skip_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!isAge) {
//                    Toast.makeText(getApplicationContext(), "만 14세 미만은 이용하실 수 없습니다.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                Call<Void> call = RetrofitClient.getApiService().kakaoSignUp(userParams);
//                call.enqueue(new Callback<Void>() {
//                    @Override
//                    public void onResponse(Call<Void> call, Response<Void> response) {
//                        if (response.isSuccessful()) {
//                            Log.d("kakaoSignup", "회원가입 성공");
//                            signOut();
//
//                            //선호 해시태그 선택 창으로 전환
//                            Intent intent = new Intent(KakaoPhoneAuthActivity.this, SelectMyHashTagActivity.class);
//                            intent.putExtra("email", userParams.getEmail());
//                            startActivityForResult(intent, SELECT_HASH_TAG);
//                        } else {
//                            Log.d("kakaoSignup", "회원가입 실패");
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Void> call, Throwable t) {
//                        Log.e("연결실패", t.getMessage());
//                    }
//                });
//            }
//        });

        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

        Intent intent = getIntent();
        userParams = (KakaoUserParams) intent.getSerializableExtra("userParams");

        mobilePhoneNumber = findViewById(R.id.kko_mobilePhoneNumber); //전화번호
        phoneGuide = findViewById(R.id.kko_phoneGuide);
        authCode = findViewById(R.id.kko_authCode); //인증코드
        startAuth = findViewById(R.id.kko_startAuth); //처음 문자요청
        resendAuth = findViewById(R.id.kko_resendAuth); //재 문자요청
        verify = findViewById(R.id.kko_verify); //인증요청

        isAge = false;
        startAuth.setOnClickListener(this);
        resendAuth.setOnClickListener(this);
        verify.setOnClickListener(this);

        ImageView authBack = findViewById(R.id.kko_authBack);
        authBack.setOnClickListener(new View.OnClickListener() {
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
                //signInWithPhoneAuthCredential(credential); (주의)이거 살리면 오류남
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
                mVerificationInProgress = false;

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    mobilePhoneNumber.setError("전화번호 형식이 올바르지 않습니다.");

                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };

        ageLimit = findViewById(R.id.kko_ageLimit);
        ageLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAge) {
                    ageLimit.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree_non));
                    isAge = false;
                } else {
                    ageLimit.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree));
                    isAge = true;
                }
            }
        });

//        phoneAgree = findViewById(R.id.kko_phoneAgree);
//        phoneAgree.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isPhoneAgree){
//                    phoneAgree.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree_non));
//                    isPhoneAgree = false;
//                } else{
//                    phoneAgree.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree));
//                    isPhoneAgree = true;
//                }
//            }
//        });


        //전화번호 칸에 글씨가 입력됨에 따라 실시간으로 phoneGuide 뜨게
        mobilePhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력란에 변화가 있을 때
                showPhoneGuide(s);
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                showPhoneGuide(arg0);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });
    }

    private void showPhoneGuide(CharSequence s) {
        String text = s.toString();

        if (text.isEmpty()) {
            phoneGuide.setText("전화번호을 입력해주세요.");
            isPhoneEmpty = true;
        } else if (!isCorrectPhoneRule(text)) {
            phoneGuide.setText("전화번호 형식이 올바르지 않습니다.");
            isPhoneEmpty = false;
            isNotPhone = true;
        } else {
            phoneGuide.setText("");

            //전화번호이 중복인지 아닌지를 위한 get api
            Call<Boolean> call = RetrofitClient.getApiService().checkDuplicateMobilePhoneNumber(text);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        Boolean result = response.body();
                        if (result) {
                            phoneGuide.setText("사용가능한 전화번호입니다.");
                            phoneNumber = text;
                            isPhoneEmpty = false;
                            isNotPhone = false;
                            isPhoneDuplicate = false;
                        } else if (!result) {
                            phoneGuide.setText("이미 가입된 전화번호입니다.");
                            isPhoneEmpty = false;
                            isNotPhone = false;
                            isPhoneDuplicate = true;
                        }
                    } else {
                        phoneGuide.setText("오류가 발생했습니다. 다시 시도해주세요.");
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.e("연결실패", t.getMessage());
                    phoneGuide.setText("오류가 발생했습니다. 다시 시도해주세요.");
                }
            });
        }
    }

    //전화번호가 11자리가 아니면 형식 틀린것으로 간주
    private boolean isCorrectPhoneRule(String text) {
        return text.length() == 11;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mVerificationInProgress) {
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

                            //회원가입을 위한 post api
                            if (isPhoneAgree)
                                userParams.setMobilePhoneNumber(mobilePhoneNumber.getText().toString());
                            else
                                userParams.setMobilePhoneNumber(null);
                            Call<Void> call = RetrofitClient.getApiService().kakaoSignUp(userParams);
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        signOut();

                                        //선호 해시태그 선택 창으로 전환
                                        Intent intent = new Intent(KakaoPhoneAuthActivity.this, SelectMyHashTagActivity.class);
                                        intent.putExtra("email", userParams.getEmail());
                                        startActivityForResult(intent, SELECT_HASH_TAG);
                                    } else {
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

    //국제 번호 붙여주는 함수
    private String changePhoneNumber(String phoneNumber) {
        return "+82" + phoneNumber.substring(1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.kko_startAuth:
                if (isPhoneEmpty) {
                    Toast.makeText(getApplicationContext(), "전화번호을 입력해주세요.", Toast.LENGTH_LONG).show();
                    break;
                } else if (isNotPhone) {
                    Toast.makeText(getApplicationContext(), "전화번호 형식이 올바르지 않습니다.", Toast.LENGTH_LONG).show();
                    break;
                } else if (isPhoneDuplicate) {
                    Toast.makeText(getApplicationContext(), "이미 가입된 전화번호입니다.", Toast.LENGTH_LONG).show();
                    break;
                } else {
                    isSend = true;
                    Toast.makeText(getApplicationContext(), "해당 번호로 인증 문자가 발송되었습니다.", Toast.LENGTH_LONG).show();
                    startPhoneNumberVerification(changePhoneNumber(mobilePhoneNumber.getText().toString()));
                    startAuth.setVisibility(View.GONE);
                    resendAuth.setVisibility(View.VISIBLE);
                    break;
                }

            case R.id.kko_verify:
                String code = authCode.getText().toString();

                if (!isAge) {
                    Toast.makeText(getApplicationContext(), "만 14세 미만은 이용하실 수 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(code)) {
                    authCode.setError("인증번호를 입력해주세요.");
                    return;
                }
                if (!isSend) {
                    authCode.setError("인증 요청을 먼저 해주세요.");
                    return;
                }
                verifyPhoneNumberWithCode(mVerificationId, code);
                break;

            case R.id.kko_resendAuth:
                if (isPhoneEmpty) {
                    Toast.makeText(getApplicationContext(), "전화번호을 입력해주세요.", Toast.LENGTH_LONG).show();
                    break;
                } else if (isNotPhone) {
                    Toast.makeText(getApplicationContext(), "전화번호 형식이 올바르지 않습니다.", Toast.LENGTH_LONG).show();
                    break;
                } else if (isPhoneDuplicate) {
                    Toast.makeText(getApplicationContext(), "이미 가입된 전화번호입니다.", Toast.LENGTH_LONG).show();
                    break;
                } else {
                    Toast.makeText(getApplicationContext(), "해당 번호로 인증 문자가 재발송되었습니다.", Toast.LENGTH_LONG).show();
                    resendVerificationCode(changePhoneNumber(mobilePhoneNumber.getText().toString()), mResendToken);
                    break;
                }
        }
    }

//    @Override //선호 해시태그 선택하다말고 뒤로 돌아오면
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == SELECT_HASH_TAG){
//            //회원정보 삭제
//            Call<Void> call = RetrofitClient.getApiService().cancelSignUp(userParams.getEmail());
//            call.enqueue(new Callback<Void>() {
//                @Override
//                public void onResponse(Call<Void> call, Response<Void> response) {
//                    if(response.isSuccessful()){
//                        System.out.println("회원정보 삭제 성공");
//                    } else{
//                        System.out.println("회원정보 삭제 실패");
//                    }
//                }
//                @Override
//                public void onFailure(Call<Void> call, Throwable t) {
//                    Log.e("연결실패", t.getMessage());
//                }
//            });
//
////            Intent intent = getIntent();
////            finish();
////            startActivity(intent);
//
//        }
//    }

}