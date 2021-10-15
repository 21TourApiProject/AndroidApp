package com.starrynight.tourapiproject.touristPointPage.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.starrynight.tourapiproject.R;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private static List<SearchData.Document> documents;
    OnSearchItemClickListener listener;

    public SearchAdapter(List<SearchData.Document> documents) {
        this.documents = documents;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.custom_daum_item, parent, false);
        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder viewHolder, int position) {
        SearchData.Document data = documents.get(position);
        viewHolder.setItem(data);
    }

    @Override
    public int getItemCount() {
        return documents == null ? 0 : documents.size();
    }

    public void setOnItemClickListener(OnSearchItemClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView blogTitle;
        TextView blogWriter;
        TextView blogDate;
        TextView blogUrl;
        ImageView blogImage;

        public ViewHolder(View itemView, final OnSearchItemClickListener listener) {
            super(itemView);
            blogTitle = itemView.findViewById(R.id.blogTitle);
            blogWriter = itemView.findViewById(R.id.blogWriter);
            blogDate = itemView.findViewById(R.id.blogDate);
            blogUrl = itemView.findViewById(R.id.blogUrl);
            blogImage = itemView.findViewById(R.id.blogImage);
            blogImage.setClipToOutline(true);
            itemView.setClickable(true);
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

        public void setItem(SearchData.Document data) {
            blogTitle.setText(data.getTitle());
            blogWriter.setText(data.getBlogname());
            blogDate.setText(data.getDatetime().substring(0, 10));
            blogUrl.setText(data.getUrl().substring(8));
            Glide.with(itemView.getContext()).load(data.getThumbnail()).into(blogImage);

        }
    }
}
