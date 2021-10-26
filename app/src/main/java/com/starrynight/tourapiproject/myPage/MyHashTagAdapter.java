package com.starrynight.tourapiproject.myPage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;

import java.util.ArrayList;
import java.util.List;

public class MyHashTagAdapter extends RecyclerView.Adapter<MyHashTagAdapter.ViewHolder> {
    private static List<String> items;

    public MyHashTagAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public MyHashTagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //viewHolder 처음 만드는 함수
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.hashtags_search, viewGroup, false);

        return new MyHashTagAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHashTagAdapter.ViewHolder viewHolder, int position) {
        //viewHolder 재사용 하는 함수
        String item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }


    public void addItem(String item) {
        items.add(item);
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    public String getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, String item) {
        items.set(position, item);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView hashTagName;

        public ViewHolder(View itemView) {
            super(itemView);
            hashTagName = itemView.findViewById(R.id.recycler_hashTagName);
        }

        public void setItem(String item) {
            hashTagName.setText("#" + item);
        }
    }

}