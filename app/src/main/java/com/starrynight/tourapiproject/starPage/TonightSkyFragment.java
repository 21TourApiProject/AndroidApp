package com.starrynight.tourapiproject.starPage;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.ortiz.touchview.TouchImageView;
import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.starPage.starItemPage.OnStarItemClickListener;
import com.starrynight.tourapiproject.starPage.starItemPage.StarItem;
import com.starrynight.tourapiproject.starPage.starItemPage.StarViewAdapter;
import com.starrynight.tourapiproject.starPage.starPageRetrofit.Constellation;
import com.starrynight.tourapiproject.starPage.starPageRetrofit.RetrofitClient;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TonightSkyFragment extends Fragment implements SensorEventListener {
    //bottomSheet 관련
    private LinearLayout bottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;

    private EditText editSearch;
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
    ImageView imgClick;
    TextView openView;

    ImageView imgClick1;
    TextView openView1;

    ImageView helpBtn;
    ImageView compass;
    ImageView starBackBtn;


    LinearLayout helpInfo;
    ImageView helpBackBtn;
    View dim;

    TouchImageView touchImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tonight_sky, container, false);

        //나침반
        mSensorManger = (SensorManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SENSOR_SERVICE);
        mAcclerometer = mSensorManger.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManger.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        // bottomSheet 설정
        editSearch = v.findViewById(R.id.edit_search);
        editSearch.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        editSearch.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        bottomSheetBehavior.setPeekHeight(editSearch.getBottom() + 50);
                    }
                }
        );

        bottomSheet = v.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        bottomSheetBehavior.setPeekHeight(editSearch.getBottom());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


        // 뒤로 가기 버튼 클릭 이벤트
        starBackBtn =  v.findViewById(R.id.star_back_btn);
        starBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                startActivity(intent);
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
            public void onResponse(Call<List<StarItem>> call, Response<List<StarItem>> response) {
                if (response.isSuccessful()) {
                    List<StarItem> result = response.body();
                    for (StarItem si : result) {
                        constAdapter.addItem(new StarItem(si.getConstId(), si.getConstName(), si.getConstImage()));
                    }
                    constList.setAdapter(constAdapter);
                } else {
                    System.out.println("오늘의 별자리 불러오기 실패");
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
                //Log.d("constId출력", item.getConstId().toString());
                startActivity(intent);
            }
        });

        //도움말 textView open
        imgClick = v.findViewById(R.id.imgClick);
        openView = v.findViewById(R.id.layout_expand);
        imgClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (openView.getVisibility() == View.VISIBLE) {
                    openView.setVisibility(View.GONE);
                    imgClick.animate().setDuration(200).rotation(0f);
                } else {
                    openView.setVisibility(View.VISIBLE);
                    imgClick.animate().setDuration(200).rotation(90f);
                }
            }
        });

        imgClick1 = v.findViewById(R.id.imgClick1);
        openView1 = v.findViewById(R.id.layout_expand1);
        imgClick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (openView1.getVisibility() == View.VISIBLE) {
                    openView1.setVisibility(View.GONE);
                    imgClick1.animate().setDuration(200).rotation(0f);
                } else {
                    openView1.setVisibility(View.VISIBLE);
                    imgClick1.animate().setDuration(200).rotation(90f);
                }
            }
        });

        helpBtn = v.findViewById(R.id.help_btn);
        helpInfo = v.findViewById(R.id.help_info);
        helpBackBtn = v.findViewById(R.id.help_back_btn);
        dim = v.findViewById(R.id.dim);
        touchImageView = v.findViewById(R.id.touchImage);
        compass = v.findViewById(R.id.compass);
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

    public void stateButton(boolean state){
        touchImageView.setEnabled(state);
        compass.setEnabled(state);
        starBackBtn.setEnabled(state);
        helpBtn.setEnabled(state);
        bottomSheetBehavior.setDraggable(state);
        editSearch.setEnabled(state);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}