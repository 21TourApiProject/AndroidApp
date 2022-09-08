package com.starrynight.tourapiproject;

import android.content.Intent;
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

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.starrynight.tourapiproject.myPage.ChangeProfileActivity;
import com.starrynight.tourapiproject.myPage.MyHashTagAdapter;
import com.starrynight.tourapiproject.myPage.MyPostActivity;
import com.starrynight.tourapiproject.myPage.MyWishActivity;
import com.starrynight.tourapiproject.myPage.SettingActivity;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.myPage.myPageRetrofit.User2;
import com.starrynight.tourapiproject.myPage.myPost.MyPost3;
import com.starrynight.tourapiproject.myPage.myWish.MyWish;
import com.starrynight.tourapiproject.postWritePage.PostWriteActivity;
import com.starrynight.tourapiproject.signUpPage.SelectMyHashTagActivity;

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

/**
 * @className : PersonFragment.java
 * @description : 마이페이지 Fragment 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
public class PersonFragment extends Fragment {

    private static final String TAG = "PersonFragment";
    private static final int HAVE_TO_REFRESH = 20;

    Long userId;
    User2 user;
    ArrayList<String> myHashTagResult;
    ImageView profileImage;
    TextView nickName;
    RecyclerView myHashTag;

    LinearLayout myWishLayout;
    LinearLayout myPostLayout;

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
        setRetainInstance(true);
        View v = inflater.inflate(R.layout.fragment_person, container, false);

        nickName = v.findViewById(R.id.nickName);
        profileImage = v.findViewById(R.id.profileImage);
        myHashTag = v.findViewById(R.id.myHashTag);

        myWishLayout = v.findViewById(R.id.myWishLayout);
        myPostLayout = v.findViewById(R.id.myPostLayout);

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

        profileImage.setBackground(new ShapeDrawable(new OvalShape()));
        profileImage.setClipToOutline(true);

        //앱 내부 저장소의 userId 데이터 읽기
        String fileName = "userId";
        try {
            FileInputStream fis = getActivity().openFileInput(fileName);
            String line = new BufferedReader(new InputStreamReader(fis)).readLine();
            userId = Long.parseLong(line);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "userId " + userId);

        //닉네임, 프로필 사진을 불러오기 위한 get api
        Call<User2> call = RetrofitClient.getApiService().getUser2(userId);
        call.enqueue(new Callback<User2>() {
            @Override
            public void onResponse(Call<User2> call, Response<User2> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    if (user.getProfileImage() != null) {
                        if (user.getProfileImage().startsWith("http://") || user.getProfileImage().startsWith("https://")) {
                            Glide.with(getContext()).load(user.getProfileImage()).into(profileImage);
                        } else {
                            String fileName = user.getProfileImage();
                            fileName = fileName.substring(1, fileName.length() - 1);
                            Glide.with(getContext()).load("https://starry-night.s3.ap-northeast-2.amazonaws.com/profileImage/" + fileName).into(profileImage);
                        }
                    }
                    nickName.setText(user.getNickName());
                } else {
                    Log.d(TAG, "사용자 정보 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<User2> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });


        //사용자 해시태그를 불러오기 위한 get api
        myHashTagResult = new ArrayList<>();
        LinearLayoutManager myHashTagLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        myHashTag.setLayoutManager(myHashTagLayoutManager);

        Call<List<String>> call3 = RetrofitClient.getApiService().getMyHashTag(userId);
        call3.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    myHashTagResult = (ArrayList<String>) response.body();
                    ArrayList<String> three = new ArrayList<>();

                    if (myHashTagResult.size() > 3) {
                        for (int i = 0; i < 3; i++) {
                            three.add(myHashTagResult.get(i));
                        }
                    } else {
                        three = myHashTagResult;
                    }

                    MyHashTagAdapter hashTagAdapter = new MyHashTagAdapter(three);
                    myHashTag.setAdapter(hashTagAdapter);
                } else {
                    Log.d(TAG, "사용자 해시태그 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });

        //선호 해시태크 변경 페이지로 이동
        LinearLayout changeMyHashTag = v.findViewById(R.id.changeMyHashTag);
        changeMyHashTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectMyHashTagActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("hashtag", myHashTagResult);
                startActivityForResult(intent, HAVE_TO_REFRESH);
            }
        });


        //내 찜 불러오는 api
        Call<List<MyWish>> call4 = RetrofitClient.getApiService().getMyWish3(userId);
        call4.enqueue(new Callback<List<MyWish>>() {
            @Override
            public void onResponse(Call<List<MyWish>> call, Response<List<MyWish>> response) {
                if (response.isSuccessful()) {
                    myWishes = response.body();
                    int size = myWishes.size();
                    int i = 0;
                    if (size == 0)
                        myWishLayout.setVisibility(View.GONE);
                    else {
                        if (myWishes.get(i).getThumbnail() != null) {
                            String imageName = myWishes.get(i).getThumbnail();
                            if (imageName.startsWith("http://") || imageName.startsWith("https://"))
                                Glide.with(getContext()).load(imageName).into(myWishImage1);
                            else
                                Glide.with(getContext()).load("https://starry-night.s3.ap-northeast-2.amazonaws.com/postImage/" + imageName).into(myWishImage1);
                        } else {
                            myWishImage1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.default_image));
                        }
                        myWishTitle1.setText(myWishes.get(i).getTitle());
                        i++;

                        if (size > 1) {
                            if (myWishes.get(i).getThumbnail() != null) {
                                String imageName = myWishes.get(i).getThumbnail();
                                if (imageName.startsWith("http://") || imageName.startsWith("https://"))
                                    Glide.with(getContext()).load(imageName).into(myWishImage2);
                                else
                                    Glide.with(getContext()).load("https://starry-night.s3.ap-northeast-2.amazonaws.com/postImage/" + imageName).into(myWishImage2);
                            } else {
                                myWishImage2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.default_image));
                            }
                            myWishTitle2.setText(myWishes.get(i).getTitle());
                            i++;

                            if (size > 2) {
                                if (myWishes.get(i).getThumbnail() != null) {
                                    String imageName = myWishes.get(i).getThumbnail();
                                    if (imageName.startsWith("http://") || imageName.startsWith("https://"))
                                        Glide.with(getContext()).load(imageName).into(myWishImage3);
                                    else
                                        Glide.with(getContext()).load("https://starry-night.s3.ap-northeast-2.amazonaws.com/postImage/" + imageName).into(myWishImage3);
                                } else {
                                    myWishImage3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.default_image));
                                }
                                myWishTitle3.setText(myWishes.get(i).getTitle());
                            }
                        }
                    }

                } else {
                    Log.d(TAG, "내 찜 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<List<MyWish>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });


        //내 게시물 불러오는 api
        Call<List<MyPost3>> call5 = RetrofitClient.getApiService().getMyPost3(userId);
        call5.enqueue(new Callback<List<MyPost3>>() {
            @Override
            public void onResponse(Call<List<MyPost3>> call, Response<List<MyPost3>> response) {
                if (response.isSuccessful()) {
                    myPost3s = response.body();
                    int size = myPost3s.size();
                    int i = 0;
                    if (size == 0)
                        myPostLayout.setVisibility(View.GONE);
                    else {
                        if (myPost3s.get(i).getThumbnail() != null) {
                            Glide.with(getContext()).load("https://starry-night.s3.ap-northeast-2.amazonaws.com/postImage/" + myPost3s.get(i).getThumbnail()).into(myPostImage1);
                        } else
                            myPostImage1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.default_image));
                        myPostTitle1.setText(myPost3s.get(i).getTitle());
                        i++;
                        if (size > 1) {
                            if (myPost3s.get(i).getThumbnail() != null) {
                                Glide.with(getContext()).load("https://starry-night.s3.ap-northeast-2.amazonaws.com/postImage/" + myPost3s.get(i).getThumbnail()).into(myPostImage2);
                            } else
                                myPostImage2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.default_image));
                            myPostTitle2.setText(myPost3s.get(i).getTitle());
                            i++;
                            if (size > 2) {
                                if (myPost3s.get(i).getThumbnail() != null) {
                                    Glide.with(getContext()).load("https://starry-night.s3.ap-northeast-2.amazonaws.com/postImage/" + myPost3s.get(i).getThumbnail()).into(myPostImage3);
                                } else
                                    myPostImage3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.default_image));
                                myPostTitle3.setText(myPost3s.get(i).getTitle());
                            }
                        }
                    }

                } else {
                    Log.d(TAG, "내 게시물 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<List<MyPost3>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });

        //나의 여행 버킷리스트 페이지로 이동
        LinearLayout myWishBtn = v.findViewById(R.id.myWishBtn);
        myWishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyWishActivity.class);
                intent.putExtra("userId", userId);
                startActivityForResult(intent, HAVE_TO_REFRESH);
            }
        });
        myWishLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyWishActivity.class);
                intent.putExtra("userId", userId);
                startActivityForResult(intent, HAVE_TO_REFRESH);
            }
        });

        //내 게시물 페이지로 이동
        LinearLayout myPostBtn = v.findViewById(R.id.myPostBtn);
        myPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyPostActivity.class);
                intent.putExtra("userId", userId);
                startActivityForResult(intent, HAVE_TO_REFRESH);
            }
        });
        myPostLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyPostActivity.class);
                intent.putExtra("userId", userId);
                startActivityForResult(intent, HAVE_TO_REFRESH);
            }
        });

        //게시물 작성 페이지로 이동
        Button goPostWrite = v.findViewById(R.id.goPostWrite);
        goPostWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PostWriteActivity.class);
                startActivityForResult(intent, HAVE_TO_REFRESH);
            }
        });

        //설정 페이지로 이동
        Button goSetting = v.findViewById(R.id.goSetting);
        goSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                intent.putExtra("userId", userId);
                startActivityForResult(intent, HAVE_TO_REFRESH);
            }
        });

        //프로필 변경 페이지로 이동
        ImageView profileImage = v.findViewById(R.id.profileImage);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangeProfileActivity.class);
                intent.putExtra("userId", userId);
                startActivityForResult(intent, HAVE_TO_REFRESH);
            }
        });
        ImageView pencil = v.findViewById(R.id.pencil);
        pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangeProfileActivity.class);
                intent.putExtra("userId", userId);
                startActivityForResult(intent, HAVE_TO_REFRESH);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HAVE_TO_REFRESH) {
            Log.e(TAG, "새로고침");
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