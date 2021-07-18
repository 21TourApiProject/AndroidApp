package com.starrynight.tourapiproject;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
//
//


    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, fragment).commit();
    }

}