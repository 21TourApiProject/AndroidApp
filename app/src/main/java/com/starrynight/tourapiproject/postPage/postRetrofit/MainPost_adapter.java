package com.starrynight.tourapiproject.postPage.postRetrofit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.mapPage.Activities;
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
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
* @className : MainPost_adapter
* @description : 메인 페이지 게시물 adapter
* @modification : 2022-09-02 (jinhyeok) 주석 수정
* @author : jinhyeok
* @date : 2022-09-02
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   2022-09-02      jinhyeok       주석 수정

 */
public class MainPost_adapter extends RecyclerView.Adapter<MainPost_adapter.ViewHolder> {
    List<MainPost> items = new ArrayList<MainPost>();
    OnMainPostClickListener listener;
    private static Long userId;
    private static Context context;
    String beforeImage;
    ArrayList<Integer> area = new ArrayList<Integer>(Collections.nCopies(17, 0));

    public MainPost_adapter(List<MainPost> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public void addItem(MainPost item) {
        items.add(item);
    }

    public void setItems(ArrayList<MainPost> items) {
        this.items = items;
    }

    public MainPost getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, MainPost item) {
        items.set(position, item);
    }

    @NonNull
    @Override
    public MainPost_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_main, parent, false);
        //앱 내부저장소에서 저장된 유저 아이디 가져오기
        String fileName = "userId";
        try {
            FileInputStream fis = itemView.getContext().openFileInput(fileName);
            String line = new BufferedReader(new InputStreamReader(fis)).readLine();
            userId = Long.parseLong(line);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MainPost_adapter.ViewHolder viewHolder, int position) {
        MainPost item = items.get(position);
        viewHolder.setItem(item);
        final boolean[] isWish = new boolean[items.size()];
        //이미 찜한건지 확인
        Call<Boolean> call0 = com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient.getApiService().isThereMyWish(userId, item.getPostId(), 2);
        call0.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    if (response.body()) {
                        isWish[position] = true;
                        viewHolder.bookmark.setSelected(!viewHolder.bookmark.isSelected());
                    } else {
                        isWish[position] = false;
                    }
                } else {
                    Log.d("isWish", "내 찜 조회하기 실패");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d("isWish", "연결실패");
            }
        });
        viewHolder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isWish[position]) { //찜 안한 상태일때
                    Call<Void> call = com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient.getApiService().createMyWish(userId, item.getPostId(), 2);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                //버튼 디자인 바뀌게 구현하기
                                isWish[position] = true;
                                v.setSelected(!v.isSelected());
                                Toast.makeText(viewHolder.bookmark.getContext(), "나의 여행버킷리스트에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                                viewHolder.bookmark.setEnabled(false);
                                Handler handle = new Handler();
                                handle.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        viewHolder.bookmark.setEnabled(true);
                                    }
                                },1500);
                            } else {
                                Log.d("myWish", "게시물 찜 실패");
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("myWish", "인터넷 오류");
                        }
                    });
                } else {
                    Call<Void> call = com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient.getApiService().deleteMyWish(userId, item.getPostId(), 2);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                isWish[position] = false;
                                v.setSelected(!v.isSelected());
                                Toast.makeText(viewHolder.bookmark.getContext(), "나의 여행버킷리스트에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d("deleteMyWish", "게시물 찜 삭제 실패");
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("deleteMyWish", "인터넷 오류");
                        }
                    });
                }
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(viewHolder.hashTagRecyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        viewHolder.hashTagRecyclerView.setLayoutManager(layoutManager);
        PostHashTagItemAdapter adapter = new PostHashTagItemAdapter();
        if (!item.getMainObservation().equals("나만의 관측지")) {
            adapter.addItem(new PostHashTagItem(item.getMainObservation(), null, item.getObservationId(), null));
        } else {
            adapter.addItem(new PostHashTagItem(item.getOptionObservation(), null, null, null));
        }
        Call<List<PostHashTag>> call = RetrofitClient.getApiService().getPostHashTags(item.getPostId());
        call.enqueue(new Callback<List<PostHashTag>>() {
            @Override
            public void onResponse(Call<List<PostHashTag>> call, Response<List<PostHashTag>> response) {
                if (response.isSuccessful()) {
                    Log.d("mainHashTag", "메인 게시물 해시태그 업로드 성공");
                    List<PostHashTag> postHashTagIds = response.body();
                    if (item.getHashTags() != null) {
                        for (int i = 0; i < item.getHashTags().size(); i++) {
                            adapter.addItem(new PostHashTagItem(item.getHashTags().get(i), null, null, postHashTagIds.get(i).getHashTagId()));
                            if (i == 2) {
                                break;
                            }
                        }
                        if (adapter.getItemCount() < 4) {
                            if (item.getOptionHashTag() != null)
                                adapter.addItem(new PostHashTagItem(item.getOptionHashTag(), null, null, null));
                        }
                    } else {
                        adapter.addItem(new PostHashTagItem(item.getOptionHashTag(), null, null, null));
                        if (item.getOptionHashTag2() != null) {
                            adapter.addItem(new PostHashTagItem(item.getOptionHashTag2(), null, null, null));
                        }
                        if (item.getOptionHashTag3() != null) {
                            adapter.addItem(new PostHashTagItem(item.getOptionHashTag3(), null, null, null));
                        }
                    }
                    viewHolder.hashTagRecyclerView.setAdapter(adapter);
                    viewHolder.hashTagRecyclerView.addItemDecoration(new ViewHolder.RecyclerViewDecoration(20));
                    Bundle bundle = new Bundle();  // 아직 게시물 상세페이지에서는 에러나서 보류
                    bundle.putInt("type", 1);
                    final String[] keyword = new String[adapter.getItemCount()];
                    adapter.setOnItemClicklistener(new OnPostHashTagClickListener() {
                        @Override
                        public void onItemClick(PostHashTagItemAdapter.ViewHolder holder, View view, int position) {
                            Intent intent1 = new Intent(viewHolder.itemView.getContext(), MainActivity.class);
                            PostHashTagItem item1 = adapter.getItem(position);
                            if (position!=0) {
                                if (item1.getHashTagId() != null) {
                                    keyword[position] = null;
                                    ArrayList<Integer> hashTag = new ArrayList<Integer>(Collections.nCopies(22, 0));
                                    intent1.putExtra("keyword", keyword[position]);
                                    int x = item1.getHashTagId().intValue();
                                    hashTag.set(x - 1, 1);
                                    intent1.putExtra("area", area);
                                    intent1.putExtra("hashTag", hashTag);
                                    intent1.putExtra("FromWhere", Activities.POST);
                                    viewHolder.itemView.getContext().startActivity(intent1);
                                } else {
                                    ArrayList<Integer> hashTag = new ArrayList<Integer>(Collections.nCopies(22, 0));
                                    keyword[position] = item1.getHashTagname();
                                    intent1.putExtra("keyword", keyword[position]);
                                    intent1.putExtra("area", area);
                                    intent1.putExtra("hashTag", hashTag);
                                    intent1.putExtra("FromWhere", Activities.POST);
                                    viewHolder.itemView.getContext().startActivity(intent1);
                                }
                            }
                        }
                    });
                } else {
                    Log.d("mainHashTag", "메인 게시물 해시태그 업로드 실패");
                }
            }

            @Override
            public void onFailure(Call<List<PostHashTag>> call, Throwable t) {
                Log.d("mainHashTag", "메인 게시물 해시태그 인터넷 오류");
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClicklistener(OnMainPostClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView hashTagRecyclerView;
        TextView title;
        ViewPager2 mainslider;
        LinearLayout indicator;
        Button bookmark;
        LinearLayout titleLinear;

        public ViewHolder(View itemView, final OnMainPostClickListener listener) {
            super(itemView);

            hashTagRecyclerView = itemView.findViewById(R.id.mainRecyclerView);
            title = itemView.findViewById(R.id.mainpost_title);
            mainslider = itemView.findViewById(R.id.mainslider);
            indicator = itemView.findViewById(R.id.mainindicator);
            bookmark = itemView.findViewById(R.id.mainplus_btn);
            titleLinear = itemView.findViewById(R.id.linear_title);
//            profileimage.setBackground(new ShapeDrawable(new OvalShape()));
//            profileimage.setClipToOutline(true);
            itemView.setClickable(true);
        }

        public void setItem(MainPost item) {

            title.setText(item.getMainTitle());
            titleLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), PostActivity.class);
                    intent.putExtra("postId", item.getPostId());
                    ((Activity) context).startActivity(intent);
                }
            });
//            nickname.setText(item.getMainNickName());
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
                        R.drawable.observe__indicator_inactive));
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

            public RecyclerViewDecoration(int divHeight) {
                this.divHeight = divHeight;
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.right = divHeight;
            }
        }
    }

}
