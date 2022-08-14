package com.starrynight.tourapiproject.postItemPage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.observationPage.ObservationsiteActivity;

import java.util.ArrayList;
/**
* @className : PostHashTagItemAdapter
* @description : 게시물 해시태그, 관측지 아이템 adpater 입니다.
* @modification : jinhyeok (2022-08-14) 주석 수정
* @author : 2022-08-14
* @date : jinhyeok
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   jinhyeok      2022-08-14       주석 수정

 */
public class PostHashTagItemAdapter extends RecyclerView.Adapter<PostHashTagItemAdapter.ViewHolder> {
    ArrayList<PostHashTagItem> items = new ArrayList<PostHashTagItem>();
    OnPostHashTagClickListener listener;

    public void addItem(PostHashTagItem item) {
        items.add(item);
    }

    public void setItems(ArrayList<PostHashTagItem> items) {
        this.items = items;
    }

    public PostHashTagItem getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, PostHashTagItem item) {
        items.set(position, item);
    }

    @NonNull
    @Override
    public PostHashTagItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.hashtags_full, parent, false);
        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHashTagItemAdapter.ViewHolder viewHolder, int position) {
        if (position != 0) {
            PostHashTagItem item = items.get(position);
            viewHolder.setItem(item);
        } else {
            PostHashTagItem item0 = items.get(0);
            viewHolder.setItem(item0);
            viewHolder.hashTagPin.setVisibility(View.VISIBLE);
            viewHolder.postHashTagName.setText(item0.getHashTagname());
            viewHolder.postHashTagName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item0.getObservationId() != null) {
                        Intent intent1 = new Intent(viewHolder.itemView.getContext(), ObservationsiteActivity.class);
                        intent1.putExtra("observationId", item0.ObservationId);
                        viewHolder.itemView.getContext().startActivity(intent1);
                    }
                }
            });
        }
    }

    public void setOnItemClicklistener(OnPostHashTagClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView postHashTagName;
        ImageView hashTagPin;
        Long observationId;
        Long hashTagId;

        public ViewHolder(View itemView, final OnPostHashTagClickListener listener) {
            super(itemView);
            postHashTagName = itemView.findViewById(R.id.recycler_hashTagName);
            hashTagPin = itemView.findViewById(R.id.recycler_hashTag_pin);

            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(PostHashTagItemAdapter.ViewHolder.this, v, position);
                    }
                }
            });
        }

        @SuppressLint("SetTextI18n")
        public void setItem(PostHashTagItem item) {
            postHashTagName.setText("#" + item.getHashTagname());
            observationId = item.getObservationId();
            hashTagId = item.getHashTagId();
        }
    }
}
