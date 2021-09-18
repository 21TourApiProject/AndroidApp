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
import com.starrynight.tourapiproject.weatherPage.wtAreaRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.weatherPage.wtAreaRetrofit.WtAreaParams;
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

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat formatDate2 = new SimpleDateFormat("yyyyMMdd");

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat formatHourMin = new SimpleDateFormat("HH:mm");

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat formatHour = new SimpleDateFormat("HH");

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy.MM.dd");


    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyyMMddHH");

    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);
    double observationalFitDegree;
    double cloudVolume;
    double cloudVolumeValue;
    double feel_like;
    double feel_likeValue;
    double moonAge;
    double moonAgeValue;
    String fineDust;
    double fineDustValue;
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

    String timePickerTxt;

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

    String plusDay;
    String plusTwoDay;

    String setTime = "00시";
    String timeZero = "00";
    String timeNoon = "12";
    String setTimeNo = "선택 불가";
    String todayTimeTxt;

    private String hour[] = {"00", "01", "02", "03", "04", "05"
            , "06", "07", "08", "09", "10", "11", "12", "13", "14", "15"
            , "16", "17", "18", "19", "20", "21", "22", "23"};

    ArrayList<String> list1 = new ArrayList<>(Arrays.asList(hour));
    ArrayList<String> list2 = new ArrayList<>(Arrays.asList(hour));

    private String hourChange[];

    public NumberPicker numberPicker;


    private Button btnConfirm;
    private Button btnCancel;

    // unix 시간 관련
    String unixTime;
    String unixSunMoon;
    String unixSunrise;
    String unixSunset;
    String unixToDate;
    String unixToDay;
    String unixToHourMin;

    Date unixDate;
    Date unixDay;
    Date unixHourMin;

    //낮 길이
    String sunriseSt;
    String sunsetSt;

    Date sunriseTime;
    Date sunsetTime;

    long dayLength;
    int hourValue;
    int minValue;
    String diff;

    //강수량
    double doublePrecip;
    int intPrecip;
    String stringPrecip;

    // 날씨 TextView
    TextView commentTv;
    TextView todayWeatherTv;
    TextView starObFitTv;
    TextView bestObTimeTv;
    TextView sunriseTv;
    TextView sunsetTv;
    TextView moonriseTv;
    TextView moonsetTv;

    TextView cloudTv;
    TextView tempTv;
    TextView moonPhaseTv;
    TextView findDustTv;
    TextView windTv;
    TextView humidityTv;
    TextView precipitationTv;
    TextView minLightPolTv;
    TextView maxLightPolTv;

    String cloudText;
    String tempText;
    String tempText1;
    String windText;
    String humidityText;
    String precipitationText;

    TextView minTempTv;
    TextView maxTempTv;
    TextView tempSlashTv;

    //날씨 지역 관련
    String cityName;
    String provName;
    Double latitude;
    Double longitude;

    //광공해
    Double minLightPol;
    Double maxLightPol;
    String minLightPolVal;
    String maxLightPolVal;

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

        setTextView();
        onClickBackBtn();
        onClickCloudInfo();

        //지역 선택 Spinner
        onSetAreaSpinner();

        onSetDatePicker();
        onSetTimePicker();

        selectDateTime = selectDate + selectTime;
        Log.d("selectDateTime", selectDateTime);

        //detailWeatherClickEvent();

        //출몰시각 API 연결
