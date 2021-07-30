package com.starrynight.tourapiproject.signUpPage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;

import java.util.ArrayList;

public class HashTagAdapter extends RecyclerView.Adapter<HashTagAdapter.ViewHolder> {
    ArrayList<HashTag> items = new ArrayList<HashTag>();
    OnHashTagItemClickListener listener;

    @NonNull
    @Override
    public HashTagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.custom_sign_up_hashtag, viewGroup, false);

        return new HashTagAdapter.ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull HashTagAdapter.ViewHolder viewHolder, int position) {
        HashTag item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void addItem(HashTag item) {
        items.add(item);
    }

    public void setItems(ArrayList<HashTag> items) {
        this.items = items;
    }

    public HashTag getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, HashTag item) {
        items.set(position, item);
    }


    public void setOnHashTagItemClickListener(OnHashTagItemClickListener listener) {
        this.listener = listener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView hashTagName;

        public ViewHolder(View itemView, final OnHashTagItemClickListener listener) {
            super(itemView);

            hashTagName = itemView.findViewById(R.id.hashTagName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(HashTagAdapter.ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(HashTag item) {
            hashTagName.setText(item.getName());
            hashTagName.setId(item.getId());
        }
    }

}

