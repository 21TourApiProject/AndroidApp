package com.starrynight.tourapiproject;

import android.Manifest;
import android.annotation.SuppressLint;
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

import java.util.ArrayList;
/**
* @className : MainActivity
* @description : 메인 페이지 입니다.
* @modification : 2022-09-02 (jinhyeok) 주석 수정
* @author : jinhyeok
* @date : 2022-09-02
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   2022-09-02      jinhyeok       주석 수정

 */
public class MainActivity extends AppCompatActivity {

    public static Context mContext;

    MainFragment mainFragment;
    SearchFragment searchFragment;
    TonightSkyFragment tonightSkyFragment;
    PersonFragment personFragment;
    View bottom;
    BottomNavigationView bottomNavigationView;
    private long backKeyPressTime = 0;
    String[] WRITE_PERMISSION = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    String[] READ_PERMISSION = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    String[] INTERNET_PERMISSION = new String[]{Manifest.permission.INTERNET};
    int PERMISSIONS_REQUEST_CODE = 100;

    Fragment map, searchResult, filter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

//        mainFragment = new MainFragment();
//        searchFragment = new SearchFragment();
//        tonightSkyFragment = new TonightSkyFragment();
//        personFragment = new PersonFragment();

        //권한 설정
        int permission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int permission3 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET);//denied면 -1

        Log.d("test", "onClick: location clicked");
        if (permission == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED && permission3 == PackageManager.PERMISSION_GRANTED) {
            Log.d("MyTag", "읽기,쓰기,인터넷 권한이 있습니다.");

        } else if (permission == PackageManager.PERMISSION_DENIED) {
            Log.d("test", "permission denied");
            Toast.makeText(getApplicationContext(), "쓰기권한이 없습니다.", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(MainActivity.this, WRITE_PERMISSION, PERMISSIONS_REQUEST_CODE);
            ActivityCompat.requestPermissions(MainActivity.this, READ_PERMISSION, PERMISSIONS_REQUEST_CODE);
            ActivityCompat.requestPermissions(MainActivity.this, INTERNET_PERMISSION, PERMISSIONS_REQUEST_CODE);
        }
        //메인 페이지 초기화 상태
        if (mainFragment == null) {
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.main_view, mainFragment).commit();
        }

        bottomNavigationView = findViewById(R.id.bottom_nav_view);

        bottom = findViewById(R.id.bottom_nav_view);
        //바텀 네비게이션 버튼 클릭 시 이벤트
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_main:
                        if(bottomNavigationView.getSelectedItemId() == R.id.navigation_main) {
                            mainFragment.toTheTop();
                        }else{
                            if (mainFragment == null) {
                                mainFragment = new MainFragment();
                                getSupportFragmentManager().beginTransaction().add(R.id.main_view, mainFragment).commit();
                            }
                            if (mainFragment != null)
                                getSupportFragmentManager().beginTransaction().show(mainFragment).commit();
                            if (searchFragment != null)
                                getSupportFragmentManager().beginTransaction().hide(searchFragment).commit();
                            if (tonightSkyFragment != null)
                                getSupportFragmentManager().beginTransaction().hide(tonightSkyFragment).commit();
                            if (personFragment != null)
                                getSupportFragmentManager().beginTransaction().hide(personFragment).commit();
                            showBottom();
                            removeFragments();
                        }
                        return true;
                    case R.id.navigation_search:
                        if (searchFragment == null) {
                            searchFragment = new SearchFragment();
                            getSupportFragmentManager().beginTransaction().add(R.id.main_view, searchFragment).commit();
                        }
                        if (mainFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(mainFragment).commit();
                        if (searchFragment != null)
                            getSupportFragmentManager().beginTransaction().show(searchFragment).commit();
                        if (tonightSkyFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(tonightSkyFragment).commit();
                        if (personFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(personFragment).commit();
                        removeFragments();
                        return true;

                    case R.id.navigation_star:
                        if (tonightSkyFragment == null) {
                            tonightSkyFragment = new TonightSkyFragment();
                            getSupportFragmentManager().beginTransaction().add(R.id.main_view, tonightSkyFragment).commit();
                        }
                        if (mainFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(mainFragment).commit();
                        if (searchFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(searchFragment).commit();
                        if (tonightSkyFragment != null)
                            getSupportFragmentManager().beginTransaction().show(tonightSkyFragment).commit();
                        if (personFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(personFragment).commit();

                        showOffBottom();
                        removeFragments();
                        return true;
                    case R.id.navigation_person:
                        if (personFragment == null) {
                            personFragment = new PersonFragment();
                            getSupportFragmentManager().beginTransaction().add(R.id.main_view, personFragment).commit();
                        }
                        getSupportFragmentManager().beginTransaction().detach(personFragment).attach(personFragment).commit();
                        if (mainFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(mainFragment).commit();
                        if (searchFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(searchFragment).commit();
                        if (tonightSkyFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(tonightSkyFragment).commit();
                        if (personFragment != null)
                            getSupportFragmentManager().beginTransaction().show(personFragment).commit();
                        removeFragments();
                        return true;
                }
                return false;
            }
        });
        if (getIntent() != null) {
            Intent intent = getIntent();
            Activities fromWhere = (Activities) intent.getSerializableExtra("FromWhere");
            if (fromWhere == Activities.OBSERVATION) {
                Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                bundle.putSerializable("FromWhere", Activities.OBSERVATION);//번들에 넘길 값 저장
                bundle.putSerializable("BalloonObject", intent.getSerializableExtra("BalloonObject"));    //지도에 필요한 내용
                bottomNavigationView.setSelectedItemId(R.id.navigation_search);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment mapfragment = new MapFragment();
                mapfragment.setArguments(bundle);
                transaction.hide(searchFragment);
                transaction.add(R.id.main_view, mapfragment);
                map = mapfragment;
                transaction.commit();
            } else if (fromWhere == Activities.TOURISTPOINT) {
                Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                bundle.putSerializable("FromWhere", Activities.TOURISTPOINT);//번들에 넘길 값 저장
                bundle.putSerializable("BalloonObject", intent.getSerializableExtra("BalloonObject"));    //지도에 필요한 내용
                bottomNavigationView.setSelectedItemId(R.id.navigation_search);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment mapfragment = new MapFragment();
                mapfragment.setArguments(bundle);
                transaction.hide(searchFragment);
                transaction.add(R.id.main_view, mapfragment);
                map = mapfragment;
                transaction.commit();
            } else if (fromWhere == Activities.POST) {
                bottomNavigationView.setSelectedItemId(R.id.navigation_search);
                getSupportFragmentManager().beginTransaction().hide(searchFragment).commit();
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                bundle.putSerializable("keyword", intent.getSerializableExtra("keyword"));
                bundle.putIntegerArrayList("hashTag", (ArrayList<Integer>) intent.getSerializableExtra("hashTag"));
                bundle.putIntegerArrayList("area", (ArrayList<Integer>) intent.getSerializableExtra("area"));
                Fragment searchResultFragment = new SearchResultFragment();
                searchResultFragment.setArguments(bundle);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main_view, searchResultFragment);
                searchResult = searchResultFragment;
                transaction.commit();
            }
        }
    }

    public void changeFragment(Fragment f) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_view, f);
        fragmentTransaction.commit();
    }
    //뒤로가기 버튼 클릭 시
    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_view);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (searchResult != null && fragment instanceof SearchResultFragment) {
            System.out.println("1번이니");
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.beginTransaction().remove(fragment).commit();
                bottomNavigationView.setSelectedItemId(R.id.navigation_search);
            } else {
                super.onBackPressed();
            }
