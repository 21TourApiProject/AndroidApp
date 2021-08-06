package com.starrynight.tourapiproject.postWritePage;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.starrynight.tourapiproject.R;

import java.util.ArrayList;

public class SelectImageAdapter extends RecyclerView.Adapter<SelectImageAdapter.ViewHolder>{
    ArrayList<SelectImage> items = new ArrayList<SelectImage>();
    OnSelectImageItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.custom_post_write_image, viewGroup, false);

        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        SelectImage item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void addItem(SelectImage item) {
        items.add(item);
    }

    public void removeItem(int position){
        items.remove(position);
    }

    public void setItems(ArrayList<SelectImage> items) {
        this.items = items;
    }

    public SelectImage getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, SelectImage item) {
        items.set(position, item);
    }

    public void setOnSelectImageItemClickListener(OnSelectImageItemClickListener listener){
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        Button remove;

        public ViewHolder(View itemView, final OnSelectImageItemClickListener listener) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            remove = itemView.findViewById(R.id.remove);

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null){
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(SelectImage item) {
            image.setImageBitmap(item.getImg());
            //image.setId(item.getId()); 이미지 id는 필요없을듯
            remove.setId(item.getId());

        }
    }
}
