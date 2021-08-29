package com.starrynight.tourapiproject.myPage.myWish.post;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;

import java.util.ArrayList;

public class MyPostWishAdapter extends RecyclerView.Adapter<MyPostWishAdapter.ViewHolder>{
    ArrayList<MyPostWish> items = new ArrayList<MyPostWish>();
    OnMyPostWishItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.custom_my_item, viewGroup, false);

        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        MyPostWish item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void addItem(MyPostWish item) {
        items.add(item);
    }

    public void setItems(ArrayList<MyPostWish> items) {
        this.items = items;
    }

    public MyPostWish getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, MyPostWish item) {
        items.set(position, item);
    }

    public void setOnMyPostWishItemClickListener(OnMyPostWishItemClickListener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title;
        LinearLayout layout;

        public ViewHolder(View itemView, final OnMyPostWishItemClickListener listener) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.myWishThumbnail);
            title = itemView.findViewById(R.id.myWishTitle);
            layout = itemView.findViewById(R.id.myWishLayout);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null){
                        listener.onItemClick(MyPostWishAdapter.ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(MyPostWish item) {
            //thumbnail.setImageBitmap(decodeFile(item.getThumbnail()));
            thumbnail.setBackgroundColor(Color.BLUE);
            title.setText(item.getTitle());
        }
    }
}
