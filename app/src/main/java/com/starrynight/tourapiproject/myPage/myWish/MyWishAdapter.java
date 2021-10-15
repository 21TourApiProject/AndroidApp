package com.starrynight.tourapiproject.myPage.myWish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.starrynight.tourapiproject.R;

import java.util.ArrayList;
import java.util.List;

public class MyWishAdapter extends RecyclerView.Adapter<MyWishAdapter.ViewHolder> {
    private static List<MyWish> items;
    private Context context;

    public MyWishAdapter(List<MyWish> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public MyWishAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.custom_my_wish_item, viewGroup, false);

        return new MyWishAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyWishAdapter.ViewHolder viewHolder, int position) {
        MyWish item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void addItem(MyWish item) {
        items.add(item);
    }

    public void setItems(ArrayList<MyWish> items) {
        this.items = items;
    }

    public MyWish getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, MyWish item) {
        items.set(position, item);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title;
        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.myWishThumbnail);
            title = itemView.findViewById(R.id.myWishTitle);
            layout = itemView.findViewById(R.id.myWishLayout);

        }

        public void setItem(MyWish item) {
            if (item.getThumbnail() != null) {
                String imageName = item.getThumbnail();
                if (imageName.startsWith("http://")) {
                    Glide.with(context).load(item.getThumbnail()).into(thumbnail);
                } else {
                    Glide.with(context).load("https://starry-night.s3.ap-northeast-2.amazonaws.com/" + imageName).into(thumbnail);
                }
            }
            title.setText(item.getTitle());
        }
    }
}
