package com.starrynight.tourapiproject.myPage;

import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @className : MyDataActivity.java
 * @description : 내 정보 페이지 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
public class MyDataActivity extends AppCompatActivity {

    private static final String TAG = "MyData";
    private static final int CHANGE_PROFILE = 11;
    Long userId;
    User user;
    ImageView profileImage2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_data);

        Intent intent = getIntent();
        userId = (Long) intent.getSerializableExtra("userId"); //전 페이지에서 받아온 사용자 id

        profileImage2 = findViewById(R.id.profileImage2);
        profileImage2.setBackground(new ShapeDrawable(new OvalShape()));
        profileImage2.setClipToOutline(true);

        //뒤로 가기
        ImageView myDataBack = findViewById(R.id.myDataBack);
        myDataBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //사용자 정보 불러오기
        Call<User> call = RetrofitClient.getApiService().getUser(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();

                    if (user.getProfileImage() != null) {
                        if (user.getProfileImage().startsWith("http://") || user.getProfileImage().startsWith("https://")) {
                            Glide.with(getApplicationContext()).load(user.getProfileImage()).circleCrop().into(profileImage2);
                        } else {
                            String fileName = user.getProfileImage();
                            fileName = fileName.substring(1, fileName.length() - 1);
                            Glide.with(getApplicationContext()).load("https://starry-night.s3.ap-northeast-2.amazonaws.com/profileImage/" + fileName).circleCrop().into(profileImage2);
                        }
                    }

                    TextView nickName2 = findViewById(R.id.nickName2);
                    nickName2.setText(user.getNickName());

                    TextView name = findViewById(R.id.name);
                    name.setText(user.getRealName());

                    TextView email = findViewById(R.id.email);
                    email.setText(user.getEmail());

                    TextView mobilePhoneNumber = findViewById(R.id.mobilePhoneNumber);
                    String mpn = user.getMobilePhoneNumber();
                    if (mpn != null)
                        mobilePhoneNumber.setText(mpn.substring(0, 3) + "-" + mpn.substring(3, 7) + "-" + mpn.substring(7));

                    TextView birth = findViewById(R.id.birth);
                    birth.setText(user.getBirthDay());

                    TextView sex = findViewById(R.id.sex);
                    if (user.getSex() != null) {
                        if (user.getSex())
                            sex.setText("남성");
                        else
                            sex.setText("여성");
                    }
                } else {
                    Log.e(TAG, "사용자 정보 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });

        //비밀번호 변경 버튼
        TextView changePwd = findViewById(R.id.changePwd);
        changePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyDataActivity.this, ChangePasswordActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        //프로필 변경 페이지로 이동
        ImageView profileImage = findViewById(R.id.profileImage2);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyDataActivity.this, ChangeProfileActivity.class);
                intent.putExtra("userId", userId);
                startActivityForResult(intent, CHANGE_PROFILE);
            }
        });
        //프로필 변경 페이지로 이동
        ImageView changeProfile = findViewById(R.id.changeProfile);
        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyDataActivity.this, ChangeProfileActivity.class);
                intent.putExtra("userId", userId);
                startActivityForResult(intent, CHANGE_PROFILE);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHANGE_PROFILE) {
            //액티비티 새로고침
            Intent intent = getIntent();
            finish();
            startActivity(intent);

        }
    }

}