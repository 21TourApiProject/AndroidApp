package com.starrynight.tourapiproject.weatherPage.wtObFit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * @className : ObFitViewAdapter
 * @description : 날씨페이지 별 관측적합도 Adapter입니다.
 * @modification : 2022-09-03 (hyeonz) 주석추가
 * @author : hyeonz
 * @date : 2022-09-03
 * @version : 1.0
====개정이력(Modification Information)====
수정일        수정자        수정내용
-----------------------------------------
hyeonz       2022-09-03   주석추가
 */
public class ObFitViewAdapter extends RecyclerView.Adapter<ObFitViewAdapter.ObFitViewHolder> {
    ArrayList<ObFitItem> items = new ArrayList<>();

    @NonNull
    @NotNull
    @Override
    public ObFitViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.wt__ob_fit_view, parent, false);

        return new ObFitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ObFitViewHolder holder, int position) {
        ObFitItem item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(ObFitItem item) {
        items.add(item);
    }

    public void setItems(ArrayList<ObFitItem> items) {
        this.items = items;
    }

    public ObFitItem getItem(int position) {
        return items.get(position);
    }

    public static class ObFitViewHolder extends RecyclerView.ViewHolder {
        ImageView obFitImage;
        TextView obFitTime;
        TextView obFitPercent;


        public ObFitViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            obFitImage = itemView.findViewById(R.id.ob_fit_image);
            obFitTime = itemView.findViewById(R.id.ob_fit_time);
            obFitPercent = itemView.findViewById(R.id.ob_fit_percent);

            itemView.setClickable(false);
        }

        public void setItem(ObFitItem item) {
            obFitImage.setImageResource(item.getObFitImage());
            obFitTime.setText(item.getObFitTime());
            obFitPercent.setText(item.getObFitPercent());
        }
    }

}
