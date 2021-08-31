package com.starrynight.tourapiproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.starrynight.tourapiproject.myPage.ChangeProfileActivity;
import com.starrynight.tourapiproject.myPage.MyHashTagAdapter;
import com.starrynight.tourapiproject.myPage.SettingActivity;
import com.starrynight.tourapiproject.myPage.MyWishActivity;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.User2;
import com.starrynight.tourapiproject.myPage.myPost.MyPost3;
import com.starrynight.tourapiproject.myPage.myWish.MyWish;
import com.starrynight.tourapiproject.postWritePage.PostWriteActivity;

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

import static android.graphics.BitmapFactory.decodeFile;

public class PersonFragment extends Fragment {

    Long userId;
    User2 user;
    List<String> myHashTagResult;
    ImageView profileImage;
    TextView nickName;

    ImageView myWishImage1;
    TextView myWishTitle1;
    ImageView myWishImage2;
    TextView myWishTitle2;
    ImageView myWishImage3;
    TextView myWishTitle3;

    ImageView myPostImage1;
    TextView myPostTitle1;
    ImageView myPostImage2;
    TextView myPostTitle2;
    ImageView myPostImage3;
    TextView myPostTitle3;

    List<MyWish> myWishes = new ArrayList<>();
    List<MyPost3> myPost3s = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_person, container, false);

        nickName = v.findViewById(R.id.nickName);
        profileImage = v.findViewById(R.id.profileImage);

        myWishImage1 = v.findViewById(R.id.myWishImage1);
        myWishTitle1 = v.findViewById(R.id.myWishTitle1);
        myWishImage2 = v.findViewById(R.id.myWishImage2);
        myWishTitle2 = v.findViewById(R.id.myWishTitle2);
        myWishImage3 = v.findViewById(R.id.myWishImage3);
        myWishTitle3 = v.findViewById(R.id.myWishTitle3);

        myPostImage1 = v.findViewById(R.id.myPostImage1);
        myPostTitle1 = v.findViewById(R.id.myPostTitle1);
        myPostImage2 = v.findViewById(R.id.myPostImage2);
        myPostTitle2 = v.findViewById(R.id.myPostTitle2);
        myPostImage3 = v.findViewById(R.id.myPostImage3);
        myPostTitle3 = v.findViewById(R.id.myPostTitle3);


        //앱 내부 저장소의 userId 데이터 읽기
        String fileName = "userId";
        try{
            FileInputStream fis = getActivity().openFileInput(fileName);
            String line = new BufferedReader(new InputStreamReader(fis)).readLine();
            userId = Long.parseLong(line);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } System.out.println("userId = " + userId);


        //닉네임, 프로필 사진을 불러오기 위한 get api
        Call<User2> call = RetrofitClient.getApiService().getUser2(userId);
        call.enqueue(new Callback<User2>() {
            @Override
            public void onResponse(Call<User2> call, Response<User2> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    assert user != null;
                    if (user.getProfileImage() != null){
                        profileImage.setImageBitmap(decodeFile(user.getProfileImage()));
                    }
                    nickName.setText(user.getNickName());
                } else {
                    System.out.println("사용자 정보 불러오기 실패");
                }
            }
            @Override
            public void onFailure(Call<User2> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });


        //사용자 해시태그 리사이클러 뷰
        RecyclerView myHashTagRecyclerview = v.findViewById(R.id.myHashTag);
        LinearLayoutManager myHashTagLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        myHashTagRecyclerview.setLayoutManager(myHashTagLayoutManager);
        myHashTagRecyclerview.setHasFixedSize(true);
        myHashTagResult = new ArrayList<>();

        //사용자 해시태그를 불러오기 위한 get api
        Call<List<String>> call2 = RetrofitClient.getApiService().getMyHashTag(userId);
        call2.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response <List<String>> response) {
                if (response.isSuccessful()) {
                    myHashTagResult = response.body();
                    MyHashTagAdapter hashTagAdapter = new MyHashTagAdapter(myHashTagResult);
                    myHashTagRecyclerview.setAdapter(hashTagAdapter);
                } else {
                    System.out.println("사용자 해시태그 불러오기 실패");
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });


        //게시물 작성 페이지로 이동
        Button goPostWrite = v.findViewById(R.id.goPostWrite);
        goPostWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PostWriteActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        //설정 페이지로 이동
        Button goSetting = v.findViewById(R.id.goSetting);
        goSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                intent.putExtra("userId", userId);
                startActivityForResult(intent, 101);
            }
        });

        //프로필 변경 페이지로 이동
        ImageView profileImage = v.findViewById(R.id.profileImage);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangeProfileActivity.class);
                intent.putExtra("userId", userId);
                startActivityForResult(intent, 101);
            }
        });
        TextView nickName = v.findViewById(R.id.nickName);
        nickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangeProfileActivity.class);
                intent.putExtra("userId", userId);
                startActivityForResult(intent, 101);
            }
        });


        //내 찜 불러오는 api
        Call<List<MyWish>> call3 = RetrofitClient.getApiService().getMyWish3(userId);
        call3.enqueue(new Callback<List<MyWish>>() {
            @Override
            public void onResponse(Call<List<MyWish>> call, Response<List<MyWish>> response) {
                if (response.isSuccessful()) {
                    myWishes = response.body();
                    int size = myWishes.size();
                    int i = 0;
                    if (size > 0){
                        Glide.with(getContext()).load(myWishes.get(i).getThumbnail()).into(myWishImage1);
                        myWishTitle1.setText(myWishes.get(i).getTitle());
                        i++;
                        if (size > 1){
                            Glide.with(getContext()).load(myWishes.get(i).getThumbnail()).into(myWishImage2);
                            myWishTitle2.setText(myWishes.get(i).getTitle());
                            i++;
                            if (size > 2){
                                Glide.with(getContext()).load(myWishes.get(i).getThumbnail()).into(myWishImage3);
                                myWishTitle3.setText(myWishes.get(i).getTitle());
                            }
                        }
                    }

                } else {
                    System.out.println("내 찜 불러오기 실패");
                }
            }
            @Override
            public void onFailure(Call<List<MyWish>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });

        //나의 여행 버킷리스트 페이지로 이동
        View myWishLayout = v.findViewById(R.id.myWishLayout);
        myWishLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyWishActivity.class);
                intent.putExtra("userId", userId);
                startActivityForResult(intent, 101);
            }
        });


        //내 게시물 불러오는 api
        Call<List<MyPost3>> call4 = RetrofitClient.getApiService().getMyPost3(userId);
        call4.enqueue(new Callback<List<MyPost3>>() {
            @Override
            public void onResponse(Call<List<MyPost3>> call, Response<List<MyPost3>> response) {
                if (response.isSuccessful()) {
                    myPost3s = response.body();
                    int size = myPost3s.size();
                    int i = 0;
                    if (size > 0){
                        Glide.with(getContext()).load(myPost3s.get(i).getThumbnail()).into(myPostImage1);
                        myPostTitle1.setText(myPost3s.get(i).getTitle());
                        i++;
                        if (size > 1){
                            Glide.with(getContext()).load(myPost3s.get(i).getThumbnail()).into(myPostImage2);
                            myPostTitle2.setText(myPost3s.get(i).getTitle());
                            i++;
                            if (size > 2){
                                Glide.with(getContext()).load(myPost3s.get(i).getThumbnail()).into(myPostImage3);
                                myPostTitle3.setText(myPost3s.get(i).getTitle());
                            }
                        }
                    }

                } else {
                    System.out.println("내 게시물 불러오기 실패");
                }
            }
            @Override
            public void onFailure(Call<List<MyPost3>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });

        //내 게시물 페이지로 이동
        View myPostLayout = v.findViewById(R.id.myPostLayout);
        myPostLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyWishActivity.class); //수정
               intent.putExtra("userId", userId);
                startActivityForResult(intent, 101);
            }
        });


