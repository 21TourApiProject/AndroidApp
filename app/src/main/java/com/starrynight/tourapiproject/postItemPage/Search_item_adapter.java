package com.starrynight.tourapiproject.postItemPage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.postWritePage.SearchObservingPointActivity;

import java.util.ArrayList;
/**
* @className : Search_item_adapter
* @description : 관측지 검색 아이템 Adapter 입니다.
* @modification : jinhyeok (2022-08-14) 주석 수정
* @author : 2022-08-14
* @date : jinhyeok
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   jinhyeok      2022-08-14       주석 수정

 */
public class Search_item_adapter extends RecyclerView.Adapter<Search_item_adapter.ViewHolder> {
    ArrayList<Search_item> searchItemArrayList;
    OnSearchItemClickListener listener;


    public Search_item_adapter(ArrayList<Search_item> searchItemArrayList, SearchObservingPointActivity searchObservingPointActivity) {
        this.searchItemArrayList = searchItemArrayList;
    }

    public Search_item getItem(int position) {
        return searchItemArrayList.get(position);
    }

    @NonNull
    @Override
    public Search_item_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.custom_search_item, parent, false);
        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.itemName.setText(searchItemArrayList.get(position).getItemName());
        holder.address.setText(searchItemArrayList.get(position).getAddress());

    }

    public void setOnItemClicklistener(OnSearchItemClickListener listener) {
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return searchItemArrayList.size();
    }

    public void filterList(ArrayList<Search_item> filteredList) {
        searchItemArrayList = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView address;
        ImageView itemimage;

        public ViewHolder(@NonNull View itemView, final OnSearchItemClickListener listener) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName);
            address = itemView.findViewById(R.id.address);
            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(Search_item_adapter.ViewHolder.this, v, position);
                    }
                }
            });

        }
    }
}
