package com.starrynight.tourapiproject.postWritePage;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PostWriteActivity extends AppCompatActivity {

    final int PICK_IMAGE_SAMSUNG = 200;
    final int PICK_IMAGE_MULTIPLE = 201;
    int numOfPicture = 0;
    LinearLayout dynamicLayout;
    private Button addPicture;

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

                Intent intent = new Intent("android.intent.action.MULTIPLE_PICK");
                intent.setType("image/*");
                PackageManager manager = getApplicationContext().getPackageManager();
                List<ResolveInfo> infos = manager.queryIntentActivities(intent, 0);

                if (infos.size() > 0) { //테스트 하고 삼성,일반 차이없으면 삭제 예정
                    Log.e("FAT=","삼성폰");
                    startActivityForResult(Intent.createChooser(intent, "사진을 선택해주세요"), PICK_IMAGE_SAMSUNG);
                } else {
                    Log.e("FAT=","일반폰");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // 일반폰 - 반드시 있어야 다중선택 가능
                    intent.setAction(Intent.ACTION_PICK); // ACTION_GET_CONTENT 사용불가 - 엘지 G2 테스트
                    startActivityForResult(Intent.createChooser(intent,"여러장을 선택하려면 갤러리를 클릭해주세요"), PICK_IMAGE_MULTIPLE);

//                    Intent intent = new Intent(Intent.ACTION_PICK);
//                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//                    //intent.setType("image/*");
//                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                    startActivityForResult(Intent.createChooser(intent, "사진 최대 9장 선택가능"), PICK_IMAGE_MULTIPLE);
                }
            }
        });

        //날짜 클릭 이벤트
        datePicker = (TextView) findViewById(R.id.datePicker);
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear += 1;
                datePicker.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
            }
        };

        //시간 클릭 이벤트
        timePicker = (TextView) findViewById(R.id.timePicker);
        callbackMethod2 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timePicker.setText(hourOfDay + " : " + minute);
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

    }


//    @Override //갤러리에서 이미지 불러온 후 행동
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == PICK_IMAGE) {
//            if (resultCode == RESULT_OK) {
//                try {
//                    // 선택한 이미지에서 비트맵 생성
//                    InputStream in = getContentResolver().openInputStream(data.getData());
//                    Bitmap img = BitmapFactory.decodeStream(in);
//                    in.close();
//                    // 이미지뷰에 세팅
//                    imageView.setImageBitmap(img);
//
//                    //설명, 버튼 숨기기
//                    textView.setVisibility(View.INVISIBLE);
//                    addPicture.setVisibility(View.INVISIBLE);
//                    System.out.println("was hidden!");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        String imageEncoded;
//        try {
//            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
//                // Get the Image from data
//
//                String[] filePathColumn = { MediaStore.Images.Media.DATA };
//                ArrayList<String> imagesEncodedList = new ArrayList<String>();
//
//                if(data.getData()!=null){ //1장 선택했을 때
//                    System.out.println("처음");
//                    Uri mImageUri=data.getData();
//                    // Get the cursor
//                    Cursor cursor = getContentResolver().query(mImageUri, filePathColumn, null, null, null);
//                    // Move to first row
//                    cursor.moveToFirst();
//
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    imageEncoded  = cursor.getString(columnIndex);
//                    cursor.close();
//
//                } else { //여러장 선택했을 때
//                    if (data.getClipData() != null) {
//                        System.out.println("다음");
//                        ClipData mClipData = data.getClipData();
//                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
//
//                        for (int i = 0; i < mClipData.getItemCount(); i++) {
//                            ClipData.Item item = mClipData.getItemAt(i);
//                            Uri uri = item.getUri();
//                            mArrayUri.add(uri);
//                            // Get the cursor
//                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
//                            // Move to first row
//                            cursor.moveToFirst();
//
//                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                            imageEncoded  = cursor.getString(columnIndex);
//                            imagesEncodedList.add(imageEncoded);
//                            cursor.close();
//                        }
//                        Log.v("LOG_TAG", "# Of Selected Images: " + mArrayUri.size());
//                    }
//                }
//            } else {
//                Toast.makeText(this, "사진을 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
//            }
//        } catch (Exception e) {
//            Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_LONG).show();
//        }
//
//        //super.onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == PICK_IMAGE_SAMSUNG) { //삼성폰 일때
            final Bundle extras = data.getExtras();
            int count = extras.getInt("selectedCount");
            Object items = extras.getStringArrayList("selectedItems");
            // do somthing
            Log.e("FAT=", "삼성폰 : " + items.toString());
        }
        else { //일반폰/단일 일때
            if (data != null && data.getData() != null) {
                Uri uri = data.getData();
                Log.e("FAT=", "일반폰/단일 : "+uri.toString());
                try {
                    InputStream in = getContentResolver().openInputStream(uri);
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    //이미지 추가
                    addImage(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else { //일반폰/다중 일때
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ClipData clipData = data.getClipData();
                    if (clipData != null) {
                        ArrayList<Uri> uris = new ArrayList<>();
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            numOfPicture ++;
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

    private void addImage(Bitmap img) {
        numOfPicture ++;
        addPicture.setText("1/10");
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(img);
        imageView.setId(numOfPicture);

        final int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        dynamicLayout.addView(imageView, new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    private void addImages(ArrayList<Uri> uris) {
        for (Uri uri : uris){
            numOfPicture ++;
            try {
                InputStream in = getContentResolver().openInputStream(uri);
                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();

                addPicture.setText(Integer.toString(numOfPicture) + "/10");
                ImageView imageView = new ImageView(this);
                imageView.setImageBitmap(img);
                imageView.setId(numOfPicture);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.setMargins(20,0,0,0);
                dynamicLayout.addView(imageView, params);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onClickDatePicker(View view){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, callbackMethod, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        datePickerDialog.show();

    }
    public void onClickTimePicker(View view){
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, callbackMethod2, 15, 24, false);
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

}