package com.starrynight.tourapiproject.searchPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.searchPage.searchPageRetrofit.SearchParams1;
import com.starrynight.tourapiproject.searchPage.searchPageRetrofit.SearchParams2;
import com.starrynight.tourapiproject.touristPointPage.HashTagAdapter2;

import java.util.ArrayList;
import java.util.List;

public class SearchResultAdapter2 extends RecyclerView.Adapter<SearchResultAdapter2.ViewHolder> {
    private static List<SearchParams2> items;
    OnSearchResultItemClickListener2 listener;
    private Context context;

    public SearchResultAdapter2(List<SearchParams2> items, Context context){
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchResultAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //viewHolder 처음 만드는 함수
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.custom_my_wish_ob_tp_item, viewGroup, false);

        return new SearchResultAdapter2.ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultAdapter2.ViewHolder viewHolder, int position) {
        //viewHolder 재사용 하는 함수
        SearchParams2 item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 :items.size();
    }


    public void addItem(SearchParams2 item) {
        items.add(item);
    }

    public void setItems(ArrayList<SearchParams2> items) {
        this.items = items;
    }

    public SearchParams2 getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, SearchParams2 item) {
        items.set(position, item);
    }


    public void setOnSearchResultItemClickListener2(OnSearchResultItemClickListener2 listener) {
        this.listener = listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView obTpImage;
        TextView obTpTitle;
        TextView opTpAddress;
        TextView obTpCat3Name;
        TextView obTpOverviewSim;
        RecyclerView obTpHashTag;

        public ViewHolder(View itemView, final OnSearchResultItemClickListener2 listener) {
            super(itemView);

            obTpImage = itemView.findViewById(R.id.myWishObTpImage);
            obTpTitle = itemView.findViewById(R.id.myWishObTpTitle);
            opTpAddress = itemView.findViewById(R.id.myWishObTpAddress);
            obTpCat3Name = itemView.findViewById(R.id.myWishObTpCat3Name);
            obTpOverviewSim = itemView.findViewById(R.id.myWishObTpOverviewSim);
            obTpHashTag = itemView.findViewById(R.id.myWishObTpHashTag);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(SearchResultAdapter2.ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(SearchParams2 item) {
            if(item.getThumbnail() != null)
                Glide.with(context).load(item.getThumbnail()).into(obTpImage);
            obTpTitle.setText(item.getTitle());
            opTpAddress.setText(item.getAddress());
            obTpCat3Name.setText(item.getContentType());
            obTpOverviewSim.setText(item.getIntro());
            obTpHashTag.setAdapter(new HashTagAdapter2(item.getHashTagNames()));
            obTpHashTag.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            obTpHashTag.setHasFixedSize(true);
        }

    }
}
