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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.myPage.ChangeProfileActivity;
import com.starrynight.tourapiproject.myPage.SettingActivity;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.User2;
import com.starrynight.tourapiproject.myPage.myPost.MyPost;
import com.starrynight.tourapiproject.myPage.myPost.MyPostAdapter;
import com.starrynight.tourapiproject.myPage.myPost.OnMyPostItemClickListener;
import com.starrynight.tourapiproject.myPage.myWish.MyWish;
import com.starrynight.tourapiproject.myPage.myWish.MyWishAdapter;
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

    ImageView profileImage;
    TextView nickName;
    TextView hashTagNameList;

    List<MyWish> wishResult;

    RecyclerView myPostList;
    MyPostAdapter myPostAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_person, container, false);

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

        nickName = v.findViewById(R.id.nickName);
        profileImage = v.findViewById(R.id.profileImage);
        hashTagNameList = v.findViewById(R.id.hashTagNameList);

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

        //해시태그 목록을 불러오기 위한 get api
        Call<List<String>> call2 = RetrofitClient.getApiService().getMyHashTag(userId);
        call2.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response <List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> result = response.body();
                    String names = "";
                    for (String name : result){
                        names += "#" + name + " ";
                    }
                    int len = names.length();
                    hashTagNameList.setText(names.substring(0 , len-1));
                } else {
                    System.out.println("사용자 정보 불러오기 실패");
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

        //내 게시물 리사이클러 뷰
        myPostList = v.findViewById(R.id.myPostList);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        myPostList.setLayoutManager(layoutManager2);
        myPostAdapter= new MyPostAdapter();
        myPostList.setAdapter(myPostAdapter);

        //내 게시물 불러오는 api
        Call<List<MyPost>> call3 = RetrofitClient.getApiService().getMyPost(userId);
        call3.enqueue(new Callback<List<MyPost>>() {
            @Override
            public void onResponse(Call<List<MyPost>> call, Response<List<MyPost>> response) {
                if (response.isSuccessful()) {
                    List<MyPost> result = response.body();
                    for (MyPost wp: result){
                        myPostAdapter.addItem(new MyPost(wp.getThumbnail(), wp.getTitle(), wp.getPostId()));
                    }
                    myPostList.setAdapter(myPostAdapter);
                } else {
                    System.out.println("내 게시물 불러오기 실패");
                }
            }
            @Override
            public void onFailure(Call<List<MyPost>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
        //내 게시물 클릭 이벤트
        myPostAdapter.setOnMyPostItemClickListener(new OnMyPostItemClickListener() {
            @Override
            public void onItemClick(MyPostAdapter.ViewHolder holder, View view, int position) {
                Toast.makeText(getActivity().getApplicationContext(), ""+"번 게시물 클릭", Toast.LENGTH_SHORT).show();
                //게시물 페이지 띄우기
            }
        });


        //찜 관련 리사이클러 뷰
        RecyclerView myWishRecyclerView = v.findViewById(R.id.myWishList);
        LinearLayoutManager myWishLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        myWishRecyclerView.setLayoutManager(myWishLayoutManager);
        myWishRecyclerView.setHasFixedSize(true);
        wishResult = new ArrayList<>();

        Call<List<MyWish>> call4 = RetrofitClient.getApiService().getMyWish(userId);
        call4.enqueue(new Callback<List<MyWish>>() {
            @Override
            public void onResponse(Call<List<MyWish>> call, Response<List<MyWish>> response) {
                if (response.isSuccessful()) {
                    wishResult = response.body();

                    MyWishAdapter myWishAdapter = new MyWishAdapter(wishResult, getContext());
                    myWishRecyclerView.setAdapter(myWishAdapter);
                } else {
                    System.out.println("내 찜 불러오기 실패");
                }
            }
            @Override
            public void onFailure(Call<List<MyWish>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });


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