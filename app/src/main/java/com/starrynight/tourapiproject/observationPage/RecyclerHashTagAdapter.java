package com.starrynight.tourapiproject.observationPage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerHashTagAdapter extends RecyclerView.Adapter<RecyclerHashTagAdapter.ViewHolder> {

    private ArrayList<RecyclerHashTagItem> listHashtags = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView hashtag_name;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            hashtag_name = (TextView) itemView.findViewById(R.id.recycler_hashTagName);
        }

        void onBind(RecyclerHashTagItem item) {
            hashtag_name.setText("#" + item.getHashtagName());
        }
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hashtags_empty, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.onBind(listHashtags.get(position));
    }

    @Override
    public int getItemCount() {
        return listHashtags.size();
    }

    public void addItem(RecyclerHashTagItem item) {
        listHashtags.add(item);
    }
}
