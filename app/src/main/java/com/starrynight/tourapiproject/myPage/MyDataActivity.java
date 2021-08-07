package com.starrynight.tourapiproject.myPage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.BitmapFactory.decodeFile;

public class MyDataActivity extends AppCompatActivity {
    private static final int CHANGE_PROFILE = 101;
    Long userId;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_data);

        Intent intent = getIntent();
        userId = (Long) intent.getSerializableExtra("userId"); //전 페이지에서 받아온 사용자 id

        Call<User> call = RetrofitClient.getApiService().getUser(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();

                    ImageView profileImage2 = findViewById(R.id.profileImage2);
                    if (user.getProfileImage() != null){
                        profileImage2.setImageBitmap(decodeFile(user.getProfileImage()));
                    }

                    TextView nickName2 = findViewById(R.id.nickName2);
                    nickName2.setText(user.getNickName());

                    TextView name = findViewById(R.id.name);
                    name.setText(user.getRealName());

                    TextView email = findViewById(R.id.email);
                    email.setText(user.getEmail());

                    TextView mobilePhoneNumber = findViewById(R.id.mobilePhoneNumber);
                    mobilePhoneNumber.setText(user.getMobilePhoneNumber());

                    TextView birth = findViewById(R.id.birth);
                    birth.setText(user.getBirthDay());

                    TextView sex = findViewById(R.id.sex);
                    if (user.getSex()){
                        sex.setText("남성");
                    } else{sex.setText("여성");}

                } else {
                    System.out.println("사용자 정보 불러오기 실패");
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
                //intent.putExtra("userId", );
                startActivity(intent);
            }
        });

        //프로필 변경 페이지로 이동
        ImageView profileImage = findViewById(R.id.profileImage2);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyDataActivity.this, ChangeProfileActivity.class);
                startActivityForResult(intent, CHANGE_PROFILE);
            }
        });

        //프로필 변경 페이지로 이동
        TextView nickName = findViewById(R.id.nickName2);
        nickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyDataActivity.this, ChangeProfileActivity.class);
                startActivityForResult(intent, CHANGE_PROFILE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHANGE_PROFILE){
            Intent intent = getIntent();
            finish();
            startActivity(intent);

        }
    }

}