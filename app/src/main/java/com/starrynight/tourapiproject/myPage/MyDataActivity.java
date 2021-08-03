package com.starrynight.tourapiproject.myPage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.User;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.User2;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDataActivity extends AppCompatActivity {

    User user;
    User2 user2 = new User2();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_data);

        Call<User> call = RetrofitClient.getApiService().getUser(1L);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();

//                    ImageView profileImage2 = findViewById(R.id.profileImage2);
//                    if (user.getProfileImage() != null){
//                        profileImage2.setImageURI();
//                    }

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

        //프로필 변경 페이지로 이동
        ImageView profileImage = findViewById(R.id.profileImage2);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyDataActivity.this, ChangeProfileActivity.class);
                user2.setUserId(user.getUserId());
                user2.setNickName(user.getNickName());
                user2.setProfileImage(user.getProfileImage());
                intent.putExtra("user2", user2);
                startActivity(intent);
            }
        });

        //프로필 변경 페이지로 이동
        TextView nickName = findViewById(R.id.nickName2);
        nickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyDataActivity.this, ChangeProfileActivity.class);
                user2.setUserId(user.getUserId());
                user2.setNickName(user.getNickName());
                user2.setProfileImage(user.getProfileImage());
                intent.putExtra("user2", user2);
                startActivity(intent);
            }
        });
    }
}