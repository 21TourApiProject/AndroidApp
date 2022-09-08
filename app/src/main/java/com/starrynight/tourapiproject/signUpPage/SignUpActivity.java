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

/**
 * @className : SignUpActivity.java
 * @description : 회원가입 페이지 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
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
                            if (result == -1L) {
                                Toast.makeText(getApplicationContext(), "로그인 정보가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                            } else if (result == -2L) {
                                Toast.makeText(getApplicationContext(), "카카오 회원은 아래의 카카오 로그인을 이용해주세요.", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d(TAG0, "로그인 성공");

                                //앱 내부 저장소에 userId란 이름으로 사용자 id 저장
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

                                //메인 페이지로 이동
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                                //권한 설정
                                int permission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                                int permission3 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET);//denied면 -1

                                Log.d("test", "onClick: location clicked");
                                if (permission == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED && permission3 == PackageManager.PERMISSION_GRANTED) {
                                    Log.d("MyTag", "읽기,쓰기,인터넷 권한이 있습니다.");

                                } else if (permission == PackageManager.PERMISSION_DENIED) {
                                    Log.d("test", "permission denied");
                                    Toast.makeText(getApplicationContext(), "쓰기권한이 없습니다.", Toast.LENGTH_SHORT).show();
                                    ActivityCompat.requestPermissions(SignUpActivity.this, WRITE_PERMISSION, PERMISSIONS_REQUEST_CODE);
                                    ActivityCompat.requestPermissions(SignUpActivity.this, READ_PERMISSION, PERMISSIONS_REQUEST_CODE);
                                    ActivityCompat.requestPermissions(SignUpActivity.this, INTERNET_PERMISSION, PERMISSIONS_REQUEST_CODE);
                                }
                                finish();

                            }
                        } else {
                            Log.e(TAG0, "로그인 실패");
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

        Session session;
        ImageButton kakaoSignUp = findViewById(R.id.kakaoSignUp);


        session = Session.getCurrentSession();
        session.addCallback(sessionCallback);
        kakaoSignUp.setOnClickListener(v -> {
            if (Session.getCurrentSession().checkAndImplicitOpen()) {
                Log.d("KakaoLogin", "onClick: 로그인 세션 살아있음");

                sessionCallback.requestMe();

                //창이안뜸, 아직 로그인 세션 살아있음
                //회원가입 안함
            } else {
                Log.d("KakaoLogin", "로그인 세션 만료");
                //카카오 로그인 창 뜸
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

        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
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
            asyncDialog.setMessage("기다려주세요");
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

        //로그인 성공
        @Override
        public void onSessionOpened() {
            Log.d(TAG, "session is opened. call request");
//            requestMe();
        }


        //로그인 실패
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());
        }

        public void requestMe() {
            kakaoUserParams = new KakaoUserParams();
            UserManagement.getInstance().me(new MeV2ResponseCallback() {
                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Log.e(TAG2, "세션이 닫혀 있음: " + errorResult);
                }

                @Override
                public void onFailure(ErrorResult errorResult) {
                    Log.e(TAG2, "사용자 정보 요청 실패: " + errorResult);
                }

                @Override
                public void onSuccess(MeV2Response result) {
                    Log.i(TAG2, "사용자 아이디: " + result.getId());
                    String id = String.valueOf(result.getId());
                    UserAccount kakaoAccount = result.getKakaoAccount();
                    if (kakaoAccount != null) {

                        // 이메일
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

                        // 프로필
                        Profile _profile = kakaoAccount.getProfile();

                        if (_profile != null) {

                            Log.d(TAG2, "nickname: " + _profile.getNickname());
                            kakaoUserParams.setNickName(profile.getNickname());
                            Log.d(TAG2, "profile image: " + _profile.getProfileImageUrl());
                            kakaoUserParams.setProfileImage(profile.getProfileImageUrl());
                            Log.d(TAG2, "thumbnail image: " + _profile.getThumbnailImageUrl());


                        } else if (kakaoAccount.profileNeedsAgreement() == OptionalBoolean.TRUE) {
                            // 동의 요청 후 프로필 정보 획득 가능

                        } else {
                            // 프로필 획득 불가
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
                                    Log.d(TAG2, "회원가입 진행");
                                    Intent intent = new Intent(SignUpActivity.this, KakaoPhoneAuthActivity.class);
                                    intent.putExtra("userParams", kakaoUserParams);
                                    startActivity(intent);
                                } else if (!result) {
                                    Log.d(TAG2, "회원가입 미진행, 이미가입된 이메일");
                                    Call<Long> call2 = RetrofitClient.getApiService().kakaoLogIn(kakaoUserParams.getEmail());
                                    call2.enqueue(new Callback<Long>() {
                                        @Override
                                        public void onResponse(Call<Long> call, Response<Long> response) {
                                            //앱 내부 저장소에 userId란 이름으로 사용자 id 저장
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

                                            //메인 페이지로 이동
                                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            //권한 설정
                                            int permission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                            int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                                            int permission3 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET);//denied면 -1

                                            Log.d("test", "onClick: location clicked");
                                            if (permission == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED && permission3 == PackageManager.PERMISSION_GRANTED) {
                                                Log.d("MyTag", "읽기,쓰기,인터넷 권한이 있습니다.");

                                            } else if (permission == PackageManager.PERMISSION_DENIED) {
                                                Log.d("test", "permission denied");
                                                Toast.makeText(getApplicationContext(), "쓰기권한이 없습니다.", Toast.LENGTH_SHORT).show();
                                                ActivityCompat.requestPermissions(SignUpActivity.this, WRITE_PERMISSION, PERMISSIONS_REQUEST_CODE);
                                                ActivityCompat.requestPermissions(SignUpActivity.this, READ_PERMISSION, PERMISSIONS_REQUEST_CODE);
                                                ActivityCompat.requestPermissions(SignUpActivity.this, INTERNET_PERMISSION, PERMISSIONS_REQUEST_CODE);
                                            }
                                            finish();
                                        }

                                        @Override
                                        public void onFailure(Call<Long> call, Throwable t) {
                                            Log.e(TAG, "카카오 로그인 사용자 없음");
                                        }
                                    });

                                }
                            } else {
                                Log.e(TAG2, "이메일 중복 체크 실패");
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Log.e("연결실패", t.getMessage());
                        }
                    });


                }
            });
        }
    }


}