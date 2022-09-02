package com.starrynight.tourapiproject.postPage.postRetrofit;

import android.view.View;

/**
 * 인터페이스명: OnMainPostClickListener
 * 설명:  메인 페이지 게시물 아이템(해시태그) 클릭 이벤트 Listener입니다.
 */
public interface OnMainPostClickListener {
    public void onItemClick(MainPost_adapter.ViewHolder holder, View view, int position);
}
