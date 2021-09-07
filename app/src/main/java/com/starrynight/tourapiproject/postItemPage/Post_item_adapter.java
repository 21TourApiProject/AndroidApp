package com.starrynight.tourapiproject.postItemPage;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.observationPage.ObservationsiteActivity;
import com.starrynight.tourapiproject.postPage.ImageSliderAdapter;
import com.starrynight.tourapiproject.postPage.ImageSliderItemClickListener;
import com.starrynight.tourapiproject.postPage.PostActivity;
import com.starrynight.tourapiproject.postWritePage.AddHashTagActivity;
import com.starrynight.tourapiproject.starPage.StarActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Post_item_adapter extends RecyclerView.Adapter<Post_item_adapter.ViewHolder>{
    ArrayList<post_item> items = new ArrayList<post_item>();
    OnPostItemClickListener listener;
   private static boolean isWish;
   private static Long userId;
   private static Long postId;

    public void addItem(post_item item){
        items.add(item);
    }
    public void setItems(ArrayList<post_item>items){
        this.items = items;
    }
    public post_item getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, post_item item){
        items.set(position,item);
    }
    @NonNull
    @Override
    public Post_item_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_main, parent, false);
        //앱 내부저장소에서 저장된 유저 아이디 가져오기
        String fileName = "userId";
        try{
            FileInputStream fis = itemView.getContext().openFileInput(fileName);
            String line = new BufferedReader(new InputStreamReader(fis)).readLine();
            userId = Long.parseLong(line);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } System.out.println("userId = " + userId);
        //앱 내부저장소에 저장된 게시글 아이디 가져오기
        String fileName3 = "postId";
        try{
            FileInputStream fis = itemView.getContext().openFileInput(fileName3);
            String line = new BufferedReader(new InputStreamReader(fis)).readLine();
            postId = Long.parseLong(line);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } System.out.println("postId = " + postId);
        return new ViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull Post_item_adapter.ViewHolder viewHolder, int position) {
        post_item item = items.get(position);
        viewHolder.setItem(item);
        Glide.with(viewHolder.itemView.getContext())
                .load(item.getImage2())
                .into(viewHolder.profileimage);
        viewHolder.observation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ObservationsiteActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void  setOnItemClicklistener(OnPostItemClickListener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView observation;
        RecyclerView hashTagRecyclerView;
        TextView title;
        TextView nickname;
        ImageView profileimage;
        ViewPager2 mainslider;
        LinearLayout indicator;
        Button bookmark;

        public ViewHolder(View itemView,final OnPostItemClickListener listener){
            super(itemView);

            observation = itemView.findViewById(R.id.mainobservepoint);
            hashTagRecyclerView = itemView.findViewById(R.id.mainRecyclerView);
            title = itemView.findViewById(R.id.mainpost_title);
            nickname = itemView.findViewById(R.id.nickname);
            profileimage = itemView.findViewById(R.id.mainprofileimage);
            mainslider = itemView.findViewById(R.id.mainslider);
            indicator = itemView.findViewById(R.id.mainindicator);
            bookmark = itemView.findViewById(R.id.mainplus_btn);
            itemView.setClickable(true);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if (listener != null){
//                        listener.onItemClick(Post_item_adapter.ViewHolder.this, v, position);
//                    }
//                }
//            });
        }

        public void setItem(post_item item){
            LinearLayoutManager layoutManager = new LinearLayoutManager(hashTagRecyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            hashTagRecyclerView.setLayoutManager(layoutManager);
            PostHashTagItemAdapter adapter  = new PostHashTagItemAdapter();
            for (int i=0;i<item.hashTags.size();i++)
            { adapter.addItem(new PostHashTagItem(item.getHashTags().get(i)));}
            hashTagRecyclerView.setAdapter(adapter);
            adapter.setOnItemClicklistener(new OnPostHashTagClickListener() {
                @Override
                public void onItemClick(PostHashTagItemAdapter.ViewHolder holder, View view, int position) {
                    Intent intent = new Intent(view.getContext(), AddHashTagActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
            //이미 찜한건지 확인
            Call<Boolean> call0 = com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient.getApiService().isThereMyWish(userId, postId, 2);
            call0.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        if (response.body()){
                            isWish = true;
                            bookmark.setSelected(!bookmark.isSelected());
                        } else{
                            isWish = false;
                        }
                    } else {
                        System.out.println("내 찜 조회하기 실패");
                    }
                }
                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.e("연결실패", t.getMessage());
                }
            });
            bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isWish){ //찜 안한 상태일때
                        Call<Void> call = com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient.getApiService().createMyWish(userId, postId, 2);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    //버튼 디자인 바뀌게 구현하기
                                    isWish = true;
                                    v.setSelected(!v.isSelected());
                                    Toast.makeText(bookmark.getContext(), "나의 여행버킷리스트에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                                } else {
                                    System.out.println("관광지 찜 실패");
                                }
                            }
                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("연결실패", t.getMessage());
                            }
                        });
                    } else{
                        Call<Void> call = com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient.getApiService().deleteMyWish(userId, postId, 2);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    isWish = false;
                                    v.setSelected(!v.isSelected());
                                    Toast.makeText(bookmark.getContext(), "나의 여행버킷리스트에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                } else {
                                    System.out.println("관광지 찜 삭제 실패");
                                }
                            }
                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("연결실패", t.getMessage());
                            }
                        });
                    }
                }
            });
            observation.setText(item.getObservation());
            observation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ObservationsiteActivity.class);
                    intent.putExtra("postId",postId);
                    v.getContext().startActivity(intent);
                }
            });
            title.setText(item.getTitle());
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), PostActivity.class);
                    intent.putExtra("postId",postId);
                    v.getContext().startActivity(intent);
                }
            });
            nickname.setText(item.getNickname());
            mainslider.setOffscreenPageLimit(3);
            ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(mainslider.getContext(), item.getImages());
            mainslider.setAdapter(imageSliderAdapter);

            mainslider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    setCurrentIndicator(position);
                }
            });
            setupIndicators(item.getImages().size());
        }
        private void setupIndicators(int count) {
            ImageView[] indicators = new ImageView[count];
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            params.setMargins(16, 8, 16, 8);

            for (int i = 0; i < indicators.length; i++) {
                indicators[i] = new ImageView(indicator.getContext());
                indicators[i].setImageDrawable(ContextCompat.getDrawable(indicator.getContext(),
                        R.drawable.post__indicator_inactive));
                indicators[i].setLayoutParams(params);
                indicator.addView(indicators[i]);
            }
            setCurrentIndicator(0);
        }
        private void setCurrentIndicator(int position) {
            int childCount = indicator.getChildCount();
            for (int i = 0; i < childCount; i++) {
                ImageView imageView = (ImageView) indicator.getChildAt(i);
                if (i == position) {
                    imageView.setImageDrawable(ContextCompat.getDrawable(
                            indicator.getContext(),
                            R.drawable.post__indicator_active
                    ));
                } else {
                    imageView.setImageDrawable(ContextCompat.getDrawable(
                            indicator.getContext(),
                            R.drawable.post__indicator_inactive
                    ));
                }
            }
        }
    }

}
