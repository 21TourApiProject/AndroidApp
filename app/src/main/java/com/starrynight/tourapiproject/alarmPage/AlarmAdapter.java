package com.starrynight.tourapiproject.alarmPage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;

import java.util.ArrayList;
/**
* @className : AlarmAdapter
* @description : 알림 클래스 adapter 입니다.
* @modification : 2022-09-02 (jinhyeok) 주석 수정
* @author : jinhyeok
* @date : 2022-09-02
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   2022-09-02      jinhyeok       주석 수정

 */
public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {
    ArrayList<Alarm> items = new ArrayList<Alarm>();
    OnAlarmClickListener listener;

    public void addItem(Alarm item) {
        items.add(item);
    }

    public void setItems(ArrayList<Alarm> items) {
        this.items = items;
    }

    public Alarm getItem(int position) {
        return items.get(position);
    }


    public void setItem(int position, Alarm item) {
        items.set(position, item);
    }

    @NonNull
    @Override
    public AlarmAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.custom_alarm_item, parent, false);
        return new AlarmAdapter.ViewHolder(itemView, listener);
    }


    @Override
    public void onBindViewHolder(@NonNull AlarmAdapter.ViewHolder viewHolder, int position) {
        Alarm item = items.get(position);
        viewHolder.setItem(item);
        viewHolder.alarmLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.alarmOpen.getVisibility() == View.GONE) { //닫혀있으면 열기
                    viewHolder.alarmBtn.setRotation(90);
                    viewHolder.alarmOpen.setVisibility(View.VISIBLE);
                    viewHolder.alarmtitle.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.name_purple));
                    viewHolder.star.setImageDrawable(ContextCompat.getDrawable(viewHolder.itemView.getContext(),R.drawable.alarm__star));
                } else { //열려있으면 닫기
                    viewHolder.alarmBtn.setRotation(360);
                    viewHolder.alarmOpen.setVisibility(View.GONE);
                    viewHolder.alarmtitle.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.white));
                    viewHolder.star.setImageDrawable(ContextCompat.getDrawable(viewHolder.itemView.getContext(),R.drawable.alarm__star_non));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClicklistener(OnAlarmClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout alarmLayout;
        TextView alarmtitle;
        TextView alarmdate;
        TextView alarmcontent;
        Button alarmBtn;
        LinearLayout alarmOpen;
        ImageView star;

        public ViewHolder(View itemView, final OnAlarmClickListener listener) {
            super(itemView);
            alarmLayout = itemView.findViewById(R.id.alarmLayout);
            alarmtitle = itemView.findViewById(R.id.alarm_title);
            alarmdate = itemView.findViewById(R.id.alarm_date);
            alarmcontent = itemView.findViewById(R.id.alarm_content);
            alarmBtn = itemView.findViewById(R.id.scroll_btn);
            alarmOpen = itemView.findViewById(R.id.alarmOpen);
            star = itemView.findViewById(R.id.alarm_star);

        }

        public void setItem(Alarm item) {
            alarmtitle.setText(item.getAlarmTitle());
            alarmdate.setText(item.getYearDate());
            alarmcontent.setText(item.getAlarmContent());
            alarmOpen.setVisibility(View.GONE);
        }
    }

}
