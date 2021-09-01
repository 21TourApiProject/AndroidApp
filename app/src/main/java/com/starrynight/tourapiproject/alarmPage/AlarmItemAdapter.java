package com.starrynight.tourapiproject.alarmPage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.postItemPage.OnPostPointItemClickListener;

import java.util.ArrayList;

public class AlarmItemAdapter extends RecyclerView.Adapter<AlarmItemAdapter.ViewHolder> {
    ArrayList<AlarmItem> items = new ArrayList<AlarmItem>();
    OnAlarmClickListener listener;

    public void addItem(AlarmItem item){items.add(item);}
    public void setItems(ArrayList<AlarmItem> items){ this.items = items; }
    public AlarmItem getItem(int position){ return items.get(position); }


    public void setItem(int position, AlarmItem item){
        items.set(position,item);
    }
    @NonNull
    @Override
    public AlarmItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.custom_alarm_item, parent, false);
        return new AlarmItemAdapter.ViewHolder(itemView,listener);
    }


    @Override
    public void onBindViewHolder(@NonNull AlarmItemAdapter.ViewHolder viewHolder, int position) {
        AlarmItem item = items.get(position);
        viewHolder.setItem(item);
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.alarmcontent.getVisibility()==View.GONE){
                viewHolder.alarmcontent.setVisibility(View.VISIBLE);
                }else{viewHolder.alarmcontent.setVisibility(View.GONE);}
            }
        });
    }

    @Override
    public int getItemCount() { return items.size(); }

    public void  setOnItemClicklistener(OnAlarmClickListener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView alarmtitle;
        TextView alarmdate;
        TextView alarmcontent;
        Button button;

        public ViewHolder(View itemView, final OnAlarmClickListener listener){
            super(itemView);
            alarmtitle =itemView.findViewById(R.id.alarm_title);
            alarmdate = itemView.findViewById(R.id.alarm_date);
            alarmcontent= itemView.findViewById(R.id.alarm_content);
            button = itemView.findViewById(R.id.scroll_btn);

        }
        public void setItem(AlarmItem item){
            alarmtitle.setText(item.alarmName);
            alarmdate.setText(item.alarmDate);
            alarmcontent.setText(item.alarmContent);
        }
    }

}
