package com.starrynight.tourapiproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.starrynight.tourapiproject.postItemPage.OnPostItemClickListener;
import com.starrynight.tourapiproject.postItemPage.Post_item_adapter;
import com.starrynight.tourapiproject.postItemPage.post_item;
import com.starrynight.tourapiproject.postPage.PostActivity;
import com.starrynight.tourapiproject.postWritePage.PostWriteActivity;
import com.starrynight.tourapiproject.signUpPage.SignUpActivity;
import com.starrynight.tourapiproject.weatherPage.WeatherActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    Layout_main layout_main;

    public MainFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        Post_item_adapter adapter = new Post_item_adapter();
        recyclerView.setAdapter(adapter);

        adapter.addItem(new post_item(" #hash","#hash2","제목1","닉네임1","https://cdn.pixabay.com/photo/2017/05/27/06/18/starry-sky-2347801_960_720.jpg","https://img-premium.flaticon.com/png/512/1144/1144811.png?token=exp=1627537493~hmac=2f43e8605ee99c9aec9e5491069d0d3c"));
        adapter.addItem(new post_item(" #hash3","#hash4","제목2","닉네임2","https://cdn.pixabay.com/photo/2017/10/09/09/26/the-atacama-desert-2832866_960_720.jpg","https://img-premium.flaticon.com/png/512/1144/1144811.png?token=exp=1627537493~hmac=2f43e8605ee99c9aec9e5491069d0d3c"));
        adapter.addItem(new post_item(" #hash5","#hash6","제목3","닉네임3","https://cdn.pixabay.com/photo/2018/12/06/02/00/the-milky-way-3859013_960_720.jpg","https://img-premium.flaticon.com/png/512/1144/1144811.png?token=exp=1627537493~hmac=2f43e8605ee99c9aec9e5491069d0d3c"));
        adapter.addItem(new post_item(" #hash7","#hash8","제목4","닉네임4","https://cdn.pixabay.com/photo/2017/02/14/11/47/natural-2065714_960_720.jpg","https://img-premium.flaticon.com/png/512/1144/1144811.png?token=exp=1627537493~hmac=2f43e8605ee99c9aec9e5491069d0d3c"));
        adapter.setOnItemClicklistener(new OnPostItemClickListener() {
            @Override
            public void onItemClick(Post_item_adapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(getActivity(), PostActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        recyclerView.setAdapter((adapter));

        Button button =(Button) v.findViewById(R.id.weather_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), WeatherActivity.class);
                startActivity(intent);
            }
        });

        // 게시물 작성 페이지로 넘어가는 이벤트
        Button postWrite =(Button) v.findViewById(R.id.postWrite);
        postWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), PostWriteActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        // 회원가입 페이지로 넘어가는 이벤트(수정 예정)
        Button testSignUp = (Button) v.findViewById(R.id.testSignUp);
        testSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, 103);
            }
        });
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}