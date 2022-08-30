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

/**
* @className : RecyclerHashTagAdapter.java
* @description : 관측지 해쉬태그 recycler adapter
* @modification : gyul chyoung (2022-08-30) 주석추가
* @author : 2022-08-30
* @date : gyul chyoung
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   gyul chyoung       2022-08-30       주석추가
 */

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hashtags_full, parent, false);
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
