package com.starrynight.tourapiproject.TouristspotPage;

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

    private static List<SearchData.Document>documents;
    OnSearchItemClickListener listener;
    public SearchAdapter(List<SearchData.Document>documents){
        this.documents=documents;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_blog, parent, false);
        return new ViewHolder(itemView, listener);
    }
    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder viewHolder, int position) {
        SearchData.Document data = documents.get(position);
        viewHolder.setItem(data);
    }
    @Override
    public int getItemCount() {
        return documents ==null ? 0 :documents.size(); }

        public void setOnItemClickListener(OnSearchItemClickListener listener) {
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView2;
        TextView textView3;
        ImageView imageView;

        public ViewHolder(View itemView, final OnSearchItemClickListener listener){
            super(itemView);
            textView =itemView.findViewById(R.id.blog_text);
            textView2=itemView.findViewById(R.id.blog_text2);
            textView3=itemView.findViewById(R.id.blog_text3);
            imageView=itemView.findViewById(R.id.blogimage);
            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null){
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });

        }

        public void setItem(SearchData.Document data){
            textView.setText(data.getBlogname());
            textView2.setText(data.getContents());
            textView3.setText(data.getTitle());
            Glide.with(itemView.getContext()).load(data.getThumbnail()).override(100,100).into(imageView);

        }
    }
}
