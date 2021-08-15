package com.starrynight.tourapiproject.postItemPage;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starrynight.tourapiproject.R;

import java.util.ArrayList;

public class Post_item_adapter extends RecyclerView.Adapter<Post_item_adapter.ViewHolder>{
    ArrayList<post_item> items = new ArrayList<post_item>();
    OnPostItemClickListener listener;

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
    public Post_item_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_main, parent, false);
        return new ViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull Post_item_adapter.ViewHolder viewHolder, int position) {
        post_item item = items.get(position);
        viewHolder.setItem(item);
        Glide.with(viewHolder.itemView.getContext())
                .load(item.getImage())
                .into(viewHolder.mainimage);
        Glide.with(viewHolder.itemView.getContext())
                .load(item.getImage2())
                .into(viewHolder.profileimage);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void  setOnItemClicklistener(OnPostItemClickListener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView Button;
        TextView Button2;
        TextView title;
        TextView nickname;
        ImageView mainimage;
        ImageView profileimage;

        public ViewHolder(View itemView,final OnPostItemClickListener listener){
            super(itemView);

            Button =itemView.findViewById(R.id.hash__button);
            Button2 = itemView.findViewById(R.id.hash__button2);
            title = itemView.findViewById(R.id.mainpost_title);
            nickname = itemView.findViewById(R.id.nickname);
            mainimage =itemView.findViewById(R.id.layout_image);
            profileimage = itemView.findViewById(R.id.mainprofileimage);
            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null){
                        listener.onItemClick(Post_item_adapter.ViewHolder.this, v, position);
                    }
                }
            });
        }

        public void setItem(post_item item){
            Button.setText(item.getHash());
            Button2.setText(item.getHash2());
            title.setText(item.getTitle());
            nickname.setText(item.getNickname());
        }
    }
}
