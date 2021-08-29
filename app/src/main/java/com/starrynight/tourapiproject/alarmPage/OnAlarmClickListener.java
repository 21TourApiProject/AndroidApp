package com.starrynight.tourapiproject.alarmPage;

import android.view.View;

import com.starrynight.tourapiproject.postItemPage.PostHashTagItemAdapter;

public interface OnAlarmClickListener {
    public void onItemClick(AlarmItemAdapter.ViewHolder holder, View view, int position);
}
