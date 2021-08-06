package com.starrynight.tourapiproject.myPage.myWish;

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

public class MyWishPostAdapter extends RecyclerView.Adapter<MyWishPostAdapter.ViewHolder>{
    ArrayList<MyWishPost> items = new ArrayList<MyWishPost>();
    OnMyWishPostItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.custom_my_wish, viewGroup, false);

        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        MyWishPost item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void addItem(MyWishPost item) {
        items.add(item);
    }

    public void setItems(ArrayList<MyWishPost> items) {
        this.items = items;
    }

    public MyWishPost getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, MyWishPost item) {
        items.set(position, item);
    }

    public void setOnMyWishItemClickListener(OnMyWishPostItemClickListener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title;
        LinearLayout layout;

        public ViewHolder(View itemView, final OnMyWishPostItemClickListener listener) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.wishThumbnail);
            title = itemView.findViewById(R.id.wishTitle);
            layout = itemView.findViewById(R.id.wishLayout);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null){
                        listener.onItemClick(MyWishPostAdapter.ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(MyWishPost item) {
            //thumbnail.setImageBitmap(decodeFile(item.getThumbnail()));
            thumbnail.setBackgroundColor(Color.BLUE);
            title.setText(item.getTitle());
        }
    }
}
