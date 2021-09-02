package com.starrynight.tourapiproject.weatherPage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.weatherPage.wtAppearTimeModel.WtAppearTimeModel;
import com.starrynight.tourapiproject.weatherPage.wtMetModel.WtMetModel;
import com.starrynight.tourapiproject.weatherPage.wtMetModel.WtMetRetrofit;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {

    //Picker 관련
    Calendar c = Calendar.getInstance();
    Calendar cal = Calendar.getInstance();
    Calendar today = Calendar.getInstance();

    SimpleDateFormat formatDate2 = new SimpleDateFormat("yyyyMMdd");

    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);

    private TextView datePicker;
    private DatePickerDialog.OnDateSetListener dateListener;
    private TextView timePicker;
    private TimePickerDialog.OnTimeSetListener timeListener;

    String WT_MET_API_KEY;
    String WT_APPEAR_TIME_API_KEY;

    String strDate;


    ArrayAdapter<CharSequence> cityAdSpin, provAdSpin;
    String choice_do = "";
    String choice_se = "";

    // 선택한 날짜 + 시간
    String selectDateTime;
    String todayDateTime;

    String selectDate;
    String selectTime;

    String todayDate;
    String todayTime;

    String plusTwoDay;

    private String hour[] = {"00", "01", "02", "03", "04", "05"
            , "06", "07", "08", "09", "10", "11", "12", "13", "14", "15"
            , "16", "17", "18", "19", "20", "21", "22", "23"};

    ArrayList<String> list1 = new ArrayList<>(Arrays.asList(hour));
    ArrayList<String> list2 = new ArrayList<>(Arrays.asList(hour));

    private String hourChange[];

    public NumberPicker numberPicker;


    private Button btnConfirm;
    private Button btnCancel;

    Date sDate;
    Date tDate;


    {
        try {
            WT_MET_API_KEY = URLDecoder.decode("7c7ba4d9df15258ce566f6592d875413", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    {
//        try {
//            WT_APPEAR_TIME_API_KEY = URLDecoder.decode("%2BbGNCh8qjhDibGZBmk6VZpWQNDaE9ePej4RbIqtZWnGBScQJshf4ELZgbQj5pqfAtnJPGU7ggOsyK0RmLDJlTQ%3D%3D", "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        onClickBackBtn();
        onClickCloudInfo();

        onSetDatePicker();
        onSetTimePicker();


        selectDateTime = selectDate + selectTime;
        Log.d("todayDateTime", selectDateTime);

        connectMetApi();


        //출몰시각 API 연결
//        Call<WtAppearTimeModel> getAppearTimeInstance = WtAppearTimeRetrofit.wtAppearTimeInterface()
//                .getAppearTimeData(WT_APPEAR_TIME_API_KEY, "고양", "20210730");
//        getAppearTimeInstance.enqueue(appearTimeModelCallback);

        //지역 선택 Spinner
        onSetAreaSpinner();
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

        selectDate = formatDate2.format(cal.getTime());
        todayDate = formatDate2.format(cal.getTime());
        Log.d("todayDate", todayDate);

        datePicker = (TextView) findViewById(R.id.wt_datePicker);

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy.MM.dd");
        strDate = formatDate.format(cal.getTime());
        Log.d("selectDate", selectDate);

        datePicker.setText(strDate);
        dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //monthOfYear += 1;
                cal.set(year, monthOfYear, dayOfMonth);
                strDate = formatDate.format(cal.getTime());
                Log.d("strDate", strDate);
                datePicker.setText(strDate);
                //20210901
                selectDate = String.valueOf(year) + String.valueOf(String.format("%02d", (monthOfYear + 1))) + String.valueOf(String.format("%02d", (dayOfMonth)));
                Log.d("selectDate", selectDate);

                selectDateTime = selectDate + selectTime;
                Log.d("selectDateTime", selectDateTime);
            }
        };
    }

    //날짜 선택 이벤트
    public void wtClickDatePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateListener, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(today.getTimeInMillis());
        today.add(Calendar.DAY_OF_MONTH, 8);
        datePickerDialog.getDatePicker().setMaxDate(today.getTimeInMillis());
        datePickerDialog.show();
        today.add(Calendar.DAY_OF_MONTH, -8);
    }

    // 시간 설정
    public void onSetTimePicker() {
        timePicker = findViewById(R.id.wt_timePicker);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyyMMddHH");
        todayDateTime = formatDateTime.format(cal.getTime());
        Log.d("todayDateTime", todayDateTime);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatTime = new SimpleDateFormat("HH");

        todayTime = formatTime.format(cal.getTime());
        selectTime = todayTime;
        Log.d("selectTime", selectTime);

        timePicker.setText(todayTime + "시");
        Log.d("strTime", todayTime);

//        timeListener = new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//
//                Log.d("hourOfDay", String.valueOf(hourOfDay));
//                timePicker.setText(String.format("%02d시", hourOfDay));
//                selectTime = String.format("%02d", hourOfDay);
//                Log.d("selectTime", selectTime);
//
//                selectDateTime = selectDate + selectTime;
//                Log.d("selectDateTime", selectDateTime);
//            }
//        };
    }

    //시간 선택 이벤트
    public void wtClickTimePicker(View view) {
        timePicker = findViewById(R.id.wt_timePicker);

        View dialogView = getLayoutInflater().inflate(R.layout.wt_dialog_timepicker, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        numberPicker = (NumberPicker) alertDialog.findViewById(R.id.hourPicker);

        try {
            cal.setTime(Objects.requireNonNull(formatDate2.parse(selectDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.add(Calendar.DATE, 2);
        plusTwoDay = formatDate2.format(cal.getTime());
        plusTwoDay += selectTime;
        Log.d("plusTwoDay", plusTwoDay);

        if (todayDate.equals(selectDate)) {
            if (todayTime.equals("01")) {
                setMinHour(0);
            } else if (todayTime.equals("02")) {
                setMinHour(1);
            } else if (todayTime.equals("03")) {
                setMinHour(2);
            } else if (todayTime.equals("04")) {
                setMinHour(3);
            } else if (todayTime.equals("05")) {
                setMinHour(4);
            } else if (todayTime.equals("06")) {
                setMinHour(5);
            } else if (todayTime.equals("07")) {
                setMinHour(6);
            } else if (todayTime.equals("08")) {
                setMinHour(7);
            } else if (todayTime.equals("09")) {
                setMinHour(8);
            } else if (todayTime.equals("10")) {
                setMinHour(9);
            } else if (todayTime.equals("11")) {
                setMinHour(10);
            } else if (todayTime.equals("12")) {
                setMinHour(11);
            } else if (todayTime.equals("13")) {
                setMinHour(12);
            } else if (todayTime.equals("14")) {
                setMinHour(13);
            } else if (todayTime.equals("15")) {
                setMinHour(14);
            } else if (todayTime.equals("16")) {
                setMinHour(15);
            } else if (todayTime.equals("17")) {
                setMinHour(16);
            } else if (todayTime.equals("18")) {
                setMinHour(17);
            } else if (todayTime.equals("19")) {
                setMinHour(18);
            } else if (todayTime.equals("20")) {
                setMinHour(19);
            } else if (todayTime.equals("21")) {
                setMinHour(20);
            } else if (todayTime.equals("22")) {
                setMinHour(21);
            } else if (todayTime.equals("23")) {
                setMinHour(22);
            }
        }

        Button btnConfirm = dialogView.findViewById(R.id.wt_btn_confirm);
        Button btnCancel = dialogView.findViewById(R.id.wt_btn_cancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1 = hourChange[numberPicker.getValue()] + "시";
                timePicker.setText(text1);

                selectTime = String.valueOf(hour[numberPicker.getValue()]);
                Log.d("selectTime", selectTime);

                selectDateTime = selectDate + selectTime;
                Log.d("selectDateTime", selectDateTime);
                alertDialog.dismiss();
            }
        });

//        hourChange = list1.toArray(new String[0]);
//
//        Log.d("dkdk", list1.toString());

//        numberPicker.setMinValue(0);
//        numberPicker.setMaxValue(hourChange.length - 1);
//        numberPicker.setDisplayedValues(hourChange);
//        numberPicker.setValue(cal.get(Calendar.HOUR_OF_DAY));

    }


    public void connectMetApi() {
        //기상청 API 연결
        Call<WtMetModel> getWeatherInstance = WtMetRetrofit.wtMetInterface()
                .getMetData(37.56666, 126.9015, "minutely,current", WT_MET_API_KEY, "metric");
        getWeatherInstance.enqueue(weatherMetCallback);
    }

    //기상청 API 연결
    private Callback<WtMetModel> weatherMetCallback = new Callback<WtMetModel>() {
        @Override
        public void onResponse(Call<WtMetModel> call, Response<WtMetModel> response) {
            if (response.isSuccessful()) {
                WtMetModel data = response.body();
                TextView textView = findViewById(R.id.wt_cloud);
                textView.setText(data.getTimezone());
                Log.d("My Tag", "response= " + response.raw().request().url().url());
            }

        }

        @Override
        public void onFailure(Call<WtMetModel> call, Throwable t) {
            t.printStackTrace();
            Log.v("My Tag", "responseError= " + call.request().url());
        }
    };

    //출몰시각 API 연결
    private Callback<WtAppearTimeModel> appearTimeModelCallback = new Callback<WtAppearTimeModel>() {
        @Override
        public void onResponse(Call<WtAppearTimeModel> call, Response<WtAppearTimeModel> response) {
            if (response.isSuccessful()) {
                WtAppearTimeModel data = response.body();
                TextView textView1 = findViewById(R.id.wt_cloud);
                textView1.setText(data.getResponse().getBody().getTotalCount());
                Log.d("AppearTime", "response= " + response.raw().request().url().url());
            }

        }

        @Override
        public void onFailure(Call<WtAppearTimeModel> call, Throwable t) {
            t.printStackTrace();
            Log.v("AppearTime", "responseError= " + call.request().url());
        }
    };

    //시,도 Spinner 동작
    public void onSetAreaSpinner() {
        final Spinner citySpinner = (Spinner) findViewById(R.id.wt_citySpinner);
        final Spinner provSpinner = (Spinner) findViewById(R.id.wt_provinceSpinner);

        cityAdSpin = ArrayAdapter.createFromResource(this, R.array.wt_cityList, android.R.layout.simple_spinner_dropdown_item);
        cityAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdSpin);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (cityAdSpin.getItem(position).equals("서울")) {
                    choice_do = "서울";
                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Seoul, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("경기")) {
                    choice_do = "경기";
                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Gyeonggi, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("인천")) {
                    choice_do = "인천";
                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Incheon, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("강원")) {
                    choice_do = "강원";
                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Gangwon, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("충북")) {
                    choice_do = "충북";
                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Chungbuk, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("충남/대전/세종")) {
                    choice_do = "충남/대전/세종";
                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_ChungnamDaejeonSejong, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("광주/전북")) {
                    choice_do = "광주/전북";
                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_GwangjuJeonbuk, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("전남")) {
                    choice_do = "전남";
                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Jeonnam, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("대구/경북")) {
                    choice_do = "대구/경북";
                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_DaeguGyeongbuk, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("경남")) {
                    choice_do = "경남";
                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Gyeongnam, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("부산/울산")) {
                    choice_do = "부산/울산";
                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_BusanUlsan, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("제주")) {
                    choice_do = "제주";
                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Jeju, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setMinHour(int num) {
        for (int i = 0; i < num + 1; i++) {
            String remove = list2.remove(0);
            Log.d("remove", remove);
        }
        hourChange = list2.toArray(new String[0]);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(hourChange.length - 1);
        numberPicker.setDisplayedValues(hourChange);

        for (int i = 0; i < num + 1; i++) {
            list2.add(i, hour[i]);
        }
    }
}