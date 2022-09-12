package com.starrynight.tourapiproject.touristPointPage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @className : HashTagAdapter.java
 * @description : 관광지 해시태그 Adapter 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
public class HashTagAdapter extends RecyclerView.Adapter<HashTagAdapter.ViewHolder> {
    private static List<String> items;

    public HashTagAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public HashTagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //viewHolder 처음 만드는 함수
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.hashtags_full, viewGroup, false);

        return new HashTagAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HashTagAdapter.ViewHolder viewHolder, int position) {
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
