package com.starrynight.tourapiproject.starPage.constNameRetrofit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * @className : ConstNameAdapter
 * @description : 별자리 검색 페이지의 Adapter 입니다.
 * @modification : 2022-09-15 (hyeonz) 주석추가
 * @author : hyeonz
 * @date : 2022-09-15
 * @version : 1.0
====개정이력(Modification Information)====
수정일        수정자        수정내용
-----------------------------------------
2022-09-15   hyeonz      주석추가
 */
public class ConstNameAdapter extends RecyclerView.Adapter<ConstNameAdapter.TodayViewHolder> {
    ArrayList<ConstellationParams2> items = new ArrayList<>();

    @NotNull
    @Override
    public TodayViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.star__search_today_const_layout, parent, false);

        return new TodayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TodayViewHolder todayViewHolder, int position) {
        ConstellationParams2 item = items.get(position);
        todayViewHolder.setItem(item);
        todayViewHolder.constNum.setText(String.valueOf(position + 1));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(ConstellationParams2 item) {
        items.add(item);
    }

    public void setItems(ArrayList<ConstellationParams2> items) {
        this.items = items;
    }

    public ConstellationParams2 getItem(int position) {
        return items.get(position);
    }

    public static class TodayViewHolder extends RecyclerView.ViewHolder {
        TextView constNum;
        TextView constName;

        public TodayViewHolder(View itemView) {
            super(itemView);
            constNum = itemView.findViewById(R.id.const_today_num);
            constName = itemView.findViewById(R.id.const_today_name);

            itemView.setClickable(false);
        }

        public void setItem(ConstellationParams2 params2) {
            constName.setText(params2.getConstName());
        }
    }
}