package com.starrynight.tourapiproject.weatherPage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.weatherPage.wtAreaRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.weatherPage.wtAreaRetrofit.WtAreaParams;
import com.starrynight.tourapiproject.weatherPage.wtFineDustModel.WtFineDustModel;
import com.starrynight.tourapiproject.weatherPage.wtFineDustModel.WtFineDustRetrofit;
import com.starrynight.tourapiproject.weatherPage.wtMetModel.WtMetModel;
import com.starrynight.tourapiproject.weatherPage.wtMetModel.WtMetRetrofit;
import com.starrynight.tourapiproject.weatherPage.wtObFit.ObFitItem;
import com.starrynight.tourapiproject.weatherPage.wtObFit.ObFitViewAdapter;
import com.starrynight.tourapiproject.weatherPage.wtTodayRetrofit.WtTodayParams;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy.MM.dd");

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat formatDateDash = new SimpleDateFormat("yyyy-MM-dd");

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat formatDate2 = new SimpleDateFormat("yyyyMMdd");

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat formatHourMin = new SimpleDateFormat("HH:mm");

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat formatHour = new SimpleDateFormat("HH");

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyyMMddHH");

    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);

    //관측적합도
    RecyclerView obFitRecycler;
    ObFitViewAdapter obFitViewAdapter;
    LinearLayout obFit;
    List<Double> obFitList = new ArrayList<>();
    List<Integer> obFitImageList = new ArrayList<>();
    Double obFitValue;
    int obFitApiId;
    int obFitImage;
    String obFitPercent;

    private String obFitHour[] = {"18", "19", "20", "21", "22", "23", "00", "01", "02", "03", "04", "05"
            , "06", "07", "08", "09", "10", "11", "12", "13", "14", "15"
            , "16", "17"};

    int obFitHourId;

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
    String WT_FINE_DUST_API_KEY;

    String strDate;

    String timePickerTxt;

    ArrayAdapter<CharSequence> cityAdSpin, provAdSpin;
    String choice_do = "";
    String choice_se = "";

    // 선택한 날짜 + 시간
    String selectDateTime;
    String todayDateTime;

    String selectDate;
    String todayDateDash;
    String selectTime;
    String selectDateDash;

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
    TextView fineDustTv;
    TextView windTv;
    TextView humidityTv;
    TextView precipitationTv;
    TextView lightPolTv;
    TextView minLightPolTv;
    TextView maxLightPolTv;
    TextView lightSlashTv;

    String cloudText;
    String tempText;
    String tempMaxText;
    String tempMinText;
    String moonPhaseText;
    String windText;
    String fineDustText;
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
    String minLightPolText;
    String maxLightPolText;

    //미세먼지
    int dustTotalCnt;
    int dustNoInfo = 0;

    Date changeDate;
    Date changeSelectDate;
    String addDate;

    String listDust;
    String[] arrayComma;

    String[] dustStateArray = new String[19];
    int index;
    String state;

    //날씨 상태
    LinearLayout detailWeather;
    String cloudState = "좋음";
    String tempState = "나쁨";
    String moonPhaseState = "좋음";
    String fineDustState = "나쁨";
    String windState = "나쁨";
    String humidityState = "좋음";
    String precipState = "나쁨";
    String lightPolState = "좋음";

    //오늘의 날씨
    String todayWtId;
    int todayWtIdInt;
    String todayWtName1;
    String todayWtName2;
    String todayWtName;


    {
        try {
            WT_MET_API_KEY = URLDecoder.decode("7c7ba4d9df15258ce566f6592d875413", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    {
        try {
            WT_FINE_DUST_API_KEY = URLDecoder.decode("%2BbGNCh8qjhDibGZBmk6VZpWQNDaE9ePej4RbIqtZWnGBScQJshf4ELZgbQj5pqfAtnJPGU7ggOsyK0RmLDJlTQ%3D%3D", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        setTextView();
        onClickBackBtn();
        onClickCloudInfo();

        onSetDatePicker();
        onSetTimePicker();

        //지역 선택 Spinner
        onSetAreaSpinner();

        selectDateTime = selectDate + selectTime;
        Log.d("selectDateTime", selectDateTime);

    }

    public void setObFitRecycler() {
        //관측 적합도 recyclerview
        obFitRecycler = findViewById(R.id.ob_fit_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        obFitRecycler.setLayoutManager(linearLayoutManager);

        obFitViewAdapter = new ObFitViewAdapter();
        obFitRecycler.setAdapter(obFitViewAdapter);

        for (int i = 0; i < obFitList.size(); i++) {
            if (obFitList.get(i) > 100.0) {
                obFitImageList.add(R.drawable.wt__ob_fit_good);
            } else {
                obFitImageList.add(R.drawable.wt__ob_fit_bad);
            }
        }

        if (selectDate.equals(todayDate)) {
            Log.d("obFitListSize", String.valueOf(obFitList.size()));
            Log.d("obFitHourId", String.valueOf(obFitHourId));

            for (int i = 0; i < obFitList.size(); i++) {
                obFitPercent = obFitList.get(i) + "%";
                obFitViewAdapter.addItem(new ObFitItem(obFitImageList.get(i), obFitHour[obFitHourId], obFitPercent));
                obFitHourId++;
            }
        } else if (selectDate.equals(plusDay)) {
            Log.d("obFitListSize", String.valueOf(obFitList.size()));
            Log.d("obFitHourId", String.valueOf(obFitHourId));
            for (int i = 0; i < 6; i++) {
                obFitPercent = obFitList.get(i) + "%";
                obFitViewAdapter.addItem(new ObFitItem(obFitImageList.get(i), obFitHour[obFitHourId], obFitPercent));
                obFitHourId++;
            }
        }

        obFitImageList.clear();
        obFitRecycler.setAdapter(obFitViewAdapter);
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
        fineDustTv = findViewById(R.id.wt_find_dust);
        windTv = findViewById(R.id.wt_wind);
        humidityTv = findViewById(R.id.wt_humidity);
        precipitationTv = findViewById(R.id.wt_precipitation);

        lightPolTv = findViewById(R.id.wt_light_pol);
        minLightPolTv = findViewById(R.id.wt_min_light_pol);
        maxLightPolTv = findViewById(R.id.wt_max_light_pol);
        lightSlashTv = findViewById(R.id.wt_light_slash);

        minTempTv = findViewById(R.id.wt_min_temp);
        maxTempTv = findViewById(R.id.wt_max_temp);
        tempSlashTv = findViewById(R.id.wt_temp_slash);
    }

    //뒤로가기 버튼 이벤트
    public void onClickBackBtn() {
        ImageView button = findViewById(R.id.wt_back_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //기상청 구름 정보 페이지로 이동
    public void onClickCloudInfo() {
        TextView button1 = findViewById(R.id.wt_today_cloud);
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
                connectFineDustApi();
                Log.d("obCheck", "0");
                setObFitRecycler();
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

        obFit = findViewById(R.id.ob_fit);

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
                            moonPhaseText = data.getDaily().get(i).getMoonPhase();
                            moonPhaseTv.setText(moonPhaseText);

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
                        }
                    }

                    detailWeather = findViewById(R.id.detail_weather);

                    //관측 적합도
                    // 관측적합도 리스트 초기화
                    obFitList.clear();
                    Log.d("obFitListSize", String.valueOf(obFitList.size()));
                    // 당일 오전 6시 ~ 오후 6시
                    if (selectDate.equals(todayDate)) {
                        obFit.setVisibility(View.VISIBLE);
                        moonAge = Double.parseDouble(moonPhaseText);

                        for (int i = 0; i < 48; i++) {
                            unixTime = data.getHourly().get(i).getDt();
                            unixChange(unixTime);
                            Log.d("unixToDate", unixToDate);

                            //당일 오후 6시 ~ 익일 오전 6시
                            if (unixToDate.equals(todayDate + "18")) {
                                obFitApiId = i;

                                for (int j = obFitApiId; j < obFitApiId + 13; j++) {
                                    cloudVolume = Double.parseDouble(data.getHourly().get(j).getClouds());
                                    feel_like = Double.parseDouble(data.getHourly().get(j).getFeelsLike());
                                    precipitationProbability = Double.parseDouble(data.getHourly().get(j).getPop());
                                    obFitValue = setObservationalFitDegree();
                                    obFitList.add(obFitValue);
                                }
                                obFitHourId = 0;
                                break;
                            } else if (unixToDate.equals(todayDate + "19")) {
                                for (int j = 0; j < 12; j++) {
                                    cloudVolume = Double.parseDouble(data.getHourly().get(j).getClouds());
                                    feel_like = Double.parseDouble(data.getHourly().get(j).getFeelsLike());
                                    precipitationProbability = Double.parseDouble(data.getHourly().get(j).getPop());
                                    obFitValue = setObservationalFitDegree();
                                    obFitList.add(obFitValue);
                                }
                                obFitHourId = 1;
                                break;
                            } else if (unixToDate.equals(todayDate + "20")) {
                                for (int j = 0; j < 11; j++) {
                                    cloudVolume = Double.parseDouble(data.getHourly().get(j).getClouds());
                                    feel_like = Double.parseDouble(data.getHourly().get(j).getFeelsLike());
                                    precipitationProbability = Double.parseDouble(data.getHourly().get(j).getPop());
                                    obFitValue = setObservationalFitDegree();
                                    obFitList.add(obFitValue);
                                }
                                obFitHourId = 2;
                                break;
                            } else if (unixToDate.equals(todayDate + "21")) {
                                for (int j = 0; j < 10; j++) {
                                    cloudVolume = Double.parseDouble(data.getHourly().get(j).getClouds());
                                    feel_like = Double.parseDouble(data.getHourly().get(j).getFeelsLike());
                                    precipitationProbability = Double.parseDouble(data.getHourly().get(j).getPop());
                                    obFitValue = setObservationalFitDegree();
                                    obFitList.add(obFitValue);
                                }
                                obFitHourId = 3;
                                break;
                            } else if (unixToDate.equals(todayDate + "22")) {
                                Log.d("obFitDate", "22");
                                for (int j = 0; j < 9; j++) {
                                    cloudVolume = Double.parseDouble(data.getHourly().get(j).getClouds());
                                    feel_like = Double.parseDouble(data.getHourly().get(j).getFeelsLike());
                                    precipitationProbability = Double.parseDouble(data.getHourly().get(j).getPop());
                                    obFitValue = setObservationalFitDegree();
                                    obFitList.add(obFitValue);
                                }
                                obFitHourId = 4;
                                break;
                            } else if (unixToDate.equals(todayDate + "23")) {
                                for (int j = 0; j < 8; j++) {
                                    cloudVolume = Double.parseDouble(data.getHourly().get(j).getClouds());
                                    feel_like = Double.parseDouble(data.getHourly().get(j).getFeelsLike());
                                    precipitationProbability = Double.parseDouble(data.getHourly().get(j).getPop());
                                    obFitValue = setObservationalFitDegree();
                                    obFitList.add(obFitValue);
                                    Log.d("obFitCloud", String.valueOf(cloudVolume));
                                    Log.d("obFitTemp", String.valueOf(feel_like));
                                    Log.d("obFitMoon", String.valueOf(moonAge));
                                    Log.d("obFitFineDust", fineDust);
                                    Log.d("obFitPrecip", String.valueOf(precipitationProbability));
                                    Log.d("obFitLightPol", String.valueOf(lightPollution));
                                    Log.d("obValue", String.valueOf(setObservationalFitDegree()));
                                }
                                obFitHourId = 5;
                                break;
                            }
                        }
                        Log.d("obCheck", "1");
                        setObFitRecycler();

                    }

                    //다음 날 오후 6시 ~ 오후 11시
                    else if (selectDate.equals(plusDay)) {
                        obFit.setVisibility(View.VISIBLE);
                        obFitList.clear();
                        obFitApiId = 0;

                        for (int i = 0; i < 48; i++) {
                            unixTime = data.getHourly().get(i).getDt();
                            unixChange(unixTime);
                            Log.d("unixToDate", unixToDate);

                            if (unixToDate.equals(plusDay + "18")) {
                                obFitApiId = i;
                            }
                        }

                        for (int i = obFitApiId; i < obFitApiId + 6; i++) {
                            cloudVolume = Double.parseDouble(data.getHourly().get(i).getClouds());
                            moonAge = Double.parseDouble(moonPhaseText);
                            feel_like = Double.parseDouble(data.getHourly().get(i).getFeelsLike());
                            precipitationProbability = Double.parseDouble(data.getHourly().get(i).getPop());
                            obFitValue = setObservationalFitDegree();
                            obFitList.add(obFitValue);
                        }
                        obFitHourId = 0;
                        Log.d("obCheck", "2");
                        setObFitRecycler();
                    } else {
                        obFit.setVisibility(View.GONE);
                    }

                    if (selectDate.equals(todayDate) || selectDate.equals(plusDay) || selectDate.equals(plusTwoDay)) {
                        for (int i = 0; i < 48; i++) {
                            unixTime = data.getHourly().get(i).getDt();
                            unixChange(unixTime);
                            Log.d("unixToDate", unixToDate);

                            if (selectDateTime.equals(unixToDate)) {
                                //tempTv만 나오게
                                setTempVisibility(0);

                                cloudText = data.getHourly().get(i).getClouds() + "%";
                                tempText = data.getHourly().get(i).getTemp() + "°C";
                                windText = data.getHourly().get(i).getWindSpeed() + "m/s";
                                humidityText = data.getHourly().get(i).getHumidity() + "%";
                                precipitationText = data.getHourly().get(i).getPop();
                                todayWtId = data.getHourly().get(i).getWeather().get(0).getId();
                                Log.d("todayWtId", todayWtId);
                                todayWtIdInt = Integer.parseInt(todayWtId);
                                Log.d("todayWtIdInt", String.valueOf(todayWtIdInt));
                                doublePrecip = Double.parseDouble(precipitationText) * 100;
                                intPrecip = (int) doublePrecip;
                                stringPrecip = intPrecip + "%";

                                cloudTv.setText(cloudText);
                                tempTv.setText(tempText);
                                windTv.setText(windText);
                                humidityTv.setText(humidityText);
                                precipitationTv.setText(stringPrecip);
                                connectTodayWeather();
                                Log.d("getI", String.valueOf(i));
                            }
                        }
                    } else {
                        for (int i = 0; i < 8; i++) {
                            unixTime = data.getDaily().get(i).getDt();
                            unixChange(unixTime);
                            Log.d("unixToDateDaily", unixToDate);

                            if (selectDateTime.equals(unixToDate)) {
                                setTempVisibility(1);

                                cloudText = data.getDaily().get(i).getClouds() + "%";
                                tempMinText = data.getDaily().get(i).getTemp().getMin() + "°C";
                                tempMaxText = data.getDaily().get(i).getTemp().getMax() + "°C";
                                windText = data.getDaily().get(i).getWindSpeed() + "m/s";
                                humidityText = data.getDaily().get(i).getHumidity() + "%";
                                precipitationText = data.getDaily().get(i).getPop();
                                todayWtId = data.getDaily().get(i).getWeather().get(0).getId();

                                doublePrecip = Double.parseDouble(precipitationText) * 100;
                                intPrecip = (int) doublePrecip;
                                stringPrecip = intPrecip + "%";

                                cloudTv.setText(cloudText);
                                minTempTv.setText(tempMinText);
                                maxTempTv.setText(tempMaxText);
                                windTv.setText(windText);
                                humidityTv.setText(humidityText);
                                precipitationTv.setText(stringPrecip);
                                connectTodayWeather();
                                Log.d("getIDaily", String.valueOf(i));
                            }
                        }
                    }
                    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (selectDate.equals(todayDate) || selectDate.equals(plusDay) || selectDate.equals(plusTwoDay)) {
                                    tempTv.setText(tempText);
                                } else {
                                    setTempVisibility(1);

                                    minTempTv.setText(tempMinText);
                                    maxTempTv.setText(tempMaxText);
                                }
                                cloudTv.setText(cloudText);
                                moonPhaseTv.setText(moonPhaseText);
                                fineDustTv.setText(fineDustText);
                                windTv.setText(windText);
                                humidityTv.setText(humidityText);
                                precipitationTv.setText(stringPrecip);

                                setLightPolVisibility(1);

                                maxLightPolTv.setText(maxLightPolText);
                                minLightPolTv.setText(minLightPolText);
                            }
                            return false;
                        }
                    };

                    detailWeather.setOnLongClickListener(new View.OnLongClickListener() {
                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public boolean onLongClick(View v) {
                            if (maxTempTv.getVisibility() == View.VISIBLE) {
                                setTempVisibility(0);
                            }

                            if (maxLightPolTv.getVisibility() == View.VISIBLE) {
                                setLightPolVisibility(0);
                            }

                            cloudTv.setText(cloudState);
                            tempTv.setText(tempState);
                            moonPhaseTv.setText(moonPhaseState);
                            fineDustTv.setText(fineDustState);
                            windTv.setText(windState);
                            humidityTv.setText(humidityState);
                            precipitationTv.setText(precipState);
                            lightPolTv.setText(lightPolState);

                            detailWeather.setOnTouchListener(onTouchListener);
                            return false;
                        }
                    });
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

    //미세먼지 API 연결
    public void connectFineDustApi() {
        try {
            addDate = todayDate;
            changeDate = formatDate2.parse(addDate);
            todayDateDash = formatDateDash.format(changeDate);
            Log.d("todayDateDash", todayDateDash);
            Log.d("dustApi", "오늘 날짜로 api 호출");

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (selectDate.equals(todayDate) && (selectTime.equals("00") || selectTime.equals("01") || selectTime.equals("02") || selectTime.equals("03") || selectTime.equals("04"))) {
            try {
                addDate = todayDate;
                cal.add(Calendar.DATE, -1);

                addDate = formatDate2.format(cal.getTime());

                cal.add(Calendar.DATE, 1);

                changeDate = formatDate2.parse(addDate);
                todayDateDash = formatDateDash.format(changeDate);
                Log.d("todayDateDash", todayDateDash);
                Log.d("dustApi", "어제 날짜로 api 호출");

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        try {
            changeSelectDate = formatDate2.parse(selectDate);
            selectDateDash = formatDateDash.format(changeSelectDate);
            Log.d("selectDateDash", selectDateDash);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Call<WtFineDustModel> getFineDustInstance = WtFineDustRetrofit.wtFineDustInterface()
                .getFineDustData(WT_FINE_DUST_API_KEY, "JSON", todayDateDash, "PM10");
        getFineDustInstance.enqueue(new Callback<WtFineDustModel>() {
            @Override
            public void onResponse(Call<WtFineDustModel> call, Response<WtFineDustModel> response) {
                if (response.isSuccessful()) {
                    WtFineDustModel data = response.body();
                    Log.d("FineDust", "response= " + response.raw().request().url().url());

                    dustTotalCnt = data.getResponse().getBody().getTotalCount();
                    for (int i = 0; i < dustTotalCnt; i++) {
                        if (data.getResponse().getBody().getItems().get(i).getInformCode().equals("PM10") &&
                                selectDateDash.equals(data.getResponse().getBody().getItems().get(i).getInformData())) {
                            listDust = data.getResponse().getBody().getItems().get(i).getInformGrade();
                            Log.d("dustDateTime", data.getResponse().getBody().getItems().get(i).getDataTime());
                            Log.d("dustInformData", data.getResponse().getBody().getItems().get(i).getInformData());
                            dustNoInfo = 1;
                        }
                    }

                }

                arrayComma = listDust.split(",");

                for (String s : arrayComma) {
                    Log.d("test", s);
                }

                for (int i = 0; i < 19; i++) {
                    index = arrayComma[i].indexOf(":");
                    state = arrayComma[i].substring(index + 2);
                    dustStateArray[i] = state;
                }

                if (cityName.equals("서울")) {
                    fineDustText = dustStateArray[0];
                    Log.d("dust", "0");
                } else if (cityName.equals("제주")) {
                    fineDustText = dustStateArray[1];
                    Log.d("dust", "1");
                } else if (cityName.equals("전남")) {
                    fineDustText = dustStateArray[2];
                    Log.d("dust", "2");
                } else if (cityName.equals("광주·전북")) {
                    //광주
                    if (provName.equals("광산구") || provName.equals("남구") || provName.equals("동구") || provName.equals("북구") || provName.equals("서구")) {
                        fineDustText = dustStateArray[4];
                        Log.d("dust", "4");
                    }
                    //전북
                    else {
                        fineDustText = dustStateArray[3];
                        Log.d("dust", "3");
                    }
                } else if (cityName.equals("경남")) {
                    fineDustText = dustStateArray[5];
                    Log.d("dust", "5");
                } else if (cityName.equals("대구·경북")) {
                    //대구
                    if (provName.equals("중구") || provName.equals("동구") || provName.equals("서구") || provName.equals("남구") || provName.equals("북구") || provName.equals("수성구") || provName.equals("달서구") || provName.equals("달성군")) {
                        fineDustText = dustStateArray[8];
                        Log.d("dust", "8");
                    }
                    //경북
                    else {
                        fineDustText = dustStateArray[6];
                        Log.d("dust", "6");
                    }
                } else if (cityName.equals("부산·울산")) {
                    //울산
                    if (provName.equals("남구") || provName.equals("동구") || provName.equals("북구") || provName.equals("울주군") || provName.equals("중구")) {
                        fineDustText = dustStateArray[7];
                        Log.d("dust", "7");
                    }
                    //부산
                    else {
                        fineDustText = dustStateArray[9];
                        Log.d("dust", "9");
                    }
                } else if (cityName.equals("충북")) {
                    fineDustText = dustStateArray[11];
                    Log.d("dust", "11");
                } else if (cityName.equals("충남·대전·세종")) {
                    //대전
                    if (provName.equals("대덕구") || provName.equals("동구") || provName.equals("서구") || provName.equals("유성구") || provName.equals("중구")) {
                        fineDustText = dustStateArray[13];
                        Log.d("dust", "13");
                    }
                    //세종
                    else if (provName.equals("세종")) {
                        fineDustText = dustStateArray[12];
                        Log.d("dust", "12");
                    }
                    //충남
                    else {
                        fineDustText = dustStateArray[10];
                        Log.d("dust", "10");
                    }
                } else if (cityName.equals("인천")) {
                    fineDustText = dustStateArray[18];
                    Log.d("dust", "18");
                } else if (cityName.equals("경기")) {
                    //경기 북부
                    if (provName.equals("가평군") || provName.equals("고양시") || provName.equals("구리시") || provName.equals("남양주시") || provName.equals("동두천시")
                            || provName.equals("양주시") || provName.equals("연천군") || provName.equals("의정부시") || provName.equals("파주시") || provName.equals("포천시")) {
                        fineDustText = dustStateArray[17];
                        Log.d("dust", "17");
                    }
                    //경기 남부
                    else {
                        fineDustText = dustStateArray[16];
                        Log.d("dust", "16");
                    }
                } else if (cityName.equals("강원")) {
                    //영동
                    if (provName.equals("강릉시") || provName.equals("고성군") || provName.equals("동해시") || provName.equals("삼척시") || provName.equals("속초시")
                            || provName.equals("양양군") || provName.equals("태백시")) {
                        fineDustText = dustStateArray[14];
                        Log.d("dust", "14");
                    }
                    //영서
                    else {
                        fineDustText = dustStateArray[15];
                        Log.d("dust", "15");
                    }
                } else {
                    Log.d("dustError", "else로 빠짐");
                }

                fineDustTv.setText(fineDustText);
                fineDust = fineDustText;
                if (dustNoInfo == 0) {
                    fineDustTv.setText("정보없음");
                    Log.d("dustNoInfo", "정보없음");
                }
                dustNoInfo = 0;
            }

            @Override
            public void onFailure(Call<WtFineDustModel> call, Throwable t) {
                t.printStackTrace();
                Log.v("FineDust", "responseError= " + call.request().url());
            }
        });
    }

//    public void setDustData() {
//        arrayComma = listDust.split(",");
//
//        for (String s : arrayComma) {
//            Log.d("test", s);
//        }
//
//        for (int i = 0; i < 19; i++) {
//            index = arrayComma[i].indexOf(":");
//            state = arrayComma[i].substring(index + 2);
//            dustStateArray[i] = state;
//        }
//
//        if (cityName.equals("서울")) {
//            findDustTv.setText(dustStateArray[0]);
//            Log.d("dust", "0");
//        } else if (cityName.equals("제주")) {
//            findDustTv.setText(dustStateArray[1]);
//            Log.d("dust", "1");
//        } else if (cityName.equals("전남")) {
//            findDustTv.setText(dustStateArray[2]);
//            Log.d("dust", "2");
//        } else if (cityName.equals("광주·전북")) {
//            //광주
//            if (provName.equals("광산구") || provName.equals("남구") || provName.equals("동구") || provName.equals("북구") || provName.equals("서구")) {
//                findDustTv.setText(dustStateArray[4]);
//                Log.d("dust", "4");
//            }
//            //전북
//            else {
//                findDustTv.setText(dustStateArray[3]);
//                Log.d("dust", "3");
//            }
//        } else if (cityName.equals("경남")) {
//            findDustTv.setText(dustStateArray[5]);
//            Log.d("dust", "5");
//        } else if (cityName.equals("대구·경북")) {
//            //대구
//            if (provName.equals("중구") || provName.equals("동구") || provName.equals("서구") || provName.equals("남구") || provName.equals("북구") || provName.equals("수성구") || provName.equals("달서구") || provName.equals("달성군")) {
//                findDustTv.setText(dustStateArray[8]);
//                Log.d("dust", "8");
//            }
//            //경북
//            else {
//                findDustTv.setText(dustStateArray[6]);
//                Log.d("dust", "6");
//            }
//        } else if (cityName.equals("부산·울산")) {
//            //울산
//            if (provName.equals("남구") || provName.equals("동구") || provName.equals("북구") || provName.equals("울주군") || provName.equals("중구")) {
//                findDustTv.setText(dustStateArray[7]);
//                Log.d("dust", "7");
//            }
//            //부산
//            else {
//                findDustTv.setText(dustStateArray[9]);
//                Log.d("dust", "9");
//            }
//        } else if (cityName.equals("충북")) {
//            findDustTv.setText(dustStateArray[11]);
//            Log.d("dust", "11");
//        } else if (cityName.equals("충남·대전·세종")) {
//            //대전
//            if (provName.equals("대덕구") || provName.equals("동구") || provName.equals("서구") || provName.equals("유성구") || provName.equals("중구")) {
//                findDustTv.setText(dustStateArray[13]);
//                Log.d("dust", "13");
//            }
//            //세종
//            else if (provName.equals("세종")) {
//                findDustTv.setText(dustStateArray[12]);
//                Log.d("dust", "12");
//            }
//            //충남
//            else {
//                findDustTv.setText(dustStateArray[10]);
//                Log.d("dust", "10");
//            }
//        } else if (cityName.equals("인천")) {
//            findDustTv.setText(dustStateArray[18]);
//            Log.d("dust", "18");
//        } else if (cityName.equals("경기")) {
//            //경기 북부
//            if (provName.equals("가평군") || provName.equals("고양시") || provName.equals("구리시") || provName.equals("남양주시") || provName.equals("동두천시")
//                    || provName.equals("양주시") || provName.equals("연천군") || provName.equals("의정부시") || provName.equals("파주시") || provName.equals("포천시")) {
//                findDustTv.setText(dustStateArray[17]);
//                Log.d("dust", "17");
//            }
//            //경기 남부
//            else {
//                findDustTv.setText(dustStateArray[16]);
//                Log.d("dust", "16");
//            }
//        } else if (cityName.equals("강원")) {
//            //영동
//            if (provName.equals("강릉시") || provName.equals("고성군") || provName.equals("동해시") || provName.equals("삼척시") || provName.equals("속초시")
//                    || provName.equals("양양군") || provName.equals("태백시")) {
//                findDustTv.setText(dustStateArray[14]);
//                Log.d("dust", "14");
//            }
//            //영서
//            else {
//                findDustTv.setText(dustStateArray[15]);
//                Log.d("dust", "15");
//            }
//        } else {
//            Log.d("dustError", "else로 빠짐");
//        }
//    }

    //관측적합도
    public double setObservationalFitDegree() {
        cloudVolumeValue = Math.round(100 * (-(1 / (-(0.25) * (cloudVolume / 100 - 2.7)) - 1.48148))*100)/100.0;

        if (moonAge <= 0.5) {
            moonAgeValue = -Math.round(((8 * Math.pow(moonAge, 3.46)) / 0.727 * 100)*100)/100.0;
        } else if (moonAge > 0.5 && moonAge <= 0.5609) {
            moonAgeValue = -Math.round(((-75 * Math.pow(moonAge - 0.5, 2) + 0.727) / 0.727 * 100)*100)/100.0;
        } else if (moonAge > 0.5609) {
            moonAgeValue = -Math.round(((1 / (5.6 * Math.pow(moonAge + 0.3493, 10)) - 0.0089) / 0.727 * 100)*100)/100.0;
        }
        if (feel_like < 18) {
            feel_likeValue = Math.round(-0.008 * Math.pow((feel_like - 18), 2)*100)/100.0;
        } else {
            feel_likeValue = Math.round(-0.09 * Math.pow((feel_like - 18), 2)*100)/100.0;
        }

        if (fineDust.equals("좋음")) {
            fineDustValue = 0;
        } else if (fineDust.equals("보통")) {
            fineDustValue = -5;
        } else if (fineDust.equals("나쁨")) {
            fineDustValue = -20;
        }
        precipitationProbabilityValue = Math.round(100 * (-(1 / (-(1.2) * (precipitationProbability / 100 - 1.5)) - 0.55556))*100)/100.0;

        if (lightPollution < 28.928) {
            lightPollutionValue = Math.round(-(1 / (-(0.001) * (lightPollution - 48)) - 20.833)*100)/100.0;
        } else if (lightPollution >= 28.928 && lightPollution < 77.53) {
            lightPollutionValue = Math.round(-(1 / (-(0.0001) * (lightPollution + 52)) + 155)*100)/100.0;
        } else if (lightPollution >= 77.53 && lightPollution < 88.674) {
            lightPollutionValue = Math.round(-(1 / (-(0.001) * (lightPollution - 110)) + 47)*100)/100.0;
        } else {
            lightPollutionValue = Math.round(-(1 / (-(0.01) * (lightPollution - 71)) + 100)*100)/100.0;
        }

        observationalFitDegree = 100 + cloudVolumeValue + feel_likeValue + moonAgeValue + fineDustValue + precipitationProbabilityValue + lightPollutionValue;

        return Math.round(observationalFitDegree*100)/100.0;
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
                            connectFineDustApi();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("경기")) {
                    choice_do = "경기";
                    cityName = choice_do;

                    choice_se = "가평군";
                    provName = choice_se;

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Gyeonggi, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                            connectFineDustApi();
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
                            connectFineDustApi();
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
                            connectFineDustApi();
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
                            connectFineDustApi();
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
                            connectFineDustApi();
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
                            connectFineDustApi();
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
                            connectFineDustApi();
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
                            connectFineDustApi();
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
                            connectFineDustApi();
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
                            connectFineDustApi();
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
                            connectFineDustApi();
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
        //connectFineDustApi();
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

                    lightPollution = (minLightPol + maxLightPol) / 2;

                    minLightPolText = minLightPol.toString();
                    maxLightPolText = maxLightPol.toString();

                    minLightPolTv.setText(minLightPolText);
                    maxLightPolTv.setText(maxLightPolText);

                    connectMetApi();
                    Log.d("latitude", String.valueOf(latitude));
                    Log.d("longitude", String.valueOf(longitude));
                } else {
                    Log.d("connectWtArea", "지역명으로 경도, 위도 받아오기 실패");
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

    public void setTempVisibility(int state) {
        if (state == 0) {
            tempTv.setVisibility(View.VISIBLE);
            maxTempTv.setVisibility(View.GONE);
            minTempTv.setVisibility(View.GONE);
            tempSlashTv.setVisibility(View.GONE);
        } else {
            tempTv.setVisibility(View.GONE);
            maxTempTv.setVisibility(View.VISIBLE);
            minTempTv.setVisibility(View.VISIBLE);
            tempSlashTv.setVisibility(View.VISIBLE);
        }
    }

    public void setLightPolVisibility(int state) {
        if (state == 0) {
            lightPolTv.setVisibility(View.VISIBLE);
            maxLightPolTv.setVisibility(View.GONE);
            minLightPolTv.setVisibility(View.GONE);
            lightSlashTv.setVisibility(View.GONE);
        } else {
            lightPolTv.setVisibility(View.GONE);
            maxLightPolTv.setVisibility(View.VISIBLE);
            minLightPolTv.setVisibility(View.VISIBLE);
            lightSlashTv.setVisibility(View.VISIBLE);
        }
    }

    //날씨 id로 오늘의 날씨 text 받아오기
    public void connectTodayWeather() {
        Call<WtTodayParams> todayInfoCall = com.starrynight.tourapiproject.weatherPage.wtTodayRetrofit.RetrofitClient.getApiService().getTodayWeatherInfo(todayWtIdInt);
        todayInfoCall.enqueue(new Callback<WtTodayParams>() {
            @Override
            public void onResponse(Call<WtTodayParams> call, Response<WtTodayParams> response) {
                if (response.isSuccessful()) {

                    WtTodayParams result = response.body();
                    todayWtName1 = result.getTodayWtName1();
                    todayWtName2 = result.getTodayWtName2();

                    if(todayWtName2 == null){
                        todayWtName = todayWtName1;
                    }else{
                        todayWtName = todayWtName1 + "\n" + todayWtName2;
                    }

                    todayWeatherTv.setText(todayWtName);
                } else {
                    Log.d("connectWtToday", "id로 오늘의 날씨 받아오기 실패");
                }
            }

            @Override
            public void onFailure(Call<WtTodayParams> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
    }
}