package com.starrynight.tourapiproject.postPage;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.MainFragment;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.mapPage.Activities;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.User;
import com.starrynight.tourapiproject.observationPage.observationPageRetrofit.Observation;
import com.starrynight.tourapiproject.postItemPage.OnPostHashTagClickListener;
import com.starrynight.tourapiproject.postItemPage.OnPostPointItemClickListener;
import com.starrynight.tourapiproject.postItemPage.PostHashTagItem;
import com.starrynight.tourapiproject.postItemPage.PostHashTagItemAdapter;
import com.starrynight.tourapiproject.postItemPage.Post_point_item_Adapter;
import com.starrynight.tourapiproject.postItemPage.post_point_item;
import com.starrynight.tourapiproject.postPage.postRetrofit.Post;
import com.starrynight.tourapiproject.postPage.postRetrofit.PostHashTag;
import com.starrynight.tourapiproject.postPage.postRetrofit.PostImage;
import com.starrynight.tourapiproject.postPage.postRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.postWritePage.postWriteRetrofit.PostParams;

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
 * className :   PostActivity
 * description : 게시물 페이지 class입니다.
 * modification : 2022.08.01(박진혁) 주석 수정
 * author : jinhyeok
 * date : 2022-08-01
 * version : 1.0
 * ====개정이력(Modification Information)====
 * 수정일        수정자        수정내용
 * -----------------------------------------
 * 2022-08-01      jinhyeok      주석 수정
 */
