package com.starrynight.tourapiproject.myPage;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
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

import androidx.annotation.RequiresApi;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @className : ChangeProfileActivity.java
 * @description : 프로필 수정 페이지 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
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

    Uri uri;
    Bitmap btp;
    String fileName;

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
        setContentView(R.layout.activity_change_profile);

        Intent intent = getIntent();
        userId = (Long) intent.getSerializableExtra("userId"); //전 페이지에서 받아온 사용자 id

        profileImage = findViewById(R.id.profileImage3);
        changeNickname = findViewById(R.id.changeNickname);
        changeNicknameGuide = findViewById(R.id.changeNicknameGuide);
        length = findViewById(R.id.length);

        profileImage.setBackground(new ShapeDrawable(new OvalShape()));
        profileImage.setClipToOutline(true);

        Call<User2> call = RetrofitClient.getApiService().getUser2(userId);
        call.enqueue(new Callback<User2>() {
            @Override
            public void onResponse(Call<User2> call, Response<User2> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    if (user.getProfileImage() != null) {
                        if (user.getProfileImage().startsWith("http://") || user.getProfileImage().startsWith("https://")) {
                            beforeImage = null;
                            Glide.with(getApplicationContext()).load(user.getProfileImage()).into(profileImage);
                        } else {
                            beforeImage = user.getProfileImage();
                            beforeImage = beforeImage.substring(1, beforeImage.length() - 1);
                            Glide.with(getApplicationContext()).load("https://starry-night.s3.ap-northeast-2.amazonaws.com/profileImage/" + beforeImage).into(profileImage);
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
                startActivityForResult(intent, GET_GALLERY_IMAGE);
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });

        //저장 버튼
        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isProfileImageChange && beforeNickName.equals(changeNickname.getText().toString())) {
                    finish();
                } else {
                    if (!isNickNameEmpty && !isNotNickName && !isNickNameDuplicate) {
                        changeNicknameGuide.setText("");

                        //s3 사진 업로드
                        if (isProfileImageChange)
                            //if(beforeImage != null) deleteS3File(beforeImage); //이전 사진 삭제
                            uploadWithTransferUtility();

                        //닉네임 변경 put api
                        Call<Void> call = RetrofitClient.getApiService().updateNickName(userId, changeNickname.getText().toString());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Log.d(TAG, "닉네임 변경 성공");

                                    if (isProfileImageChange) { //프로필 사진 변경 put api
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
                                        finish(); //종료
                                    }

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
        ImageView back = findViewById(R.id.profileBack);
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
                    uri = data.getData();
                    InputStream in = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    in.close();
                    btp = rotateImage(uri, bitmap); //회전
                    profileImage.setImageBitmap(btp); //미리보기

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "사진 선택 취소");
            }
        }
    }

    // 이미지 uri 경로 함수
    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    //s3에 사진업로드 하는 함수
    public void uploadWithTransferUtility() {

        int width = 2000; // 축소시킬 너비
        int height = 1200; // 축소시킬 높이
        float bmpWidth = btp.getWidth();
        float bmpHeight = btp.getHeight();

        if (bmpWidth > width) {
            float mWidth = bmpWidth / 100;
            float scale = width / mWidth;
            bmpWidth *= (scale / 100);
            bmpHeight *= (scale / 100);
        } else if (bmpHeight > height) {
            float mHeight = bmpHeight / 100;
            float scale = height / mHeight;
            bmpWidth *= (scale / 100);
            bmpHeight *= (scale / 100);
        }
        Bitmap resizedBmp = Bitmap.createScaledBitmap(btp, (int) bmpWidth, (int) bmpHeight, true);

        Uri resizedUri = getImageUri(getApplicationContext(), resizedBmp);
        File resizedFile = new File(getRealPathFromURI(resizedUri));
        fileName = "PI" + userId + "_" + resizedFile.getName();
        Log.d(TAG, "새로운 파일 이름 = " + fileName);


        AWSCredentials awsCredentials = new BasicAWSCredentials(readAccessKey(), readSecretkey());    // IAM 생성하며 받은 것 입력
        AmazonS3Client s3Client = new AmazonS3Client(awsCredentials, Region.getRegion(Regions.AP_NORTHEAST_2));

        TransferUtility transferUtility = TransferUtility.builder().s3Client(s3Client).context(getApplicationContext()).build();
        TransferNetworkLossHandler.getInstance(getApplicationContext());

        TransferObserver uploadObserver = transferUtility.upload("starry-night/profileImage", fileName, resizedFile);    // (bucket api, file이름, file객체)

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Bitmap rotateImage(Uri uri, Bitmap bitmap) throws IOException {
        InputStream in = getContentResolver().openInputStream(uri);
        ExifInterface exif = new ExifInterface(in);
        in.close();
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        Matrix matrix = new Matrix();
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
            matrix.postRotate(90);
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
            matrix.postRotate(180);
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
            matrix.postRotate(270);
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    // bitmap -> uri
    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private String readAccessKey() {
        String data = null;
        InputStream inputStream = getResources().openRawResource(R.raw.access);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i;
        try {
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            data = new String(byteArrayOutputStream.toByteArray(), "MS949");

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private String readSecretkey() {
        String data = null;
        InputStream inputStream = getResources().openRawResource(R.raw.secret);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i;
        try {
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            data = new String(byteArrayOutputStream.toByteArray(), "MS949");

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

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
        } else if (!text.equals(user.getNickName())) {
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