package com.starrynight.tourapiproject.starPage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.starPage.constNameRetrofit.ConstNameAdapter;
import com.starrynight.tourapiproject.starPage.constNameRetrofit.ConstellationParams2;
import com.starrynight.tourapiproject.starPage.starPageRetrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @className : StarActivity
 * @description : 별자리 검색 페이지입니다.
 * @modification : 2022-09-15 (hyeonz) 주석추가
 * @author : hyeonz
 * @date : 2022-09-15
 * @version : 1.0
====개정이력(Modification Information)====
수정일        수정자        수정내용
-----------------------------------------
2022-09-15   hyeonz      주석추가
 */
public class StarSearchActivity extends AppCompatActivity {

    androidx.appcompat.widget.SearchView constSearch;
    ListView searchList;
    LinearLayout searchWordLayout;
    ImageView backBtn;

    List<String> nameList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    String itemClickId;

    String constName;

    ConstNameAdapter constNameAdapter;
    RecyclerView constNameRecycler;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_search);

        backBtn = findViewById(R.id.star_search_back_btn);

        //별자리 검색
        constSearch = findViewById(R.id.edit_search);
        searchList = findViewById(R.id.const_list_view);
        searchWordLayout = findViewById(R.id.search_word_layout);

        constSearch.setIconifiedByDefault(false);
        constSearch.setQueryHint("궁금한 별자리를 입력해보세요");

        // 오늘 볼 수 있는 별자리 이름 recyclerview
        constNameRecycler = findViewById(R.id.star_search_today_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        constNameRecycler.setLayoutManager(linearLayoutManager);
        constNameAdapter = new ConstNameAdapter();
        constNameRecycler.setAdapter(constNameAdapter);

        callTodayConstName();
        callAllConstName();
        connectClickConst();
        changeConstSearchText();
        onclickBackBtn();

        constSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchList.setVisibility(View.VISIBLE);
                searchWordLayout.setVisibility(View.GONE);
            }
        });

        constSearch.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("hasFocus", "0");
                    searchList.setVisibility(View.VISIBLE);
                    searchWordLayout.setVisibility(View.GONE);
                }

            }
        });
    }

    //오늘의 별자리 이름 리스트 불러오는  api
    public void callTodayConstName() {
        Call<List<ConstellationParams2>> todayConstNameCall = RetrofitClient.getApiService().getTodayConstName();
        todayConstNameCall.enqueue(new Callback<List<ConstellationParams2>>() {
            @Override
            public void onResponse(Call<List<ConstellationParams2>> call, Response<List<ConstellationParams2>> response) {
                if (response.isSuccessful()) {
                    List<ConstellationParams2> result = response.body();

                    for (ConstellationParams2 cp2 : result) {
                        ;
                        constNameAdapter.addItem(new ConstellationParams2(cp2.getConstName()));
                    }
                    constNameRecycler.setAdapter(constNameAdapter);
                } else {
                    Log.d("todayConst", "오늘의 별자리 이름 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<List<ConstellationParams2>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
    }

    // 모든 별자리 이름 호출
    public void callAllConstName() {
        Call<List<ConstellationParams2>> constNameCall = RetrofitClient.getApiService().getConstNames();
        constNameCall.enqueue(new Callback<List<ConstellationParams2>>() {
            @Override
            public void onResponse(Call<List<ConstellationParams2>> call, Response<List<ConstellationParams2>> response) {
                if (response.isSuccessful()) {
                    List<ConstellationParams2> result = response.body();

                    for (ConstellationParams2 cp2 : result) {
                        constName = cp2.getConstName();
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
    }

    //별자리 눌렀을 때 해당 별자리 페이지로 이동
    public void connectClickConst() {
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.star__search_list_item, nameList);
        searchList.setAdapter(arrayAdapter);
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemClickId = parent.getItemAtPosition(position).toString();
                Intent intent = new Intent(StarSearchActivity.this, StarActivity.class);
                intent.putExtra("constName", itemClickId);
                Log.d("constName", itemClickId);
                startActivity(intent);
            }
        });
    }

    //별자리 텍스트 검색 변화
    public void changeConstSearchText() {
        constSearch.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //검색 버튼 누를 때 호출
                StarSearchActivity.this.arrayAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 검색 창에서 글자가 변경이 일어날 때마다 호출
                StarSearchActivity.this.arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    // 별자리 뒤로가기 버튼 클릭 이벤트
    public void onclickBackBtn() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    @Override
    public void onBackPressed() {
        if (searchList.getVisibility() == View.VISIBLE) {
            searchList.setVisibility(View.GONE);
            searchWordLayout.setVisibility(View.VISIBLE);
            constSearch.clearFocus();
        } else {
            finish();
        }
    }
}