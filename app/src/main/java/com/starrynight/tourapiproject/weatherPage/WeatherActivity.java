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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

    String API_KEY;

    {
        try {
            API_KEY = URLDecoder.decode("%2BbGNCh8qjhDibGZBmk6VZpWQNDaE9ePej4RbIqtZWnGBScQJshf4ELZgbQj5pqfAtnJPGU7ggOsyK0RmLDJlTQ%3D%3D", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        onClickBackBtn();
        onClickCloudInfo();

        onSetDatePicker();
        onSetTimePicker();

        Call<WtMetModel> getWeatherInstance = WtMetRetrofit.wtMetInterface()
                .getMetData(API_KEY, "50", "1", "JSON", "20210729", "0500", "59", "127");
        getWeatherInstance.enqueue(weeklyWeatherCallback);

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

    //기상청 API 연결
    private Callback<WtMetModel> weeklyWeatherCallback = new Callback<WtMetModel>() {
        @Override
        public void onResponse(Call<WtMetModel> call, Response<WtMetModel> response) {
            if (response.isSuccessful()) {
                WtMetModel data = response.body();
                TextView textView = findViewById(R.id.wt_cloud_info);
                textView.setText(data.getResponse().getBody().getTotalCount());
                Log.d("My Tag", "response= " + response.raw().request().url().url());
            }

        }

        @Override
        public void onFailure(Call<WtMetModel> call, Throwable t) {
            t.printStackTrace();
            Log.v("My Tag", "response= " + call.request().url());
        }
    };
}