//        Call<WtAppearTimeModel> getAppearTimeInstance = WtAppearTimeRetrofit.wtAppearTimeInterface()
//                .getAppearTimeData(WT_APPEAR_TIME_API_KEY, "고양", "20210730");
//        getAppearTimeInstance.enqueue(appearTimeModelCallback);

    }

    public void setTextView() {
        commentTv = findViewById(R.id.wt_comment);
        todayWeatherTv = findViewById(R.id.wt_today_weather_info);
        starObFitTv = findViewById(R.id.wt_star_ob_fit_info);
        bestObTimeTv = findViewById(R.id.wt_best_ob_time_info);
        sunriseTv = findViewById(R.id.sunrise_info);
        sunsetTv = findViewById(R.id.sunset_info);
        moonriseTv = findViewById(R.id.moonrise_info);
        moonsetTv = findViewById(R.id.moonset_info);

        cloudTv = findViewById(R.id.wt_cloud);
        tempTv = findViewById(R.id.wt_temp);
        moonPhaseTv = findViewById(R.id.wt_moon_phase);
        findDustTv = findViewById(R.id.wt_find_dust);
        windTv = findViewById(R.id.wt_wind);
        humidityTv = findViewById(R.id.wt_humidity);
        precipitationTv = findViewById(R.id.wt_precipitation);
        minLightPolTv = findViewById(R.id.wt_min_light_pol);
        maxLightPolTv = findViewById(R.id.wt_max_light_pol);

        minTempTv = findViewById(R.id.wt_min_temp);
        maxTempTv = findViewById(R.id.wt_max_temp);
        tempSlashTv = findViewById(R.id.wt_temp_slash);
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
        //        cal.add(Calendar.DATE, 2);
//        plusDay = formatDate2.format(cal.getTime());
//        plusDay += selectTime;
//        Log.d("plusTwoDay", plusDay);

        selectDate = formatDate2.format(cal.getTime());
        todayDate = formatDate2.format(cal.getTime());
        Log.d("todayDate", todayDate);

        datePicker = (TextView) findViewById(R.id.wt_datePicker);
        timePicker = findViewById(R.id.wt_timePicker);

        strDate = formatDate.format(cal.getTime());
        Log.d("selectDate", selectDate);
        datePicker.setText(strDate);

        todayTime = formatHour.format(cal.getTime());
        todayTimeTxt = todayTime + "시";
        timePicker.setText(todayTimeTxt);

        dateListener = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //monthOfYear += 1;
                cal.set(year, monthOfYear, dayOfMonth);
                strDate = formatDate.format(cal.getTime());
                Log.d("strDate", strDate);
                datePicker.setText(strDate);
                //20210901
                selectDate = year + String.format("%02d", (monthOfYear + 1)) + String.format("%02d", (dayOfMonth));
                Log.d("selectDate", selectDate);

                if (selectDate.equals(plusDay)) {
                    timePicker.setText(setTime);
                    selectTime = timeZero;
                    Log.d("selectTime1", selectTime);
                } else if (selectDate.equals(plusTwoDay)) {
                    timePicker.setText(setTime);
                    selectTime = timeZero;
                    Log.d("selectTime2", selectTime);
                } else if (selectDate.equals(todayDate)) {
                    todayTimeTxt = todayTime + "시";
                    Log.d("todayTime3", todayTime);
                    timePicker.setText(todayTimeTxt);
                } else {
                    timePicker.setText(setTimeNo);
                    selectTime = timeNoon;
                    Log.d("selectTime4", selectTime);
                }

                selectDateTime = selectDate + selectTime;
                Log.d("selectDateTime", selectDateTime);

                connectMetApi();
            }
        };
    }

    //날짜 선택 이벤트
    public void wtClickDatePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateListener, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(today.getTimeInMillis());
        today.add(Calendar.DAY_OF_MONTH, 7);
        datePickerDialog.getDatePicker().setMaxDate(today.getTimeInMillis());
        datePickerDialog.show();
        today.add(Calendar.DAY_OF_MONTH, -7);
    }

    // 시간 설정
    public void onSetTimePicker() {
        timePicker = findViewById(R.id.wt_timePicker);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyyMMddHH");
        todayDateTime = formatDateTime.format(cal.getTime());
        Log.d("todayDateTime", todayDateTime);

        selectTime = todayTime;
        Log.d("selectTime5", selectTime);

        c.add(Calendar.DATE, 1);
        plusDay = formatDate2.format(c.getTime());
        c.add(Calendar.DATE, 1);
        plusTwoDay = formatDate2.format(c.getTime());
        Log.d("plusDay", plusDay);
        Log.d("plusTwoDay", plusTwoDay);
        c.add(Calendar.DATE, -2);

//        timePicker.setText(todayTime);

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
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(0);

        try {
            cal.setTime(Objects.requireNonNull(formatDate2.parse(selectDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 오늘 날짜에서 현재 시간 전으로 선택 불가
        if (todayDate.equals(selectDate)) {
            switch (todayTime) {
                case "00":
                    setMinHour(-1);
                    break;
                case "01":
                    setMinHour(0);
                    break;
                case "02":
                    setMinHour(1);
                    break;
                case "03":
                    setMinHour(2);
                    break;
                case "04":
                    setMinHour(3);
                    break;
                case "05":
                    setMinHour(4);
                    break;
                case "06":
                    setMinHour(5);
                    break;
                case "07":
                    setMinHour(6);
                    break;
                case "08":
                    setMinHour(7);
                    break;
                case "09":
                    setMinHour(8);
                    break;
                case "10":
                    setMinHour(9);
                    break;
                case "11":
                    setMinHour(10);
                    break;
                case "12":
                    setMinHour(11);
                    break;
                case "13":
                    setMinHour(12);
                    break;
                case "14":
                    setMinHour(13);
                    break;
                case "15":
                    setMinHour(14);
                    break;
                case "16":
                    setMinHour(15);
                    break;
                case "17":
                    setMinHour(16);
                    break;
                case "18":
                    setMinHour(17);
                    break;
                case "19":
                    setMinHour(18);
                    break;
                case "20":
                    setMinHour(19);
                    break;
                case "21":
                    setMinHour(20);
                    break;
                case "22":
                    setMinHour(21);
                    break;
                case "23":
                    setMinHour(22);
                    break;
            }
            // 48시간 내에만 시간 선택 가능
        } else {
            if (selectDate.equals(plusDay)) {
                setLimitHour(1);
            } else {
                if (selectDate.equals(plusTwoDay)) {
                    switch (todayTime) {
                        case "00":
                            numberPicker.setEnabled(false);
                            break;
                        case "01":
                            setLimitHour(24);
                            break;
                        case "02":
                            setLimitHour(23);
                            break;
                        case "03":
                            setLimitHour(22);
                            break;
                        case "04":
                            setLimitHour(21);
                            break;
                        case "05":
                            setLimitHour(20);
                            break;
                        case "06":
                            setLimitHour(19);
                            break;
                        case "07":
                            setLimitHour(18);
                            break;
                        case "08":
                            setLimitHour(17);
                            break;
                        case "09":
                            setLimitHour(16);
                            break;
                        case "10":
                            setLimitHour(15);
                            break;
                        case "11":
                            setLimitHour(14);
                            break;
                        case "12":
                            setLimitHour(13);
                            break;
                        case "13":
                            setLimitHour(12);
                            break;
                        case "14":
                            setLimitHour(11);
                            break;
                        case "15":
                            setLimitHour(10);
                            break;
                        case "16":
                            setLimitHour(9);
                            break;
                        case "17":
                            setLimitHour(8);
                            break;
                        case "18":
                            setLimitHour(7);
                            break;
                        case "19":
                            setLimitHour(6);
                            break;
                        case "20":
                            setLimitHour(5);
                            break;
                        case "21":
                            setLimitHour(4);
                            break;
                        case "22":
                            setLimitHour(3);
                            break;
                        case "23":
                            setLimitHour(2);
                            break;
                    }
                } else {
                    numberPicker.setEnabled(false);
                }
            }
        }

        btnConfirm = dialogView.findViewById(R.id.wt_btn_confirm);
        btnCancel = dialogView.findViewById(R.id.wt_btn_cancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((selectDate.equals(todayDate)) || (selectDate.equals(plusDay)) || (selectDate.equals(plusTwoDay))) {
                    timePickerTxt = hourChange[numberPicker.getValue()] + "시";
                    timePicker.setText(timePickerTxt);
                    selectTime = String.valueOf(hourChange[numberPicker.getValue()]);
                } else {
                    timePicker.setText(setTimeNo);
                    selectTime = timeNoon;
                }

                Log.d("selectTime6", selectTime);

                selectDateTime = selectDate + selectTime;
                Log.d("selectDateTime", selectDateTime);

                connectMetApi();
                alertDialog.dismiss();
            }
        });
    }

    public void connectMetApi() {
        Log.d("latitude1`", String.valueOf(latitude));
        Log.d("longitude1", String.valueOf(longitude));

        Call<WtMetModel> getWeatherInstance = WtMetRetrofit.wtMetInterface()
                .getMetData(latitude, longitude, "minutely,current", WT_MET_API_KEY, "metric");

        getWeatherInstance.enqueue(new Callback<WtMetModel>() {
            @Override
            public void onResponse(Call<WtMetModel> call, Response<WtMetModel> response) {

                if (response.isSuccessful()) {
                    WtMetModel data = response.body();

                    for (int i = 0; i < 8; i++) {
                        unixTime = data.getDaily().get(i).getDt();
                        unixChange(unixTime);
                        Log.d("unixToDay", unixToDay);

                        if (selectDate.equals(unixToDay)) {
                            //월령 정보
                            moonPhaseTv.setText(data.getDaily().get(i).getMoonPhase());

                            //일출
                            unixSunMoon = data.getDaily().get(i).getSunrise();
                            unixSunrise = unixSunMoon;
                            unixChange(unixSunMoon);
                            sunriseTv.setText(unixToHourMin);

                            //일몰
                            unixSunMoon = data.getDaily().get(i).getSunset();
                            unixSunset = unixSunMoon;
                            unixChange(unixSunMoon);
                            sunsetTv.setText(unixToHourMin);

                            //월출
                            unixSunMoon = data.getDaily().get(i).getMoonrise();
                            unixChange(unixSunMoon);
                            moonriseTv.setText(unixToHourMin);

                            //월몰
                            unixSunMoon = data.getDaily().get(i).getMoonset();
                            unixChange(unixSunMoon);
                            moonsetTv.setText(unixToHourMin);

                            //낮 길이
//                            unixChange(unixSunrise);
//                            sunriseSt = unixToHourMin;
//                            Log.d("sunriseTime", sunriseSt);
//                            unixChange(unixSunset);
//                            sunsetSt = unixToHourMin;
//                            Log.d("date2", sunsetSt);
//
//                            try {
//                                sunriseTime = formatHourMin.parse(sunriseSt);
//                                sunsetTime = formatHourMin.parse(sunsetSt);
//
//                                assert sunsetTime != null;
//                                assert sunriseTime != null;
//
//                                dayLength = sunsetTime.getTime() - sunriseTime.getTime();
//                                hourValue = (int) (dayLength / (1000 * 60 * 60));
//                                minValue = (int) ((dayLength / (1000 * 60)) % 60);
//
//                                diff = hourValue + "시간 " + minValue + "분";
//                                Log.d("dayLength", diff);
//                            } catch (ParseException ignored) {
//
//                            }
//
//                            dayLengthTv.setText(diff);
                        }
                    }

                    if (selectDate.equals(todayDate) || selectDate.equals(plusDay) || selectDate.equals(plusTwoDay)) {
                        for (int i = 0; i < 48; i++) {
                            unixTime = data.getHourly().get(i).getDt();
                            unixChange(unixTime);
                            Log.d("unixToDate", unixToDate);

                            if (selectDateTime.equals(unixToDate)) {
                                tempTv.setVisibility(View.VISIBLE);
                                maxTempTv.setVisibility(View.GONE);
                                minTempTv.setVisibility(View.GONE);
                                tempSlashTv.setVisibility(View.GONE);

                                cloudText = data.getHourly().get(i).getClouds() + "%";
                                tempText = data.getHourly().get(i).getTemp() + "°C";
                                windText = data.getHourly().get(i).getWindSpeed() + "m/s";
                                humidityText = data.getHourly().get(i).getHumidity() + "%";
                                precipitationText = data.getHourly().get(i).getPop();

                                doublePrecip = Double.parseDouble(precipitationText) * 100;
                                intPrecip = (int) doublePrecip;
                                stringPrecip = intPrecip + "%";

                                cloudTv.setText(cloudText);
                                tempTv.setText(tempText);
                                windTv.setText(windText);
                                humidityTv.setText(humidityText);
                                precipitationTv.setText(stringPrecip);
                                Log.d("getI", String.valueOf(i));
                            }
                        }
                    } else {
                        for (int i = 0; i < 8; i++) {
                            unixTime = data.getDaily().get(i).getDt();
                            unixChange(unixTime);
                            Log.d("unixToDateDaily", unixToDate);

                            if (selectDateTime.equals(unixToDate)) {
                                tempTv.setVisibility(View.GONE);
                                maxTempTv.setVisibility(View.VISIBLE);
                                minTempTv.setVisibility(View.VISIBLE);
                                tempSlashTv.setVisibility(View.VISIBLE);

                                cloudText = data.getDaily().get(i).getClouds() + "%";
                                tempText = data.getDaily().get(i).getTemp().getMin() + "°C";
                                tempText1 = data.getDaily().get(i).getTemp().getMax() + "°C";
                                windText = data.getDaily().get(i).getWindSpeed() + "m/s";
                                humidityText = data.getDaily().get(i).getHumidity() + "%";
                                precipitationText = data.getDaily().get(i).getPop();

                                doublePrecip = Double.parseDouble(precipitationText) * 100;
                                intPrecip = (int) doublePrecip;
                                stringPrecip = intPrecip + "%";

                                cloudTv.setText(cloudText);
                                minTempTv.setText(tempText);
                                maxTempTv.setText(tempText1);
                                windTv.setText(windText);
                                humidityTv.setText(humidityText);
                                precipitationTv.setText(stringPrecip);
                                Log.d("getIDaily", String.valueOf(i));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<WtMetModel> call, Throwable t) {
                t.printStackTrace();
                Log.v("My Tag", "responseError= " + call.request().url());
            }
        });
        Log.d("openWeatherApi", "불러진다");
    }

    //unix 시간 변환
    public void unixChange(String unixTime) {
        //yyyyMMddHH
        unixDate = new java.util.Date(Long.parseLong(unixTime) * 1000L);
        formatDateTime.setTimeZone(java.util.TimeZone.getTimeZone("GMT+9"));
        unixToDate = formatDateTime.format(unixDate);

        //yyyyMMdd
        unixDay = new java.util.Date(Long.parseLong(unixTime) * 1000L);
        formatDate2.setTimeZone(java.util.TimeZone.getTimeZone("GMT+9"));
        unixToDay = formatDate2.format(unixDay);

        //HH:mm
        unixHourMin = new java.util.Date(Long.parseLong(unixTime) * 1000L);
        formatHourMin.setTimeZone(java.util.TimeZone.getTimeZone("GMT+9"));
        unixToHourMin = formatHourMin.format(unixHourMin);
    }


//    public void detailWeatherClickEvent(){
//        LinearLayout detailWeather;
//        detailWeather = findViewById(R.id.detail_weather);
//        detailWeather.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                tempTv.setVisibility(View.GONE);
//                maxTempTv.setVisibility(View.VISIBLE);
//                minTempTv.setVisibility(View.VISIBLE);
//                tempSlashTv.setVisibility(View.VISIBLE);
//                return false;
//            }
//        });
//    }

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
    public double setObservationalFitDegree() {
        cloudVolumeValue = -100*(-(1/(-(0.25)*(cloudVolume/100-2.7))-1.48148));

        if (moonAge <= 0.5) {
            moonAgeValue = Math.round((-8 * Math.pow(moonAge, 3.46)) / 0.727 * 100);
        } else if (moonAge > 0.5 && moonAge <= 0.5609) {
            moonAgeValue = Math.round((-75 * Math.pow(moonAge - 0.5, 2) + 0.727) / 0.727 * 100);
        } else if (moonAge > 0.5609) {
            moonAgeValue = Math.round((-1 / (5.6 * Math.pow(moonAge + 0.3493, 10))-0.0089) / 0.727 * 100);
        }
        if (feel_like<18){
            feel_likeValue=-0.008*Math.pow((feel_like-18),2);
        }else{
            feel_likeValue=-0.09*Math.pow((feel_like-18),2);
        }

        if (fineDust == "good") {
            fineDustValue = 0;
        } else if (fineDust == "normal") {
            fineDustValue = -5;
        } else if (fineDust == "bad") {
            fineDustValue = -20;
        }
        precipitationProbabilityValue = 100*(-(1/(-(1.2)*(precipitationProbability/100-1.5))-0.55556));

        if (lightPollution<28.928){
            lightPollutionValue=-(1/(-(0.001)*(lightPollution-48))-20.833);
        }else if (lightPollution>=28.928 && lightPollution<77.53){
            lightPollutionValue=-(1/(-(0.0001)*(lightPollution+52))+155);
        }else if (lightPollution>=77.53 && lightPollution<88.674){
            lightPollutionValue = -(1/(-(0.001)*(lightPollution-110))+47);
        }else{
            lightPollutionValue= -(1/(-(0.01)*(lightPollution-71))+100);
        }

        observationalFitDegree = 100 + cloudVolumeValue + feel_likeValue + moonAgeValue + fineDustValue + precipitationProbabilityValue + lightPollutionValue;

        return observationalFitDegree;
    }

    //시,도 Spinner 동작
    public void onSetAreaSpinner() {
        final Spinner citySpinner = (Spinner) findViewById(R.id.wt_citySpinner);
        final Spinner provSpinner = (Spinner) findViewById(R.id.wt_provinceSpinner);

        cityAdSpin = ArrayAdapter.createFromResource(this, R.array.wt_cityList, android.R.layout.simple_spinner_dropdown_item);
        cityAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdSpin);

        choice_do = "서울";
        choice_se = "강남구";

        cityName = choice_do;
        provName = choice_se;

        latitude = 37.5006;
        longitude = 127.0508;

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (cityAdSpin.getItem(position).equals("서울")) {
                    choice_do = "서울";
                    cityName = choice_do;

                    choice_se = "강남구";
                    provName = choice_se;
                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Seoul, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("경기")) {
                    choice_do = "경기";
                    cityName = choice_do;

                    choice_se = "가평군";
                    cityName = choice_se;

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Gyeonggi, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("인천")) {
                    choice_do = "인천";
                    cityName = choice_do;

                    choice_se = "강화군";
                    provName = choice_se;

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Incheon, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("강원")) {
                    choice_do = "강원";
                    cityName = choice_do;

                    choice_se = "강릉시";
                    provName = choice_se;

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Gangwon, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }

                    });
                    Log.d("cityName", cityName);
                    Log.d("provName", provName);
                } else if (cityAdSpin.getItem(position).equals("충북")) {
                    choice_do = "충북";
                    cityName = choice_do;

                    choice_se = "괴산군";
                    provName = choice_se;

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Chungbuk, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("충남·대전·세종")) {
                    choice_do = "충남·대전·세종";
                    cityName = choice_do;

                    choice_se = "계룡시";
                    provName = choice_se;

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_ChungnamDaejeonSejong, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("광주·전북")) {
                    choice_do = "광주·전북";
                    cityName = choice_do;

                    choice_se = "광산구";
                    provName = choice_se;

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_GwangjuJeonbuk, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("전남")) {
                    choice_do = "전남";
                    cityName = choice_do;

                    choice_se = "강진군";
                    provName = choice_se;

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Jeonnam, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("대구·경북")) {
                    choice_do = "대구·경북";
                    cityName = choice_do;

                    choice_se = "중가";
                    provName = choice_se;

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_DaeguGyeongbuk, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("경남")) {
                    choice_do = "경남";
                    cityName = choice_do;

                    choice_se = "거제시";
                    provName = choice_se;

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Gyeongnam, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("부산·울산")) {
                    choice_do = "부산·울산";
                    cityName = choice_do;

                    choice_se = "강서구";
                    provName = choice_se;

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_BusanUlsan, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("제주")) {
                    choice_do = "제주";
                    cityName = choice_do;

                    choice_se = "제주시";
                    provName = choice_se;

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Jeju, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
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

        connectWtArea();
    }

    public void connectWtArea() {
        Log.d("cityName1", cityName);
        Log.d("provName1", provName);

        //지역명으로 경도, 위도, 광공해 받아오기
        Call<WtAreaParams> areaInfoCall = RetrofitClient.getApiService().getAreaInfo(cityName, provName);
        areaInfoCall.enqueue(new Callback<WtAreaParams>() {
            @Override
            public void onResponse(Call<WtAreaParams> call, Response<WtAreaParams> response) {
                if (response.isSuccessful()) {
                    WtAreaParams result = response.body();
                    latitude = result.getLatitude();
                    longitude = result.getLongitude();

                    minLightPol = result.getMinLightPol();
                    maxLightPol = result.getMaxLightPol();

                    minLightPolVal = minLightPol.toString();
                    maxLightPolVal = maxLightPol.toString();

                    minLightPolTv.setText(minLightPolVal);
                    maxLightPolTv.setText(maxLightPolVal);

                    connectMetApi();
                    Log.d("latitude", String.valueOf(latitude));
                    Log.d("longitude", String.valueOf(longitude));
                } else {
                    System.out.println("지역명으로 경도, 위도 받아오기 실패");
                }
            }

            @Override
            public void onFailure(Call<WtAreaParams> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
    }

    public void setMinHour(int num) {
        for (int i = 0; i < num + 1; i++) {
            String remove = list2.remove(0);
            Log.d("remove", remove);
            Log.d("number", String.valueOf(num));
        }
        hourChange = list2.toArray(new String[0]);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(hourChange.length - 1);
        numberPicker.setDisplayedValues(hourChange);

        for (int i = 0; i < num + 1; i++) {
            list2.add(i, hour[i]);
        }
        // hourChange = list2.toArray(new String[0]);
    }

    public void setLimitHour(int num) {
        hourChange = list2.toArray(new String[0]);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(hourChange.length - num);
        numberPicker.setDisplayedValues(hourChange);
    }
}