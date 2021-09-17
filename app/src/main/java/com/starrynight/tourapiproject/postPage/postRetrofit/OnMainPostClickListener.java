package com.starrynight.tourapiproject.postPage.postRetrofit;

import android.view.View;

import com.starrynight.tourapiproject.postPage.postRetrofit.MainPost_adapter;

public interface OnMainPostClickListener {
    public void onItemClick(MainPost_adapter.ViewHolder holder, View view, int position);
}
