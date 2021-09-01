package com.starrynight.tourapiproject.weatherPage;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.weatherPage.wtAppearTimeModel.WtAppearTimeModel;
import com.starrynight.tourapiproject.weatherPage.wtAppearTimeModel.WtAppearTimeRetrofit;
import com.starrynight.tourapiproject.weatherPage.wtMetModel.WtMetModel;
import com.starrynight.tourapiproject.weatherPage.wtMetModel.WtMetRetrofit;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {

    //Picker 관련
    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);
    double observationalFitDegree;
    double cloudVolume;
    double cloudVolumeValue;
    double temperature;
    double humidity;
    double temperatureAndhumidityValue;
    double moonAge;
    double moonAgeValue;
    String fineDust;
    double fineDustValue;
    double windSpeed;
    double windSpeedValue;
    double precipitationProbability;
    double precipitationProbabilityValue;
    double lightPollution;
    double lightPollutionValue;

    private TextView datePicker;
    private DatePickerDialog.OnDateSetListener dateListener;
    private TextView timePicker;
    private TimePickerDialog.OnTimeSetListener timeListener;

    String WT_MET_API_KEY;
    String WT_APPEAR_TIME_API_KEY;

    String strDate;
    String strTime;

    ArrayAdapter<CharSequence> cityAdSpin, provAdSpin;
    String choice_do = "";
    String choice_se = "";
    {
        try {
            WT_MET_API_KEY = URLDecoder.decode("%2BbGNCh8qjhDibGZBmk6VZpWQNDaE9ePej4RbIqtZWnGBScQJshf4ELZgbQj5pqfAtnJPGU7ggOsyK0RmLDJlTQ%3D%3D", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    {
        try {
            WT_APPEAR_TIME_API_KEY = URLDecoder.decode("%2BbGNCh8qjhDibGZBmk6VZpWQNDaE9ePej4RbIqtZWnGBScQJshf4ELZgbQj5pqfAtnJPGU7ggOsyK0RmLDJlTQ%3D%3D", "UTF-8");
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

        connectMetApi();
        //출몰시각 API 연결
        Call<WtAppearTimeModel> getAppearTimeInstance = WtAppearTimeRetrofit.wtAppearTimeInterface()
                .getAppearTimeData(WT_APPEAR_TIME_API_KEY, "고양", "20210730");
        getAppearTimeInstance.enqueue(appearTimeModelCallback);

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
        datePicker = (TextView) findViewById(R.id.wt_datePicker);
        //mMonth += 1;
        // datePicker.setText(mYear + "." + mMonth + "." + mDay);
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        strDate = formatDate.format(c.getTime());
        datePicker.setText(strDate);
        dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //monthOfYear += 1;
                c.set(year, monthOfYear, dayOfMonth);
                strDate = formatDate.format(c.getTime());
                //datePicker.setText(year + "." + monthOfYear + "." + dayOfMonth);
                datePicker.setText(strDate);
            }
        };
    }

    //날짜 선택 이벤트
    public void wtClickDatePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateListener, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    //시간 설정
    public void onSetTimePicker() {
        timePicker = (TextView) findViewById(R.id.wt_timePicker);

        SimpleDateFormat formatTime = new SimpleDateFormat("HHmm");
        strTime = formatTime.format(c.getTime());
        timePicker.setText(strTime);

        timeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = String.valueOf(hourOfDay) + String.valueOf(minute);
                Date date = null;
                try {
                    date = formatTime.parse(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // c.set(hourOfDay,minute);
                strTime = formatTime.format(date.getTime());
                timePicker.setText(strTime);
                //timePicker.setText(hourOfDay + " : " + minute);
            }
        };
    }

    //시간 선택 이벤트
    public void wtClickTimePicker(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, timeListener, 15, 0, false);
        timePickerDialog.show();
    }

    public void connectMetApi() {
        //기상청 API 연결
        Call<WtMetModel> getWeatherInstance = WtMetRetrofit.wtMetInterface()
                .getMetData(WT_MET_API_KEY, "50", "1", "JSON", strDate, "0500", "59", "127");
        getWeatherInstance.enqueue(weatherMetCallback);
    }

    //기상청 API 연결
    private Callback<WtMetModel> weatherMetCallback = new Callback<WtMetModel>() {
        @Override
        public void onResponse(Call<WtMetModel> call, Response<WtMetModel> response) {
            if (response.isSuccessful()) {
                WtMetModel data = response.body();
                TextView textView = findViewById(R.id.wt_cloud);
                //textView.setText(data.getResponse().getBody().getTotalCount());
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

    //관측적합도
    public double setObservationalFitDegree(){
        cloudVolumeValue = -cloudVolume*4/5;
        if (-((double)10/3)*temperature+((double)370/3)-humidity>0){
            temperatureAndhumidityValue = Math.round(-3*Math.log10(Math.pow(-(double)(10/3)*temperature+(double)(370/3)-humidity,2))-2);
            System.out.println("기온"+temperatureAndhumidityValue);
        }else if (-((double)10/3)*temperature+((double)370/3)-humidity==0){
            temperatureAndhumidityValue = 0;
        }else if (-((double)10/3)*temperature+((double)370/3)-humidity<0){
            temperatureAndhumidityValue=Math.round(-3*Math.log10(Math.pow(-((double)10/3)*temperature+((double)370/3)-humidity,2))-2);
            System.out.println("기온"+temperatureAndhumidityValue);
        }
        if (moonAge <= 0.5) {
            moonAgeValue= Math.round((-8*Math.pow(moonAge,3.46))/0.727*100);
        }else if (moonAge>0.5&&moonAge<=0.5609){
            moonAgeValue=Math.round((-75*Math.pow(moonAge-0.5,2)+0.727)/0.727*100);
        }else if (moonAge>0.5609){
            moonAgeValue=Math.round((-1/(5.6*Math.pow(moonAge+0.3493,10)))/0.727*100);
        }

        if (fineDust=="good"){
            fineDustValue = 0;
        }else if (fineDust =="normal"){
            fineDustValue = -5;
        }
        else if (fineDust == "bad"){
            fineDustValue = -20;
        }
        if(windSpeed <9.5){
            windSpeedValue=Math.round(-(100*(1/(-(1.9)*(windSpeed-10))-0.053)));
        }else if (windSpeed >= 9.5){
            windSpeedValue=-100;
        }
        precipitationProbabilityValue = Math.round(100*(-(1/(-(2.6)*(precipitationProbability/100-1.25))-0.31)));

        lightPollutionValue = Math.round(100*(-(1/(-(0.08)*(lightPollution/10-15.7))-0.8)));

        observationalFitDegree = 100 + cloudVolumeValue + temperatureAndhumidityValue+moonAgeValue+fineDustValue+windSpeedValue+precipitationProbabilityValue+lightPollutionValue;

        return observationalFitDegree;
    }

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
}