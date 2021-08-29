package com.starrynight.tourapiproject.myPage.myWish.obTp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.starrynight.tourapiproject.R;

import java.util.ArrayList;
import java.util.List;

public class MyWishObTpAdapter extends RecyclerView.Adapter<MyWishObTpAdapter.ViewHolder> {
    private static List<MyWishObTp> items;
    OnMyWishObTpItemClickListener listener;
    private Context context;

    public MyWishObTpAdapter(List<MyWishObTp> items, Context context){
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public MyWishObTpAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //viewHolder 처음 만드는 함수
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.custom_my_wish_ob_tp_item, viewGroup, false);

        return new MyWishObTpAdapter.ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyWishObTpAdapter.ViewHolder viewHolder, int position) {
        //viewHolder 재사용 하는 함수
        MyWishObTp item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 :items.size();
    }


    public void addItem(MyWishObTp item) {
        items.add(item);
    }

    public void setItems(ArrayList<MyWishObTp> items) {
        this.items = items;
    }

    public MyWishObTp getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, MyWishObTp item) {
        items.set(position, item);
    }


    public void setOnMyWishObTpItemClickListener(OnMyWishObTpItemClickListener listener) {
        this.listener = listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView obTpImage;
        TextView obTpTitle;
        TextView opTpAddress;
        TextView obTpCat3Name;
        TextView obTpOverviewSim;

        public ViewHolder(View itemView, final OnMyWishObTpItemClickListener listener) {
            super(itemView);

            obTpImage = itemView.findViewById(R.id.myWishObTpImage);
            obTpTitle = itemView.findViewById(R.id.myWishObTpTitle);
            opTpAddress = itemView.findViewById(R.id.myWishObTpAddress);
            obTpCat3Name = itemView.findViewById(R.id.myWishObTpCat3Name);
            obTpOverviewSim = itemView.findViewById(R.id.myWishObTpOverviewSim);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(MyWishObTpAdapter.ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(MyWishObTp item) {
            Glide.with(context).load(item.getThumbnail()).into(obTpImage);
            obTpTitle.setText(item.getTitle());
            opTpAddress.setText(item.getAddress());
            obTpCat3Name.setText(item.getCat3());
            obTpOverviewSim.setText(item.getOverviewSim());
        }

    }
}
