package com.starrynight.tourapiproject.alarmPage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;

import java.util.ArrayList;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {
    ArrayList<Alarm> items = new ArrayList<Alarm>();
    OnAlarmClickListener listener;

    public void addItem(Alarm item){items.add(item);}
    public void setItems(ArrayList<Alarm> items){ this.items = items; }
    public Alarm getItem(int position){ return items.get(position); }


    public void setItem(int position, Alarm item){
        items.set(position,item);
    }
    @NonNull
    @Override
    public AlarmAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.custom_alarm_item, parent, false);
        return new AlarmAdapter.ViewHolder(itemView,listener);
    }


    @Override
    public void onBindViewHolder(@NonNull AlarmAdapter.ViewHolder viewHolder, int position) {
        Alarm item = items.get(position);
        viewHolder.setItem(item);
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.button.setRotation(270);
                if (viewHolder.alarmcontent.getVisibility()==View.GONE){
                viewHolder.alarmcontent.setVisibility(View.VISIBLE);
                }else{viewHolder.button.setRotation(90);
                    viewHolder.alarmcontent.setVisibility(View.GONE);
                    }
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
        public void setItem(Alarm item){
            alarmtitle.setText(item.getAlarmTitle());
            alarmdate.setText(item.getYearDate());
            alarmcontent.setText(item.getAlarmContent());
        }
    }

}
