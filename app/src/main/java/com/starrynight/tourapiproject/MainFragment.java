
package com.starrynight.tourapiproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.starrynight.tourapiproject.alarmPage.AlarmActivity;
import com.starrynight.tourapiproject.postPage.postRetrofit.MainPost;
import com.starrynight.tourapiproject.postPage.postRetrofit.MainPost_adapter;
import com.starrynight.tourapiproject.postPage.postRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.postWritePage.PostWriteActivity;
import com.starrynight.tourapiproject.searchPage.searchPageRetrofit.Filter;
import com.starrynight.tourapiproject.weatherPage.WeatherActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    Long userId;
    String[] filename2 = new String[10];
    private ArrayList<String> urls = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    ScrollView scrollView;
    List<Long> myhashTagIdList;

    public MainFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        swipeRefreshLayout = v.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        myhashTagIdList = new ArrayList<>();
        String fileName = "userId";
        try {
            FileInputStream fis = getActivity().openFileInput(fileName);
            String line = new BufferedReader(new InputStreamReader(fis)).readLine();
            userId = Long.parseLong(line);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Call<List<Long>> call = RetrofitClient.getApiService().getMyHashTagIdList(userId);
        call.enqueue(new Callback<List<Long>>() {
            @Override
            public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {
                if (response.isSuccessful()) {
                    myhashTagIdList = response.body();
                    Filter filter = new Filter(null, myhashTagIdList);
                    Call<List<MainPost>> call2 = RetrofitClient.getApiService().getMainPosts(filter);
                    call2.enqueue(new Callback<List<MainPost>>() {
                        @Override
                        public void onResponse(Call<List<MainPost>> call, Response<List<MainPost>> response) {
                            if (response.isSuccessful()) {
                                List<MainPost> result = response.body();
                                RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                layoutManager.setReverseLayout(true);
                                layoutManager.setStackFromEnd(true);
                                recyclerView.setLayoutManager(layoutManager);
                                MainPost_adapter adapter = new MainPost_adapter(result, getContext());
                                recyclerView.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<MainPost>> call, Throwable t) {
                            Log.d("mainPostList", "인터넷 오류");
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<Long>> call, Throwable t) {
                Log.d("mainPostIdList", "인터넷 오류");
            }
        });


        ImageButton button = (ImageButton) v.findViewById(R.id.weather_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), WeatherActivity.class);
                startActivity(intent);
            }
        });

        // 게시물 작성 페이지로 넘어가는 이벤트
        Button postWrite = (Button) v.findViewById(R.id.postWrite);
        postWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), PostWriteActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        // 알림 페이지로 넘어가는 이벤트

        Button alarm = (Button) v.findViewById(R.id.main_alarm);
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AlarmActivity.class);
                startActivityForResult(intent, 104);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            //프래그먼트 새로고침
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
        swipeRefreshLayout.setRefreshing(false);
    }

}