//        //내 게시물 리사이클러 뷰
//        myPostList = v.findViewById(R.id.myPostList);
//        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        myPostList.setLayoutManager(layoutManager2);
//        myPostAdapter= new MyPostAdapter();
//        myPostList.setAdapter(myPostAdapter);
//
//        //내 게시물 불러오는 api
//        Call<List<MyPost>> call4 = RetrofitClient.getApiService().getMyPost(userId);
//        call4.enqueue(new Callback<List<MyPost>>() {
//            @Override
//            public void onResponse(Call<List<MyPost>> call, Response<List<MyPost>> response) {
//                if (response.isSuccessful()) {
//                    List<MyPost> result = response.body();
//                    for (MyPost wp: result){
//                        myPostAdapter.addItem(new MyPost(wp.getThumbnail(), wp.getTitle(), wp.getPostId()));
//                    }
//                    myPostList.setAdapter(myPostAdapter);
//                } else {
//                    System.out.println("내 게시물 불러오기 실패");
//                }
//            }
//            @Override
//            public void onFailure(Call<List<MyPost>> call, Throwable t) {
//                Log.e("연결실패", t.getMessage());
//            }
//        });
//        //내 게시물 클릭 이벤트
//        myPostAdapter.setOnMyPostItemClickListener(new OnMyPostItemClickListener() {
//            @Override
//            public void onItemClick(MyPostAdapter.ViewHolder holder, View view, int position) {
//                Toast.makeText(getActivity().getApplicationContext(), ""+"번 게시물 클릭", Toast.LENGTH_SHORT).show();
//                //게시물 페이지 띄우기
//            }
//        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101){
            //프래그먼트 새로고침
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}