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
    private TextView phoneGuide; //???????????? ??? ?????? ?????? ??????
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
    private Boolean isPhoneEmpty = true; //??????????????? ???????????????
    private Boolean isNotPhone = false; //????????? ???????????? ????????? ?????????
    private Boolean isPhoneDuplicate = true; //??????????????? ????????????

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
//                    Toast.makeText(getApplicationContext(), "??? 14??? ????????? ???????????? ??? ????????????.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                Call<Void> call = RetrofitClient.getApiService().kakaoSignUp(userParams);
//                call.enqueue(new Callback<Void>() {
//                    @Override
//                    public void onResponse(Call<Void> call, Response<Void> response) {
//                        if (response.isSuccessful()) {
//                            Log.d("kakaoSignup", "???????????? ??????");
//                            signOut();
//
//                            //?????? ???????????? ?????? ????????? ??????
//                            Intent intent = new Intent(KakaoPhoneAuthActivity.this, SelectMyHashTagActivity.class);
//                            intent.putExtra("email", userParams.getEmail());
//                            startActivityForResult(intent, SELECT_HASH_TAG);
//                        } else {
//                            Log.d("kakaoSignup", "???????????? ??????");
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Void> call, Throwable t) {
//                        Log.e("????????????", t.getMessage());
//                    }
//                });
//            }
//        });

        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

        Intent intent = getIntent();
        userParams = (KakaoUserParams) intent.getSerializableExtra("userParams");

        mobilePhoneNumber = findViewById(R.id.kko_mobilePhoneNumber); //????????????
        phoneGuide = findViewById(R.id.kko_phoneGuide);
        authCode = findViewById(R.id.kko_authCode); //????????????
        startAuth = findViewById(R.id.kko_startAuth); //?????? ????????????
        resendAuth = findViewById(R.id.kko_resendAuth); //??? ????????????
        verify = findViewById(R.id.kko_verify); //????????????

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
                //signInWithPhoneAuthCredential(credential); (??????)?????? ????????? ?????????
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
                mVerificationInProgress = false;

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    mobilePhoneNumber.setError("???????????? ????????? ???????????? ????????????.");

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


        //???????????? ?????? ????????? ???????????? ?????? ??????????????? phoneGuide ??????
        mobilePhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ???????????? ????????? ?????? ???
                showPhoneGuide(s);
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // ????????? ????????? ???
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
            phoneGuide.setText("??????????????? ??????????????????.");
            isPhoneEmpty = true;
        } else if (!isCorrectPhoneRule(text)) {
            phoneGuide.setText("???????????? ????????? ???????????? ????????????.");
            isPhoneEmpty = false;
            isNotPhone = true;
        } else {
            phoneGuide.setText("");

            //??????????????? ???????????? ???????????? ?????? get api
            Call<Boolean> call = RetrofitClient.getApiService().checkDuplicateMobilePhoneNumber(text);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        Boolean result = response.body();
                        if (result) {
                            phoneGuide.setText("??????????????? ?????????????????????.");
                            phoneNumber = text;
                            isPhoneEmpty = false;
                            isNotPhone = false;
                            isPhoneDuplicate = false;
                        } else if (!result) {
                            phoneGuide.setText("?????? ????????? ?????????????????????.");
                            isPhoneEmpty = false;
                            isNotPhone = false;
                            isPhoneDuplicate = true;
                        }
                    } else {
                        phoneGuide.setText("????????? ??????????????????. ?????? ??????????????????.");
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.e("????????????", t.getMessage());
                    phoneGuide.setText("????????? ??????????????????. ?????? ??????????????????.");
                }
            });
        }
    }

    //??????????????? 11????????? ????????? ?????? ??????????????? ??????
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
                            Log.d(TAG, "?????? ??????"); //?????? ????????????

                            //??????????????? ?????? post api
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

                                        //?????? ???????????? ?????? ????????? ??????
                                        Intent intent = new Intent(KakaoPhoneAuthActivity.this, SelectMyHashTagActivity.class);
                                        intent.putExtra("email", userParams.getEmail());
                                        startActivityForResult(intent, SELECT_HASH_TAG);
                                    } else {
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Log.e("????????????", t.getMessage());
                                }
                            });
                        } else {
                            Log.w(TAG, "?????? ??????", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                authCode.setError("???????????? ?????? ?????????????????????.");
                            }
                        }
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
    }

    //?????? ?????? ???????????? ??????
    private String changePhoneNumber(String phoneNumber) {
        return "+82" + phoneNumber.substring(1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.kko_startAuth:
                if (isPhoneEmpty) {
                    Toast.makeText(getApplicationContext(), "??????????????? ??????????????????.", Toast.LENGTH_LONG).show();
                    break;
                } else if (isNotPhone) {
                    Toast.makeText(getApplicationContext(), "???????????? ????????? ???????????? ????????????.", Toast.LENGTH_LONG).show();
                    break;
                } else if (isPhoneDuplicate) {
                    Toast.makeText(getApplicationContext(), "?????? ????????? ?????????????????????.", Toast.LENGTH_LONG).show();
                    break;
                } else {
                    isSend = true;
                    Toast.makeText(getApplicationContext(), "?????? ????????? ?????? ????????? ?????????????????????.", Toast.LENGTH_LONG).show();
                    startPhoneNumberVerification(changePhoneNumber(mobilePhoneNumber.getText().toString()));
                    startAuth.setVisibility(View.GONE);
                    resendAuth.setVisibility(View.VISIBLE);
                    break;
                }

            case R.id.kko_verify:
                String code = authCode.getText().toString();

                if (!isAge) {
                    Toast.makeText(getApplicationContext(), "??? 14??? ????????? ???????????? ??? ????????????.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(code)) {
                    authCode.setError("??????????????? ??????????????????.");
                    return;
                }
                if (!isSend) {
                    authCode.setError("?????? ????????? ?????? ????????????.");
                    return;
                }
                verifyPhoneNumberWithCode(mVerificationId, code);
                break;

            case R.id.kko_resendAuth:
                if (isPhoneEmpty) {
                    Toast.makeText(getApplicationContext(), "??????????????? ??????????????????.", Toast.LENGTH_LONG).show();
                    break;
                } else if (isNotPhone) {
                    Toast.makeText(getApplicationContext(), "???????????? ????????? ???????????? ????????????.", Toast.LENGTH_LONG).show();
                    break;
                } else if (isPhoneDuplicate) {
                    Toast.makeText(getApplicationContext(), "?????? ????????? ?????????????????????.", Toast.LENGTH_LONG).show();
                    break;
                } else {
                    Toast.makeText(getApplicationContext(), "?????? ????????? ?????? ????????? ????????????????????????.", Toast.LENGTH_LONG).show();
                    resendVerificationCode(changePhoneNumber(mobilePhoneNumber.getText().toString()), mResendToken);
                    break;
                }
        }
    }

//    @Override //?????? ???????????? ?????????????????? ?????? ????????????
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == SELECT_HASH_TAG){
//            //???????????? ??????
//            Call<Void> call = RetrofitClient.getApiService().cancelSignUp(userParams.getEmail());
//            call.enqueue(new Callback<Void>() {
//                @Override
//                public void onResponse(Call<Void> call, Response<Void> response) {
//                    if(response.isSuccessful()){
//                        System.out.println("???????????? ?????? ??????");
//                    } else{
//                        System.out.println("???????????? ?????? ??????");
//                    }
//                }
//                @Override
//                public void onFailure(Call<Void> call, Throwable t) {
//                    Log.e("????????????", t.getMessage());
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