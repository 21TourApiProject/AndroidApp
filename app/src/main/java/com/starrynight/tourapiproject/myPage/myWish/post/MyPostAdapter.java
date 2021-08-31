package com.starrynight.tourapiproject.myPage.myWish.post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;

import java.util.ArrayList;
import java.util.List;

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.ViewHolder>{
    private static List<MyPost> items;
    OnMyPostItemClickListener listener;
    private Context context;

    public MyPostAdapter(List<MyPost> items, Context context){
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public MyPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.custom_my_wish_post_item, viewGroup, false);

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

    public void setOnMyWishPostItemClickListener(OnMyPostItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView myWishPostImage;
        TextView myWishPostTitle;
        ImageView myWishPostProfileImage;
        TextView myWishPostWriter;

        public ViewHolder(View itemView, final OnMyPostItemClickListener listener) {
            super(itemView);

            myWishPostImage = itemView.findViewById(R.id.myWishPostImage);
            myWishPostTitle = itemView.findViewById(R.id.myWishPostTitle);
            myWishPostProfileImage = itemView.findViewById(R.id.myWishPostProfileImage);
            myWishPostWriter = itemView.findViewById(R.id.myWishPostWriter);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(MyPostAdapter.ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(MyPost item) {
            myWishPostTitle.setText(item.getTitle());
            myWishPostWriter.setText(item.getWriter());
        }
    }
}