package com.starrynight.tourapiproject.starPage.horoscope;

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

import java.util.ArrayList;

public class HoroscopeAdapter extends RecyclerView.Adapter<HoroscopeAdapter.HorViewHolder> {
    ArrayList<Horoscope> horItems = new ArrayList<>();

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
        Horoscope horItem = horItems.get(position);
        holder.setHorItem(horItem);
        Glide.with(holder.itemView.getContext())
                .load(horItem.getHorImage())
                .into(holder.horImage);
    }

    @Override
    public int getItemCount() {
        return horItems.size();
    }

    public void addItem(Horoscope horItem) {
        horItems.add(horItem);
    }

    public void setItems(ArrayList<Horoscope> horItems) {
        this.horItems = horItems;
    }

    public Horoscope getItem(int position) {
        return horItems.get(position);
    }

    public static class HorViewHolder extends RecyclerView.ViewHolder {
        ImageView horImage;
        TextView horEngTitle;
        TextView horKrTitle;
        TextView horPeriod;
        TextView horDesc;

        public HorViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            horImage = itemView.findViewById(R.id.hor_image);
            horEngTitle = itemView.findViewById(R.id.hor_eng_name);
            horKrTitle = itemView.findViewById(R.id.hor_kr_name);
            horPeriod = itemView.findViewById(R.id.hor_period);
            horDesc = itemView.findViewById(R.id.hor_desc);

            itemView.setClickable(false);
        }

        public void setHorItem(Horoscope horItem) {
            horEngTitle.setText(horItem.getHorEngTitle());
            horKrTitle.setText(horItem.getHorKrTitle());
            horPeriod.setText(horItem.getHorPeriod());
            horDesc.setText(horItem.getHorDesc());
        }
    }
}
