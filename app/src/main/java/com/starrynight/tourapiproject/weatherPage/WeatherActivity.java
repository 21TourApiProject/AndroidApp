package com.starrynight.tourapiproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

public class WeatherActivity extends AppCompatActivity {

    private TextView wtDatePicker;
    private TextView wtTimePicker;

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            wtDatePicker.setText(month + "월 " + dayOfMonth + "일");
        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            wtTimePicker.setText(hourOfDay + "시");
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ImageButton button = findViewById(R.id.wt_back_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        wtDatePicker = findViewById(R.id.wt_datePicker);

        // 기상청 구름 정보 페이지로 이동
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

    public void wtClickDatePicker(View view) {
        WtDatePickerDialog pd = new WtDatePickerDialog();
        pd.setListener(d);
        pd.setListenerT(t);
        pd.show(getFragmentManager(), "test");
    }

    public void wtClickTimePicker(View view){

    }

    public void wtClickAreaPicker(View view) {

    }

}