package com.starrynight.tourapiproject.postItemPage;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;

import java.util.ArrayList;

public class PostWriteHashTagItem2Adapter extends RecyclerView.Adapter<PostWriteHashTagItem2Adapter.ViewHolder>{
    ArrayList<PostWriteHashTagItem2> items = new ArrayList<PostWriteHashTagItem2>();

    public void addItem(PostWriteHashTagItem2 item){
        items.add(item);
    }
    public void setItems(ArrayList<PostWriteHashTagItem2>items){
        this.items = items;
    }
    public void removeItem(int position){ items.remove(position); }
    public PostWriteHashTagItem2 getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, PostWriteHashTagItem2 item){
        items.set(position,item);
    }

    @NonNull
    @Override
    public PostWriteHashTagItem2Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.hashtags_full2, parent, false);
        return new PostWriteHashTagItem2Adapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostWriteHashTagItem2Adapter.ViewHolder viewHolder, int position){
        PostWriteHashTagItem2 item = items.get(position);
        viewHolder.setItem(item);
        viewHolder.hashTagDelete.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView postHashTagName;
        ImageView hashTagDelete;

        public ViewHolder(View itemView){
            super(itemView);
            postHashTagName =itemView.findViewById(R.id.hashtags_name);
            hashTagDelete = itemView.findViewById(R.id.hashtags_delete);
        }

        @SuppressLint("SetTextI18n")
        public void setItem(PostWriteHashTagItem2 item){
            postHashTagName.setText("#"+item.getHashTagname());
        }
    }
}
