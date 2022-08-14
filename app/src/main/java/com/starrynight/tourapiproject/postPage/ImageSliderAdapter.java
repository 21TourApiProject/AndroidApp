package com.starrynight.tourapiproject.postPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.starrynight.tourapiproject.R;

import java.util.ArrayList;
/**
* @className : ImageSliderAdapter
* @description : 게시물 이미지 슬라이드 adpater 입니다.
* @modification : jinhyeok (2022-08-12) 주석 수정
* @author : 2022-08-12
* @date : jinhyeok
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   jinhyeok      2022-08-12       주석 수정

 */
public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<String> sliderImage;
    ImageSliderItemClickListener listener;

    public ImageSliderAdapter(Context context, ArrayList<String> sliderImage) {
        this.context = context;
        this.sliderImage = sliderImage;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_main_image, parent, false);
        return new MyViewHolder(view, listener);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindSliderImage(sliderImage.get(position));
    }

    @Override
    public int getItemCount() {
        return sliderImage.size();
    }

    public void OnItemClicklistener(ImageSliderItemClickListener listener) {
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;

        public MyViewHolder(@NonNull View itemView, final ImageSliderItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.custom_main_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(ImageSliderAdapter.MyViewHolder.this, v, position);
                    }
                }
            });
        }

        public void bindSliderImage(String imageURL) {
            Glide.with(context)
                    .load(imageURL)
                    .into(mImageView);

        }
    }
}