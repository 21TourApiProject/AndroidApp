package com.starrynight.tourapiproject.postPage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;

import java.util.ArrayList;
/**
* @className : RelatePostAdapter
* @description : 관련 게시물 adapter입니다.
* @modification : jinhyeok (2022-08-12) 주석 수정
* @author : 2022-08-12
* @date : jinhyeok
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   jinhyeok      2022-08-12       주석 수정

 */
public class RelatePostAdapter extends RecyclerView.Adapter<RelatePostAdapter.ViewHolder> {
    ArrayList<RelatePost> items = new ArrayList<RelatePost>();
    OnRelatePostItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.custom_relate_post, viewGroup, false);

        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        RelatePost item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void addItem(RelatePost item) {
        items.add(item);
    }

    public void setItems(ArrayList<RelatePost> items) {
        this.items = items;
    }

    public RelatePost getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, RelatePost item) {
        items.set(position, item);
    }


    public void setOnPersonItemClickListener(OnRelatePostItemClickListener listener) {
        this.listener = listener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView postTitle;
        ImageView postThumbnail;

        public ViewHolder(View itemView, final OnRelatePostItemClickListener listener) {
            super(itemView);

            postTitle = itemView.findViewById(R.id.postTitle);
            postThumbnail = itemView.findViewById(R.id.postThumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(RelatePost item) {
            postTitle.setText(item.getTitle());
            postThumbnail.setImageBitmap(item.getImg());
        }
    }

}
