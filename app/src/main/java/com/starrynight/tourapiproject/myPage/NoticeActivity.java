package com.starrynight.tourapiproject.myPage;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.myPage.notice.Notice;
import com.starrynight.tourapiproject.myPage.notice.NoticeAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NoticeActivity extends AppCompatActivity {

    private static final String TAG = "Notice";
    RecyclerView noticeRecyclerView;
    List<Notice> finalAlarmList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        noticeRecyclerView = findViewById(R.id.noticeRecyclerView);

        LinearLayoutManager noticeLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        noticeLayoutManager.setReverseLayout(true);
        noticeLayoutManager.setStackFromEnd(true);
        noticeRecyclerView.setLayoutManager(noticeLayoutManager);

        Call<List<Notice>> call = RetrofitClient.getApiService().getAllNotice();
        call.enqueue(new Callback<List<Notice>>() {
            @Override
            public void onResponse(Call<List<Notice>> call, Response<List<Notice>> response) {
                if (response.isSuccessful()) {
                    List<Notice> result = response.body();
                    for (int i=0;i<result.size();i++){
                        if (!result.get(i).getNoticeTitle().contains("$")){
                            finalAlarmList.add(result.get(i));
                        }
                    }
                    NoticeAdapter noticeAdapter = new NoticeAdapter(getApplicationContext(), finalAlarmList);
                    noticeRecyclerView.setAdapter(noticeAdapter);
                } else {
                    Log.e(TAG, "공지사항 실패");
                }
            }

            @Override
            public void onFailure(Call<List<Notice>> call, Throwable t) {
                Log.e(TAG, "연결 오류");
            }
        });

        ImageView noticeBack = findViewById(R.id.noticeBack);
        noticeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}