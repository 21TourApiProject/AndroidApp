//package com.starrynight.tourapiproject;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.starrynight.tourapiproject.myPage.myWish.observation.MyObWishAdapter;
//import com.starrynight.tourapiproject.myPage.myWish.post.MyPostWishAdapter;
//import com.starrynight.tourapiproject.myPage.myWish.post.OnMyPostWishItemClickListener;
//import com.starrynight.tourapiproject.myPage.myWish.touristPoint.MyTpWishAdapter;
//import com.starrynight.tourapiproject.postItemPage.Post_point_item_Adapter;
//import com.starrynight.tourapiproject.postItemPage.post_point_item;
//
//public class WishActivity extends AppCompatActivity {
//
//    RecyclerView myPostWishList;
//    RecyclerView myObWishList;
//    RecyclerView myTpWishList;
//    MyPostWishAdapter myPostWishAdapter;
//    MyTpWishAdapter myTpWishAdapter;
//    MyObWishAdapter myObWishAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wish);
//
//        //찜 관련 리사이클러(게시물, 관측지, 관광지)
//        myPostWishList = v.findViewById(R.id.myWishList);
//        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        myPostWishList.setLayoutManager(layoutManager1);
//
//        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        myObWishList.setLayoutManager(layoutManager2);
//
//        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        myTpWishList.setLayoutManager(layoutManager3);
//
//
//
//
//
//        //찜(관측지) 버튼 클릭
//        Button myOb = v.findViewById(R.id.myOb);
//        myOb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myObWishAdapter = new MyObWishAdapter();
//                myObWishList.setVisibility(View.VISIBLE);
//                myPostWishList.setVisibility(View.GONE);
//                myTpWishList.setVisibility(View.GONE);
//                // 찜(관측지) 불러오는 get api
//
//            }
//        });
//        //찜(관측지) 클릭 이벤트
//        myObWishAdapter.setOnMyWishItemClickListener(new OnMyPostWishItemClickListener() {
//            @Override
//            public void onItemClick(MyPostWishAdapter.ViewHolder holder, View view, int position) {
//                Toast.makeText(getActivity().getApplicationContext(), ""+"번 관측지 클릭", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        //찜(관광지) 버튼 클릭
//        Button myTour = v.findViewById(R.id.myTour);
//        myTour.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myTpWishAdapter = new MyTpWishAdapter();
//                myTpWishList.setVisibility(View.VISIBLE);
//                myPostWishList.setVisibility(View.GONE);
//                myObWishList.setVisibility(View.GONE);
//                // 찜(관광지) 불러오는 get api
//
//            }
//        });
//        //찜(관광지) 클릭 이벤트
//        myTpWishAdapter.setOnMyWishItemClickListener(new OnMyPostWishItemClickListener() {
//            @Override
//            public void onItemClick(MyPostWishAdapter.ViewHolder holder, View view, int position) {
//                Toast.makeText(getActivity().getApplicationContext(), ""+"번 관광지 클릭", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        RecyclerView recyclerView = v.findViewById(R.id.personrecyclerview);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//
//        Post_point_item_Adapter adapter = new Post_point_item_Adapter();
//        recyclerView.setAdapter(adapter);
//
//        adapter.addItem(new post_point_item("내 게시물","https://cdn.pixabay.com/photo/2018/08/11/20/37/cathedral-3599450_960_720.jpg"));
//
//    }
//}