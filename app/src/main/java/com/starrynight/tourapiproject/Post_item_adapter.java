package com.starrynight.tourapiproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Post_item_adapter extends RecyclerView.Adapter<Post_item_adapter.ViewHolder>{
    ArrayList<post_item> items = new ArrayList<post_item>();

    public void addItem(post_item item){
        items.add(item);
    }
    public void setItems(ArrayList<post_item>items){
        this.items = items;
    }
    public post_item getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, post_item item){
        items.set(position,item);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_main, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Post_item_adapter.ViewHolder viewHolder, int position) {
        post_item item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView Button;
        TextView Button2;

        public ViewHolder(View itemView){
            super(itemView);

            Button =itemView.findViewById(R.id.hash__button);
            Button2 = itemView.findViewById(R.id.hash__button2);
        }

        public void setItem(post_item item){
            Button.setText(item.getHash());
            Button2.setText(item.getHash2());
        }
    }
}
