package com.starrynight.tourapiproject.postPage;

import android.view.View;

public interface OnRelatePostItemClickListener {
    //어떤 item이 클릭되면 이 함수가 호출되도록
    public void onItemClick(RelatePostAdapter.ViewHolder holder, View view, int position);
}