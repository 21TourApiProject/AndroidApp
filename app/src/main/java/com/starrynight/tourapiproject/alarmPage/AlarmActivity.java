package com.starrynight.tourapiproject.alarmPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.starrynight.tourapiproject.R;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        RecyclerView recyclerView = findViewById(R.id.alarm_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        AlarmItemAdapter adapter = new AlarmItemAdapter();
        adapter.addItem(new AlarmItem("알람 제목", "21,08,27","알림 내용"));
        adapter.addItem(new AlarmItem("알람 제목2", "21,08,27","알림 내용"));
        adapter.addItem(new AlarmItem("알람 제목3", "21,08,27","알림 내용"));
        adapter.addItem(new AlarmItem("알람 제목4", "21,08,27","알림 내용"));
        adapter.addItem(new AlarmItem("알람 제목5", "21,08,27","알림 내용"));
        recyclerView.setAdapter(adapter);

    }
}