//                fragmentManager.popBackStackImmediate("result", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else if (filter!=null && fragment instanceof FilterFragment) {
            System.out.println("2번이니");
            fragmentManager.beginTransaction().hide(fragment).commit();
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack();
            } else {
                super.onBackPressed();
            }
            showBottom();
        } else if (fragment instanceof MapFragment) {
            fragmentManager.beginTransaction().remove(fragment).commit();
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack();
                map = null;
            } else {
                super.onBackPressed();
            }
        } else if (bottomNavigationView.getSelectedItemId() == R.id.navigation_star) {
            if (tonightSkyFragment != null)
                getSupportFragmentManager().beginTransaction().hide(tonightSkyFragment).commit();
            bottomNavigationView.setSelectedItemId(R.id.navigation_main);
            if (mainFragment != null)
                getSupportFragmentManager().beginTransaction().show(mainFragment).commit();
            showBottom();
        } else if (bottomNavigationView.getSelectedItemId() == R.id.navigation_main) {
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
        } else if (bottomNavigationView.getSelectedItemId() == R.id.navigation_person || bottomNavigationView.getSelectedItemId() == R.id.navigation_search) {
            System.out.println("3번이니");
            if (map != null || searchResult != null) {
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    bottomNavigationView.setSelectedItemId(R.id.navigation_search);
                } else {
                    finish();
                }
            } else {
                bottomNavigationView.setSelectedItemId(R.id.navigation_main);
            }
        } else {
            System.out.println("4번이니");
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack();
            } else {
                super.onBackPressed();
//                finish();
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view, fragment).commitAllowingStateLoss();
    }


    public void showOffBottom() {
        bottom.setVisibility(View.GONE);
    }

    public void showBottom() {
        bottom.setVisibility(View.VISIBLE);
    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.detach(mainFragment).attach(mainFragment).commit();
//    }

    public Fragment getMap() {
        return map;
    }

    public void setMap(Fragment map) {
        this.map = map;
    }

    public Fragment getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(Fragment searchResult) {
        this.searchResult = searchResult;
    }

    public Fragment getFilter() {
        return filter;
    }

    public void setFilter(Fragment filter) {
        this.filter = filter;
    }

    /**
         * TODO 남아있는 프래그먼트를 전부 없앰
         * @param   -
         * @return
         * @throws
     */
    private void removeFragments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (map != null) {
            transaction.remove(map);
            map = null;
        }
        if (searchResult != null) {
            transaction.remove(searchResult);
            searchResult = null;
        }
        if (filter != null) {
            transaction.remove(filter);
            filter = null;
        }

        transaction.commit();
    }
}