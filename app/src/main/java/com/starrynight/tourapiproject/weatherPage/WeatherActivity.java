package com.starrynight.tourapiproject.weatherPage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @className : WeatherActivity
 * @description : 날씨 페이지입니다.
 * @modification : 2022-09-03 (hyeonz) 주석추가
 * @author : hyeonz
 * @date : 2022-09-03
 * @version : 1.0
====개정이력(Modification Information)====
수정일        수정자        수정내용
-----------------------------------------
2022-09-03      hyeonz    주석추가
 */
public class WeatherActivity extends AppCompatActivity {
    WeatherLoadingDialog dialog;
    WeatherActivity.LoadingAsyncTask task;

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

    LinearLayout timePickerLinear;
    ImageView wtHelp;
    ImageView wtWeather;

    int cntClick = 0;

    //관측적합도
    RecyclerView obFitRecycler;
    ObFitViewAdapter obFitViewAdapter;
    LinearLayout obFit;
    List<Double> obFitList = new ArrayList<>();
    Double obFitMax = 0.0;
    int obFitMaxId = 0;
    List<Integer> obFitImageList = new ArrayList<>();
    Double obFitValue;
    Double obFitValueSelect;
    int obFitApiId;
    int obFitApiPlusId;
    String obFitPercent;

    private String obFitHour[] = {"00", "01", "02", "03", "04", "05"
            , "06", "07", "08", "09", "10", "11", "12", "13", "14", "15"
            , "16", "17", "18", "19", "20", "21", "22", "23", "익일 00", "익일 01", "익일 02", "익일 03", "익일 04", "익일 05"
            , "익일 06", "익일 07", "익일 08", "익일 09", "익일 10", "익일 11", "익일 12", "익일 13", "익일1 4", "익일 15"
            , "익일 16", "익일 17", "익일 18", "익일 19", "익일 20", "익일 21", "익일 22", "익일 23"};

    List<String> obFitHourList = new ArrayList<>();

    double observationalFitDegree;
    double cloudVolume;
    double cloudVolumeValue;
    double feel_like;
    double feel_likeValue;
    double moonAge;
    double moonAgeValue;
    String fineDust="없음";
    String fineDustSt="없음";
    double fineDustValue;
    double precipitationProbability;
    double precipitationProbabilityValue;
    double lightPollution;
    double lightPollutionValue;
    double biggestValue;
    double averageValue;
    double obFitFinal;

    private TextView datePicker;
    private DatePickerDialog.OnDateSetListener dateListener;
    private TextView timePicker;
    private TimePickerDialog.OnTimeSetListener timeListener;


    TextView wtTimePickerHour;

    String WT_MET_API_KEY;
    String WT_FINE_DUST_API_KEY;

    String strDate;

    String timePickerTxt;

    ArrayAdapter<CharSequence> cityAdSpin, provAdSpin;
    String choice_se = "";

    // 선택한 날짜 + 시간
    String selectDateTime;
    String todayDateTime;

    String selectDate;
    String todayDateDash;
    String nextDateDash;
    String selectTime;
    String selectDateDash;

    String todayDate;
    String todayTime;

    String plusDay;
    String plusTwoDay;


    String timeZero = "00";
    String timeNoon = "12";

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
    String unixToHour;

    String sunriseHr;
    String sunsetHr;
    String selectHr;
    Integer selectHrInt;

    Date unixDate;
    Date unixDay;
    Date unixHourMin;
    Date unixHour;

    //강수량
    double doublePrecip;

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
    TextView tempTextTv;
    TextView moonPhaseTv;
    TextView fineDustTv;
    TextView windTv;
    TextView humidityTv;
    TextView precipitationTv;
    TextView lightPolTv;
    TextView minLightPolTv;
    TextView maxLightPolTv;
    TextView lightSlashTv;

    TextView detailMent;

    String cloudValue;
    String feelsLikeValue;
    String precipValue;
    String tempValue;
    Double tempValueDouble;
    String humidityValue;
    String windValue;
    String tempMaxValue;
    String tempMinValue;

    String cloudText;
    String tempText;
    String tempMaxText;
    String tempMinText;
    String moonPhaseText;
    String windText;
    String fineDustText;
    String fineDustText2;
    String humidityText;
    String precipText;

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
    int dustCheck = 0;
    int dustNoCheck = 0;

    Date changeDate;
    Date dustNextDate;
    Date changeSelectDate;
    String addDate;

    String listDust;
    String listDustNext;
    String[] arrayComma;
    String[] arrayCommaNext;

    String[] dustStateArray = new String[19];
    String[] dustStateArray2 = new String[19];
    int index;
    String state;

    //날씨 상태
    LinearLayout detailWeather;
    String cloudState;
    String tempState;
    String moonPhaseState;
    String fineDustState;
    String windState;
    String humidityState;
    String precipState;
    String lightPolState;

    //오늘의 날씨
    String todayWtId;
    int todayWtIdInt;
    String todayWtName1;
    String todayWtName2;
    String todayWtName;

    //현위치
    private GpsTracker gpsTracker;

    String nowLocation;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    Double nowLatitude;
    Double nowLongitude;

    String nowCity = "서울";
    String nowProvince = "강남구";

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

        dialog = new WeatherLoadingDialog(WeatherActivity.this);
        task = new LoadingAsyncTask(WeatherActivity.this, 5000);
        task.execute();

        if (!checkLocationServicesStatus()) {
            //gps 동의
            showDialogForLocationServiceSetting();
            Log.d("checkLocationCancel", "0");
        } else {
            Log.d("checkLocationCancel", "1");
            checkRunTimePermission();
        }

        gpsTracker = new GpsTracker(WeatherActivity.this);

        nowLatitude = gpsTracker.getLatitude();
        nowLongitude = gpsTracker.getLongitude();

        nowLocation = getCurrentAddress(nowLatitude, nowLongitude);
        Log.d("nowLocationResult", nowLocation);
        Log.d("nowCityProv1", nowCity + " " + nowProvince);

        setCityName();

        setTextView();
        onClickBackBtn();
        onClickCloudInfo();
        onClickHelpBtn();

        onSetDatePicker();
        onSetTimePicker();

        //지역 선택 Spinner
        onSetAreaSpinner();

