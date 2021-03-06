package com.starrynight.tourapiproject.signUpPage;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Gender;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;
import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.signUpPage.signUpRetrofit.KakaoUserParams;
import com.starrynight.tourapiproject.signUpPage.signUpRetrofit.RetrofitClient;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG0 = "SignUpActivity";
    private static final String TAG2 = "KakaoLoginApi";
    String[] WRITE_PERMISSION = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    String[] READ_PERMISSION = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    String[] INTERNET_PERMISSION = new String[]{Manifest.permission.INTERNET};
    int PERMISSIONS_REQUEST_CODE = 100;
    private SessionCallback sessionCallback = new SessionCallback();
    KakaoUserParams kakaoUserParams;

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
        setContentView(R.layout.activity_sign_up);

        //????????? ??????
        Button logIn = findViewById(R.id.logIn);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText) (findViewById(R.id.logInEmail))).getText().toString();
                String password = ((EditText) (findViewById(R.id.logInPwd))).getText().toString();
                //???????????? ?????? get api
                Call<Long> call = RetrofitClient.getApiService().logIn(email, password);
                call.enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {
                        if (response.isSuccessful()) {
                            Long result = response.body();
                            if (result == -1L) {
                                Toast.makeText(getApplicationContext(), "????????? ????????? ???????????? ????????????.", Toast.LENGTH_SHORT).show();
                            } else if (result == -2L) {
                                Toast.makeText(getApplicationContext(), "????????? ????????? ????????? ????????? ???????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d(TAG0, "????????? ??????");

                                //??? ?????? ???????????? userId??? ???????????? ????????? id ??????
                                String fileName = "userId";
                                String userId = result.toString();
                                Log.d(TAG0, "userId " + userId);
                                try {
                                    FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                                    fos.write(userId.getBytes());
                                    fos.close();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                //?????? ???????????? ??????
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                                //?????? ??????
                                int permission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                                int permission3 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET);//denied??? -1

                                Log.d("test", "onClick: location clicked");
                                if (permission == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED && permission3 == PackageManager.PERMISSION_GRANTED) {
                                    Log.d("MyTag", "??????,??????,????????? ????????? ????????????.");

                                } else if (permission == PackageManager.PERMISSION_DENIED) {
                                    Log.d("test", "permission denied");
                                    Toast.makeText(getApplicationContext(), "??????????????? ????????????.", Toast.LENGTH_SHORT).show();
                                    ActivityCompat.requestPermissions(SignUpActivity.this, WRITE_PERMISSION, PERMISSIONS_REQUEST_CODE);
                                    ActivityCompat.requestPermissions(SignUpActivity.this, READ_PERMISSION, PERMISSIONS_REQUEST_CODE);
                                    ActivityCompat.requestPermissions(SignUpActivity.this, INTERNET_PERMISSION, PERMISSIONS_REQUEST_CODE);
                                }
                                finish();

                            }
                        } else {
                            Log.e(TAG0, "????????? ??????");
                        }
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {
                        Log.e("????????????", t.getMessage());
                    }
                });
            }
        });

        //????????? ?????? ??????
        TextView findEmail = findViewById(R.id.findEmail);
        findEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, FindEmailActivity.class);
                startActivity(intent);
            }
        });

        //???????????? ?????? ??????
        TextView findPwd = findViewById(R.id.findPwd);
        findPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, FindPasswordActivity.class);
                startActivity(intent);
            }
        });


        //?????? ????????????
        Button generalSignUp = findViewById(R.id.generalSignUp);
        generalSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, GeneralSingUpActivity.class);
                startActivity(intent);
            }
        });

        //????????? ????????????

        Session session;
        ImageButton kakaoSignUp = findViewById(R.id.kakaoSignUp);


        session = Session.getCurrentSession();
        session.addCallback(sessionCallback);
        kakaoSignUp.setOnClickListener(v -> {
            if (Session.getCurrentSession().checkAndImplicitOpen()) {
                Log.d("KakaoLogin", "onClick: ????????? ?????? ????????????");

                sessionCallback.requestMe();

                //????????????, ?????? ????????? ?????? ????????????
                //???????????? ??????
            } else {
                Log.d("KakaoLogin", "????????? ?????? ??????");
                //????????? ????????? ??? ???
                session.open(AuthType.KAKAO_LOGIN_ALL, SignUpActivity.this);

                KakaoLoginAsyncTask task = new KakaoLoginAsyncTask(SignUpActivity.this);
                task.execute();
//                kakaoUserParams = sessionCallback.requestMe();
//                Intent intent = new Intent(SignUpActivity.this, KakaoPhoneAuthActivity.class);
//                intent.putExtra("userParams", kakaoUserParams);
//                startActivity(intent);
            }
        });

