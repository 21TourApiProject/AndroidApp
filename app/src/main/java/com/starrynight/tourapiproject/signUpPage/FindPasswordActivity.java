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

public class FindPasswordActivity extends AppCompatActivity implements
        View.OnClickListener {
    private static final String TAG = "FindPassword";
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    private FirebaseAuth mAuth;

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private EditText findPwdEmail;
    private EditText findPwdRealName;
    private EditText mobilePhoneNumber;
    private EditText authCode;
    private Button startAuth;
    private Button resendAuth;
    private Button verify;
    private TextView showPassword;

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
        setContentView(R.layout.activity_find_password);

        findPwdEmail = findViewById(R.id.findPwdEmail); //?????????
        findPwdRealName = findViewById(R.id.findPwdRealName); //??????
        mobilePhoneNumber = findViewById(R.id.findPwdMobilePhoneNumber); //????????????
        authCode = findViewById(R.id.authCode3); //????????????
        startAuth = findViewById(R.id.startAuth3); //?????? ????????????
        resendAuth = findViewById(R.id.resendAuth3); //??? ????????????
        verify = findViewById(R.id.verify3); //????????????
        showPassword = findViewById(R.id.showPassword);

        startAuth.setOnClickListener(this);
        resendAuth.setOnClickListener(this);
        verify.setOnClickListener(this);


        //?????? ??????
        ImageView findPasswordBack = findViewById(R.id.passwordBack);
        findPasswordBack.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(getApplicationContext(), "???????????? ?????? ???????????? ?????????.", Toast.LENGTH_SHORT).show();
                    //mobilePhoneNumber.setError("???????????? ?????? ???????????? ?????????.");

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
                            Log.d(TAG, "?????? ??????");
                            String email = ((EditText) findViewById(R.id.findPwdEmail)).getText().toString();
                            String realName = ((EditText) findViewById(R.id.findPwdRealName)).getText().toString();

                            //???????????? ????????? ?????? get api
                            Call<String> call = RetrofitClient.getApiService().getPassword(email, realName, mobilePhoneNumber.getText().toString());
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    if (response.isSuccessful()) {
                                        String result = response.body();
                                        if (!result.equals("none")) {
                                            Log.d(TAG, "???????????? ?????? ??????");
                                            signOut();
                                            showPassword.setText("????????????: " + result);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "?????? ????????? ???????????? ????????? ????????????.", Toast.LENGTH_SHORT).show();
                                            showPassword.setText("");
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "?????? ????????? ???????????? ????????? ????????????.", Toast.LENGTH_SHORT).show();
                                        showPassword.setText("");
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Log.e("????????????", t.getMessage());
                                    showPassword.setText("");
                                }
                            });

                        } else {
                            Log.w(TAG, "?????? ??????", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(), "???????????? ?????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                                //authCode.setError("???????????? ?????? ?????????????????????.");
                                showPassword.setText("");
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
            Toast.makeText(getApplicationContext(), "??????????????? ??????????????????.", Toast.LENGTH_SHORT).show();
            //mobilePhoneNumber.setError("??????????????? ??????????????????.");
            return false;
        }
        if (phoneNumber.length() != 11) {
            Toast.makeText(getApplicationContext(), "????????? ?????? ??????????????? ??????????????????.", Toast.LENGTH_SHORT).show();
            //mobilePhoneNumber.setError("????????? ?????? ??????????????? ??????????????????.");
            return false;
        }
        return true;
    }

    //?????? ?????? ???????????? ??????
    private String changePhoneNumber(String phoneNumber) {
        return "+82" + phoneNumber.substring(1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startAuth3:
                if (!validatePhoneNumber()) {
                    Log.e(TAG, "?????? ????????????????????? ??????????????? ?????????");
                    return;
                }
                isSend = true;
                Toast.makeText(getApplicationContext(), "?????? ????????? ?????? ????????? ?????????????????????.", Toast.LENGTH_LONG).show();
                startPhoneNumberVerification(changePhoneNumber(mobilePhoneNumber.getText().toString()));
                startAuth.setVisibility(View.GONE);
                resendAuth.setVisibility(View.VISIBLE);
                break;

            case R.id.verify3:
                String code = authCode.getText().toString();

                if (findPwdEmail.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "???????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    //findPwdEmail.setError("???????????? ??????????????????.");
                    return;
                }
                if (findPwdRealName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    //findPwdRealName.setError("????????? ??????????????????.");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(getApplicationContext(), "??????????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    //authCode.setError("??????????????? ??????????????????.");
                    return;
                }
                if (!isSend) {
                    Toast.makeText(getApplicationContext(), "?????? ????????? ?????? ????????????.", Toast.LENGTH_SHORT).show();
                    //authCode.setError("?????? ????????? ?????? ????????????.");
                    return;
                }
                Log.d(TAG, code + "???????????? ????????? ??????????????? ");
                showPassword.setText("????????? ??????????????????...");
                verifyPhoneNumberWithCode(mVerificationId, code);
                break;

            case R.id.resendAuth3:
                Toast.makeText(getApplicationContext(), "?????? ????????? ?????? ????????? ????????????????????????.", Toast.LENGTH_LONG).show();
                resendVerificationCode(changePhoneNumber(mobilePhoneNumber.getText().toString()), mResendToken);
                break;
        }
    }
}