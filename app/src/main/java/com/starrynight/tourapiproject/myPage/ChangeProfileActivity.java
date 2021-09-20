package com.starrynight.tourapiproject.myPage;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
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
import androidx.loader.content.CursorLoader;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.bumptech.glide.Glide;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.User2;

import java.io.File;
import java.io.InputStream;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeProfileActivity extends AppCompatActivity {

    private static final int GET_GALLERY_IMAGE = 0;
    private static final String TAG = "ChangeProfile";

    User2 user;
    Long userId;
    String beforeNickName; //변경하기전 닉네임
    String beforeImage; //변경하기전 사진이름
    Boolean isProfileImageChange = false; //프로필 사진을 바꿨는지

    ImageView profileImage;
    EditText changeNickname;
    TextView changeNicknameGuide;
    TextView length; // 글자수

    private Boolean isNickNameEmpty = false; //닉네임이 비어있는지
    private Boolean isNotNickName = false; //올바른 닉네임 형식이 아닌지
    private Boolean isNickNameDuplicate = false; //닉네임이 중복인지

    File file; //이미지 파일
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        Intent intent = getIntent();
        userId = (Long) intent.getSerializableExtra("userId"); //전 페이지에서 받아온 사용자 id

        profileImage = findViewById(R.id.profileImage3);
        changeNickname = findViewById(R.id.changeNickname);
        changeNicknameGuide = findViewById(R.id.changeNicknameGuide);
        length = findViewById(R.id.length);

        Call<User2> call = RetrofitClient.getApiService().getUser2(userId);
        call.enqueue(new Callback<User2>() {
            @Override
            public void onResponse(Call<User2> call, Response<User2> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    if (user.getProfileImage() != null) {
                        if(user.getProfileImage().startsWith("http://")){
                            beforeImage = null;
                            Glide.with(getApplicationContext()).load(user.getProfileImage()).circleCrop().into(profileImage);
                        }
                        else{
                            String fileName = user.getProfileImage();
                            beforeImage = fileName;
                            fileName = fileName.substring(1, fileName.length() - 1);
                            Glide.with(getApplicationContext()).load("https://starry-night.s3.ap-northeast-2.amazonaws.com/profileImage/" + fileName).circleCrop().into(profileImage);
                        }
                    }
                    changeNickname.setText(user.getNickName());
                    beforeNickName = user.getNickName();
                } else {
                    Log.d(TAG, "사용자 정보 불러오기 실패");
                }
            }
            @Override
            public void onFailure(Call<User2> call, Throwable t) {
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
                String input = changeNickname.getText().toString();
                length.setText(input.length() + " / 15"); //실시간 글자수
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
                if(!isProfileImageChange && beforeNickName.equals(changeNickname.getText().toString())){
                    finish();
                }
                else{
                    if (!isNickNameEmpty && !isNotNickName && !isNickNameDuplicate) {
                        changeNicknameGuide.setText("");

                        //닉네임 변경 put api
                        Call<Void> call = RetrofitClient.getApiService().updateNickName(userId, changeNickname.getText().toString());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Log.d(TAG, "닉네임 변경 성공");

                                    //프로필 사진 변경 put api
                                    Log.d(TAG, "파일 이름 = " + fileName);
                                    //if(beforeImage != null) deleteS3File(beforeImage); //이전 사진 삭제
                                    uploadWithTransferUtility(fileName, file); //s3 사진 업로드

                                    Call<Void> call2 = RetrofitClient.getApiService().updateProfileImage(userId, fileName);
                                    call2.enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call2, Response<Void> response) {
                                            if (response.isSuccessful()) {
                                                Log.d(TAG, "프로필 사진 변경 성공");
                                                finish(); //종료
                                            } else {
                                                Log.e(TAG, "프로필 사진 변경 실패");
                                                Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<Void> call2, Throwable t) {
                                            Log.e("연결실패", t.getMessage());
                                            Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Log.d(TAG, "닉네임 변경 실패");
                                    Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("연결실패", t.getMessage());
                                Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });


        //뒤로 가기
        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                    Bitmap croppedBitmap = getCroppedBitmap(img);
                    profileImage.setImageBitmap(croppedBitmap);

                    Uri uri = data.getData();
                    file = new File(getRealPathFromURI(uri));
                    Log.d(TAG, "파일 이름 = " + fileName);
                    fileName = "PI" + userId + "_" + file.getName();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "사진 선택 취소");
            }
        }
    }

    // 비트맵 둥글게 자르는 함수
    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    // 이미지 uri 경로 함수
    public String getRealPathFromURI(Uri contentUri) {
        String [] proj={MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    //s3에 사진업로드 하는 함수
    public void uploadWithTransferUtility(String fileName, File file) {

        AWSCredentials awsCredentials = new BasicAWSCredentials("AKIA56KLCEH5WNTFY4OK", "RSuNQ5qtPpMu1c1zojcfAmTbwfA4QZ6Zq8uDuOiM");    // IAM 생성하며 받은 것 입력
        AmazonS3Client s3Client = new AmazonS3Client(awsCredentials, Region.getRegion(Regions.AP_NORTHEAST_2));

        TransferUtility transferUtility = TransferUtility.builder().s3Client(s3Client).context(getApplicationContext()).build();
        TransferNetworkLossHandler.getInstance(getApplicationContext());

        TransferObserver uploadObserver = transferUtility.upload("starry-night/profileImage", fileName, file);    // (bucket api, file이름, file객체)

        uploadObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (state == TransferState.COMPLETED) {
                    Log.d("TAG", "onStateChanged: " + id + ", " + state.toString());
                }
            }

            @Override
            public void onProgressChanged(int id, long current, long total) {
                int done = (int) (((double) current / total) * 100.0);
                Log.d("MYTAG", "UPLOAD - - ID: $id, percent done = $done");
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.d("MYTAG", "UPLOAD ERROR - - ID: $id - - EX:" + ex.toString());
            }
        });
    }

