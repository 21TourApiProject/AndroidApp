package com.starrynight.tourapiproject.postPage;

import android.view.View;
/**
 * 인터페이스명: OnRelatePostItemClickListener
 * 설명: 관련 게시물 아이템(해시태그, 이미지) 클릭 이벤트 Listener 입니다.
 */
public interface OnRelatePostItemClickListener {
    //어떤 item이 클릭되면 이 함수가 호출되도록
    public void onItemClick(RelatePostAdapter.ViewHolder holder, View view, int position);
}