public class PostActivity extends AppCompatActivity {
    private ViewPager2 sliderViewPager;
    private LinearLayout indicator;
    boolean isWish;
    Button like_btn;
    Post post;
    Long postId;
    Long userId;
    int allsize = 0;
    TextView nickname;
    TextView postTitle;
    TextView postContent;
    TextView postTime;
    TextView postDate;
    ImageView profileImage;
    List<String> postHashTags;
    List<PostHashTag> postHashTagList;
    String[] filename2 = new String[10];
    String[] relatefilename = new String[4];
    ArrayList<Integer> area = new ArrayList<Integer>(Collections.nCopies(17, 0));
    String keyword;
    MainFragment mainFragment = new MainFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Intent intent = getIntent();
        PostParams postParams = (PostParams) intent.getSerializableExtra("postParams");
        postId = (Long) intent.getSerializableExtra("postId");
        //앱 내부저장소에서 저장된 유저 아이디 가져오기
        String fileName = "userId";
        try {
            FileInputStream fis = openFileInput(fileName);
            String line = new BufferedReader(new InputStreamReader(fis)).readLine();
            userId = Long.parseLong(line);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sliderViewPager = findViewById(R.id.slider); // 이미지 좌우 슬라이드
        indicator = findViewById(R.id.indicator);
        //게시물 이미지 가져오는 get api
        Call<List<String>> call = RetrofitClient.getApiService().getPostImage(postId);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    Log.d("postImageList", "이미지 업로드 성공" + response.body());
                    List<String> result = response.body();
                    ArrayList<String> FileName = new ArrayList<>();
                    for (int i = 0; i < result.size(); i++) {
                        filename2[i] = result.get(i);
                    }
                    for (int i = 0; i < filename2.length; i++) {
                        if (filename2[i] != null) {
                            FileName.add("https://starry-night.s3.ap-northeast-2.amazonaws.com/postImage/" + filename2[i]);
                        }
                    }
                    sliderViewPager.setAdapter(new ImageSliderAdapter(getApplicationContext(), FileName));

                    sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                        @Override
                        public void onPageSelected(int position) {
                            super.onPageSelected(position);
                            setCurrentIndicator(position);
                        }
                    });
                    setupIndicators(FileName.size());
                } else {
                    Log.d("postImageList", "이미지 업로드 실패");
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.d("postImageList", "이미지 업로드 인터넷 오류");
            }
        });

        postTitle = findViewById(R.id.postTitleText);
        postContent = findViewById(R.id.postContent);
        postTime = findViewById(R.id.postTime);
        postDate = findViewById(R.id.postDate);
        profileImage = findViewById(R.id.post_profileImage);
        nickname = findViewById(R.id.post_nickname);
        profileImage.setBackground(new ShapeDrawable(new OvalShape()));
        profileImage.setClipToOutline(true);
        //게시물 정보가져오는 get api
        Call<Post> call1 = RetrofitClient.getApiService().getPost(postId);
        call1.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    Log.d("post", "게시물 정보 가져옴");
                    post = response.body();
                    postTitle.setText(post.getPostTitle());
                    postContent.setText(post.getPostContent());
                    String postRealTime = post.getTime();
                    postRealTime = postRealTime.substring(0, postRealTime.length() - 3);
                    postTime.setText(postRealTime);
                    postDate.setText(post.getYearDate());
                    //관측지
                    Call<Observation> call2 = RetrofitClient.getApiService().getObservation(post.getObservationId());
                    call2.enqueue(new Callback<Observation>() {
                        @Override
                        public void onResponse(Call<Observation> call, Response<Observation> response) {
                            if (response.isSuccessful()) {
                                Log.d("postObservation", "게시물 관측지 가져옴");
                                Observation observation = response.body();
                                //게시물 해시태그
                                Call<List<PostHashTag>> call6 = RetrofitClient.getApiService().getPostHashTags(postId);
                                call6.enqueue(new Callback<List<PostHashTag>>() {
                                    @Override
                                    public void onResponse(Call<List<PostHashTag>> call, Response<List<PostHashTag>> response) {
                                        if (response.isSuccessful()) {
                                            if (!response.body().isEmpty()) {
                                                Log.d("postHashTag", "게시물 해시태그 가져옴");
                                                postHashTagList = response.body();
                                                RecyclerView hashTagRecyclerView = findViewById(R.id.hashTagRecyclerView);
                                                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL); // 해시태그 나열하는 Layout
                                                hashTagRecyclerView.setLayoutManager(staggeredGridLayoutManager);
                                                PostHashTagItemAdapter adapter2 = new PostHashTagItemAdapter();
                                                if (!observation.getObservationName().equals("나만의 관측지")) {
                                                    adapter2.addItem(new PostHashTagItem(observation.getObservationName(), null, observation.getObservationId(), null));
                                                } else {
                                                    adapter2.addItem(new PostHashTagItem(post.getOptionObservation(), null, null, null));
                                                }
                                                for (int i = 0; i < postHashTagList.size(); i++) {
                                                    if (postHashTagList.get(i).getHashTagId() != null) {
                                                        adapter2.addItem(new PostHashTagItem(postHashTagList.get(i).getHashTagName(), null, null, postHashTagList.get(i).getHashTagId()));
                                                    }
                                                }
                                                if (post.getOptionHashTag() != null) {
                                                    adapter2.addItem(new PostHashTagItem(post.getOptionHashTag(), null, null, null));
                                                }
                                                if (post.getOptionHashTag2() != null) {
                                                    adapter2.addItem(new PostHashTagItem(post.getOptionHashTag2(), null, null, null));
                                                }
                                                if (post.getOptionHashTag3() != null) {
                                                    adapter2.addItem(new PostHashTagItem(post.getOptionHashTag3(), null, null, null));
                                                }
                                                if (post.getOptionHashTag4() != null) {
                                                    adapter2.addItem(new PostHashTagItem(post.getOptionHashTag4(), null, null, null));
                                                }
                                                if (post.getOptionHashTag5() != null) {
                                                    adapter2.addItem(new PostHashTagItem(post.getOptionHashTag5(), null, null, null));
                                                }
                                                if (post.getOptionHashTag6() != null) {
                                                    adapter2.addItem(new PostHashTagItem(post.getOptionHashTag6(), null, null, null));
                                                }
                                                if (post.getOptionHashTag7() != null) {
                                                    adapter2.addItem(new PostHashTagItem(post.getOptionHashTag7(), null, null, null));
                                                }
                                                if (post.getOptionHashTag8() != null) {
                                                    adapter2.addItem(new PostHashTagItem(post.getOptionHashTag8(), null, null, null));
                                                }
                                                if (post.getOptionHashTag9() != null) {
                                                    adapter2.addItem(new PostHashTagItem(post.getOptionHashTag9(), null, null, null));
                                                }
                                                if (post.getOptionHashTag10() != null) {
                                                    adapter2.addItem(new PostHashTagItem(post.getOptionHashTag10(), null, null, null));
                                                }
                                                //해시태그 개수에 따라 레이아웃 변경
                                                for (int i = 0; i < adapter2.getItemCount(); i++) {
                                                    allsize += adapter2.getItem(i).getHashTagname().length();
                                                }
                                                if (allsize > 20 && allsize < 41) {
                                                    staggeredGridLayoutManager.setSpanCount(2);
                                                } else if (allsize > 40 && allsize < 57) {
                                                    staggeredGridLayoutManager.setSpanCount(3);
                                                } else if (allsize > 56) {
                                                    staggeredGridLayoutManager.setSpanCount(4);
                                                }
                                                hashTagRecyclerView.setAdapter(adapter2);
                                                hashTagRecyclerView.addItemDecoration(new RecyclerViewDecoration(20, 20));
                                                //게시물 해시태그 클릭 시 관련 게시물,관측지 검색 페이지로 이동
                                                adapter2.setOnItemClicklistener(new OnPostHashTagClickListener() {
                                                    @Override
                                                    public void onItemClick(PostHashTagItemAdapter.ViewHolder holder, View view, int position) {
                                                        Intent intent1 = new Intent(PostActivity.this, MainActivity.class);
                                                        PostHashTagItem item = adapter2.getItem(position);
                                                        if (position!=0) {
                                                            if (item.getHashTagId() != null) {//지정된 해시태그를 클릭했을 경우
                                                                ArrayList<Integer> hashTag = new ArrayList<Integer>(Collections.nCopies(22, 0));
                                                                keyword = null;
                                                                intent1.putExtra("keyword", keyword);
                                                                int x = item.getHashTagId().intValue();
                                                                hashTag.set(x - 1, 1);
                                                                intent1.putExtra("area", area);
                                                                intent1.putExtra("hashTag", hashTag);
                                                                intent1.putExtra("FromWhere", Activities.POST);
                                                                startActivity(intent1);
                                                            } else {//임의의 해시태그를 클릭 했을 경우
                                                                ArrayList<Integer> hashTag = new ArrayList<Integer>(Collections.nCopies(22, 0));
                                                                keyword = item.getHashTagname();
                                                                intent1.putExtra("keyword", keyword);
                                                                intent1.putExtra("area", area);
                                                                intent1.putExtra("hashTag", hashTag);
                                                                intent1.putExtra("FromWhere", Activities.POST);
                                                                startActivity(intent1);
                                                            }
                                                        }
                                                    }
                                                });
                                            } else {
                                                Log.d("optionHashTag", "메인 해시태그 없음. 임의 해시태그 가져옴");
                                                RecyclerView hashTagRecyclerView = findViewById(R.id.hashTagRecyclerView);
                                                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
                                                hashTagRecyclerView.setLayoutManager(staggeredGridLayoutManager);
                                                PostHashTagItemAdapter adapter = new PostHashTagItemAdapter();
                                                if (!observation.getObservationName().equals("나만의 관측지")) {
                                                    adapter.addItem(new PostHashTagItem(observation.getObservationName(), null, observation.getObservationId(), null));
                                                } else {
                                                    adapter.addItem(new PostHashTagItem(post.getOptionObservation(), null, null, null));
                                                }
                                                adapter.addItem(new PostHashTagItem(post.getOptionHashTag(), null, null, null));
                                                if (post.getOptionHashTag2() != null) {
                                                    adapter.addItem(new PostHashTagItem(post.getOptionHashTag2(), null, null, null));
                                                }
                                                if (post.getOptionHashTag3() != null) {
                                                    adapter.addItem(new PostHashTagItem(post.getOptionHashTag3(), null, null, null));
                                                }
                                                if (post.getOptionHashTag4() != null) {
                                                    adapter.addItem(new PostHashTagItem(post.getOptionHashTag4(), null, null, null));
                                                }
                                                if (post.getOptionHashTag5() != null) {
                                                    adapter.addItem(new PostHashTagItem(post.getOptionHashTag5(), null, null, null));
                                                }
                                                if (post.getOptionHashTag6() != null) {
                                                    adapter.addItem(new PostHashTagItem(post.getOptionHashTag6(), null, null, null));
                                                }
                                                if (post.getOptionHashTag7() != null) {
                                                    adapter.addItem(new PostHashTagItem(post.getOptionHashTag7(), null, null, null));
                                                }
                                                if (post.getOptionHashTag8() != null) {
                                                    adapter.addItem(new PostHashTagItem(post.getOptionHashTag8(), null, null, null));
                                                }
                                                if (post.getOptionHashTag9() != null) {
                                                    adapter.addItem(new PostHashTagItem(post.getOptionHashTag9(), null, null, null));
                                                }
                                                if (post.getOptionHashTag10() != null) {
                                                    adapter.addItem(new PostHashTagItem(post.getOptionHashTag10(), null, null, null));
                                                }
                                                //해시태그 갯수에 따라 레이아웃 변경
                                                for (int i = 0; i < adapter.getItemCount(); i++) {
                                                    allsize += adapter.getItem(i).getHashTagname().length();
                                                }
                                                if (allsize > 20 && allsize < 41) {
                                                    staggeredGridLayoutManager.setSpanCount(2);
                                                } else if (allsize > 40 && allsize < 57) {
                                                    staggeredGridLayoutManager.setSpanCount(3);
                                                } else if (allsize > 56) {
                                                    staggeredGridLayoutManager.setSpanCount(4);
                                                }
                                                // 해시태그 클릭시 페이지 이동
                                                hashTagRecyclerView.setAdapter(adapter);
                                                hashTagRecyclerView.addItemDecoration(new RecyclerViewDecoration(20, 20));
                                                adapter.setOnItemClicklistener(new OnPostHashTagClickListener() {
                                                    @Override
                                                    public void onItemClick(PostHashTagItemAdapter.ViewHolder holder, View view, int position) {
                                                        Intent intent1 = new Intent(PostActivity.this, MainActivity.class);
                                                        PostHashTagItem item = adapter.getItem(position);
                                                        if (item.getHashTagId() != null) {
                                                            ArrayList<Integer> hashTag = new ArrayList<Integer>(Collections.nCopies(22, 0));
                                                            keyword = null;
                                                            intent1.putExtra("keyword", keyword);
                                                            int x = item.getHashTagId().intValue();
                                                            hashTag.set(x - 1, 1);
                                                            intent1.putExtra("area", area);
                                                            intent1.putExtra("hashTag", hashTag);
                                                            intent.putExtra("FromWhere", Activities.POST);
                                                            startActivity(intent1);
                                                        }
                                                    }
                                                });
                                            }
                                        } else {
                                            Log.d("postHashTag", "메인 해시태그 오류");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<PostHashTag>> call, Throwable t) {
                                        Log.d("postHashTag", "해시태그 인터넷 오류");
                                    }
                                });
                            } else {
                                Log.d("postObservation", "게시물 관측지 실패");
                            }
                        }

                        @Override
                        public void onFailure(Call<Observation> call, Throwable t) {
                            Log.d("postObservation", "게시물 관측지 인터넷 오류");
                        }
                    });
                    //게시물 작성자 정보 가져오기
                    Call<User> call3 = RetrofitClient.getApiService().getUser(post.getUserId());
                    call3.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                Log.d("user", "게시물 유저정보 업로드");
                                User user = response.body();
                                if (user.getProfileImage() != null) {
                                    if (user.getProfileImage().startsWith("http://") || user.getProfileImage().startsWith("https://")) {
                                        Glide.with(getApplicationContext()).load(user.getProfileImage()).into(profileImage);
                                    } else {
                                        String fileName = user.getProfileImage();
                                        fileName = fileName.substring(1, fileName.length() - 1);
                                        Glide.with(getApplicationContext()).load("https://starry-night.s3.ap-northeast-2.amazonaws.com/profileImage/" + fileName).into(profileImage);
                                    }
                                }
                                nickname.setText(user.getNickName());
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });

                    //뒤로 버튼
                    FrameLayout back = findViewById(R.id.back_btn_layout);
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });

                    //삭제 버튼
                    LinearLayout deleteLayout = findViewById(R.id.delete_layout);
                    if (post.getUserId() == userId) {
                        deleteLayout.setVisibility(View.VISIBLE);
                    } else if (post.getUserId() != userId) {
                        deleteLayout.setVisibility(View.GONE);
                    }
                    deleteLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder ad = new AlertDialog.Builder(PostActivity.this, R.style.MyDialogTheme);
                            ad.setMessage("정말로 게시물을 삭제하시겠습니까?");
                            ad.setTitle("알림");
                            ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Call<Void> call8 = RetrofitClient.getApiService().deletePost(postId);
                                    call8.enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            if (response.isSuccessful()) {
                                                Log.d("deletePost", "게시물 삭제 완료");

                                                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent1);
                                                finish();
                                            } else {
                                                Log.d("deletePost", "게시물 삭제 실패");
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {
                                            Log.d("deletePost", "게시물 삭제 실패 2");
                                        }
                                    });
                                    dialog.dismiss();
                                }
                            });
                            ad.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            ad.show();
                        }
                    });


                    //관련 게시물
                    Call<List<PostImage>> call5 = RetrofitClient.getApiService().getRelatePostImageList(post.getObservationId());
                    //관련 게시물 이미지
                    call5.enqueue(new Callback<List<PostImage>>() {
                        @Override
                        public void onResponse(Call<List<PostImage>> call, Response<List<PostImage>> response) {
                            if (response.isSuccessful()) {
                                Log.d("relatePostImage", "관련 게시물 이미지 업로드");
                                List<PostImage> relateImageList = response.body();
                                RecyclerView recyclerView = findViewById(R.id.relatePost);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                Post_point_item_Adapter adapter = new Post_point_item_Adapter();
                                for (int i = 0; i < relateImageList.size(); i++) {
                                    if (i > 3) {
                                        break;
                                    }
                                    if (relateImageList.get(i).getPostId() != post.getPostId()) {
                                        relatefilename[i] = relateImageList.get(i).getImageName();
                                    }
                                }
                                for (int i = 0; i < relatefilename.length; i++) {
                                    if (relatefilename[i] != null) {
                                        adapter.addItem(new post_point_item("", "https://starry-night.s3.ap-northeast-2.amazonaws.com/postImage/" + relatefilename[i]));
                                    }
                                }
                                //관련 게시물 클릭 시 이동
                                recyclerView.setAdapter(adapter);
                                adapter.setOnItemClicklistener(new OnPostPointItemClickListener() {
                                    @Override
                                    public void onItemClick(Post_point_item_Adapter.ViewHolder holder, View view, int position) {
                                        post_point_item item = adapter.getItem(position);

                                        Intent intent1 = new Intent(PostActivity.this, PostActivity.class);
                                        if (relateImageList.get(position).getPostId() != post.getPostId()){
                                        intent1.putExtra("postId", relateImageList.get(position).getPostId());}
                                        else{intent1.putExtra("postId", relateImageList.get(position+1).getPostId());}
                                        startActivity(intent1);
                                    }
                                });

                            } else {
                                Log.d("relatePostImage", "관련 게시물 이미지 실패");
                            }
                        }

                        @Override
                        public void onFailure(Call<List<PostImage>> call, Throwable t) {
                            Log.d("relatePostImage", "관련 게시물 이미지 업로드 인터넷 오류");
                        }
                    });
                } else {
                    Log.d("post", "게시물 정보 업로드 실패");
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.d("post", "게시물 인터넷 오류");
            }
        });


        //이미 찜한건지 확인
        like_btn = findViewById(R.id.like);
        Call<Boolean> call0 = com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient.getApiService().isThereMyWish(userId, postId, 2);
        call0.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    if (response.body()) {
                        isWish = true;
                        like_btn.setSelected(!like_btn.isSelected());
                    } else {
                        isWish = false;
                    }
                } else {
                    Log.d("isWish", "내 찜 조회하기 실패");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d("isWish", "인터넷 연결실패");
            }
        });

        like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isWish) { //찜 안한 상태일때
                    Call<Void> call = com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient.getApiService().createMyWish(userId, postId, 2);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                //버튼 디자인 바뀌게 구현하기
                                isWish = true;
                                v.setSelected(!v.isSelected());
                                Toast.makeText(getApplicationContext(), "나의 여행버킷리스트에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                                ((MainActivity) MainActivity.mContext).replaceFragment(mainFragment);
                                like_btn.setEnabled(false);
                                Handler handle = new Handler();
                                handle.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        like_btn.setEnabled(true);
                                    }
                                },1500);
                            } else {
                                Log.d("myWish", "관광지 찜 실패");
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("myWish", "연결실패");
                        }
                    });
                } else {
                    Call<Void> call = com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient.getApiService().deleteMyWish(userId, postId, 2);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                isWish = false;
                                v.setSelected(!v.isSelected());
                                Toast.makeText(getApplicationContext(), "나의 여행버킷리스트에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                ((MainActivity) MainActivity.mContext).replaceFragment(mainFragment);
                            } else {
                                Log.d("deleteMyWish", "관광지 찜 삭제 실패");
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("delteMyWish", "인터넷 연결실패");
                        }
                    });
                }
            }
        });
    }
    // 슬라이드 아래 indicator 양식
    private void setupIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.mainpage_postimage));
            indicators[i].setLayoutParams(params);
            indicator.addView(indicators[i]);
        }
        setCurrentIndicator(0);
    }
    //슬라이드 이동 시 indicator 색 변화
    private void setCurrentIndicator(int position) {
        int childCount = indicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) indicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.mainpage_postimage
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.mainpage_postimage_non
                ));
            }
        }
    }

    //recyclerview 간격
    public static class RecyclerViewDecoration extends RecyclerView.ItemDecoration {

        private final int divHeight;
        private final int divRight;

        public RecyclerViewDecoration(int divHeight, int divRight) {
            this.divHeight = divHeight;
            this.divRight = divRight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.top = divHeight;
            outRect.right = divRight;
        }
    }
}
