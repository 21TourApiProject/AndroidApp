package com.starrynight.tourapiproject.starPage.starItemPage;

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

public class StarViewAdapter extends RecyclerView.Adapter<StarViewAdapter.ViewHolder> {
    ArrayList<StarItem> items = new ArrayList<>();
    OnStarItemClickListener listener;

    public void addItem(StarItem item) {
        items.add(item);
    }

    public void setItems(ArrayList<StarItem> items) {
        this.items = items;
    }

    public StarItem getItem(int position) {
        return items.get(position);
    }

    @NonNull
    @NotNull
    @Override

    public StarViewAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.star__custom_grid_layout, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder viewHolder, int position) {
        StarItem item = items.get(position);
        viewHolder.setItem(item);
        Glide.with(viewHolder.itemView.getContext())
                .load(item.getCelImage())
                .into(viewHolder.celImage);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(OnStarItemClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView celTxt;
        ImageView celImage;

        public ViewHolder(View itemView, final OnStarItemClickListener listener) {
            super(itemView);

            celImage = itemView.findViewById(R.id.cel_image);
            celTxt = itemView.findViewById(R.id.cel_txt);

            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();

                    if (listener != null) {
                        listener.onItemClick(StarViewAdapter.ViewHolder.this, v, position);
                    }
                }
            });
        }

        public void setItem(StarItem item) {
            celTxt.setText(item.getCelName());
        }
    }
}