//    public void deleteS3File(String key){
//        AWSCredentials awsCredentials = new BasicAWSCredentials("AKIA56KLCEH5WNTFY4OK", "RSuNQ5qtPpMu1c1zojcfAmTbwfA4QZ6Zq8uDuOiM");
//        AmazonS3Client s3Client = new AmazonS3Client(awsCredentials, Region.getRegion(Regions.AP_NORTHEAST_2));
//
//        try {
//            //Delete 객체 생성
//            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest("starry-night/profileImage", key);
//            //Delete
//            s3Client.deleteObject(deleteObjectRequest);
//            System.out.println(String.format("[%s] deletion complete", key));
//
//        } catch (AmazonServiceException e) {
//            e.printStackTrace();
//        } catch (SdkClientException e) {
//            e.printStackTrace();
//        }
//    }

    //닉네임 규칙 함수
    private Boolean isCorrectNickName(String nickName) {
        String pattern = "^[[ㄱ-ㅎㅏ-ㅢ가-힣0-9a-zA-z ]*]{1,15}$";
        return Pattern.matches(pattern, nickName);
    }

    //닉네임 가이드 함수
    private void showNickNameGuide(CharSequence s) {
        String text = s.toString();
        if (text.isEmpty()) {
            isNickNameEmpty = true;
            changeNicknameGuide.setText("닉네임을 입력해주세요.");
        } else if (!isCorrectNickName(text)) {
            changeNicknameGuide.setText(getString(R.string.changeprofile_error));
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
                            //changeNicknameGuide.setText("사용가능한 닉네임입니다.");
                            isNickNameEmpty = false;
                            isNotNickName = false;
                            isNickNameDuplicate = false;
                        } else if (!result) {
                            changeNicknameGuide.setText("중복된 닉네임입니다.");
                            isNickNameEmpty = false;
                            isNotNickName = false;
                            isNickNameDuplicate = true;
                        }
                    } else {
                        Log.d(TAG, "중복 체크 실패");
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