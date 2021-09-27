package com.starrynight.tourapiproject.postItemPage;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starrynight.tourapiproject.R;

import java.util.ArrayList;

public class Post_point_item_Adapter extends RecyclerView.Adapter<Post_point_item_Adapter.ViewHolder> {
    ArrayList<post_point_item> items = new ArrayList<post_point_item>();
    private Intent intent;
    private ArrayList<post_point_item> itemList;
    OnPostPointItemClickListener listener;

    public void addItem(post_point_item item){ items.add(item); }
    public void setItems(ArrayList<post_point_item> items){ this.items = items; }
    public post_point_item getItem(int position){ return items.get(position); }

    public void setItem(int position, post_point_item item){
        items.set(position,item);
    }
    @NonNull
    @Override
    public Post_point_item_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_spot, parent, false);
        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull Post_point_item_Adapter.ViewHolder viewHolder, int position) {
        post_point_item item = items.get(position);
        viewHolder.setItem(item);
        viewHolder.imageView.setBackground(ContextCompat.getDrawable(viewHolder.imageView.getContext(), R.drawable.myitem_image));
        viewHolder.imageView.setClipToOutline(true);
        Glide.with(viewHolder.itemView.getContext())
                .load(item.getTourimage())
                .into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() { return items.size(); }

    public void  setOnItemClicklistener(OnPostPointItemClickListener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView, final OnPostPointItemClickListener listener){
            super(itemView);
            textView =itemView.findViewById(R.id.PostText);
            imageView = itemView.findViewById(R.id.postimage);
            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null){
                        listener.onItemClick(Post_point_item_Adapter.ViewHolder.this, view, position);
                    }
                }
            });

        }
        public void setItem(post_point_item item){
            textView.setText(item.getTourname());
        }
    }
}

