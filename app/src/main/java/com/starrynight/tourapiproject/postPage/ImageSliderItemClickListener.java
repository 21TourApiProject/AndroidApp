package com.starrynight.tourapiproject.postPage;

import android.view.View;
/**
 * 인터페이스명: ImageSliderItemClickListener
 * 설명: 슬라이드 이미지 클릭 이벤트 Listener.
 */
public interface ImageSliderItemClickListener {
    public void onItemClick(ImageSliderAdapter.MyViewHolder holder, View view, int position);
}