//                Button logout = findViewById(R.id.logout_tmp);
//        logout.setOnClickListener(v -> {
//            Log.d("KakaoLogin", "onCreate:click ");
//            UserManagement.getInstance()
//                    .requestLogout(new LogoutResponseCallback() {
//                        @Override
//                        public void onSessionClosed(ErrorResult errorResult) {
//                            super.onSessionClosed(errorResult);
//                            Log.d("KakaoLogin", "onSessionClosed: " + errorResult.getErrorMessage());
//
//                        }
//
//                        @Override
//                        public void onCompleteLogout() {
//                            if (sessionCallback != null) {
//                                Session.getCurrentSession().removeCallback(sessionCallback);
//                            }
//                            Log.d("KakaoLogin", "onCompleteLogout:logout ");
//                        }
//                    });
//        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // ?????? ?????? ??????
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // ????????????|????????? ??????????????? ?????? ????????? ????????? SDK??? ??????
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class KakaoLoginAsyncTask extends AsyncTask<String, Long, Boolean> {
        private Context mContext = null;
        ProgressDialog asyncDialog = new ProgressDialog(SignUpActivity.this);

        public KakaoLoginAsyncTask(Context context) {
            mContext = context.getApplicationContext();
        }

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("??????????????????");
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... strings) {

            while (!Session.getCurrentSession().checkAndImplicitOpen()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return (Session.getCurrentSession().checkAndImplicitOpen());
        }

        @Override
        protected void onPostExecute(Boolean result) {
            asyncDialog.dismiss();
            sessionCallback.requestMe();
        }
    }


    public class SessionCallback implements ISessionCallback {

        private static final String TAG = "KakaoLoginApi";

        //????????? ??????
        @Override
        public void onSessionOpened() {
            Log.d(TAG, "session is opened. call request");
//            requestMe();
        }


        //????????? ??????
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());
        }

        public void requestMe() {
            kakaoUserParams = new KakaoUserParams();
            UserManagement.getInstance().me(new MeV2ResponseCallback() {
                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Log.e(TAG2, "????????? ?????? ??????: " + errorResult);
                }

                @Override
                public void onFailure(ErrorResult errorResult) {
                    Log.e(TAG2, "????????? ?????? ?????? ??????: " + errorResult);
                }

                @Override
                public void onSuccess(MeV2Response result) {
                    Log.i(TAG2, "????????? ?????????: " + result.getId());
                    String id = String.valueOf(result.getId());
                    UserAccount kakaoAccount = result.getKakaoAccount();
                    if (kakaoAccount != null) {

                        // ?????????
                        String email = kakaoAccount.getEmail();
                        if (email != null) {
                            Log.d(TAG2, "onSuccess:getEmail " + email);
                            kakaoUserParams.setEmail(email);
                        } else {
                            Log.d(TAG2, "onSuccess:email null ");
                        }
                        if (kakaoAccount.getAgeRange() != null) {
                            Log.d(TAG2, "onSuccess:get ageRange " + kakaoAccount.getAgeRange());
                            kakaoUserParams.setAgeRange(kakaoAccount.getAgeRange().toString());
                        }
                        if (kakaoAccount.getBirthday() != null) {
                            Log.d(TAG2, "onSuccess:get Birthday " + kakaoAccount.getBirthday());
                            kakaoUserParams.setBirthDay(kakaoAccount.getBirthday());
                        }
                        if (kakaoAccount.getGender() != null) {
                            Log.d(TAG2, "onSuccess:get Gender " + kakaoAccount.getGender());
                            if (kakaoAccount.getGender() == Gender.FEMALE)
                                kakaoUserParams.setSex(false);
                            else if (kakaoAccount.getGender() == Gender.MALE)
                                kakaoUserParams.setSex(true);
                        }

                        Profile profile = kakaoAccount.getProfile();
                        if (profile == null) {
                            Log.d(TAG2, "onSuccess:profile null ");
                        } else {
                            Log.d(TAG2, "onSuccess:getProfileImageUrl " + profile.getProfileImageUrl());
                            kakaoUserParams.setProfileImage(profile.getProfileImageUrl());
                            Log.d(TAG2, "onSuccess:getNickname " + profile.getNickname());
                            kakaoUserParams.setNickName(profile.getNickname());
                        }
                        if (email != null) {

                            Log.d(TAG2, "onSuccess:email " + email);
                        } else {
                            Log.d(TAG2, "onSuccess:email null ");
                        }

                        // ?????????
                        Profile _profile = kakaoAccount.getProfile();

                        if (_profile != null) {

                            Log.d(TAG2, "nickname: " + _profile.getNickname());
                            kakaoUserParams.setNickName(profile.getNickname());
                            Log.d(TAG2, "profile image: " + _profile.getProfileImageUrl());
                            kakaoUserParams.setProfileImage(profile.getProfileImageUrl());
                            Log.d(TAG2, "thumbnail image: " + _profile.getThumbnailImageUrl());


                        } else if (kakaoAccount.profileNeedsAgreement() == OptionalBoolean.TRUE) {
                            // ?????? ?????? ??? ????????? ?????? ?????? ??????

                        } else {
                            // ????????? ?????? ??????
                        }
                    } else {
                        Log.i(TAG2, "onSuccess: kakaoAccount null");
                    }

                    Call<Boolean> call = RetrofitClient.getApiService().checkDuplicateEmail(kakaoUserParams.getEmail());
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if (response.isSuccessful()) {
                                Boolean result = response.body();
                                if (result) {
                                    Log.d(TAG2, "???????????? ??????");
                                    Intent intent = new Intent(SignUpActivity.this, KakaoPhoneAuthActivity.class);
                                    intent.putExtra("userParams", kakaoUserParams);
                                    startActivity(intent);
                                } else if (!result) {
                                    Log.d(TAG2, "???????????? ?????????, ??????????????? ?????????");
                                    Call<Long> call2 = RetrofitClient.getApiService().kakaoLogIn(kakaoUserParams.getEmail());
                                    call2.enqueue(new Callback<Long>() {
                                        @Override
                                        public void onResponse(Call<Long> call, Response<Long> response) {
                                            //??? ?????? ???????????? userId??? ???????????? ????????? id ??????
                                            String fileName = "userId";
                                            String userId = response.body().toString();
                                            Log.d(TAG0, "userId " + userId);
                                            try {
                                                FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                                                fos.write(userId.getBytes());
                                                fos.close();
                                            } catch (FileNotFoundException e) {
                                                e.printStackTrace();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                            //?????? ???????????? ??????
                                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            //?????? ??????
                                            int permission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                            int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                                            int permission3 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET);//denied??? -1

                                            Log.d("test", "onClick: location clicked");
                                            if (permission == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED && permission3 == PackageManager.PERMISSION_GRANTED) {
                                                Log.d("MyTag", "??????,??????,????????? ????????? ????????????.");

                                            } else if (permission == PackageManager.PERMISSION_DENIED) {
                                                Log.d("test", "permission denied");
                                                Toast.makeText(getApplicationContext(), "??????????????? ????????????.", Toast.LENGTH_SHORT).show();
                                                ActivityCompat.requestPermissions(SignUpActivity.this, WRITE_PERMISSION, PERMISSIONS_REQUEST_CODE);
                                                ActivityCompat.requestPermissions(SignUpActivity.this, READ_PERMISSION, PERMISSIONS_REQUEST_CODE);
                                                ActivityCompat.requestPermissions(SignUpActivity.this, INTERNET_PERMISSION, PERMISSIONS_REQUEST_CODE);
                                            }
                                            finish();
                                        }

                                        @Override
                                        public void onFailure(Call<Long> call, Throwable t) {
                                            Log.e(TAG, "????????? ????????? ????????? ??????");
                                        }
                                    });

                                }
                            } else {
                                Log.e(TAG2, "????????? ?????? ?????? ??????");
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Log.e("????????????", t.getMessage());
                        }
                    });


                }
            });
        }
    }


}