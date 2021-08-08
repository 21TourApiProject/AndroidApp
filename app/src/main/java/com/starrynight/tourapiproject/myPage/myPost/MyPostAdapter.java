package com.starrynight.tourapiproject.myPage.myPost;

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

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.ViewHolder>{
    ArrayList<MyPost> items = new ArrayList<MyPost>();
    OnMyPostItemClickListener listener;

    @NonNull
    @Override
    public MyPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.custom_my_item, viewGroup, false);

        return new MyPostAdapter.ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostAdapter.ViewHolder viewHolder, int position) {
        MyPost item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void addItem(MyPost item) {
        items.add(item);
    }

    public void setItems(ArrayList<MyPost> items) {
        this.items = items;
    }

    public MyPost getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, MyPost item) {
        items.set(position, item);
    }

    public void setOnMyPostItemClickListener(OnMyPostItemClickListener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title;
        LinearLayout layout;

        public ViewHolder(View itemView, final OnMyPostItemClickListener listener) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.myThumbnail);
            title = itemView.findViewById(R.id.myTitle);
            layout = itemView.findViewById(R.id.myLayout);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null){
                        listener.onItemClick(MyPostAdapter.ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(MyPost item) {
            //thumbnail.setImageBitmap(decodeFile(item.getThumbnail()));
            thumbnail.setBackgroundColor(Color.BLUE);
            title.setText(item.getTitle());
        }
    }
}