package com.starrynight.tourapiproject.myPage;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.User;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.User2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.BitmapFactory.decodeFile;

public class ChangeProfileActivity extends AppCompatActivity {

    private static final int GET_GALLERY_IMAGE = 0;
    private static final String TAG = "test for path";

    User user;
    String beforeNickName; //변경하기전 닉네임
    Boolean isProfileImageChange = false; //프로필 사진을 바꿨는지

    ImageView profileImage;
    EditText changeNickname;
    TextView changeNicknameGuide;

    private Boolean isNickNameEmpty = false; //닉네임이 비어있는지
    private Boolean isNotNickName = false; //올바른 닉네임 형식이 아닌지
    private Boolean isNickNameDuplicate = false; //닉네임이 중복인지
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        profileImage = findViewById(R.id.profileImage3);
        changeNickname = findViewById(R.id.changeNickname);
        changeNicknameGuide = findViewById(R.id.changeNicknameGuide);

        Call<User> call = RetrofitClient.getApiService().getUser(1L);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    //profileImage.setImageURI(user.getProfileImage());
                    changeNickname.setText(user.getNickName());
                    beforeNickName = user.getNickName();
                } else {
                    System.out.println("사용자 정보 불러오기 실패");
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });

        //프로필 사진 변경 1
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent,GET_GALLERY_IMAGE);
            }
        });

        //닉네임 변경
        changeNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력란에 변화가 있을 때
                showNickNameGuide(s);
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                showNickNameGuide(arg0);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });
        
        //저장 버튼
        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNickNameEmpty){
                    changeNicknameGuide.setText("닉네임을 입력해주세요.");
                }
                else if(isNotNickName){
                    changeNicknameGuide.setText("사용할 수 없는 닉네임입니다. (한글/영문/숫자 조합 15자 이내)");
                }
                else if(isNickNameDuplicate){
                    changeNicknameGuide.setText("닉네임 중복확인이 필요합니다.");
                }
                //닉네임 변경 put api
                else{
                    changeNicknameGuide.setText("");
                    Call<Void> call = RetrofitClient.getApiService().updateNickName(user.getUserId(), changeNickname.getText().toString());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                System.out.println("닉네임 변경 성공");
                                User2 user2 = new User2(changeNickname.getText().toString(), "");
                                Intent intent = new Intent();
                                intent.putExtra("result", user2);
                                finish();
                            } else {
                                System.out.println("닉네임 변경 실패");
                                Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("연결실패", t.getMessage());
                            Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    });

                    //프로필 사진 변경 put api
                    if (isProfileImageChange){
//                        Call<Void> call = RetrofitClient.getApiService().updateNickName(user.getUserId(), changeNickname.getText().toString());
//                        call.enqueue(new Callback<Void>() {
//                            @Override
//                            public void onResponse(Call<Void> call, Response<Void> response) {
//                                if (response.isSuccessful()) {
//                                    System.out.println("닉네임 변경 성공");
//                                    User2 user2 = new User2(changeNickname.getText().toString(), "");
//                                    Intent intent = new Intent();
//                                    intent.putExtra("result", user2);
//                                    finish();
//                                } else {
//                                    System.out.println("닉네임 변경 실패");
//                                    Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                            @Override
//                            public void onFailure(Call<Void> call, Throwable t) {
//                                Log.e("연결실패", t.getMessage());
//                                Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                    }
                }
            }
        });


        //뒤로 가기
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //프로필 사진 변경 2
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE) {
            if (resultCode == RESULT_OK) {
                try {
                    isProfileImageChange = true;
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    //profileImage.setImageBitmap(img);
                    String file = BitmapToFile(img, "profileImage");
                    System.out.println("file = " + file);
                    profileImage.setImageBitmap(decodeFile(file));

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                System.out.println("사진 선택 취소");
            }
        }
    }

    //Bitmap을 File로 변경
    public String BitmapToFile(Bitmap bitmap, String name) {
        File storage = getFilesDir();
        String fileName = name + ".jpg";
        File imgFile = new File(storage, fileName);
        try {
            imgFile.createNewFile();
            FileOutputStream out = new FileOutputStream(imgFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, out);
        } catch (FileNotFoundException e) {
            Log.e("saveBitmapToJpg", "FileNotFoundException: "+ e.getMessage());
        } catch (IOException e) {
            Log.e("saveBitmapToJpg", "IOException: "+ e.getMessage());
        }
        Log.d("imgPath", getFilesDir() + "/" + fileName);
        //return imgFile;
        return getFilesDir() + "/" + fileName;
    }

    //닉네임 규칙 함수
    private Boolean isCorrectNickName(String nickName) {
        String pattern = "^[[가-힣]*[ㄱ-ㅎ]*[0-9]*[a-zA-z]*[ ]*]{1,15}$";
        return Pattern.matches(pattern, nickName);
    }

    private void showNickNameGuide(CharSequence s) {
        String text = s.toString();
        if (text.isEmpty()) {
            isNickNameEmpty = true;
        } else if (!isCorrectNickName(text)) {
            changeNicknameGuide.setText("사용할 수 없는 닉네임입니다. (한글/영문/숫자 조합 15자 이내)");
            isNickNameEmpty = false;
            isNotNickName = true;
        } else if (!text.equals(user.getNickName())){
            //닉네임이 중복인지 아닌지를 위한 get api
            changeNicknameGuide.setText("");
            Call<Boolean> call = RetrofitClient.getApiService().checkDuplicateNickName(text);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        Boolean result = response.body();
                        if (result) {
                            changeNicknameGuide.setText("사용가능한 닉네임입니다.");
                            isNickNameEmpty = false;
                            isNickNameDuplicate = false;
                            isNotNickName = false;
                        } else if (!result) {
                            changeNicknameGuide.setText("중복된 닉네임입니다.");
                            isNickNameEmpty = false;
                            isNickNameDuplicate = false;
                            isNotNickName = true;
                        }
                    } else {
                        System.out.println("중복 체크 실패");
                        changeNicknameGuide.setText("");
                        Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.e("연결실패", t.getMessage());
                    changeNicknameGuide.setText("");
                    Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}