        selectDateTime = selectDate + selectTime;
        Log.d("selectDateTime", selectDateTime);

    }

    // 별 관측 적합도 recycler 메소드
    @SuppressLint("SetTextI18n")
    public void setObFitRecycler() {
        obFitRecycler = findViewById(R.id.ob_fit_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        obFitRecycler.setLayoutManager(linearLayoutManager);

        obFitViewAdapter = new ObFitViewAdapter();
        obFitRecycler.setAdapter(obFitViewAdapter);

        Log.d("obFitListOut", obFitList.toString());

        for (int i = 0; i < obFitList.size(); i++) {
            if (obFitList.get(i) > obFitMax) {
                obFitMax = obFitList.get(i);
                obFitMaxId = i;
                Log.d("obFitMaxCheck", String.valueOf(obFitMax));
                Log.d("obFitMaxCheck", String.valueOf(obFitMaxId));
            }

            if (obFitList.get(i) < 40) {
                obFitImageList.add(R.drawable.wt__hour_very_bad);
            } else if (obFitList.get(i) < 60) {
                obFitImageList.add(R.drawable.wt__hour_very_bad);
            } else if (obFitList.get(i) < 70) {
                obFitImageList.add(R.drawable.wt__hour_average);
            } else if (obFitList.get(i) < 85) {
                obFitImageList.add(R.drawable.wt__hour_good);
            } else {
                obFitImageList.add(R.drawable.wt__hour_very_good);
            }
        }
        bestObTimeTv.setText(obFitHourList.get(obFitMaxId) + "시");
        obFitMax = 0.0;

        for (int i = 0; i < obFitList.size(); i++) {
            obFitPercent = String.format("%.0f", obFitList.get(i)) + "%";
            obFitViewAdapter.addItem(new ObFitItem(obFitImageList.get(i), obFitHourList.get(i) + "시", obFitPercent));
        }
        obFitImageList.clear();
        obFitRecycler.setAdapter(obFitViewAdapter);
    }

    // textView를 세팅하는 메소드
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
        tempTextTv = findViewById(R.id.wt_temp_text);
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

        detailMent = findViewById(R.id.wt_detail_ment);

        wtTimePickerHour = findViewById(R.id.wt_timePicker_hour);
        timePickerLinear = findViewById(R.id.wt_timePicker_linear);
        wtHelp = findViewById(R.id.wt_help);
        wtWeather = findViewById(R.id.wt_weather);
    }

    // 뒤로가기 버튼 이벤트 메소드
    public void onClickBackBtn() {
        ImageView button = findViewById(R.id.wt_back_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // 도움말 버튼 이벤트 메소드
    public void onClickHelpBtn() {
        wtHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WtHelpActivity.class);
                startActivity(intent);
            }
        });
    }

    // 기상청 구름 정보 페이지로 이동 이벤트 메소드
    public void onClickCloudInfo() {
        TextView button1 = findViewById(R.id.wt_today_cloud);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.weather.go.kr/wgis-nuri/html/map.html"));
                startActivity(intent);
            }
        });
    }

    // 날짜 Picker 설정 메소드
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
        timePickerLinear.setVisibility(View.VISIBLE);
        timePicker.setText(todayTime);
        wtTimePickerHour.setVisibility(View.VISIBLE);

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

                if (todayTime.equals("00")) {
                    if (selectDate.equals(plusDay)) {
                        timePickerLinear.setVisibility(View.VISIBLE);
                        timePicker.setText(timeZero);
                        wtTimePickerHour.setVisibility(View.VISIBLE);
                        selectTime = timeZero;
                        Log.d("selectTime1", selectTime);
                    } else if (selectDate.equals(todayDate)) {
                        Log.d("todayTime3", todayTime);
                        timePickerLinear.setVisibility(View.VISIBLE);
                        timePicker.setText(todayTime);
                        wtTimePickerHour.setVisibility(View.VISIBLE);
                    } else {
                        timePickerLinear.setVisibility(View.GONE);
                        wtTimePickerHour.setVisibility(View.GONE);
                        selectTime = timeNoon;
                        Log.d("selectTime4", selectTime);
                    }
                } else {
                    if (selectDate.equals(plusDay)) {
                        timePickerLinear.setVisibility(View.VISIBLE);
                        timePicker.setText(timeZero);
                        wtTimePickerHour.setVisibility(View.VISIBLE);
                        selectTime = timeZero;
                        Log.d("selectTime1", selectTime);
                    } else if (selectDate.equals(plusTwoDay)) {
                        timePickerLinear.setVisibility(View.VISIBLE);
                        timePicker.setText(timeZero);
                        wtTimePickerHour.setVisibility(View.VISIBLE);
                        selectTime = timeZero;
                        Log.d("selectTime2", selectTime);
                    } else if (selectDate.equals(todayDate)) {
                        Log.d("todayTime3", todayTime);
                        timePickerLinear.setVisibility(View.VISIBLE);
                        timePicker.setText(todayTime);
                        wtTimePickerHour.setVisibility(View.VISIBLE);
                    } else {
                        timePickerLinear.setVisibility(View.GONE);
                        wtTimePickerHour.setVisibility(View.GONE);
                        selectTime = timeNoon;
                        Log.d("selectTime4", selectTime);
                    }
                }

                selectDateTime = selectDate + selectTime;
                Log.d("selectDateTime", selectDateTime);

                connectMetApi();
                Log.d("connectMetApiCheck", "0");
                connectFineDustApi();
                Log.d("obCheck", "0");
            }
        };
    }

    // 날짜 선택 이벤트 메소드
    public void wtClickDatePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateListener, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(today.getTimeInMillis());
        today.add(Calendar.DAY_OF_MONTH, 7);
        datePickerDialog.getDatePicker().setMaxDate(today.getTimeInMillis());
        datePickerDialog.show();
        today.add(Calendar.DAY_OF_MONTH, -7);
    }

    // 시간 설정 메소드
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
    }

    //시간 선택 이벤트 메소드
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
                Log.d("checkSelectTime", selectTime);
                if (todayTime.equals("00")) {
                    if ((selectDate.equals(todayDate)) || (selectDate.equals(plusDay))) {
                        timePickerTxt = hourChange[numberPicker.getValue()];
                        timePickerLinear.setVisibility(View.VISIBLE);
                        timePicker.setText(timePickerTxt);
                        wtTimePickerHour.setVisibility(View.VISIBLE);
                        selectTime = String.valueOf(hourChange[numberPicker.getValue()]);
                    } else {
                        timePickerLinear.setVisibility(View.VISIBLE);
                        wtTimePickerHour.setVisibility(View.GONE);
                        selectTime = timeNoon;
                    }
                } else {
                    if ((selectDate.equals(todayDate)) || (selectDate.equals(plusDay)) || (selectDate.equals(plusTwoDay))) {
                        timePickerTxt = hourChange[numberPicker.getValue()];
                        timePickerLinear.setVisibility(View.VISIBLE);
                        timePicker.setText(timePickerTxt);
                        wtTimePickerHour.setVisibility(View.VISIBLE);
                        selectTime = String.valueOf(hourChange[numberPicker.getValue()]);
                    } else {
                        timePickerLinear.setVisibility(View.GONE);
                        wtTimePickerHour.setVisibility(View.GONE);
                        selectTime = timeNoon;
                    }
                }

                Log.d("selectTime6", selectTime);

                selectDateTime = selectDate + selectTime;
                Log.d("selectDateTime", selectDateTime);

                connectMetApi();
                Log.d("connectMetApiCheck", "1");
                alertDialog.dismiss();
            }
        });
    }

    // 지역별 날씨 연결 메소드
    public void connectMetApi() {
        Log.d("latitude1`", String.valueOf(latitude));
        Log.d("longitude1", String.valueOf(longitude));

        obFit = findViewById(R.id.ob_fit);

        Call<WtMetModel> getWeatherInstance = WtMetRetrofit.wtMetInterface()
                .getMetData(latitude, longitude, "minutely,current", WT_MET_API_KEY, "metric");

        getWeatherInstance.enqueue(new Callback<WtMetModel>() {
            @SuppressLint({"DefaultLocale", "SetTextI18n", "LongLogTag"})
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
                            moonAge = Double.parseDouble(moonPhaseText);
                            moonPhaseTv.setText(moonPhaseText);

                            //일출
                            unixSunMoon = data.getDaily().get(i).getSunrise();
                            unixSunrise = unixSunMoon;
                            unixChange(unixSunMoon);
                            sunriseTv.setText(unixToHourMin);

                            sunriseHr = unixToHour;
                            Log.d("sunriseHr", unixToHour);

                            //일몰
                            unixSunMoon = data.getDaily().get(i).getSunset();
                            unixSunset = unixSunMoon;
                            unixChange(unixSunMoon);
                            sunsetTv.setText(unixToHourMin);

                            sunsetHr = unixToHour;
                            Log.d("sunsetHr", unixToHour);

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

                    if (todayTime.equals("00")) {
                        if (selectDate.equals(todayDate) || selectDate.equals(plusDay)) {
                            for (int i = 0; i < 48; i++) {
                                unixTime = data.getHourly().get(i).getDt();
                                unixChange(unixTime);
                                Log.d("unixToDate", unixToDate);

                                if (selectDateTime.equals(unixToDate)) {
                                    Log.d("selectDateTime", selectDateTime);
                                    //tempTv만 나오게
                                    setTempVisibility(0);

                                    cloudValue = data.getHourly().get(i).getClouds();
                                    feelsLikeValue = data.getHourly().get(i).getFeelsLike();
                                    precipValue = data.getHourly().get(i).getPop();
                                    tempValue = data.getHourly().get(i).getTemp();
                                    humidityValue = data.getHourly().get(i).getHumidity();
                                    windValue = data.getHourly().get(i).getWindSpeed();

                                    cloudText = cloudValue + "%";
                                    tempText = String.format("%.1f", Double.parseDouble(tempValue)) + "°C";
                                    windText = windValue + "m/s";
                                    humidityText = humidityValue + "%";
                                    todayWtId = data.getHourly().get(i).getWeather().get(0).getId();
                                    todayWtIdInt = Integer.parseInt(todayWtId);
                                    doublePrecip = Double.parseDouble(precipValue) * 100;
                                    precipText = doublePrecip + "%";

                                    //관측적합도
                                    cloudVolume = Double.parseDouble(cloudValue);
                                    feel_like = Double.parseDouble(feelsLikeValue);
                                    precipitationProbability = Double.parseDouble(precipValue);
                                    obFitValueSelect = setObservationalFitDegree();
                                    Log.d("cloudVolume", String.valueOf(cloudVolume));
                                    Log.d("feel_like", String.valueOf(feel_like));
                                    Log.d("precipitationProbability", String.valueOf(precipitationProbability));
                                    Log.d("obFitValueSelect", String.valueOf(obFitValueSelect));

                                    setDetailState(cloudVolume, Double.parseDouble(tempValue),
                                            Double.parseDouble(humidityValue), moonAge,
                                            Double.parseDouble(windValue), doublePrecip, lightPollution, fineDustSt);

                                    cloudTv.setText(cloudText);
                                    tempTv.setText(tempText);
                                    tempTextTv.setText("기온");
                                    windTv.setText(windText);
                                    humidityTv.setText(humidityText);
                                    precipitationTv.setText(precipText);

                                    selectHr = selectTime;
                                    Log.d("selectHr", selectHr);
                                    selectHrInt = Integer.parseInt(selectHr);
                                    if (selectHrInt >= 7 && selectHrInt < 18) {
                                        starObFitTv.setText("0%");
                                        setObFitComment(0.0);
                                    } else {
                                        starObFitTv.setText(String.format("%.0f", obFitValueSelect) + "%");
                                        setObFitComment(obFitValueSelect);
                                    }
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

                                    cloudValue = data.getDaily().get(i).getClouds();
                                    feelsLikeValue = data.getDaily().get(i).getFeelsLike().getDay();
                                    precipValue = data.getDaily().get(i).getPop();
                                    tempMinValue = data.getDaily().get(i).getTemp().getMin();
                                    tempMaxValue = data.getDaily().get(i).getTemp().getMax();
                                    humidityValue = data.getDaily().get(i).getHumidity();
                                    windValue = data.getDaily().get(i).getWindSpeed();

                                    cloudText = cloudValue + "%";
                                    tempMinText = String.format("%.1f", Double.parseDouble(tempMinValue)) + "°C";
                                    tempMaxText = String.format("%.1f", Double.parseDouble(tempMaxValue)) + "°C";

                                    tempValueDouble = (Double.parseDouble(tempMinValue) + Double.parseDouble(tempMaxValue)) / 2;

                                    windText = windValue + "m/s";
                                    humidityText = humidityValue + "%";
                                    todayWtId = data.getDaily().get(i).getWeather().get(0).getId();

                                    doublePrecip = Double.parseDouble(precipValue) * 100;
                                    precipText = doublePrecip + "%";

                                    //관측적합도
                                    cloudVolume = Double.parseDouble(cloudValue);
                                    feel_like = Double.parseDouble(feelsLikeValue);
                                    precipitationProbability = Double.parseDouble(precipValue);
                                    obFitValueSelect = setObservationalFitDegree();
                                    Log.d("cloudVolume", String.valueOf(cloudVolume));
                                    Log.d("feel_like", String.valueOf(feel_like));
                                    Log.d("precipitationProbability", String.valueOf(precipitationProbability));
                                    Log.d("obFitValueSelect", String.valueOf(obFitValueSelect));

                                    setDetailState(cloudVolume, tempValueDouble,
                                            Double.parseDouble(humidityValue), moonAge,
                                            Double.parseDouble(windValue), doublePrecip, lightPollution, fineDustSt);

                                    cloudTv.setText(cloudText);
                                    minTempTv.setText(tempMinText);
                                    maxTempTv.setText(tempMaxText);
                                    tempTextTv.setText("기온\n(최저/최고)");
                                    windTv.setText(windText);
                                    humidityTv.setText(humidityText);
                                    precipitationTv.setText(precipText);

                                    starObFitTv.setText(String.format("%.0f", obFitValueSelect) + "%");
                                    setObFitComment(obFitValueSelect);

                                    connectTodayWeather();
                                    Log.d("getIDaily", String.valueOf(i));
                                }
                            }
                        }
                    } else {
                        if (selectDate.equals(todayDate) || selectDate.equals(plusDay) || selectDate.equals(plusTwoDay)) {
                            for (int i = 0; i < 48; i++) {
                                unixTime = data.getHourly().get(i).getDt();
                                unixChange(unixTime);
                                Log.d("unixToDate", unixToDate);

                                if (selectDateTime.equals(unixToDate)) {
                                    //tempTv만 나오게
                                    setTempVisibility(0);

                                    cloudValue = data.getHourly().get(i).getClouds();
                                    feelsLikeValue = data.getHourly().get(i).getFeelsLike();
                                    precipValue = data.getHourly().get(i).getPop();
                                    tempValue = data.getHourly().get(i).getTemp();
                                    humidityValue = data.getHourly().get(i).getHumidity();
                                    windValue = data.getHourly().get(i).getWindSpeed();

                                    cloudText = cloudValue + "%";
                                    tempText = String.format("%.1f", Double.parseDouble(tempValue)) + "°C";
                                    windText = windValue + "m/s";
                                    humidityText = humidityValue + "%";
                                    todayWtId = data.getHourly().get(i).getWeather().get(0).getId();
                                    todayWtIdInt = Integer.parseInt(todayWtId);
                                    doublePrecip = Double.parseDouble(precipValue) * 100;
                                    precipText = doublePrecip + "%";

                                    //관측적합도
                                    cloudVolume = Double.parseDouble(cloudValue);
                                    feel_like = Double.parseDouble(feelsLikeValue);
                                    precipitationProbability = Double.parseDouble(precipValue);
                                    obFitValueSelect = setObservationalFitDegree();
                                    Log.d("cloudVolume", String.valueOf(cloudVolume));
                                    Log.d("feel_like", String.valueOf(feel_like));
                                    Log.d("precipitationProbability", String.valueOf(precipitationProbability));
                                    Log.d("obFitValueSelect", String.valueOf(obFitValueSelect));

                                    setDetailState(cloudVolume, Double.parseDouble(tempValue),
                                            Double.parseDouble(humidityValue), moonAge,
                                            Double.parseDouble(windValue), doublePrecip, lightPollution, fineDustSt);

                                    cloudTv.setText(cloudText);
                                    tempTv.setText(tempText);
                                    tempTextTv.setText("기온");
                                    windTv.setText(windText);
                                    humidityTv.setText(humidityText);
                                    precipitationTv.setText(precipText);

                                    selectHr = selectTime;
                                    Log.d("selectHr", selectHr);
                                    selectHrInt = Integer.parseInt(selectHr);
                                    if (selectHrInt >= 7 && selectHrInt < 18) {
                                        starObFitTv.setText("0%");
                                        setObFitComment(0.0);
                                    } else {
                                        starObFitTv.setText(String.format("%.0f", obFitValueSelect) + "%");
                                        setObFitComment(obFitValueSelect);
                                    }
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

                                    cloudValue = data.getDaily().get(i).getClouds();
                                    feelsLikeValue = data.getDaily().get(i).getFeelsLike().getDay();
                                    precipValue = data.getDaily().get(i).getPop();
                                    tempMinValue = data.getDaily().get(i).getTemp().getMin();
                                    tempMaxValue = data.getDaily().get(i).getTemp().getMax();
                                    humidityValue = data.getDaily().get(i).getHumidity();
                                    windValue = data.getDaily().get(i).getWindSpeed();

                                    cloudText = cloudValue + "%";
                                    tempMinText = String.format("%.1f", Double.parseDouble(tempMinValue)) + "°C";
                                    tempMaxText = String.format("%.1f", Double.parseDouble(tempMaxValue)) + "°C";

                                    tempValueDouble = (Double.parseDouble(tempMinValue) + Double.parseDouble(tempMaxValue)) / 2;

                                    windText = windValue + "m/s";
                                    humidityText = humidityValue + "%";
                                    todayWtId = data.getDaily().get(i).getWeather().get(0).getId();

                                    doublePrecip = Double.parseDouble(precipValue) * 100;
                                    precipText = doublePrecip + "%";

                                    //관측적합도
                                    cloudVolume = Double.parseDouble(cloudValue);
                                    feel_like = Double.parseDouble(feelsLikeValue);
                                    precipitationProbability = Double.parseDouble(precipValue);
                                    obFitValueSelect = setObservationalFitDegree();
                                    Log.d("cloudVolume", String.valueOf(cloudVolume));
                                    Log.d("feel_like", String.valueOf(feel_like));
                                    Log.d("precipitationProbability", String.valueOf(precipitationProbability));
                                    Log.d("obFitValueSelect", String.valueOf(obFitValueSelect));

                                    setDetailState(cloudVolume, tempValueDouble,
                                            Double.parseDouble(humidityValue), moonAge,
                                            Double.parseDouble(windValue), doublePrecip, lightPollution, fineDustSt);

                                    cloudTv.setText(cloudText);
                                    minTempTv.setText(tempMinText);
                                    maxTempTv.setText(tempMaxText);
                                    tempTextTv.setText("기온\n(최저/최고)");
                                    windTv.setText(windText);
                                    humidityTv.setText(humidityText);
                                    precipitationTv.setText(precipText);

                                    starObFitTv.setText(String.format("%.0f", obFitValueSelect) + "%");
                                    setObFitComment(obFitValueSelect);
                                    connectTodayWeather();
                                    Log.d("getIDaily", String.valueOf(i));
                                }
                            }
                        }
                    }

                    // 시간별 관측적합도

                    // 관측적합도 리스트 초기화
                    obFitList.clear();
                    obFitHourList.clear();
                    if (selectDate.equals(todayDate)) {
                        obFit.setVisibility(View.VISIBLE);

                        //당일 0?시 ~ 당일 06시
                        if (todayDateTime.equals(todayDate + "00")) {
                            obFitApiId = 0;
                        } else if (todayDateTime.equals(todayDate + "01")) {
                            obFitApiId = 1;
                        } else if (todayDateTime.equals(todayDate + "02")) {
                            obFitApiId = 2;
                        } else if (todayDateTime.equals(todayDate + "03")) {
                            obFitApiId = 3;
                        } else if (todayDateTime.equals(todayDate + "04")) {
                            obFitApiId = 4;
                        } else if (todayDateTime.equals(todayDate + "05")) {
                            obFitApiId = 5;
                        } else if (todayDateTime.equals(todayDate + "06")) {
                            obFitApiId = 6;
                        } else if (todayDateTime.equals(todayDate + "07")) {
                            obFitApiId = 7;
                        } else if (todayDateTime.equals(todayDate + "08")) {
                            obFitApiId = 8;
                        } else if (todayDateTime.equals(todayDate + "09")) {
                            obFitApiId = 9;
                        } else if (todayDateTime.equals(todayDate + "10")) {
                            obFitApiId = 10;
                        } else if (todayDateTime.equals(todayDate + "11")) {
                            obFitApiId = 11;
                        } else if (todayDateTime.equals(todayDate + "12")) {
                            obFitApiId = 12;
                        } else if (todayDateTime.equals(todayDate + "13")) {
                            obFitApiId = 13;
                        } else if (todayDateTime.equals(todayDate + "14")) {
                            obFitApiId = 14;
                        } else if (todayDateTime.equals(todayDate + "15")) {
                            obFitApiId = 15;
                        } else if (todayDateTime.equals(todayDate + "16")) {
                            obFitApiId = 16;
                        } else if (todayDateTime.equals(todayDate + "17")) {
                            obFitApiId = 17;
                        } else if (todayDateTime.equals(todayDate + "18")) {
                            obFitApiId = 18;
                        } else if (todayDateTime.equals(todayDate + "19")) {
                            obFitApiId = 19;
                        } else if (todayDateTime.equals(todayDate + "20")) {
                            obFitApiId = 20;
                        } else if (todayDateTime.equals(todayDate + "21")) {
                            obFitApiId = 21;
                        } else if (todayDateTime.equals(todayDate + "22")) {
                            obFitApiId = 22;
                        } else if (todayDateTime.equals(todayDate + "23")) {
                            obFitApiId = 23;
                        }
                        Log.d("obFitApiId", String.valueOf(obFitApiId));

                        if (obFitApiId < 7) {
                            for (int j = 0; j < 7 - obFitApiId; j++) {
                                cloudVolume = Double.parseDouble(data.getHourly().get(j).getClouds());
                                feel_like = Double.parseDouble(data.getHourly().get(j).getFeelsLike());
                                precipitationProbability = Double.parseDouble(data.getHourly().get(j).getPop());
                                obFitValue = setObservationalFitDegree();
                                obFitList.add(obFitValue);
                                obFitHourList.add(obFitHour[obFitApiId + j]);
                                Log.d("obFitCloud", String.valueOf(cloudVolume));
                                Log.d("obFitTemp", String.valueOf(feel_like));
                                Log.d("obFitMoon", String.valueOf(moonAge));
                                Log.d("obFitFineDust", fineDust);
                                Log.d("obFitPrecip", String.valueOf(precipitationProbability));
                                Log.d("obFitLightPol", String.valueOf(lightPollution));
                                Log.d("obValue", String.valueOf(setObservationalFitDegree()));
                            }
                        }

                        if (obFitApiId >= 18 && obFitApiId < 24) {
                            // 당일 1?시 ~ 당일 23시
                            for (int j = 0; j < 24 - obFitApiId; j++) {
                                cloudVolume = Double.parseDouble(data.getHourly().get(j).getClouds());
                                feel_like = Double.parseDouble(data.getHourly().get(j).getFeelsLike());
                                precipitationProbability = Double.parseDouble(data.getHourly().get(j).getPop());
                                obFitValue = setObservationalFitDegree();
                                obFitList.add(obFitValue);
                                obFitHourList.add(obFitHour[obFitApiId + j]);
                                Log.d("obFitCloud", String.valueOf(cloudVolume));
                                Log.d("obFitTemp", String.valueOf(feel_like));
                                Log.d("obFitMoon", String.valueOf(moonAge));
                                Log.d("obFitFineDust", fineDust);
                                Log.d("obFitPrecip", String.valueOf(precipitationProbability));
                                Log.d("obFitLightPol", String.valueOf(lightPollution));
                                Log.d("obValue", String.valueOf(setObservationalFitDegree()));
                            }
                        } else {
                            // 당일 오후 6시 ~ 당일 23시
                            for (int j = 18 - obFitApiId; j < 24 - obFitApiId; j++) {
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
                            obFitHourList.addAll(Arrays.asList(obFitHour).subList(18, 24));
                        }
                        //익일 00시 ~ 익일 06시
                        moonPhaseText = data.getDaily().get(1).getMoonPhase();
                        moonAge = Double.parseDouble(moonPhaseText);
                        Log.d("obFitSelect", String.valueOf(moonAge));

                        connectNextFineDustApi();
                        for (int j = 24 - obFitApiId; j < 31 - obFitApiId; j++) {
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
                        obFitHourList.addAll(Arrays.asList(obFitHour).subList(24, 31));
                        setObFitRecycler();
                    } else if (selectDate.equals(plusDay)) {
                        obFit.setVisibility(View.VISIBLE);
                        obFitList.clear();

                        for (int i = 0; i < 48; i++) {
                            unixTime = data.getHourly().get(i).getDt();
                            unixChange(unixTime);
                            Log.d("unixToDate", unixToDate);
                            if (unixToDate.equals(plusDay + "00")) {
                                obFitApiPlusId = i;
                            }
                        }
                        // 익일 00시 ~ 익일 06시
                        for (int i = obFitApiPlusId; i < obFitApiPlusId + 7; i++) {
                            cloudVolume = Double.parseDouble(data.getHourly().get(i).getClouds());
                            moonAge = Double.parseDouble(moonPhaseText);
                            feel_like = Double.parseDouble(data.getHourly().get(i).getFeelsLike());
                            precipitationProbability = Double.parseDouble(data.getHourly().get(i).getPop());
                            obFitValue = setObservationalFitDegree();
                            obFitList.add(obFitValue);
                            Log.d("errorcheck", "0");
                        }
                        obFitHourList.addAll(Arrays.asList(obFitHour).subList(0, 7));

                        // 익일 18시 ~ 익일 23시
                        for (int i = obFitApiPlusId + 18; i < obFitApiPlusId + 24; i++) {
                            cloudVolume = Double.parseDouble(data.getHourly().get(i).getClouds());
                            moonAge = Double.parseDouble(moonPhaseText);
                            feel_like = Double.parseDouble(data.getHourly().get(i).getFeelsLike());
                            precipitationProbability = Double.parseDouble(data.getHourly().get(i).getPop());
                            obFitValue = setObservationalFitDegree();
                            obFitList.add(obFitValue);
                            Log.d("errorcheck", "1");
                        }
                        obFitHourList.addAll(Arrays.asList(obFitHour).subList(18, 24));
                        Log.d("obFitApiid", "obFitApiId");
                        if (obFitApiId >= 1 && obFitApiId < 7) {
                            for (int i = obFitApiPlusId + 24; i < obFitApiPlusId + obFitApiId + 24; i++) {
                                cloudVolume = Double.parseDouble(data.getHourly().get(i).getClouds());
                                moonAge = Double.parseDouble(moonPhaseText);
                                feel_like = Double.parseDouble(data.getHourly().get(i).getFeelsLike());
                                precipitationProbability = Double.parseDouble(data.getHourly().get(i).getPop());
                                obFitValue = setObservationalFitDegree();
                                obFitList.add(obFitValue);
                                Log.d("errorcheck", "2");
                            }
                            obFitHourList.addAll(Arrays.asList(obFitHour).subList(24, 24 + obFitApiId));
                        } else if (obFitApiId >= 7) {
                            for (int i = obFitApiPlusId + 24; i < obFitApiPlusId + 31; i++) {
                                cloudVolume = Double.parseDouble(data.getHourly().get(i).getClouds());
                                moonAge = Double.parseDouble(moonPhaseText);
                                feel_like = Double.parseDouble(data.getHourly().get(i).getFeelsLike());
                                precipitationProbability = Double.parseDouble(data.getHourly().get(i).getPop());
                                obFitValue = setObservationalFitDegree();
                                obFitList.add(obFitValue);
                                Log.d("errorcheck", "3");
                            }
                            obFitHourList.addAll(Arrays.asList(obFitHour).subList(24, 31));
                        }
                        setObFitRecycler();
                    } else {
                        obFit.setVisibility(View.GONE);
                        bestObTimeTv.setText("정보없음");
                    }

                    detailWeather.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public void onClick(View v) {
                            if (cntClick == 0) {
                                if (maxTempTv.getVisibility() == View.VISIBLE) {
                                    setTempVisibility(0);
                                }

                                if (maxLightPolTv.getVisibility() == View.VISIBLE) {
                                    setLightPolVisibility(0);
                                }

                                cloudTv.setText(cloudState);
                                tempTv.setText(tempState);
                                moonPhaseTv.setText(moonPhaseState);
                                Log.d("dustNoInfo1", String.valueOf(dustNoInfo));

                                if (dustNoCheck == 1) {
                                    fineDustTv.setText("정보없음");
                                } else {
                                    fineDustTv.setText(fineDustState);
                                }

                                windTv.setText(windState);
                                humidityTv.setText(humidityState);
                                precipitationTv.setText(precipState);
                                lightPolTv.setText(lightPolState);
                                detailMent.setText("터치해서 상세 수치를 확인해보세요!");

                                cntClick = 1;
                            } else {
                                if (todayTime.equals("00")) {
                                    if (selectDate.equals(todayDate) || selectDate.equals(plusDay)) {
                                        tempTv.setText(tempText);
                                        tempTextTv.setText("기온");
                                    } else {
                                        setTempVisibility(1);

                                        minTempTv.setText(tempMinText);
                                        maxTempTv.setText(tempMaxText);
                                        tempTextTv.setText("기온\n(최저/최고)");
                                    }
                                } else {
                                    if (selectDate.equals(todayDate) || selectDate.equals(plusDay) || selectDate.equals(plusTwoDay)) {
                                        tempTv.setText(tempText);
                                    } else {
                                        setTempVisibility(1);

                                        minTempTv.setText(tempMinText);
                                        maxTempTv.setText(tempMaxText);
                                    }
                                }

                                cloudTv.setText(cloudText);
                                moonPhaseTv.setText(moonPhaseText);
                                Log.d("dustNoInfo2", String.valueOf(dustNoInfo));

                                if (dustNoCheck == 1) {
                                    fineDustTv.setText("정보없음");
                                } else {
                                    fineDustTv.setText(fineDustSt);
                                }

                                windTv.setText(windText);
                                humidityTv.setText(humidityText);
                                precipitationTv.setText(precipText);

                                setLightPolVisibility(1);

                                maxLightPolTv.setText(maxLightPolText);
                                minLightPolTv.setText(minLightPolText);
                                detailMent.setText("터치해서 요약 정보를 확인해보세요!");


                                cntClick = 0;
                            }
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

    /**
     * TODO unix 시간 변환 메소드
     * @param  unixTime - unix 시간
     */
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

        //HH
        unixHour = new java.util.Date(Long.parseLong(unixTime) * 1000L);
        formatHour.setTimeZone(java.util.TimeZone.getTimeZone("GMT+9"));
        unixToHour = formatHour.format(unixHour);
    }

    // 미세먼지 API 연결
    public void connectFineDustApi() {
        if (dustCheck == 0) {
            if ((todayTime.equals("00") || todayTime.equals("01") || todayTime.equals("02") || todayTime.equals("03") || todayTime.equals("04"))) {
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
                dustCheck = 1;
            } else {
                try {
                    addDate = todayDate;
                    changeDate = formatDate2.parse(addDate);
                    todayDateDash = formatDateDash.format(changeDate);
                    Log.d("todayDateDash", todayDateDash);
                    Log.d("dustApi", "오늘 날짜로 api 호출");

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            dustCheck = 1;
        }

        try {
            changeSelectDate = formatDate2.parse(selectDate);
            selectDateDash = formatDateDash.format(changeSelectDate);
            Log.d("selectDateDash", selectDateDash);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d("dustCheck", String.valueOf(dustCheck));
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
                            dustNoCheck = 0;
                            Log.d("dustNoInfo3", String.valueOf(dustNoInfo));
                            break;
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
                } else if (cityName.equals("충남")) {
                    fineDustText = dustStateArray[10];
                    Log.d("dust", "10");

                } else if (cityName.equals("대전·세종")) {
                    //대전
                    if (provName.equals("대덕구") || provName.equals("동구") || provName.equals("서구") || provName.equals("유성구") || provName.equals("중구")) {
                        fineDustText = dustStateArray[13];
                        Log.d("dust", "13");
                    }
                    //세종
                    else {
                        fineDustText = dustStateArray[12];
                        Log.d("dust", "12");
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

                fineDustSt = setFineDustValue(fineDustText);
                fineDustTv.setText(fineDustSt);
                fineDust = fineDustText;
                if (dustNoInfo == 0) {
                    fineDust = "좋음";
                    dustNoCheck = 1;
                    fineDustTv.setText("정보없음");
                    Log.d("dustNoInfo4", String.valueOf(dustNoInfo));
                    Log.d("obFitCheckWhenDustNo", fineDust);
                }
                dustNoInfo = 0;
                Log.d("dustNoInfo7", String.valueOf(dustNoInfo));
            }

            @Override
            public void onFailure(Call<WtFineDustModel> call, Throwable t) {
                t.printStackTrace();
                Log.v("FineDust", "responseError= " + call.request().url());
            }
        });
    }

    //다음 날 미세먼지 API 연결
    public void connectNextFineDustApi() {
        if (dustCheck == 0) {
            if ((todayTime.equals("00") || todayTime.equals("01") || todayTime.equals("02") || todayTime.equals("03") || todayTime.equals("04"))) {
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
                dustCheck = 1;
            } else {
                try {
                    addDate = todayDate;
                    changeDate = formatDate2.parse(addDate);
                    todayDateDash = formatDateDash.format(changeDate);
                    Log.d("todayDateDash", todayDateDash);
                    Log.d("dustApi", "오늘 날짜로 api 호출");

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            dustCheck = 1;
        }

        try {
            dustNextDate = formatDate2.parse(plusDay);
            nextDateDash = formatDateDash.format(dustNextDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d("dustCheck", String.valueOf(dustCheck));
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
                                nextDateDash.equals(data.getResponse().getBody().getItems().get(i).getInformData())) {
                            listDustNext = data.getResponse().getBody().getItems().get(i).getInformGrade();
                            Log.d("dustDateTime", data.getResponse().getBody().getItems().get(i).getDataTime());
                            Log.d("dustInformData", data.getResponse().getBody().getItems().get(i).getInformData());
                            break;
                        }
                    }
                }

                arrayCommaNext = listDustNext.split(",");

                for (String s : arrayCommaNext) {
                    Log.d("test", s);
                }

                for (int i = 0; i < 19; i++) {
                    index = arrayCommaNext[i].indexOf(":");
                    state = arrayCommaNext[i].substring(index + 2);
                    dustStateArray2[i] = state;
                }

                if (cityName.equals("서울")) {
                    fineDustText2 = dustStateArray2[0];
                    Log.d("dust", "0");
                } else if (cityName.equals("제주")) {
                    fineDustText2 = dustStateArray2[1];
                    Log.d("dust", "1");
                } else if (cityName.equals("전남")) {
                    fineDustText2 = dustStateArray2[2];
                    Log.d("dust", "2");
                } else if (cityName.equals("광주·전북")) {
                    //광주
                    if (provName.equals("광산구") || provName.equals("남구") || provName.equals("동구") || provName.equals("북구") || provName.equals("서구")) {
                        fineDustText2 = dustStateArray2[4];
                        Log.d("dust", "4");
                    }
                    //전북
                    else {
                        fineDustText2 = dustStateArray2[3];
                        Log.d("dust", "3");
                    }
                } else if (cityName.equals("경남")) {
                    fineDustText2 = dustStateArray2[5];
                    Log.d("dust", "5");
                } else if (cityName.equals("대구·경북")) {
                    //대구
                    if (provName.equals("중구") || provName.equals("동구") || provName.equals("서구") || provName.equals("남구") || provName.equals("북구") || provName.equals("수성구") || provName.equals("달서구") || provName.equals("달성군")) {
                        fineDustText2 = dustStateArray2[8];
                        Log.d("dust", "8");
                    }
                    //경북
                    else {
                        fineDustText2 = dustStateArray2[6];
                        Log.d("dust", "6");
                    }
                } else if (cityName.equals("부산·울산")) {
                    //울산
                    if (provName.equals("남구") || provName.equals("동구") || provName.equals("북구") || provName.equals("울주군") || provName.equals("중구")) {
                        fineDustText2 = dustStateArray2[7];
                        Log.d("dust", "7");
                    }
                    //부산
                    else {
                        fineDustText2 = dustStateArray2[9];
                        Log.d("dust", "9");
                    }
                } else if (cityName.equals("충북")) {
                    fineDustText2 = dustStateArray2[11];
                    Log.d("dust", "11");
                } else if (cityName.equals("충남")) {
                    //충남
                    fineDustText2 = dustStateArray2[10];
                    Log.d("dust", "10");
                } else if (cityName.equals("대전·세종")) {
                    //대전
                    if (provName.equals("대덕구") || provName.equals("동구") || provName.equals("서구") || provName.equals("유성구") || provName.equals("중구")) {
                        fineDustText2 = dustStateArray2[13];
                        Log.d("dust", "13");
                    }
                    //세종
                    else {
                        fineDustText2 = dustStateArray2[12];
                        Log.d("dust", "12");
                    }
                } else if (cityName.equals("인천")) {
                    fineDustText2 = dustStateArray2[18];
                    Log.d("dust", "18");
                } else if (cityName.equals("경기")) {
                    //경기 북부
                    if (provName.equals("가평군") || provName.equals("고양시") || provName.equals("구리시") || provName.equals("남양주시") || provName.equals("동두천시")
                            || provName.equals("양주시") || provName.equals("연천군") || provName.equals("의정부시") || provName.equals("파주시") || provName.equals("포천시")) {
                        fineDustText2 = dustStateArray2[17];
                        Log.d("dust", "17");
                    }
                    //경기 남부
                    else {
                        fineDustText2 = dustStateArray2[16];
                        Log.d("dust", "16");
                    }
                } else if (cityName.equals("강원")) {
                    //영동
                    if (provName.equals("강릉시") || provName.equals("고성군") || provName.equals("동해시") || provName.equals("삼척시") || provName.equals("속초시")
                            || provName.equals("양양군") || provName.equals("태백시")) {
                        fineDustText2 = dustStateArray2[14];
                        Log.d("dust", "14");
                    }
                    //영서
                    else {
                        fineDustText2 = dustStateArray2[15];
                        Log.d("dust", "15");
                    }
                } else {
                    Log.d("dustError", "else로 빠짐");
                }

                fineDustSt = setFineDustValue(fineDustText2);
                fineDust = fineDustText2;
                Log.d("obFitCheckWhenDustNo", fineDust);
            }

            @Override
            public void onFailure(Call<WtFineDustModel> call, Throwable t) {
                t.printStackTrace();
                Log.v("FineDust", "responseError= " + call.request().url());
            }
        });
    }

    /**
     * TODO 관측적합도 계산 메소드
     * @return 관측적합도 결과값
     * @throws
     */
    public double setObservationalFitDegree() {
        cloudVolumeValue = Math.round(100 * (-(1 / (-(0.25) * (cloudVolume / 100 - 2.7)) - 1.48148)) * 100) / 100.0;

        if (moonAge <= 0.5) {
            moonAgeValue = -Math.round(((8 * Math.pow(moonAge, 3.46)) / 0.727 * 100) * 100) / 100.0;
        } else if (moonAge > 0.5 && moonAge <= 0.5609) {
            moonAgeValue = -Math.round(((-75 * Math.pow(moonAge - 0.5, 2) + 0.727) / 0.727 * 100) * 100) / 100.0;
        } else if (moonAge > 0.5609) {
            moonAgeValue = -Math.round(((1 / (5.6 * Math.pow(moonAge + 0.3493, 10)) - 0.0089) / 0.727 * 100) * 100) / 100.0;
        }
        if (feel_like < 18) {
            feel_likeValue = Math.round(-0.008 * Math.pow((feel_like - 18), 2) * 100) / 100.0;
        } else {
            feel_likeValue = Math.round(-0.09 * Math.pow((feel_like - 18), 2) * 100) / 100.0;
        }

        switch (fineDust) {
            case "좋음":
                fineDustValue = 0;
                break;
            case "보통":
                fineDustValue = -5;
                break;
            case "나쁨":
                fineDustValue = -15;
                break;
            case "매우나쁨":
                fineDustValue = -30;
                break;
            case"없음":
                fineDustValue = 0;
                Log.d("fineDust", "미세먼지 정보 없음");
                break;
            default:
                fineDustValue = 0;
                break;
        }
        precipitationProbabilityValue = Math.round(100 * (-(1 / (-(1.2) * (precipitationProbability / 100 - 1.5)) - 0.55556)) * 100) / 100.0;

        if (lightPollution < 28.928) {
            lightPollutionValue = Math.round(-(1 / (-(0.001) * (lightPollution - 48)) - 20.833) * 100) / 100.0;
        } else if (lightPollution >= 28.928 && lightPollution < 77.53) {
            lightPollutionValue = Math.round(-(1 / (-(0.0001) * (lightPollution + 52)) + 155) * 100) / 100.0;
        } else if (lightPollution >= 77.53 && lightPollution < 88.674) {
            lightPollutionValue = Math.round(-(1 / (-(0.001) * (lightPollution - 110)) + 47) * 100) / 100.0;
        } else {
            lightPollutionValue = Math.round(-(1 / (-(0.01) * (lightPollution - 71)) + 100) * 100) / 100.0;
        }
        if (cloudVolumeValue < feel_likeValue && cloudVolumeValue < moonAgeValue && cloudVolumeValue < fineDustValue && cloudVolumeValue < precipitationProbabilityValue && cloudVolumeValue < lightPollutionValue) {
            biggestValue = cloudVolumeValue;
        } else if (feel_likeValue < cloudVolumeValue && feel_likeValue < moonAgeValue && feel_likeValue < fineDustValue && feel_likeValue < precipitationProbabilityValue && feel_likeValue < lightPollutionValue) {
            biggestValue = feel_likeValue;
        } else if (moonAgeValue < cloudVolumeValue && moonAgeValue < feel_likeValue && moonAgeValue < fineDustValue && moonAgeValue < precipitationProbabilityValue && moonAgeValue < lightPollutionValue) {
            biggestValue = moonAgeValue;
        } else if (fineDustValue < cloudVolumeValue && fineDustValue < feel_likeValue && fineDustValue < moonAgeValue && fineDustValue < precipitationProbabilityValue && fineDustValue < lightPollutionValue) {
            biggestValue = fineDustValue;
        } else if (precipitationProbabilityValue < cloudVolumeValue && precipitationProbabilityValue < feel_likeValue && precipitationProbabilityValue < moonAgeValue && precipitationProbabilityValue < fineDustValue && precipitationProbabilityValue < lightPollutionValue) {
            biggestValue = precipitationProbabilityValue;
        } else {
            biggestValue = lightPollutionValue;
        }
        averageValue = (cloudVolumeValue + feel_likeValue + moonAgeValue + fineDustValue + precipitationProbabilityValue + lightPollutionValue - biggestValue) / 6;
        if (100 + (biggestValue + averageValue * 0.3) > 0) {
            observationalFitDegree = 100 + (biggestValue + averageValue * 0.3);
        } else {
            observationalFitDegree = 0;
        }

        obFitFinal = Math.round(observationalFitDegree * 100) / 100.0;

        if (obFitFinal < 0) {
            return 0;
        } else {
            return obFitFinal;
        }
    }

    // 시,도 Spinner 동작 메소드
    public void onSetAreaSpinner() {
        final Spinner citySpinner = (Spinner) findViewById(R.id.wt_citySpinner);
        final Spinner provSpinner = (Spinner) findViewById(R.id.wt_provinceSpinner);

        cityAdSpin = ArrayAdapter.createFromResource(this, R.array.wt_cityList, android.R.layout.simple_spinner_dropdown_item);
        cityAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdSpin);

        //default
//        choice_do = "서울";
//        choice_se = "강남구";
//
//        cityName = choice_do;
//        provName = choice_se;

//        latitude = 37.5006;
//        longitude = 127.0508;

        citySpinner.setSelection(getIndex(citySpinner, cityName));
        Log.d("nowCityName", cityName);

        if (nowCity == null || provName == null) {
            nowCity = "서울";
            provName = "강남구";
            Toast.makeText(WeatherActivity.this, "현재 위치가 인식되지 않습니다.", Toast.LENGTH_LONG).show();

        }

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (cityAdSpin.getItem(position).equals("서울")) {
                    cityName = "서울";

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Seoul, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setSelection(getIndex(provSpinner, provName));
                    Log.d("nowProvName", provName);

                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                            Log.d("connectWtAreaCheck", "0");
                            connectFineDustApi();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("경기")) {
                    cityName = "경기";

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Gyeonggi, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setSelection(getIndex(provSpinner, provName));
                    Log.d("nowProvName", provName);

                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                            Log.d("connectWtAreaCheck", "1");
                            connectFineDustApi();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("인천")) {
                    cityName = "인천";

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Incheon, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setSelection(getIndex(provSpinner, provName));
                    Log.d("nowProvName", provName);

                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                            Log.d("connectWtAreaCheck", "2");
                            connectFineDustApi();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("강원")) {
                    cityName = "강원";

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Gangwon, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setSelection(getIndex(provSpinner, provName));
                    Log.d("nowProvName", provName);

                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                            Log.d("connectWtAreaCheck", "3");
                            connectFineDustApi();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }

                    });
                    Log.d("cityName", cityName);
                    Log.d("provName", provName);
                } else if (cityAdSpin.getItem(position).equals("충북")) {
                    cityName = "충북";

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Chungbuk, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setSelection(getIndex(provSpinner, provName));
                    Log.d("nowProvName", provName);

                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                            Log.d("connectWtAreaCheck", "4");
                            connectFineDustApi();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("충남")) {
                    cityName = "충남";

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Chungnam, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setSelection(getIndex(provSpinner, provName));
                    Log.d("nowProvName", provName);

                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                            Log.d("connectWtAreaCheck", "5");
                            connectFineDustApi();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("대전·세종")) {
                    cityName = "대전·세종";

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_DaejeonSejong, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setSelection(getIndex(provSpinner, provName));
                    Log.d("nowProvName", provName);

                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                            Log.d("connectWtAreaCheck", "6");
                            connectFineDustApi();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("광주·전북")) {
                    cityName = "광주·전북";

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_GwangjuJeonbuk, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setSelection(getIndex(provSpinner, provName));
                    Log.d("nowProvName", provName);

                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                            Log.d("connectWtAreaCheck", "7");
                            connectFineDustApi();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("전남")) {
                    cityName = "전남";

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Jeonnam, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setSelection(getIndex(provSpinner, provName));
                    Log.d("nowProvName", provName);

                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                            Log.d("connectWtAreaCheck", "8");
                            connectFineDustApi();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("대구·경북")) {
                    cityName = "대구·경북";

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_DaeguGyeongbuk, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setSelection(getIndex(provSpinner, provName));
                    Log.d("nowProvName", provName);

                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                            Log.d("connectWtAreaCheck", "9");
                            connectFineDustApi();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("경남")) {
                    cityName = "경남";

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Gyeongnam, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setSelection(getIndex(provSpinner, provName));
                    Log.d("nowProvName", provName);

                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                            Log.d("connectWtAreaCheck", "10");
                            connectFineDustApi();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("부산·울산")) {
                    cityName = "부산·울산";

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_BusanUlsan, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setSelection(getIndex(provSpinner, provName));
                    Log.d("nowProvName", provName);

                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                            Log.d("connectWtAreaCheck", "11");
                            connectFineDustApi();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (cityAdSpin.getItem(position).equals("제주")) {
                    cityName = "제주";

                    provAdSpin = ArrayAdapter.createFromResource(WeatherActivity.this, R.array.wt_Jeju, android.R.layout.simple_spinner_dropdown_item);
                    provAdSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provSpinner.setAdapter(provAdSpin);
                    provSpinner.setSelection(getIndex(provSpinner, provName));
                    Log.d("nowProvName", provName);

                    provSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = provAdSpin.getItem(position).toString();
                            provName = choice_se;

                            connectWtArea();
                            Log.d("connectWtAreaCheck", "12");
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
    }

    // 지역명으로 경도, 위도, 광공해 받아오기
    public void connectWtArea() {
        Log.d("cityName1", cityName);
        Log.d("provName1", provName);

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
                    Log.d("lightPollution", String.valueOf(lightPollution));

                    minLightPolText = minLightPol.toString();
                    maxLightPolText = maxLightPol.toString();

                    minLightPolTv.setText(minLightPolText);
                    maxLightPolTv.setText(maxLightPolText);

                    connectMetApi();
                    Log.d("connectMetApiCheck", "2");
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

    /**
     * TODO 날짜별 제공하는 최소 시 설정 메소드
     * @param  num - 설정할 최소 시
     */
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

    /**
     * TODO 날짜별 제공하는 최대 시 설정 메소드
     * @param  num - 설정할 최대 시
     */
    public void setLimitHour(int num) {
        hourChange = list2.toArray(new String[0]);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(hourChange.length - num);
        numberPicker.setDisplayedValues(hourChange);
    }

    /**
     * TODO 상세 날씨 정보에서 제공하는 최저/최고 기온 설정 메소드
     * @param  state - 최저/최고 기온을 설정할 상태
     */
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

    /**
     * TODO 상세 날씨 정보에서 제공하는 광공해 설정 메소드
     * @param  state - 광공해를 설정할 상태
     */
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

                    if (todayWtName2 == null) {
                        todayWtName = todayWtName1;
                    } else {
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

    /**
     * TODO 별 관측 적합도 멘트 설정 메소드
     * @param  obFitValueSelect - 계산한 별 관측 적합도 값
     */
    public void setObFitComment(Double obFitValueSelect) {
        if (obFitValueSelect < 40) {
            commentTv.setText("별을 보기 어려워요");
            wtWeather.setImageResource(R.drawable.wt__very_bad);
        } else if (obFitValueSelect < 60) {
            commentTv.setText("별 보기 조금 아쉽네요");
            wtWeather.setImageResource(R.drawable.wt__bad);
        } else if (obFitValueSelect < 70) {
            commentTv.setText("별 보기 적당한 날이네요");
            wtWeather.setImageResource(R.drawable.wt__average);
        } else if (obFitValueSelect < 85) {
            commentTv.setText("별 보기 좋은 날이에요!");
            wtWeather.setImageResource(R.drawable.wt__good);
        } else {
            commentTv.setText("별 보기 최고의 날이네요!");
            wtWeather.setImageResource(R.drawable.wt__very_good);
        }
    }

    /**
     * TODO 상세 날씨 정보 성태 설정 메소드
     * @param  cloud - 구름량
     * @param  temp - 기온
     * @param  humidity - 습도
     * @param  moonAge - 월령
     * @param  wind - 풍속
     * @param  precip - 강수량
     * @param  lightPol - 광공해
     * @param  Dust - 미세먼지
     */
    public void setDetailState(Double cloud, Double temp, Double humidity, Double moonAge, Double wind, Double precip, Double lightPol, String Dust) {
        if (cloud < 11) {
            cloudState = "매우좋음";
        } else if (cloud < 21) {
            cloudState = "좋음";
        } else if (cloud < 41) {
            cloudState = "보통";
        } else if (cloud < 61) {
            cloudState = "나쁨";
        } else {
            cloudState = "매우나쁨";
        }

        if ((temp <= -1) || (temp >= 31)) {
            tempState = "매우나쁨";
        } else if ((temp >= 0 && temp <= 9) || (temp >= 29 && temp <= 30)) {
            tempState = "나쁨";
        } else if ((temp >= 10) && (temp <= 13) || (temp >= 26 && temp <= 28)) {
            tempState = "보통";
        } else if ((temp >= 14 && temp <= 17) || (temp >= 24 && temp <= 25)) {
            tempState = "좋음";
        } else {
            tempState = "매우좋음";
        }

        if ((humidity >= 0 && humidity <= 14) || (humidity >= 85 && humidity <= 100)) {
            humidityState = "매우나쁨";
        } else if ((humidity >= 20 && humidity <= 29) || (humidity >= 72 && humidity <= 84)) {
            humidityState = "나쁨";
        } else if ((humidity >= 30) && (humidity <= 38) || (humidity >= 63 && humidity <= 71)) {
            humidityState = "보통";
        } else if ((humidity >= 37 && humidity <= 42) || (humidity >= 58 && humidity <= 62)) {
            humidityState = "좋음";
        } else {
            humidityState = "매우좋음";
        }

        if ((moonAge >= 0.36 && moonAge <= 0.62)) {
            moonPhaseState = "매우나쁨";
        } else if ((moonAge >= 0.22 && moonAge <= 0.35) || (moonAge >= 0.63 && moonAge <= 0.74)) {
            moonPhaseState = "나쁨";
        } else if ((moonAge >= 0.14) && (moonAge <= 0.21) || (moonAge >= 0.75 && moonAge <= 0.84)) {
            moonPhaseState = "보통";
        } else if ((moonAge >= 0.07 && moonAge <= 0.13) || (moonAge >= 0.85 && moonAge <= 0.93)) {
            moonPhaseState = "좋음";
        } else {
            moonPhaseState = "매우좋음";
        }

        if (wind <= 2.5) {
            windState = "매우좋음";
        } else if (wind <= 4.5) {
            windState = "좋음";
        } else if (wind <= 6.5) {
            windState = "보통";
        } else if (wind <= 10) {
            windState = "나쁨";
        } else {
            windState = "매우나쁨";
        }

        if (precip < 16) {
            precipState = "매우좋음";
        } else if (precip < 26) {
            precipState = "좋음";
        } else if (precip < 41) {
            precipState = "보통";
        } else if (precip < 61) {
            precipState = "나쁨";
        } else {
            precipState = "매우나쁨";
        }

        if (lightPol <= 1) {
            lightPolState = "매우좋음";
        } else if (lightPol <= 15) {
            lightPolState = "좋음";
        } else if (lightPol <= 45) {
            lightPolState = "보통";
        } else if (lightPol <= 80) {
            lightPolState = "나쁨";
        } else {
            lightPolState = "매우나쁨";
        }

        if (Dust.equals("151㎍/㎥ 이상")) {
            fineDustState = "매우나쁨";
        } else if (Dust.equals("31~80㎍/㎥")) {
            fineDustState = "보통";
        } else if (Dust.equals("81~150㎍/㎥")) {
            fineDustState = "나쁨";
        } else if(Dust.equals("없음")){
            fineDustState = "좋음";
            fineDustSt="정보 없음";
            Log.d("fineDustState","미세먼지 상태 정보 없음");
        }
        else {
            fineDustState = "좋음";
        }
    }

    /**
     * TODO 미세먼지 상태로 미세먼지 상세값 설정 메소드
     * @param  fineDustApiValue - 미세먼지 상태
     * @return fineDust 상세 값
     */
    public String setFineDustValue(String fineDustApiValue) {
        String dust;
        if (fineDustApiValue.equals("좋음")) {
            dust = "0~30㎍/㎥";
        } else if (fineDustApiValue.equals("보통")) {
            dust= "31~80㎍/㎥";
        } else if (fineDustApiValue.equals("나쁨")) {
            dust = "81~150㎍/㎥";
        } else {
            dust = "151㎍/㎥ 이상";
        }
        return dust;
    }

    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;

            Log.d("nowCityProv7", nowCity + " " + nowProvince);
            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    Log.d("nowCityProv8 ", nowCity + " " + nowProvince);
                    break;
                }
            }

            if (check_result) {
                //위치 권한 허용
                gpsTracker = new GpsTracker(WeatherActivity.this);

                nowLatitude = gpsTracker.getLatitude();
                nowLongitude = gpsTracker.getLongitude();

                nowLocation = getCurrentAddress(nowLatitude, nowLongitude);

                Log.d("nowLocationResult", nowLocation);
                Log.d("nowCityProv6", nowCity + " " + nowProvince);

                setCityName();

                setTextView();
                onClickBackBtn();
                onClickCloudInfo();
                onClickHelpBtn();

                //setMyLocation();

                onSetDatePicker();
                onSetTimePicker();

                //지역 선택 Spinner
                onSetAreaSpinner();

                selectDateTime = selectDate + selectTime;
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.
                // 위치 권한 거부
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0]) || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    nowCity = "서울";
                    nowProvince = "강남구";

                    Log.d("nowCityProv2", nowCity + " " + nowProvince);

                } else {
                    Toast.makeText(WeatherActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    //런타임 퍼미션 처리
    void checkRunTimePermission() {
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(WeatherActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(WeatherActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        Log.d("LocationAllow", "1");
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
            Log.d("LocationAllow", "0");

            // 3.  위치 값을 가져올 수 있음


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(WeatherActivity.this, REQUIRED_PERMISSIONS[0])) {
                Log.d("LocationAllow", "3");
                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(WeatherActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자에게 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(WeatherActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);

            } else {
                Log.d("LocationAllow", "4");
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(WeatherActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }

    /**
     * TODO 현제 위도, 적도로 지역 조회 메소드
     * @param  latitude - 위도
     * @param  longitude - 경도
     * @return 에러 메시지
     */
    public String getCurrentAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1
            );
        } catch (IOException ioException) {
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }

        if (addresses == null || addresses.size() == 00) {
            return "";
        }

        Address address = addresses.get(0);
        nowCity = address.getAdminArea();
        nowProvince = address.getLocality();

        if (nowProvince == null) {
            nowProvince = address.getSubLocality();
        }
        Log.d("nowCityProv3", nowCity + " " + nowProvince);

        setCityName();

        return address.getAddressLine(0).toString() + "\n";
    }

    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(WeatherActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하시겠습니까?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);

                gpsTracker = new GpsTracker(WeatherActivity.this);

                nowLatitude = gpsTracker.getLatitude();
                nowLongitude = gpsTracker.getLongitude();

                nowLocation = getCurrentAddress(nowLatitude, nowLongitude);
                Log.d("nowLocationResult", nowLocation);
                Log.d("nowCityProv4", nowCity + " " + nowProvince);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                nowCity = "서울";
                nowProvince = "강남구";
                Log.d("nowCityProv5", nowCity + " " + nowProvince);
                dialog.cancel();
            }
        });
        builder.create().show();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        gpsTracker = new GpsTracker(WeatherActivity.this);

                        nowLatitude = gpsTracker.getLatitude();
                        nowLongitude = gpsTracker.getLongitude();

                        nowLocation = getCurrentAddress(nowLatitude, nowLongitude);

                        Log.d("nowLocationResult", nowLocation);
                        Log.d("nowCityProv6", nowCity + " " + nowProvince);

                        setCityName();

                        setTextView();
                        onClickBackBtn();
                        onClickCloudInfo();
                        onClickHelpBtn();


                        onSetDatePicker();
                        onSetTimePicker();

                        //지역 선택 Spinner
                        onSetAreaSpinner();

                        selectDateTime = selectDate + selectTime;
                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }
                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // 시 이름 설정 메소드
    public void setCityName() {
        if (nowCity.equals("서울특별시")) {
            nowCity = "서울";
        } else if (nowCity.equals("경기도")) {
            nowCity = "경기";
        } else if (nowCity.equals("인쳔광역시")) {
            nowCity = "인천";
        } else if (nowCity.equals("강원도")) {
            nowCity = "강원";
        } else if (nowCity.equals("충청북도")) {
            nowCity = "충북";
        } else if (nowCity.equals("충청남도")) {
            nowCity = "충남";
        } else if (nowCity.equals("대전광역시") || nowCity.equals("세종특별자치시")) {
            nowCity = "대전·세종";
        } else if (nowCity.equals("광주광역시") || nowCity.equals("전라북도")) {
            nowCity = "광주·전북";
        } else if (nowCity.equals("전라남도")) {
            nowCity = "전남";
        } else if (nowCity.equals("대구광역시") || nowCity.equals("경상북도")) {
            nowCity = "대구·경북";
        } else if (nowCity.equals("경상남도")) {
            nowCity = "경남";
        } else if (nowCity.equals("부산광역시") || nowCity.equals("울산광역시")) {
            nowCity = "부산·울산";
        } else if (nowCity.equals("제주특별자치도")) {
            nowCity = "제주";
        }

        cityName = nowCity;
        provName = nowProvince;

        Log.d("nowCityProv8", cityName + provName);
    }

    private int getIndex(Spinner spinner, String myString) {

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
                index = i;
            }
        }
        return index;
    }

    private class LoadingAsyncTask extends AsyncTask<String, Long, Boolean> {
        private Context mContext = null;
        private Long mtime;

        public LoadingAsyncTask(Context context, long time) {
            mContext = WeatherActivity.this;
            mtime = time;
        }

        @Override
        protected void onPreExecute() {
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                Thread.sleep(mtime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return (true);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dialog.dismiss();
        }
    }
}