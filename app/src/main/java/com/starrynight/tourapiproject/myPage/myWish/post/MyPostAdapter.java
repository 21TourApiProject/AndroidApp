package com.starrynight.tourapiproject.myPage.myWish.post;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
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
import com.starrynight.tourapiproject.touristPointPage.HashTagAdapter2;

import java.util.ArrayList;
import java.util.List;
/**
* @className : MyPostAdapter
* @description : 찜목록 내 게시물, 내가 쓴 게시물, 관측지 관련 게시물 아이템 adpater 입니다.
* @modification : jinhyeok (2022-08-16) 주석 수정
* @author : 2022-08-16
* @date : jinhyeok
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   jinhyeok      2022-08-16       주석 수정

 */
public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.ViewHolder> {
    private static List<MyPost> items;
    OnMyPostItemClickListener listener;
    private Context context;

    public MyPostAdapter(List<MyPost> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public MyPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.custom_my_wish_post_item, viewGroup, false);

        return new MyPostAdapter.ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostAdapter.ViewHolder viewHolder, int position) {
        MyPost item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void addItem(MyPost item) {
        items.add(item);
    }

    public void setItems(ArrayList<MyPost> items) {
        this.items = items;
    }

    public MyPost getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, MyPost item) {
        items.set(position, item);
    }

    public void setOnMyWishPostItemClickListener(OnMyPostItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView myWishPostImage;
        TextView myWishPostTitle;
        ImageView myWishPostProfileImage;
        TextView myWishPostWriter;
        RecyclerView myWishPostHashTag;

        public ViewHolder(View itemView, final OnMyPostItemClickListener listener) {
            super(itemView);

            myWishPostImage = itemView.findViewById(R.id.myWishPostImage);
            myWishPostTitle = itemView.findViewById(R.id.myWishPostTitle);
            myWishPostProfileImage = itemView.findViewById(R.id.myWishPostProfileImage);
            myWishPostWriter = itemView.findViewById(R.id.myWishPostWriter);
            myWishPostHashTag = itemView.findViewById(R.id.myWishPostHashTag);

            myWishPostProfileImage.setBackground(new ShapeDrawable(new OvalShape()));
            myWishPostProfileImage.setClipToOutline(true);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(MyPostAdapter.ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(MyPost item) {
            //게시물 썸네일 가져오기
            if (item.getThumbnail() != null) {
                Glide.with(context).load("https://starry-night.s3.ap-northeast-2.amazonaws.com/postImage/" + item.getThumbnail()).into(myWishPostImage);
                myWishPostImage.setClipToOutline(true);
            }
            //게시물 작성자 프로필 이미지 가져오기
            if (item.getProfileImage() != null) {
                String imageName = item.getProfileImage();
                if (item.getProfileImage().startsWith("http://") || item.getProfileImage().startsWith("https://")) {
                    Glide.with(context).load(imageName).into(myWishPostProfileImage);
                } else {
                    imageName = imageName.substring(1, imageName.length() - 1);
                    Glide.with(context).load("https://starry-night.s3.ap-northeast-2.amazonaws.com/profileImage/" + imageName).into(myWishPostProfileImage);
                }
            }

            myWishPostTitle.setText(item.getTitle());
            myWishPostWriter.setText(item.getNickName());
            myWishPostHashTag.setAdapter(new HashTagAdapter2(item.getHashTagNames()));
            myWishPostHashTag.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        }
    }
}