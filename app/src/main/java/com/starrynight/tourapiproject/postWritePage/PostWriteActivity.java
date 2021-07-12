package com.starrynight.tourapiproject.postWritePage;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.starrynight.tourapiproject.R;

import java.io.InputStream;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

public class PostWriteActivity extends AppCompatActivity {
    final int PICK_IMAGE = 201;
    private TextView textView;
    private Button addPicture;
    private ImageView imageView;

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

        imageView = findViewById(R.id.imageView);

        // + 버튼 클릭 이벤트
        addPicture = findViewById(R.id.addPicture);
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_IMAGE);
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

        textView = findViewById(R.id.textView);
        //작성란 클릭 이벤트
        EditText editText = findViewById(R.id.editText);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equals("내용을 입력해주세요.")){
                    editText.setText("");
                }
            }
        });

        //관측지점검색 버튼 클릭 이벤트
        Button observingPoint = findViewById(R.id.observingPoint);
        observingPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchObservingPointActivity.class);
                startActivityForResult(intent, 202);
            }
        });

    }

    @Override //갤러리에서 이미지 불러온 후 행동
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지뷰에 세팅
                    imageView.setImageBitmap(img);

                    //설명, 버튼 숨기기
                    textView.setVisibility(View.INVISIBLE);
                    addPicture.setVisibility(View.INVISIBLE);
                    System.out.println("was hidden!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
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