package com.starrynight.tourapiproject.postWritePage;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.observationPage.observationPageRetrofit.Observation;
import com.starrynight.tourapiproject.observationPage.observationPageRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.postItemPage.OnSearchItemClickListener;
import com.starrynight.tourapiproject.postItemPage.Search_item;
import com.starrynight.tourapiproject.postItemPage.Search_item_adapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
* @className : SearchObservingPointActivity
* @description : 게시물 작성시 관측지 기입 페이지 입니다.
* @modification : 2022-09-02 (jinhyeok) 주석 수정
* @author : jinhyeok
* @date : 2022-09-02
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   2022-09-02      jinhyeok       주석 수정

 */
public class SearchObservingPointActivity extends AppCompatActivity {
    EditText findObservePoint;
    String observePoint;
    ArrayList<Search_item> searchitemArrayList, filteredList;
    Search_item_adapter search_item_adapter;
    LinearLayoutManager layoutManager;
    RecyclerView optionObservationRecyclerView;
    TextView optionText;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_observing_point);

        optionObservationRecyclerView = findViewById(R.id.optionObservationRecyclerView);
        optionObservationRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1));
        findObservePoint = findViewById(R.id.findObservePoint);
        searchitemArrayList = new ArrayList<>();
        filteredList = new ArrayList<>();
        search_item_adapter = new Search_item_adapter(searchitemArrayList, this);
        layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        optionObservationRecyclerView.setLayoutManager(layoutManager);
        optionObservationRecyclerView.setAdapter(search_item_adapter);
        optionText = findViewById(R.id.optionText);

        Call<List<Observation>> call = RetrofitClient.getApiService().getAllObservation();
        call.enqueue(new Callback<List<Observation>>() {
            @Override
            public void onResponse(Call<List<Observation>> call, Response<List<Observation>> response) {
                if (response.isSuccessful()) {
                    Log.d("observation", "관측지 리스트 업로드");
                    List<Observation> observationList = response.body();
                    for (int i = 0; i < observationList.size() - 1; i++) {
                        searchitemArrayList.add(new Search_item(observationList.get(i).getObservationName(), observationList.get(i).getAddress()));
                    }
                } else {
                    Log.d("observation", "관측지 리스트 업로드 실패");
                }
            }


            @Override
            public void onFailure(Call<List<Observation>> call, Throwable t) {
                Log.d("observation", "관측지 리스트 업로드 인터넷 실패");
            }
        });
        search_item_adapter.notifyDataSetChanged();

        findObservePoint.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = findObservePoint.getText().toString();
                searchFilter(text);
            }
        });

        search_item_adapter.setOnItemClicklistener(new OnSearchItemClickListener() {
            @Override
            public void onItemClick(Search_item_adapter.ViewHolder holder, View view, int position) {
                Search_item item = search_item_adapter.getItem(position);
                observePoint = item.getItemName();
                Intent intent = new Intent();
                intent.putExtra("observationName", observePoint);
                setResult(2, intent);
                finish();
            }
        });
        optionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observePoint = ((EditText) (findViewById(R.id.findObservePoint))).getText().toString();
                Intent intent = new Intent();
                intent.putExtra("optionObservationName", observePoint);
                setResult(2, intent);
                finish();
            }
        });
        //관측지 선택 버튼
        Button addObservePoint = findViewById(R.id.addObservePoint);
        addObservePoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observePoint = ((EditText) (findViewById(R.id.findObservePoint))).getText().toString();
                Intent intent = new Intent();
                intent.putExtra("optionObservationName", observePoint);
                setResult(2, intent);
                finish();
            }
        });
        //뒤로가기 버튼
        ImageView back_btn = findViewById(R.id.search__back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //임의 관측지 입력 칸
        editText = findViewById(R.id.findObservePoint);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (!editText.getText().toString().equals("")) {
                        observePoint = ((EditText) (findViewById(R.id.findObservePoint))).getText().toString();
                        Intent intent = new Intent();
                        intent.putExtra("optionObservationName", observePoint);
                        setResult(2, intent);
                        finish();
                    }
                } else {
                    return false;
                }
                return true;
            }
        });

    }

    public void searchFilter(String searchText) {
        filteredList.clear();
        Button add_btn = findViewById(R.id.addObservePoint);
        for (int i = 0; i < searchitemArrayList.size(); i++) {
            if (searchitemArrayList.get(i).getItemName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(searchitemArrayList.get(i));
                optionText.setVisibility(View.GONE);
            }
        }
        if (filteredList.size() == 0) {
            {
                optionText.setVisibility(View.VISIBLE);
            }
        }
        search_item_adapter.filterList(filteredList);
    }

}