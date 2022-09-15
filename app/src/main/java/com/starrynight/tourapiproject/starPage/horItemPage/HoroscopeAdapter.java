package com.starrynight.tourapiproject.starPage.horItemPage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.starrynight.tourapiproject.R;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * @className : HoroscopeAdapter
 * @description : 별자리 운세 Adapter입니다.
 * @modification : 2022-09-15 (hyeonz) 주석추가
 * @author : hyeonz
 * @date : 2022-09-15
 * @version : 1.0
====개정이력(Modification Information)====
수정일        수정자        수정내용
-----------------------------------------
2022-09-15   hyeonz      주석추가
 */
public class HoroscopeAdapter extends RecyclerView.Adapter<HoroscopeAdapter.HorViewHolder> {
    ArrayList<HorItem> horItems = new ArrayList<>();

//    public HoroscopeAdapter(List<HorItem> horItems){
//        this.horItems = horItems;
//    }

    @NonNull
    @NotNull
    @Override
    public HoroscopeAdapter.HorViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.star__horoscope_view, parent, false);

        return new HorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HorViewHolder holder, int position) {
        HorItem horItem = horItems.get(position);
        holder.setHorItem(horItem);
        Glide.with(holder.itemView.getContext())
                .load(horItem.getHorImage())
                .into(holder.horImage);
    }

    @Override
    public int getItemCount() {
        return horItems.size();
    }

    public void addItem(HorItem horItem) {
        horItems.add(horItem);
    }

    public void setItems(ArrayList<HorItem> horItems) {
        this.horItems = horItems;
    }

    public HorItem getItem(int position) {
        return horItems.get(position);
    }

    public static class HorViewHolder extends RecyclerView.ViewHolder {
        ImageView horImage;
        TextView horEngTitle;
        TextView horKrTitle;
        TextView horPeriod;
        TextView horDesc;
        TextView horGuard;
        TextView horPersonality;
        TextView horTravel;

        public HorViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            horImage = itemView.findViewById(R.id.hor_image);
            horEngTitle = itemView.findViewById(R.id.hor_eng_name);
            horKrTitle = itemView.findViewById(R.id.hor_kr_name);
            horPeriod = itemView.findViewById(R.id.hor_period);
            horDesc = itemView.findViewById(R.id.hor_desc);
            horGuard = itemView.findViewById(R.id.hor_guard);
            horPersonality = itemView.findViewById(R.id.hor_personality);
            horTravel = itemView.findViewById(R.id.hor_travel);

            itemView.setClickable(false);
        }

        public void setHorItem(HorItem horItem) {
            horEngTitle.setText(horItem.getHorEngTitle());
            horKrTitle.setText(horItem.getHorKrTitle());
            horPeriod.setText(horItem.getHorPeriod());
            horDesc.setText(horItem.getHorDesc());
            horGuard.setText(horItem.getHorGuard());
            horPersonality.setText(horItem.getHorPersonality());
            horTravel.setText(horItem.getHorTravel());
        }
    }
}
