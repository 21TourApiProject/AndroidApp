package com.starrynight.tourapiproject.postItemPage;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.starrynight.tourapiproject.R;

import java.util.ArrayList;

public class Post_point_item_Adapter extends RecyclerView.Adapter<Post_point_item_Adapter.ViewHolder> {
    ArrayList<post_point_item> items = new ArrayList<post_point_item>();

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
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Post_point_item_Adapter.ViewHolder viewHolder, int position) {
        post_point_item item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public ViewHolder(View itemView){
            super(itemView);

            textView =itemView.findViewById(R.id.PostText);
        }

        public void setItem(post_point_item item){ textView.setText(item.getTourname()); }
    }
}

