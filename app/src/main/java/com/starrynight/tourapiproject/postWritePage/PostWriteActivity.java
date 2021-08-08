package com.starrynight.tourapiproject.postWritePage;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.postPage.PostActivity;
import com.starrynight.tourapiproject.postPage.postRetrofit.Post;
import com.starrynight.tourapiproject.postPage.postRetrofit.PostImage;
import com.starrynight.tourapiproject.postWritePage.postWriteRetrofit.PostHashTagParams;
import com.starrynight.tourapiproject.postWritePage.postWriteRetrofit.PostImageParams;
import com.starrynight.tourapiproject.postWritePage.postWriteRetrofit.PostParams;
import com.starrynight.tourapiproject.postWritePage.postWriteRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.signUpPage.signUpRetrofit.UserParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostWriteActivity extends AppCompatActivity {

    final int PICK_IMAGE_SAMSUNG = 200;
    final int PICK_IMAGE_MULTIPLE = 201;
    int numOfPicture = 0;
    private Button addPicture;
    SelectImageAdapter adapter;
    RecyclerView recyclerView;
    String postContent,observeFit,yearDate,time;
    String postImage;
    PostParams postParams;

    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH) + 1;

    private TextView datePicker;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private TextView timePicker;
    private TimePickerDialog.OnTimeSetListener callbackMethod2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_write);

        // + 버튼 클릭 이벤트
        addPicture = findViewById(R.id.addPicture);
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numOfPicture >= 10){
                    Toast.makeText(PostWriteActivity.this, "사진은 최대 10장까지 선택할수있습니다.", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent("android.intent.action.MULTIPLE_PICK");
                intent.setType("image/*");
                PackageManager manager = getApplicationContext().getPackageManager();
                List<ResolveInfo> infos = manager.queryIntentActivities(intent, 0);


                if (infos.size() > 0) { //테스트 하고 삼성,일반 차이없으면 삭제 예정
                    Log.e("FAT=","삼성폰");
                    startActivityForResult(intent, PICK_IMAGE_SAMSUNG);
                } else {
                    Log.e("FAT=","일반폰");
                    Intent pickerIntent = new Intent(Intent.ACTION_PICK);
                    pickerIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    pickerIntent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                    pickerIntent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickerIntent, PICK_IMAGE_MULTIPLE);

//                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // 일반폰 - 반드시 있어야 다중선택 가능
//                    intent.setAction(Intent.ACTION_PICK); // ACTION_GET_CONTENT 사용불가 - 엘지 G2 테스트
//                    startActivityForResult(intent, PICK_IMAGE_MULTIPLE);

//                    Intent intent = new Intent(Intent.ACTION_PICK);
//                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//                    //intent.setType("image/*");
//                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                    startActivityForResult(Intent.createChooser(intent, "사진 최대 9장 선택가능"), PICK_IMAGE_MULTIPLE);
                }
            }

        });

        //선택한 사진 추가 어댑터
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SelectImageAdapter();
        recyclerView.setAdapter(adapter);

        //사진 삭제 버튼 클릭 이벤트
        adapter.setOnSelectImageItemClickListener(new OnSelectImageItemClickListener() {
            @Override
            public void onItemClick(SelectImageAdapter.ViewHolder holder, View view, int position) {
                System.out.println("position = " + position);
                adapter.removeItem(position);
                adapter.notifyDataSetChanged();
                numOfPicture --;
                addPicture.setText(Integer.toString(numOfPicture) + "/10");
            }
        });

        //날짜 클릭 이벤트
        datePicker = (TextView) findViewById(R.id.datePicker);
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear += 1;
                String month = Integer.toString(monthOfYear);
                String day = Integer.toString(dayOfMonth);

                if (monthOfYear < 10){
                    month = "0"+ Integer.toString(monthOfYear);
                }

                if(dayOfMonth < 10){
                    day = "0"+ Integer.toString(dayOfMonth);
                }
               datePicker.setText(year + "-" + month + "-" + day);
               yearDate = datePicker.getText().toString();

            }
        };

        //시간 클릭 이벤트
        timePicker = (TextView) findViewById(R.id.timePicker);
        callbackMethod2 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timePicker.setText(hourOfDay + ":" + minute+":" + "00" );
                time = timePicker.getText().toString();
            }
        };

        //관측지점검색 버튼 클릭 이벤트
        Button observingPoint = findViewById(R.id.observingPoint);
        observingPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchObservingPointActivity.class);
                startActivityForResult(intent, 202);
            }
        });

        //해시태그추가 버튼 클릭 이벤트
        Button addHashTag = findViewById(R.id.hashTag);
        addHashTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddHashTagActivity.class);
                startActivityForResult(intent, 203);
            }
        });

        Button save_btn = findViewById(R.id.save);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numOfPicture == 0){
                    Toast.makeText(getApplicationContext(), "사진을 추가해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                postContent= ((EditText)(findViewById(R.id.postContentText))).getText().toString();
                if(postContent.isEmpty()){
                    Toast.makeText(getApplicationContext(), "게시물 내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(yearDate.isEmpty()){
                    Toast.makeText(getApplicationContext(), "관측 날짜을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(time.isEmpty()){
                Toast.makeText(getApplicationContext(), "관측 시간을 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
                }
                Intent intent = getIntent();
                postParams = new PostParams(postContent,observeFit,yearDate,time,1l);
                postParams.setObserveFit("관광지");
                Call<Void>call = RetrofitClient.getApiService().postup(postParams);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            System.out.println("post 성공");
                        }else{ System.out.println("post 실패");}
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        System.out.println("post 실패");
                    }
                });

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == PICK_IMAGE_SAMSUNG) { //삼성폰 일때
            final Bundle extras = data.getExtras();
            int count = extras.getInt("selectedCount");
            Object items = extras.getStringArrayList("selectedItems");

            Log.e("FAT=", "삼성폰 : " + items.toString());
        }
        else { //일반폰/단일 일때
            if (data != null && data.getData() != null) {
                Uri uri = data.getData();
                Log.e("FAT=", "일반폰/단일 : "+uri.toString());
                try {
                    Bitmap img = resize(this, uri, 75);
                    System.out.println("img = " + img);
                    addImage(img);
                    postImage = BitmapToFile(img,"postImage");
                    System.out.println("file = "+postImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else { //일반폰/다중 일때
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ClipData clipData = data.getClipData();
                    if (clipData != null) {
                        if (numOfPicture + clipData.getItemCount() >= 10){
                            Toast.makeText(PostWriteActivity.this, "사진은 최대 10장까지 선택할수있습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        ArrayList<Uri> uris = new ArrayList<>();
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            ClipData.Item item = clipData.getItemAt(i);
                            Uri uri = item.getUri();
                            Log.e("FAT=", "일반폰/다중 : "+uri.toString());
                            uris.add(uri);
                        }
                        addImages(uris);
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //Bitmap을 File로 변경하는 함수
    public String BitmapToFile(Bitmap bitmap, String image) {
        File storage = getFilesDir();
        String fileName = image + ".jpg";
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

    private void addImage(Bitmap img) {
        numOfPicture ++;
        addPicture.setText(Integer.toString(numOfPicture) + "/10");

        adapter.addItem(new SelectImage(img, numOfPicture));
        recyclerView.setAdapter(adapter);
    }

    private void addImages(ArrayList<Uri> uris) {
        for (Uri uri : uris){
            numOfPicture ++;
            try {
//                InputStream in = getContentResolver().openInputStream(uri);
//                Bitmap img = BitmapFactory.decodeStream(in);
//                in.close();

                Bitmap img = resize(this, uri, 75); //해상도 최대로 하고싶으면 100으로
                System.out.println("img = " + img);
                adapter.addItem(new SelectImage(img, numOfPicture));
                recyclerView.setAdapter(adapter);
                postImage = BitmapToFile(img,"postImage");
                System.out.println("file = "+postImage);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        addPicture.setText(Integer.toString(numOfPicture) + "/10");
    }

    public void onClickDatePicker(View view){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, callbackMethod, mYear, mMonth, mDay);
        //datePickerDialog.getDatePicker().setCalendarViewShown(false);
        //datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        datePickerDialog.show();

    }
    public void onClickTimePicker(View view){
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, callbackMethod2, 15, 24, false);
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

    private Bitmap resize(Context context, Uri uri, int resize){
        Bitmap resizeBitmap=null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);

            int width = options.outWidth;
            int height = options.outHeight;
            int samplesize = 1;

            while (true) {
                if (width / 2 < resize || height / 2 < resize)
                    break;
                width /= 2;
                height /= 2;
                samplesize *= 2;
            }
            options.inSampleSize = samplesize;
            Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
            resizeBitmap = bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return resizeBitmap;
    }
}