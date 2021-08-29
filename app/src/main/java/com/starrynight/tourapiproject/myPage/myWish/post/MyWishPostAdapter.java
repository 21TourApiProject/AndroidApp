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

public class MyWishPostAdapter extends RecyclerView.Adapter<MyWishPostAdapter.ViewHolder>{
    private static List<MyWishPost> items;
    OnMyWishPostItemClickListener listener;
    private Context context;

    public MyWishPostAdapter(List<MyWishPost> items, Context context){
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public MyWishPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.custom_my_wish_post_item, viewGroup, false);

        return new MyWishPostAdapter.ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyWishPostAdapter.ViewHolder viewHolder, int position) {
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

    public void setOnMyWishPostItemClickListener(OnMyWishPostItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView myWishPostImage;
        TextView myWishPostTitle;
        ImageView myWishPostProfileImage;
        TextView myWishPostWriter;

        public ViewHolder(View itemView, final OnMyWishPostItemClickListener listener) {
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
                        listener.onItemClick(MyWishPostAdapter.ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(MyWishPost item) {
            myWishPostTitle.setText(item.getTitle());
            myWishPostWriter.setText(item.getWriter());
        }
    }
}