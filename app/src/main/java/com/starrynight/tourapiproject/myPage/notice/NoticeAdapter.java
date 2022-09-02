package com.starrynight.tourapiproject.myPage.notice;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;

import java.util.ArrayList;
import java.util.List;
/**
* @className : NoticeAdapter
* @description : 공지 게시물 adapter 입니다.
* @modification : 2022-09-02 (jinhyeok) 주석 수정
* @author : jinhyeok
* @date : 2022-09-02
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   2022-09-02      jinhyeok       주석 수정

 */
public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {
    private static List<Notice> items;
    Context context;
    OnNoticeClickListener listener;

    public NoticeAdapter(Context context, List<Notice> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.custom_notice, viewGroup, false);

        return new NoticeAdapter.ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeAdapter.ViewHolder viewHolder, int position) {
        Notice item = items.get(position);
        viewHolder.setItem(item);
        viewHolder.noticeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.noticeOpen.getVisibility() == View.GONE) { //닫혀있으면 열기
                    viewHolder.noticeBtn.setRotation(90);
                    viewHolder.noticeOpen.setVisibility(View.VISIBLE);
                } else { //열려있으면 닫기
                    viewHolder.noticeBtn.setRotation(360);
                    viewHolder.noticeOpen.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClicklistener(OnNoticeClickListener listener) {
        this.listener = listener;
    }

    public void addItem(Notice item) {
        items.add(item);
    }

    public void setItems(ArrayList<Notice> items) {
        this.items = items;
    }

    public Notice getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Notice item) {
        items.set(position, item);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout noticeLayout;
        TextView noticeTitle;
        Button noticeBtn;
        LinearLayout noticeOpen;
        TextView noticeContent;
        TextView noticeDate;

        public ViewHolder(View itemView, final OnNoticeClickListener listener) {
            super(itemView);
            noticeLayout = itemView.findViewById(R.id.noticeLayout);
            noticeTitle = itemView.findViewById(R.id.noticeTitle);
            noticeBtn = itemView.findViewById(R.id.noticeBtn);
            noticeOpen = itemView.findViewById(R.id.noticeOpen);
            noticeContent = itemView.findViewById(R.id.noticeContent);
            noticeDate = itemView.findViewById(R.id.noticeDate);
        }

        public void setItem(Notice item) {
            noticeTitle.setText(item.getNoticeTitle());
            noticeContent.setText(Html.fromHtml(item.getNoticeContent(), HtmlCompat.FROM_HTML_MODE_LEGACY));
            noticeDate.setText(item.getNoticeDate());
            noticeOpen.setVisibility(View.GONE);
        }
    }
}
