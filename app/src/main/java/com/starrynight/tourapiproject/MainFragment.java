package com.starrynight.tourapiproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
import com.starrynight.tourapiproject.observationPage.observationPageRetrofit.Observation;
import com.starrynight.tourapiproject.postItemPage.Post_item_adapter;
import com.starrynight.tourapiproject.postItemPage.post_item;
import com.starrynight.tourapiproject.postPage.postRetrofit.Post;
import com.starrynight.tourapiproject.postPage.postRetrofit.PostObservePoint;
import com.starrynight.tourapiproject.postPage.postRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.postWritePage.PostWriteActivity;
import com.starrynight.tourapiproject.signUpPage.SignUpActivity;
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
    Long postId;
    String[] filename2= new String[10];
    private ArrayList<String> urls = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    ScrollView scrollView;

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
        scrollView = v.findViewById(R.id.scroll_layout);
        swipeRefreshLayout = v.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        postId=1L;

//        String fileName = "postId";
//        try{
//            FileInputStream fis = getActivity().openFileInput(fileName);
//            String line = new BufferedReader(new InputStreamReader(fis)).readLine();
//            postId = Long.parseLong(line);
//            fis.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } System.out.println("postId = " + postId);

        Call<List<String>>call = RetrofitClient.getApiService().getPostImage(postId);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()){
                    System.out.println("이미지 업로드 성공"+response.body());
                    List<String> result = response.body();
                    ArrayList<String> FileName = new ArrayList<>();
                    for (int i=0;i<result.size();i++){
                        filename2[i]=result.get(i);
                        System.out.println(filename2[i]);
                    }
                    for (int i = 0; i <filename2.length;i++){
                        if(filename2[i] != null) {
                            System.out.println("https://starry-night.s3.ap-northeast-2.amazonaws.com/" + filename2[i]);
                            FileName.add("https://starry-night.s3.ap-northeast-2.amazonaws.com/" + filename2[i]);
                        }
                    }
                    Call<List<String>>call1 = RetrofitClient.getApiService().getPostHashTagName(postId);
                    call1.enqueue(new Callback<List<String>>() {
                        @Override
                        public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                            if (response.isSuccessful()){
                                System.out.println("해시태그 가져옴");
                                List<String> result2 = response.body();
                                Call<Post>call2 = RetrofitClient.getApiService().getPost(postId);
                                call2.enqueue(new Callback<Post>() {
                                    @Override
                                    public void onResponse(Call<Post> call, Response<Post> response) {
                                        if (response.isSuccessful()){
                                            System.out.println("게시물 정보 가져옴");
                                            Post post =response.body();
                                            Call<Observation>call3 = RetrofitClient.getApiService().getObservation(post.getObservationId());
                                            call3.enqueue(new Callback<Observation>() {
                                                @Override
                                                public void onResponse(Call<Observation> call, Response<Observation> response) {
                                                    if (response.isSuccessful()){
                                                        System.out.println("관측지도 가져왔네?!");
                                                        Observation observation = response.body();
                                                        String observePoint = observation.getObservationName();
                                                        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
                                                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                                        recyclerView.setLayoutManager(layoutManager);

                                                        Post_item_adapter adapter = new Post_item_adapter();
                                                        recyclerView.setAdapter(adapter);;

                                            adapter.addItem(new post_item(observePoint,"제목1","닉네임1", FileName,result2,"https://img-premium.flaticon.com/png/512/1144/1144811.png?token=exp=1627537493~hmac=2f43e8605ee99c9aec9e5491069d0d3c"));
                                            adapter.addItem(new post_item(observePoint,"제목2","닉네임2",FileName,result2,"https://img-premium.flaticon.com/png/512/1144/1144811.png?token=exp=1627537493~hmac=2f43e8605ee99c9aec9e5491069d0d3c"));
                                            adapter.addItem(new post_item(observePoint,"제목3","닉네임3",FileName,result2,"https://img-premium.flaticon.com/png/512/1144/1144811.png?token=exp=1627537493~hmac=2f43e8605ee99c9aec9e5491069d0d3c"));
                                            adapter.addItem(new post_item(observePoint,"제목4","닉네임4",FileName,result2,"https://img-premium.flaticon.com/png/512/1144/1144811.png?token=exp=1627537493~hmac=2f43e8605ee99c9aec9e5491069d0d3c"));
                                                    }else {System.out.println("관측지 못 가져옴");}
                                                }

                                                @Override
                                                public void onFailure(Call<Observation> call, Throwable t) {
                                                    System.out.println("관측지 못 가져옴 2");
                                                }
                                            });
                                        }else{
                                            Log.d("postPage","게시물 정보 못 가져옴");}
                                    }

                                    @Override
                                    public void onFailure(Call<Post> call, Throwable t) {
                                        Log.d("postPage","게시물 정보 인터넷 실패");
                                    }
                                });
                            }else {System.out.println("해시태그 못 가져옴");}
                        }

                        @Override
                        public void onFailure(Call<List<String>> call, Throwable t) {
                            System.out.println("해시태그 못 가져옴 2");
                        }
                    });
                }else {System.out.println("이미지 업로드 실패");}
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                System.out.println("이미지 업로드 실패2");
            }
        });

        ImageButton button =(ImageButton) v.findViewById(R.id.weather_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), WeatherActivity.class);
                startActivity(intent);
            }
        });

        // 게시물 작성 페이지로 넘어가는 이벤트
        Button postWrite =(Button) v.findViewById(R.id.postWrite);
        postWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), PostWriteActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        // 회원가입 페이지로 넘어가는 이벤트(수정 예정)
        Button testSignUp = (Button) v.findViewById(R.id.testSignUp);
        testSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, 103);
            }
        });

        // 알림 페이지로 넘어가는 이벤트

        Button alarm = (Button)v.findViewById(R.id.main_alarm);
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AlarmActivity.class);
                startActivityForResult(intent, 104);
            }
        });

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (scrollView.getScrollY()==0){
                    swipeRefreshLayout.isEnabled();
                }
            }
        });

        return v;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101){
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