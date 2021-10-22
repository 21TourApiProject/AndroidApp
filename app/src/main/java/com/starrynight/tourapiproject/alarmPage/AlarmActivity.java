package com.starrynight.tourapiproject.alarmPage;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        RecyclerView recyclerView = findViewById(R.id.alarm_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        AlarmAdapter adapter = new AlarmAdapter();
        Call<List<Alarm>> call = RetrofitClient.getApiService().getAllAlarm();
        call.enqueue(new Callback<List<Alarm>>() {
            @Override
            public void onResponse(Call<List<Alarm>> call, Response<List<Alarm>> response) {
                if (response.isSuccessful()) {
                    List<Alarm> alarms = response.body();
                    for (int i = 0; i < alarms.size(); i++) {
                        adapter.addItem(new Alarm(alarms.get(i).getAlarmTitle(), alarms.get(i).getYearDate(), alarms.get(i).getAlarmContent()));
                    }
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.d("alarm", "알림 업로드 실패");
                }
            }

            @Override
            public void onFailure(Call<List<Alarm>> call, Throwable t) {
                Log.d("alarm", "알림 인터넷 오류");
            }
        });
        recyclerView.setAdapter(adapter);

        FrameLayout back_btn = findViewById(R.id.alarmBack);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}