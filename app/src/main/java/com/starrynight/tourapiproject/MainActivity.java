package com.starrynight.tourapiproject;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.starrynight.tourapiproject.mapPage.Activities;
import com.starrynight.tourapiproject.mapPage.MapFragment;
import com.starrynight.tourapiproject.observationPage.ObservationsiteActivity;
import com.starrynight.tourapiproject.searchPage.FilterFragment;
import com.starrynight.tourapiproject.searchPage.SearchResultFragment;
import com.starrynight.tourapiproject.starPage.TonightSkyFragment;
import com.starrynight.tourapiproject.touristPointPage.TouristPointActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static Context mContext;

    MainFragment mainFragment;
    SearchFragment searchFragment;
    TonightSkyFragment tonightSkyFragment;
    PersonFragment personFragment;
    View bottom;
    private long backKeyPressTime=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        mainFragment = new MainFragment();
        searchFragment = new SearchFragment();
        tonightSkyFragment = new TonightSkyFragment();
        personFragment = new PersonFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.main_view, mainFragment).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);

        bottom = findViewById(R.id.bottom_nav_view);



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navigation_main:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_view, mainFragment).commit();
                        return true;
                    case R.id.navigation_search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_view, searchFragment).commit();
                        return true;
                    case R.id.navigation_star:
                        showOffBottom();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_view, tonightSkyFragment).commit();
                        return true;
                    case R.id.navigation_person:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_view, personFragment).commit();
                        return true;
                }
                return false;
            }
        });
        if (getIntent() != null) {

            System.out.println("여기 들어오니?");
            Intent intent = getIntent();
            Activities fromWhere = (Activities) intent.getSerializableExtra("FromWhere");
            System.out.println("어디서"+fromWhere);
            if (fromWhere == Activities.OBSERVATION) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_view, searchFragment).commit();
                Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                bundle.putSerializable("FromWhere",Activities.OBSERVATION);//번들에 넘길 값 저장
                bundle.putSerializable("BalloonObject", intent.getSerializableExtra("BalloonObject"));    //지도에 필요한 내용
                MapFragment mapFragment = new MapFragment();
                mapFragment.setArguments(bundle);
                replaceFragment(mapFragment);
            } else if (fromWhere ==Activities.TOURISTPOINT) {
                Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                bundle.putSerializable("FromWhere", Activities.TOURISTPOINT);//번들에 넘길 값 저장
                bundle.putSerializable("BalloonObject", intent.getSerializableExtra("BalloonObject"));    //지도에 필요한 내용
                MapFragment mapFragment = new MapFragment();
                mapFragment.setArguments(bundle);
                ((MainActivity)MainActivity.mContext).replaceFragment(mapFragment);
            }
        }
    }

    public void changeFragment(Fragment f)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_view, f);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed(){
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_view);

        if ((fragment instanceof FilterFragment) || fragment instanceof SearchResultFragment||(fragment instanceof MapFragment)) {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }else {
            if(System.currentTimeMillis() > backKeyPressTime+2000){
                backKeyPressTime = System.currentTimeMillis();
                Toast.makeText(this,"한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (System.currentTimeMillis() <= backKeyPressTime+2000){
                finish();
            }
        }

//        if(!(fragment instanceof FilterFragment)) {
//            if(System.currentTimeMillis() > backKeyPressTime+2000){
//                backKeyPressTime = System.currentTimeMillis();
//                Toast.makeText(this,"한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (System.currentTimeMillis() <= backKeyPressTime+2000){
//                finish();
//            }
//        } else{
//            if (getFragmentManager().getBackStackEntryCount() > 0 ){
//                getFragmentManager().popBackStack();
//            } else {
//                super.onBackPressed();
//            }
//        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view, fragment).commitAllowingStateLoss();
    }


    public void showOffBottom(){
        bottom.setVisibility(View.GONE);
    }

    public void showBottom(){
        bottom.setVisibility(View.VISIBLE);
    }

}