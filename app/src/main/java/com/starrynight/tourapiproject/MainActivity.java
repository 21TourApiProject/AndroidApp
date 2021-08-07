package com.starrynight.tourapiproject;

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
import com.starrynight.tourapiproject.starPage.TonightSkyFragment;

public class MainActivity extends AppCompatActivity {

    MainFragment mainFragment;
    SearchFragment searchFragment;
    TonightSkyFragment tonightSkyFragment;
    PersonFragment personFragment;
    private long backkeyPressTime=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = new MainFragment();
        searchFragment = new SearchFragment();
        tonightSkyFragment = new TonightSkyFragment();
        personFragment = new PersonFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.main_view,mainFragment).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navigation_main:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_view,mainFragment).commit();
                        return true;
                    case R.id.navigation_search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_view,searchFragment).commit();
                        return true;
                    case R.id.navigation_star:
                        setBottomNavVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_view, tonightSkyFragment).commit();
                        return true;
                    case R.id.navigation_person:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_view,personFragment).commit();
                        return true;
                }
                return false;
            }
        });

//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.nav_host_fragment_activity_main, SearchFragment.newInstance()).commit();


    }

    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()>backkeyPressTime+2000){
            backkeyPressTime=System.currentTimeMillis();
            Toast.makeText(this,"한 번 더 누르시면 종료됩니다.",Toast.LENGTH_SHORT).show();
            return;
        }
        if (System.currentTimeMillis()<=backkeyPressTime+2000){
            finish();
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view, fragment).commit();
    }

    public void setBottomNavVisibility(int visibility){
        findViewById(R.id.bottom_nav_view).setVisibility(visibility);
    }

}