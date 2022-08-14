package com.starrynight.tourapiproject.postWritePage;

import android.view.View;
/**
 * 인터페이스명: OnSelectImageItemClickListener
 * 설명: 게시물 작성 페이지에서 선택한 이미지 클릭 이벤트 Listener.
 */
public interface OnSelectImageItemClickListener {
    public void onItemClick(SelectImageAdapter.ViewHolder holder, View view, int position);
}
