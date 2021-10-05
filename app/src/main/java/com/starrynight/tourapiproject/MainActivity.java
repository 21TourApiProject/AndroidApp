package com.starrynight.tourapiproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.starrynight.tourapiproject.mapPage.Activities;
import com.starrynight.tourapiproject.mapPage.MapFragment;
import com.starrynight.tourapiproject.searchPage.FilterFragment;
import com.starrynight.tourapiproject.searchPage.SearchResultFragment;
import com.starrynight.tourapiproject.starPage.TonightSkyFragment;

public class MainActivity extends AppCompatActivity {

    public static Context mContext;

    MainFragment mainFragment;
    SearchFragment searchFragment;
    TonightSkyFragment tonightSkyFragment;
    PersonFragment personFragment;
    View bottom;
    BottomNavigationView bottomNavigationView;
    private long backKeyPressTime=0;
    String[] WRITE_PERMISSION = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    String[] READ_PERMISSION = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    String[] INTERNET_PERMISSION = new String[]{Manifest.permission.INTERNET};
    int PERMISSIONS_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        mainFragment = new MainFragment();
        searchFragment = new SearchFragment();
        tonightSkyFragment = new TonightSkyFragment();
        personFragment = new PersonFragment();

        //권한 설정
        int permission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int permission3 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET);//denied면 -1

        Log.d("test", "onClick: location clicked");
        if (permission == PackageManager.PERMISSION_GRANTED&&permission2 == PackageManager.PERMISSION_GRANTED&&permission3==PackageManager.PERMISSION_GRANTED) {
            Log.d("MyTag","읽기,쓰기,인터넷 권한이 있습니다.");

        } else if (permission == PackageManager.PERMISSION_DENIED){
            Log.d("test", "permission denied");
            Toast.makeText(getApplicationContext(), "쓰기권한이 없습니다.", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(MainActivity.this, WRITE_PERMISSION, PERMISSIONS_REQUEST_CODE);
            ActivityCompat.requestPermissions(MainActivity.this, READ_PERMISSION, PERMISSIONS_REQUEST_CODE);
            ActivityCompat.requestPermissions(MainActivity.this, INTERNET_PERMISSION, PERMISSIONS_REQUEST_CODE);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.main_view, mainFragment).commit();
        bottomNavigationView = findViewById(R.id.bottom_nav_view);

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

            Intent intent = getIntent();
            Activities fromWhere = (Activities) intent.getSerializableExtra("FromWhere");
            if (fromWhere == Activities.OBSERVATION) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.main_view, searchFragment).commit();
                Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                bundle.putSerializable("FromWhere",Activities.OBSERVATION);//번들에 넘길 값 저장
                bundle.putSerializable("BalloonObject", intent.getSerializableExtra("BalloonObject"));    //지도에 필요한 내용
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment mapfragment = new MapFragment();
                mapfragment.setArguments(bundle);
                transaction.replace(R.id.main_view, mapfragment);
                transaction.commit();
            } else if (fromWhere ==Activities.TOURISTPOINT) {
                Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                bundle.putSerializable("FromWhere", Activities.TOURISTPOINT);//번들에 넘길 값 저장
                bundle.putSerializable("BalloonObject", intent.getSerializableExtra("BalloonObject"));    //지도에 필요한 내용

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment mapfragment = new MapFragment();
                mapfragment.setArguments(bundle);
                transaction.replace(R.id.main_view, mapfragment);
                transaction.commit();
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

        if ((fragment instanceof FilterFragment) || fragment instanceof SearchResultFragment) {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        } else if (fragment instanceof MapFragment) {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            } else {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                super.onBackPressed();
            }
        } else if (fragment instanceof TonightSkyFragment) {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            } else {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                bottomNavigationView.setSelectedItemId(R.id.navigation_main);
                replaceFragment(mainFragment);

                showBottom();
            }
        } else if (fragment instanceof MainFragment) {
            if (System.currentTimeMillis() > backKeyPressTime + 2000) {
                backKeyPressTime = System.currentTimeMillis();
                Toast.makeText(this, "한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (System.currentTimeMillis() <= backKeyPressTime + 2000) {
                finishAffinity();
                System.runFinalization();
                System.exit(0);
//                finish();
            }
        } else {
            bottomNavigationView.setSelectedItemId(R.id.navigation_main);
            replaceFragment(mainFragment);
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
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
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

    @Override
    public void onResume() {
        super.onResume();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.detach(mainFragment).attach(mainFragment).commit();
    }

}