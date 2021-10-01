package com.starrynight.tourapiproject.observationPage;

import android.media.Image;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerFeeAdapter extends RecyclerView.Adapter<RecyclerFeeAdapter.ViewHolder> {

    private ArrayList<RecyclerFeeItem> listFee = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fee_name;
        TextView entrance_fee;
        ImageView line;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            fee_name = (TextView) itemView.findViewById(R.id.feename_txt);
            entrance_fee = (TextView) itemView.findViewById(R.id.entrancefee_txt);
            line = (ImageView) itemView.findViewById(R.id.fee_line);
        }

        void onBind(RecyclerFeeItem item) {
            fee_name.setText(Html.fromHtml(item.getFeeName(), HtmlCompat.FROM_HTML_MODE_LEGACY));
            if(item.getEntranceFee()==null)
                line.setVisibility(View.GONE);
            entrance_fee.setText(item.getEntranceFee());
        }
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.observe_fee, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.onBind(listFee.get(position));
    }

    @Override
    public int getItemCount() {
        return listFee.size();
    }

    void addItem(RecyclerFeeItem item) {
        listFee.add(item);
    }
}

