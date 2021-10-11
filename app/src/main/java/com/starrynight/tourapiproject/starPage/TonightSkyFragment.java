package com.starrynight.tourapiproject.starPage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.ortiz.touchview.TouchImageView;
import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.starPage.constNameRetrofit.ConstellationParams2;
import com.starrynight.tourapiproject.starPage.horItemPage.HorItem;
import com.starrynight.tourapiproject.starPage.horItemPage.HoroscopeAdapter;
import com.starrynight.tourapiproject.starPage.horPageRetriofit.Horoscope;
import com.starrynight.tourapiproject.starPage.starItemPage.OnStarItemClickListener;
import com.starrynight.tourapiproject.starPage.starItemPage.StarItem;
import com.starrynight.tourapiproject.starPage.starItemPage.StarViewAdapter;
import com.starrynight.tourapiproject.starPage.starPageRetrofit.Constellation;
import com.starrynight.tourapiproject.starPage.starPageRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.weatherPage.WeatherActivity;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TonightSkyFragment extends Fragment implements SensorEventListener {
    //bottomSheet 관련
    private LinearLayout bottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;

    //나침반 관련
    private SensorManager mSensorManger;
    private Sensor mAcclerometer;
    private Sensor mMagnetometer;
    private final float[] mLastAcceleromter = new float[3];
    private final float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;
    private final float[] mR = new float[9];
    private final float[] mOrientation = new float[3];
    private float mCurrentDegree = 0f;

    //recyclerview 관련
    RecyclerView constList;
    StarViewAdapter constAdapter;

    RecyclerView allConstList;
    TextView allConstBtn;

    Long constId;
    Constellation constellation;

    //도움말
    LinearLayout imgClick;
    TextView openView;

    LinearLayout imgClick1;
    TextView openView1;

    ImageView helpBtn;
    ImageView compass;
    ImageView starBackBtn;
    ImageView todayWeather;


    LinearLayout helpInfo;
    ImageView helpBackBtn;
    View dim;

    TouchImageView touchImageView;

    //계절에 따라 이미지 변경
    String spring = "0321";
    String summer = "0622";
    String fall = "0923";
    String winter = "1221";
    String yearEnd = "1231";
    String yearStart = "0101";

    Date springDate;
    Date summerDate;
    Date fallDate;
    Date winterDate;
    Date todayDate;
    Date yearEndDate;
    Date yearStartDate;

    //별자리 운세
    List<HorItem> horItems = new ArrayList<>();

    ImageView horPrevBtn, horNextBtn;

    private HoroscopeAdapter horAdapter;
    private ViewPager2 horViewpager;
    Long horId = 0L;
    String todayMonth;
    String horDesc;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat formatMonth = new SimpleDateFormat("MM");

    Calendar cal = Calendar.getInstance();

    //검색
    SearchView constSearch;
    ListView searchList;
    List<String> nameList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    long itemClickId;


    Integer compareDataSpring, compareDataSummer, compareDataFall, compareDataWinter, compareDataYearEnd, compareDataYearStart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tonight_sky, container, false);

        todayWeather = v.findViewById(R.id.tonight_weather);

        todayWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WeatherActivity.class);
                startActivity(intent);
            }
        });

        //별자리 운세
        horViewpager = v.findViewById(R.id.hor_viewpager);

        horPrevBtn = v.findViewById(R.id.hor_prev_btn);
        horNextBtn = v.findViewById(R.id.hor_next_btn);

        horPrevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horViewpager.setCurrentItem(getItem(-1), true);
            }
        });

        horNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horViewpager.setCurrentItem(getItem(1), true);
            }
        });

        for (int i = 0; i < 12; i++) {
            connectHoroscope((long) i);
        }

        //별자리 검색
        constSearch = v.findViewById(R.id.edit_search);
        searchList = v.findViewById(R.id.const_list_view);

        constSearch.setQueryHint("검색");

        constSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constSearch.setIconified(false);
            }
        });


        //모든 별자리 이름 호출
        Call<List<ConstellationParams2>> constNameCall = RetrofitClient.getApiService().getConstNames();
        constNameCall.enqueue(new Callback<List<ConstellationParams2>>() {
            @Override
            public void onResponse(Call<List<ConstellationParams2>> call, Response<List<ConstellationParams2>> response) {
                if (response.isSuccessful()) {
                    List<ConstellationParams2> result = response.body();

                    for (ConstellationParams2 cp2 : result) {
                        String constName = cp2.getConstName();
                        nameList.add(constName);
                    }

                } else {
                    Log.d("constName", "전체 별자리 이름 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<List<ConstellationParams2>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });

        //별자리 눌렀을 때 해당 별자리 페이지로 이동
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, nameList);
        searchList.setAdapter(arrayAdapter);
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemClickId = parent.getItemIdAtPosition(position) + 1;
                Intent intent = new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(), StarActivity.class);
                intent.putExtra("constId", itemClickId);
                startActivity(intent);
                //Toast.makeText(getActivity().getApplicationContext(), "You Click -" + parent.getItemAtPosition(position).toString() + parent.getItemIdAtPosition(position), Toast.LENGTH_SHORT).show();
            }
        });

        //별자리 텍스트 검색 변화
        constSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //검색 버튼 누를 때 호출
                TonightSkyFragment.this.arrayAdapter.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 검색 창에서 글자가 변경이 일어날 때마다 호출
                TonightSkyFragment.this.arrayAdapter.getFilter().filter(newText);

                return false;
            }
        });

        //나침반
        mSensorManger = (SensorManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SENSOR_SERVICE);
        mAcclerometer = mSensorManger.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManger.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        // bottomSheet 설정
        constSearch.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        constSearch.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        bottomSheetBehavior.setPeekHeight(constSearch.getBottom() + 50);
                    }
                }
        );

        bottomSheet = v.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        bottomSheetBehavior.setPeekHeight(constSearch.getBottom());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


        // 뒤로 가기 버튼 클릭 이벤트
        starBackBtn = v.findViewById(R.id.star_back_btn);
        starBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });

        // 모든 천체 보기 버튼 클릭 이벤트
        allConstList = v.findViewById(R.id.all_const_recycler);
        allConstBtn = v.findViewById(R.id.all_const_btn);

        allConstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constAdapter = new StarViewAdapter();
                Intent intent = new Intent(getActivity().getApplicationContext(), StarAllActivity.class);
                startActivity(intent);
            }
        });


        // recyclerview 설정
        constList = v.findViewById(R.id.today_cel_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        constList.setLayoutManager(gridLayoutManager);
        constAdapter = new StarViewAdapter();
        constList.setAdapter(constAdapter);


        // 오늘의 별자리 리스트 불러오는 api
        Call<List<StarItem>> todayConstCall = RetrofitClient.getApiService().getTodayConst();
        todayConstCall.enqueue(new Callback<List<StarItem>>() {
            @Override
            public void onResponse(@NotNull Call<List<StarItem>> call, Response<List<StarItem>> response) {
                if (response.isSuccessful()) {
                    List<StarItem> result = response.body();
                    for (StarItem si : result) {
                        constAdapter.addItem(new StarItem(si.getConstId(), si.getConstName(), si.getConstEng()));
                    }
                    constList.setAdapter(constAdapter);
                } else {
                    Log.d("todayConst", "오늘의 별자리 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<List<StarItem>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });

        // item 클릭 시 해당 아이템 constId 넘겨주기
        constAdapter.setOnItemClickListener(new OnStarItemClickListener() {
            @Override
            public void onItemClick(StarViewAdapter.ViewHolder holder, View view, int position) {
                StarItem item = constAdapter.getItem(position);
                Intent intent = new Intent(getActivity().getApplicationContext(), StarActivity.class);
                intent.putExtra("constId", item.getConstId());
                //Log.d("constId 출력", item.getConstId().toString());
                startActivity(intent);
            }
        });

        //도움말 textView open
        imgClick = v.findViewById(R.id.imgClick);
        openView = v.findViewById(R.id.layout_expand);
        ImageView arrow = v.findViewById(R.id.arrow);

        imgClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (openView.getVisibility() == View.VISIBLE) {
                    openView.setVisibility(View.GONE);
                    arrow.animate().setDuration(200).rotation(0f);
                } else {
                    openView.setVisibility(View.VISIBLE);
                    arrow.animate().setDuration(200).rotation(90f);
                }
            }
        });

        imgClick1 = v.findViewById(R.id.imgClick1);
        openView1 = v.findViewById(R.id.layout_expand1);
        ImageView arrow1 = v.findViewById(R.id.arrow1);

        imgClick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (openView1.getVisibility() == View.VISIBLE) {
                    openView1.setVisibility(View.GONE);
                    arrow1.animate().setDuration(200).rotation(0f);
                } else {
                    openView1.setVisibility(View.VISIBLE);
                    arrow1.animate().setDuration(200).rotation(90f);
                }
            }
        });

        helpBtn = v.findViewById(R.id.help_btn);
        helpInfo = v.findViewById(R.id.help_info);
        helpBackBtn = v.findViewById(R.id.help_back_btn);
        dim = v.findViewById(R.id.dim);
        touchImageView = v.findViewById(R.id.touchImage);
        compass = v.findViewById(R.id.compass_needle);
        starBackBtn = v.findViewById(R.id.star_back_btn);

        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpInfo.setVisibility(View.VISIBLE);
                dim.setAlpha(1);
                stateButton(false);
            }
        });

        helpBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpInfo.setVisibility(View.GONE);
                dim.setAlpha(0);
                stateButton(true);
            }
        });

        // 계절 별로 다른 이미지 넣기
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("MMdd");

        try {
            Date today = new Date();
            springDate = sdf.parse(spring);
            summerDate = sdf.parse(summer);
            fallDate = sdf.parse(fall);
            winterDate = sdf.parse(winter);
            yearEndDate = sdf.parse(yearEnd);
            yearStartDate = sdf.parse(yearStart);
            todayDate = sdf.parse(sdf.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        compareDataSpring = todayDate.compareTo(springDate);            //3월 21일
        compareDataSummer = todayDate.compareTo(summerDate);            //6월 22일
        compareDataFall = todayDate.compareTo(fallDate);                //9월 23일
        compareDataWinter = todayDate.compareTo(winterDate);            //12월 21일
        compareDataYearStart = todayDate.compareTo(yearStartDate);      //1월 1일
        compareDataYearEnd = todayDate.compareTo(yearEndDate);          //12월 31일


        // 봄(3/21 ~ 6/21)
        if ((compareDataSpring == 1 || compareDataSpring == 0) && compareDataSummer == -1) {
            touchImageView.setImageResource(R.drawable.star__spring);
        }
        // 여름(6/22 ~ 9/22)
        else if ((compareDataSummer == 1 || compareDataSummer == 0) && compareDataFall == -1) {
            touchImageView.setImageResource(R.drawable.star__summer);
        }
        // 가을(9/23 ~ 12/20)
        else if ((compareDataFall == 1 || compareDataFall == 0) && compareDataWinter == -1) {
            touchImageView.setImageResource(R.drawable.star__fall);
        }
        // 겨울(12/21 ~ 12/31)
        else if ((compareDataWinter == 1 || compareDataWinter == 0) && compareDataYearEnd == -1) {
            touchImageView.setImageResource(R.drawable.star__winter);
        }
        // 겨울(01/01 ~ 03/20)
        else if ((compareDataYearStart == 1 || compareDataYearStart == 0) && compareDataSpring == -1) {
            touchImageView.setImageResource(R.drawable.star__winter);
        }

        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        mSensorManger.registerListener(this, mAcclerometer, SensorManager.SENSOR_DELAY_GAME);
        mSensorManger.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManger.unregisterListener(this, mAcclerometer);
        mSensorManger.unregisterListener(this, mMagnetometer);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == mAcclerometer) {
            System.arraycopy(event.values, 0, mLastAcceleromter, 0, event.values.length);
            mLastAccelerometerSet = true;
        } else if (event.sensor == mMagnetometer) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
            mLastMagnetometerSet = true;
        }
        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            SensorManager.getRotationMatrix(mR, null, mLastAcceleromter, mLastMagnetometer);
            float azimuthinDegress = (int) (Math.toDegrees(SensorManager.getOrientation(mR, mOrientation)[0]) + 360) % 360;
            RotateAnimation ra = new RotateAnimation(
                    mCurrentDegree,
                    -azimuthinDegress,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
            );
            ra.setDuration(250);
            ra.setFillAfter(true);
            compass.startAnimation(ra);
            mCurrentDegree = -azimuthinDegress;
        }
    }

    public void stateButton(boolean state) {
        touchImageView.setEnabled(state);
        compass.setEnabled(state);
        starBackBtn.setEnabled(state);
        helpBtn.setEnabled(state);
        bottomSheetBehavior.setDraggable(state);
        constSearch.setEnabled(state);
    }

    //별자리 운세
    public void connectHoroscope(Long horId1) {

        todayMonth = formatMonth.format(cal.getTime());
        Log.d("todayMonth", todayMonth);

        horId = horId1;

        //별자리 운세를 불러오는 api
        Call<Horoscope> horoscopeCall = com.starrynight.tourapiproject.starPage.horPageRetriofit.RetrofitClient.getApiService().getHoroscopes(horId);
        horoscopeCall.enqueue(new Callback<Horoscope>() {
            @Override
            public void onResponse(Call<Horoscope> call, Response<Horoscope> response) {
                if (response.isSuccessful()) {
                    Horoscope result = response.body();

                    if (todayMonth.equals("01")) {
                        horDesc = result.getHorDesc1();
                    } else if (todayMonth.equals("02")) {
                        horDesc = result.getHorDesc2();
                    } else if (todayMonth.equals("03")) {
                        horDesc = result.getHorDesc3();
                    } else if (todayMonth.equals("04")) {
                        horDesc = result.getHorDesc4();
                    } else if (todayMonth.equals("05")) {
                        horDesc = result.getHorDesc5();
                    } else if (todayMonth.equals("06")) {
                        horDesc = result.getHorDesc6();
                    } else if (todayMonth.equals("07")) {
                        horDesc = result.getHorDesc7();
                    } else if (todayMonth.equals("08")) {
                        horDesc = result.getHorDesc8();
                    } else if (todayMonth.equals("09")) {
                        horDesc = result.getHorDesc9();
                    } else if (todayMonth.equals("10")) {
                        horDesc = result.getHorDesc10();
                    } else if (todayMonth.equals("11")) {
                        horDesc = result.getHorDesc11();
                    } else {
                        horDesc = result.getHorDesc12();
                    }
                    Log.d("horDesc", horDesc);

                    HorItem item = new HorItem();
                    item.setHorImage(result.getHorImage());
                    item.setHorEngTitle(result.getHorEngTitle());
                    item.setHorKrTitle(result.getHorKrTitle());
                    item.setHorPeriod(result.getHorPeriod());
                    item.setHorDesc(horDesc);

                    horItems.add(item);

                    horViewpager.setAdapter(new HoroscopeAdapter(horItems));
//                    horAdapter.addItem(new HorItem(result.getHorImage(), result.getHorEngTitle(), result.getHorKrTitle(), result.getHorPeriod(), horDesc));
                } else {
                    Log.d("horoscope", "별자리 운세 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<Horoscope> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private int getItem(int i) {
        return horViewpager.getCurrentItem() + i;
    }
}