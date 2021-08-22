package com.starrynight.tourapiproject.postItemPage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;

import java.util.ArrayList;

public class PostHashTagItemAdapter extends RecyclerView.Adapter<PostHashTagItemAdapter.ViewHolder> {
    ArrayList<PostHashTagItem>items = new ArrayList<PostHashTagItem>();

    public void addItem(PostHashTagItem item){
        items.add(item);
    }
    public void setItems(ArrayList<PostHashTagItem>items){
        this.items = items;
    }
    public PostHashTagItem getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, PostHashTagItem item){
        items.set(position,item);
    }
    @NonNull
    @Override
    public PostHashTagItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.hashtags, parent, false);
        return new ViewHolder (itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull PostHashTagItemAdapter.ViewHolder viewHolder, int position) {
        PostHashTagItem item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView postHashTagName;

        public ViewHolder(View itemView){
            super(itemView);
            postHashTagName =itemView.findViewById(R.id.recycler_hashTagName);
        }

        public void setItem(PostHashTagItem item){
            postHashTagName.setText(item.getHashTagname());
        }
    }
}
