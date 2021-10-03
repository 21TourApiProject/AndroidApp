package com.starrynight.tourapiproject.postItemPage;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;

import java.util.ArrayList;

public class PostWriteHashTagItemAdapter extends RecyclerView.Adapter<PostWriteHashTagItemAdapter.ViewHolder> {
    ArrayList<PostWriteHashTagItem> items = new ArrayList<PostWriteHashTagItem>();

    public void addItem(PostWriteHashTagItem item){
        items.add(item);
    }
    public void setItems(ArrayList<PostWriteHashTagItem>items){
        this.items = items;
    }
    public PostWriteHashTagItem getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, PostWriteHashTagItem item){
        items.set(position,item);
    }

    @NonNull
    @Override
    public PostWriteHashTagItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.hashtags_full, parent, false);
        return new PostWriteHashTagItemAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostWriteHashTagItemAdapter.ViewHolder viewHolder, int position){
        PostWriteHashTagItem item = items.get(position);
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

        @SuppressLint("SetTextI18n")
        public void setItem(PostWriteHashTagItem item){
            postHashTagName.setText("#"+item.getHashTagname());
        }
    }
}
