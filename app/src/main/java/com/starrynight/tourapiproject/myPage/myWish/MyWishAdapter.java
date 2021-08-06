package com.starrynight.tourapiproject.myPage.myWish;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;

import java.util.ArrayList;

import static android.graphics.BitmapFactory.decodeFile;

public class MyWishAdapter extends RecyclerView.Adapter<MyWishAdapter.ViewHolder>{
    ArrayList<MyWish> items = new ArrayList<>();
    OnMyWishItemClickListener listener;

    @NonNull
    @Override
    public MyWishAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.custom_my_wish, viewGroup, false);

        return new MyWishAdapter.ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyWishAdapter.ViewHolder viewHolder, int position) {
        MyWish item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void addItem(MyWish item) {
        items.add(item);
    }

    public void setItems(ArrayList<MyWish> items) {
        this.items = items;
    }

    public MyWish getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, MyWish item) {
        items.set(position, item);
    }

    public void setOnMyWishItemClickListener(OnMyWishItemClickListener listener){
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title;
        LinearLayout layout;

        public ViewHolder(View itemView, final OnMyWishItemClickListener listener) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.wishThumbnail);
            title = itemView.findViewById(R.id.wishTitle);
            layout = itemView.findViewById(R.id.wishLayout);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null){
                        listener.onItemClick(MyWishAdapter.ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(MyWish item) {
            thumbnail.setImageBitmap(decodeFile(item.getThumbnail()));
            title.setText(item.getTitle());
        }
    }
}
