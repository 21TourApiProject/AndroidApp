package com.starrynight.tourapiproject.TouristspotPage;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.starrynight.tourapiproject.R;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<SearchDocument>documents;
    public SearchAdapter(List<SearchDocument>documents){
        this.documents=documents;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_blog, parent, false);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder viewHolder, int position) {
        SearchDocument data = documents.get(position);
        viewHolder.setItem(data);
    }
    @Override
    public int getItemCount() { if (documents != null) {
        return documents.size(); }
    return 0;}
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView2;

        public ViewHolder(View itemView){
            super(itemView);
            textView =itemView.findViewById(R.id.blog_text);
            textView2=itemView.findViewById(R.id.blog_text2);

        }
        public void setItem(SearchDocument data){
            textView.setText(data.getTitle());
            textView2.setText(data.getContents());
        }
    }
}
