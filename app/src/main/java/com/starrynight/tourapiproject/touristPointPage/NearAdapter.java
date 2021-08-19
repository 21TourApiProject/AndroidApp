package com.starrynight.tourapiproject.touristPointPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.touristPointPage.touristPointRetrofit.Near;

import java.util.ArrayList;
import java.util.List;

public class NearAdapter extends RecyclerView.Adapter<NearAdapter.ViewHolder> {
    private static List<Near> items;
    OnNearItemClickListener listener;
    private Context context;
    private String[] imageUrl;

    public NearAdapter(List<Near> items, String[] imageUrl, Context context){
        this.items = items;
        this.imageUrl = imageUrl;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //viewHolder 처음 만드는 함수
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.custom_near_item, viewGroup, false);

        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        //viewHolder 재사용 하는 함수
        Near item = items.get(position);
        viewHolder.setItem(item);
        viewHolder.bindSliderImage(imageUrl[position]);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 :items.size();
    }


    public void addItem(Near item) {
        items.add(item);
    }

    public void setItems(ArrayList<Near> items) {
        this.items = items;
    }

    public Near getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Near item) {
        items.set(position, item);
    }


    public void setOnNearItemClickListener(OnNearItemClickListener listener) {
        this.listener = listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView nearImage;
        TextView nearTitle;
        TextView nearAddr;
        TextView nearCat3Name;
        TextView nearOverviewSimple;

        public ViewHolder(View itemView, final OnNearItemClickListener listener) {
            super(itemView);

            nearImage = itemView.findViewById(R.id.nearImage);
            nearTitle = itemView.findViewById(R.id.nearTitle);
            nearAddr = itemView.findViewById(R.id.nearAddr);
            nearCat3Name = itemView.findViewById(R.id.nearCat3Name);
            nearOverviewSimple = itemView.findViewById(R.id.nearOverviewSimple);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void bindSliderImage(String imageURL) {
            Glide.with(context)
                    .load(imageURL)
                    .into(nearImage);
        }

        public void setItem(Near item) {
            nearTitle.setText(item.getTitle());
            nearAddr.setText(item.getAddr());
            nearCat3Name.setText(item.getCat3Name());
            nearOverviewSimple.setText(item.getOverviewSimple());
        }
    }

}
