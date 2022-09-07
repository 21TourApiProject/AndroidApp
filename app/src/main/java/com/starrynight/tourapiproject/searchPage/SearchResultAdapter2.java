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
import com.starrynight.tourapiproject.touristPointPage.HashTagAdapter2;

import java.util.ArrayList;
import java.util.List;

/**
 * @className : SearchResultAdapter2.java
 * @description : 관광지, 관측지 검색 결과 Adapter 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
public class SearchResultAdapter2 extends RecyclerView.Adapter<SearchResultAdapter2.ViewHolder> {
    private List<SearchParams1> items;
    OnSearchResultItemClickListener2 listener;
    private Context context;

    public SearchResultAdapter2(List<SearchParams1> items, Context context) {
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
        SearchParams1 item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }


    public void addItem(SearchParams1 item) {
        items.add(item);
    }

    public void setItems(ArrayList<SearchParams1> items) {
        this.items = items;
    }

    public SearchParams1 getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, SearchParams1 item) {
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

        public void setItem(SearchParams1 item) {
            if (item.getThumbnail() != null) {
                String imageName = item.getThumbnail();
                if (imageName.startsWith("http://") || imageName.startsWith("https://"))
                    Glide.with(context).load(imageName).into(obTpImage);
                else {
                    imageName = imageName.substring(1, imageName.length() - 1);
                    Glide.with(context).load("https://starry-night.s3.ap-northeast-2.amazonaws.com/observationImage/" + imageName).into(obTpImage);

                }
            }

            obTpTitle.setText(item.getTitle());

            //주소를 두단어까지 줄임
            String address = item.getAddress();
            int i = address.indexOf(' ');
            if (i != -1) {
                int j = address.indexOf(' ', i + 1);
                if (j != -1) {
                    opTpAddress.setText(item.getAddress().substring(0, j));
                } else {
                    opTpAddress.setText(item.getAddress());
                }
            } else {
                opTpAddress.setText(item.getAddress());
            }

            obTpCat3Name.setText(item.getContentType());
            obTpOverviewSim.setText(item.getIntro());
            obTpHashTag.setAdapter(new HashTagAdapter2(item.getHashTagNames()));
            obTpHashTag.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        }

    }
}
