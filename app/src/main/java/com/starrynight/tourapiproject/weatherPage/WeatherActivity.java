package com.starrynight.tourapiproject.weatherPage;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.weatherPage.weatherMetModel.WtMetModel;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {

    //Picker 관련
    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);

    private TextView datePicker;
    private DatePickerDialog.OnDateSetListener dateListener;
    private TextView timePicker;
    private TimePickerDialog.OnTimeSetListener timeListener;

    private static final String API_KEY = "%2BbGNCh8qjhDibGZBmk6VZpWQNDaE9ePej4RbIqtZWnGBScQJshf4ELZgbQj5pqfAtnJPGU7ggOsyK0RmLDJlTQ%3D%3D";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        onClickBackBtn();
        onClickCloudInfo();

        onSetDatePicker();
        onSetTimePicker();

        WtMetInterface wtMetInterface = WtMetRetrofit.create();
        wtMetInterface.getMetData(API_KEY, 50,1, "JSON","20210728","0500", 59, 127)
                .enqueue(new Callback<WtMetModel>() {
                            @Override
                            public void onResponse(Call<WtMetModel> call, Response<WtMetModel> response) {
                                Log.d("checkcheck", "성공");
                            }

                            @Override
                            public void onFailure(Call<WtMetModel> call, Throwable t) {
                                Log.d("checkcheck", t.getMessage());
                            }
                        }
                );



    }

    //뒤로가기 버튼 이벤트
    public void onClickBackBtn() {
        ImageButton button = findViewById(R.id.wt_back_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //기상청 구름 정보 페이지로 이동
    public void onClickCloudInfo() {
        Button button1 = findViewById(R.id.wt_today_cloud);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.weather.go.kr/w/index.do"));
                startActivity(intent);
            }
        });
    }

    //날짜 설정
    public void onSetDatePicker() {
        datePicker = (TextView) findViewById(R.id.wt_datePicker);
        datePicker.setText(mYear + "." + (mMonth + 1) + "." + mDay);
        dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear += 1;
                datePicker.setText(year + "." + monthOfYear + "." + dayOfMonth);
            }
        };
    }

    //날짜 선택 이벤트
    public void wtClickDatePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateListener, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.show();

//        Button confirmBtn = (Button)datePickerDialog.getButton(datePickerDialog.BUTTON_POSITIVE);
//        confirmBtn.setOnClickListener(new View.OnClickListener(){
//
//        });
    }

    //시간 설정
    public void onSetTimePicker() {
        timePicker = (TextView) findViewById(R.id.wt_timePicker);
        timeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timePicker.setText(hourOfDay + " : " + minute);
            }
        };
    }

    //시간 선택 이벤트
    public void wtClickTimePicker(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, timeListener, 15, 0, false);
        timePickerDialog.show();
    }

    public void wtClickAreaPicker(View view) {

    }
}