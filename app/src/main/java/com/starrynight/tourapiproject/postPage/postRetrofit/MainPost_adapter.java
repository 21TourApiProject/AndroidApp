package com.starrynight.tourapiproject.postPage.postRetrofit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.SearchFragment;
import com.starrynight.tourapiproject.postItemPage.OnPostHashTagClickListener;
import com.starrynight.tourapiproject.postItemPage.PostHashTagItem;
import com.starrynight.tourapiproject.postItemPage.PostHashTagItemAdapter;
import com.starrynight.tourapiproject.postPage.ImageSliderAdapter;
import com.starrynight.tourapiproject.postPage.PostActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPost_adapter extends RecyclerView.Adapter<MainPost_adapter.ViewHolder>{
    List<MainPost> items = new ArrayList<MainPost>();
    OnMainPostClickListener listener;
   private static boolean isWish;
   private static Long userId;
   private static Long postId;
    private static Context context;

    public MainPost_adapter(List<MainPost> items, Context context){
        this.items = items;
        this.context = context;
    }

    public void addItem(MainPost item){
        items.add(item);
    }
    public void setItems(ArrayList<MainPost>items){
        this.items = items;
    }
    public MainPost getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, MainPost item){
        items.set(position,item);
    }
    @NonNull
    @Override
    public MainPost_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        }

        return new ViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MainPost_adapter.ViewHolder viewHolder, int position) {
        MainPost item = items.get(position);
        viewHolder.setItem(item);
        String fileName = item.getProfileImage();
        fileName = fileName.substring(1, fileName.length() -1);
        Glide.with(viewHolder.itemView.getContext())
                .load("https://starry-night.s3.ap-northeast-2.amazonaws.com/profileImage/"+fileName)
                .into(viewHolder.profileimage);

        LinearLayoutManager layoutManager = new LinearLayoutManager(viewHolder.hashTagRecyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        viewHolder.hashTagRecyclerView.setLayoutManager(layoutManager);
        PostHashTagItemAdapter adapter  = new PostHashTagItemAdapter();
        if (!item.getMainObservation().equals("나만의 관측지")){
        adapter.addItem(new PostHashTagItem(item.getMainObservation(),null, item.getObservationId()));
        }else{adapter.addItem(new PostHashTagItem(item.getOptionObservation(),null,null));}
        if (item.getHashTags()!=null){
            for (int i=0;i<item.getHashTags().size();i++){
                adapter.addItem(new PostHashTagItem(item.getHashTags().get(i),null,null));
            if (i==2){break;}}
        }else{
            adapter.addItem(new PostHashTagItem(item.getOptionHashTag(),null,null));
            if (item.getOptionHashTag2()!=null){adapter.addItem(new PostHashTagItem(item.getOptionHashTag2(),null,null));}
            if (item.getOptionHashTag3()!=null){adapter.addItem(new PostHashTagItem(item.getOptionHashTag3(),null,null));}
        }
        viewHolder.hashTagRecyclerView.setAdapter(adapter);
        viewHolder.hashTagRecyclerView.addItemDecoration(new ViewHolder.RecyclerViewDecoration(20));
//        adapter.setOnItemClicklistener(new OnPostHashTagClickListener() {
//            @Override
//            public void onItemClick(PostHashTagItemAdapter.ViewHolder holder, View view, int position) {
//                Bundle bundle = new Bundle();
//                Fragment searchFragment = new SearchFragment();
//                FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.main_view, searchFragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void  setOnItemClicklistener(OnMainPostClickListener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerView hashTagRecyclerView;
        TextView title;
        TextView nickname;
        ImageView profileimage;
        ViewPager2 mainslider;
        LinearLayout indicator;
        Button bookmark;

        public ViewHolder(View itemView,final OnMainPostClickListener listener){
            super(itemView);

            hashTagRecyclerView = itemView.findViewById(R.id.mainRecyclerView);
            title = itemView.findViewById(R.id.mainpost_title);
            nickname = itemView.findViewById(R.id.nickname);
            profileimage = itemView.findViewById(R.id.mainprofileimage);
            mainslider = itemView.findViewById(R.id.mainslider);
            indicator = itemView.findViewById(R.id.mainindicator);
            bookmark = itemView.findViewById(R.id.mainplus_btn);
            profileimage.setBackground(new ShapeDrawable(new OvalShape()));
            profileimage.setClipToOutline(true);
            itemView.setClickable(true);
        }

        public void setItem(MainPost item){

            //이미 찜한건지 확인
            Call<Boolean> call0 = com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient.getApiService().isThereMyWish(userId,item.getPostId(), 2);
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
                        Log.d("isWish","내 찜 조회하기 실패");
                    }
                }
                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.d("isWish","연결실패");
                }
            });
            bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isWish){ //찜 안한 상태일때
                        Call<Void> call = com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient.getApiService().createMyWish(userId, item.getPostId(), 2);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    //버튼 디자인 바뀌게 구현하기
                                    isWish = true;
                                    v.setSelected(!v.isSelected());
                                    Toast.makeText(bookmark.getContext(), "나의 여행버킷리스트에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d("myWish","관광지 찜 실패");
                                }
                            }
                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.d("myWish","인터넷 오류");
                            }
                        });
                    } else{
                        Call<Void> call = com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient.getApiService().deleteMyWish(userId, item.getPostId(), 2);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    isWish = false;
                                    v.setSelected(!v.isSelected());
                                    Toast.makeText(bookmark.getContext(), "나의 여행버킷리스트에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d("myWish","관광지 찜 삭제 실패");
                                }
                            }
                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.d("my wish","인터넷 오류");
                            }
                        });
                    }
                }
            });
            title.setText(item.getMainTitle());
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), PostActivity.class);
                    intent.putExtra("postId",item.getPostId());
                    v.getContext().startActivity(intent);
                }
            });
            nickname.setText(item.getMainNickName());
            mainslider.setOffscreenPageLimit(3);

//            ViewGroup.LayoutParams params = mainslider.getLayoutParams();
//            params.width=ViewGroup.LayoutParams.MATCH_PARENT;
//            params.height= params.width;
//            mainslider.requestLayout();
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
                            R.drawable.mainpage_postimage
                    ));
                } else {
                    imageView.setImageDrawable(ContextCompat.getDrawable(
                            indicator.getContext(),
                            R.drawable.mainpage_postimage_non
                    ));
                }
            }
        }
        public static class RecyclerViewDecoration extends RecyclerView.ItemDecoration {

            private final int divHeight;

            public RecyclerViewDecoration(int divHeight)
            {
                this.divHeight = divHeight;
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
            {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.right = divHeight;
            }
        }
    }

}
