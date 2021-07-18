package com.starrynight.tourapiproject;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;

//세인 주석
public class MainActivity extends AppCompatActivity {
//주석 추가
    MainFragment mainFragment;
    SearchFragment searchFragment;
    StarFragment starFragment;
    PersonFragment personFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = new MainFragment();
        searchFragment = new SearchFragment();
        starFragment = new StarFragment();
        personFragment = new PersonFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main,mainFragment).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_main:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main,mainFragment).commit();
                        return true;
                    case R.id.navigation_search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main,searchFragment).commit();
                        return true;
                    case R.id.navigation_star:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main,starFragment).commit();
                        return true;
                    case R.id.navigation_person:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main,personFragment).commit();
                        return true;
                }
                return false;
            }
        });

//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.nav_host_fragment_activity_main, SearchFragment.newInstance()).commit();


    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, fragment).commit();
    }

}