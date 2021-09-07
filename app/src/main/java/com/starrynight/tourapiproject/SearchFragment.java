package com.starrynight.tourapiproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.myPage.MyWishActivity;
import com.starrynight.tourapiproject.myPage.myWish.obtp.MyWishObTp;
import com.starrynight.tourapiproject.myPage.myWish.obtp.MyWishObTpAdapter;
import com.starrynight.tourapiproject.myPage.myWish.obtp.OnMyWishObTpItemClickListener;
import com.starrynight.tourapiproject.myPage.myWish.post.MyPost;
import com.starrynight.tourapiproject.observationPage.ObservationsiteActivity;
import com.starrynight.tourapiproject.postItemPage.OnPostPointItemClickListener;
import com.starrynight.tourapiproject.postItemPage.Post_point_item_Adapter;
import com.starrynight.tourapiproject.postItemPage.post_point_item;
import com.starrynight.tourapiproject.searchPage.FilterActivity;
import com.starrynight.tourapiproject.searchPage.searchPageRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.touristPointPage.TouristPointActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private static final int FILTER = 101;
    Long[] areaCode = {1L, 31L, 2L, 32L, 33L, 34L, 3L, 8L, 37L, 5L, 38L, 35L, 4L, 36L, 6L, 7L, 39L}; //관광지 지역코드
    String[] hashTagName = {"공기 좋은", "깔끔한", "감성적인", "이색적인", "인생샷", "전문적인", "캠핑", "차박", "뚜벅이", "드라이브",
            "반려동물", "한적한", "근교", "도심 속", "연인", "가족", "친구", "혼자", "가성비", "소확행", "럭셔리한", "경치 좋은"};

    List<MyWishObTp> obResult = new ArrayList<>(); //관측지 필터 결과
    List<MyWishObTp> tpResult = new ArrayList<>(); //관광지 필터 결과
    List<MyPost> postResult = new ArrayList<>(); //게시물 필터 결과

    LinearLayout firstContent; //검색탭 들어왔을 때 처음에 보이는 레이아웃
    LinearLayout searchResult; //필터선택 또는 검색했을 때 보이는 레이아웃
    LinearLayout selectFilterItem; //선택한 필터가 보이는 레이아웃

    Button obBtn;
    Button tpBtn;
    Button postBtn;

    RecyclerView obSearchResult; //관측지 필터 결과 리사이클러뷰
    RecyclerView tpSearchResult; //관광지 필터 결과 리사이클러뷰
    RecyclerView postSearchResult; //게시물 필터 결과 리사이클러뷰

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        firstContent = v.findViewById(R.id.firstContent);
        searchResult = v.findViewById(R.id.searchResult);
        selectFilterItem = v.findViewById(R.id.selectFilterItem);
        obBtn = v.findViewById(R.id.obBtn);
        tpBtn = v.findViewById(R.id.tpBtn);
        postBtn = v.findViewById(R.id.postBtn);


        tpSearchResult = v.findViewById(R.id.tpSearchResult);
        LinearLayoutManager tpLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        tpSearchResult.setLayoutManager(tpLayoutManager);
        tpSearchResult.setHasFixedSize(true);


        //지도 페이지로
        Button map_btn = (Button) v.findViewById(R.id.mapBtn);
        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((MainActivity)getActivity()).replaceFragment(MapFragment.newInstance());
            }
        });

        //필터 고르는 페이지로
        Button filter_btn = (Button) v.findViewById(R.id.filterBtn);
        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                startActivityForResult(intent, FILTER);
            }
        });


        //요즘 핫한 밤하늘 명소
        RecyclerView recyclerView = v.findViewById(R.id.hotpointRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        Post_point_item_Adapter adapter = new Post_point_item_Adapter();
        recyclerView.setAdapter(adapter);
        adapter.addItem(new post_point_item("게시글1","https://cdn.pixabay.com/photo/2018/08/11/20/37/cathedral-3599450_960_720.jpg"));
        adapter.addItem(new post_point_item("게시글2","https://cdn.pixabay.com/photo/2018/07/15/23/22/prague-3540883_960_720.jpg"));
        adapter.addItem(new post_point_item("게시글3","https://cdn.pixabay.com/photo/2019/12/13/07/35/city-4692432_960_720.jpg"));
        adapter.setOnItemClicklistener(new OnPostPointItemClickListener() {
            @Override
            public void onItemClick(Post_point_item_Adapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(getActivity(), ObservationsiteActivity.class);
                intent.putExtra("observationId", 1L);
                startActivity(intent);
            }
        });
        //나와 가까운 밤하늘 명소
        RecyclerView recyclerView2 = v.findViewById(R.id.nearPointRecyclerView);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        Post_point_item_Adapter adapter2 = new Post_point_item_Adapter();
        recyclerView2.setAdapter(adapter2);
        adapter2.addItem(new post_point_item("게시글1","https://cdn.pixabay.com/photo/2018/08/11/20/37/cathedral-3599450_960_720.jpg"));
        adapter2.addItem(new post_point_item("게시글2","https://cdn.pixabay.com/photo/2018/07/15/23/22/prague-3540883_960_720.jpg"));
        adapter2.addItem(new post_point_item("게시글3","https://cdn.pixabay.com/photo/2019/12/13/07/35/city-4692432_960_720.jpg"));

        adapter2.setOnItemClicklistener(new OnPostPointItemClickListener() {
            @Override
            public void onItemClick(Post_point_item_Adapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(getActivity(), TouristPointActivity.class);
                intent.putExtra("contentId", 125266L);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == FILTER){ //필터를 골랐으면
            Bundle bundle = getArguments();
            int type = bundle.getInt("type");
            if (type == 1){
                firstContent.setVisibility(View.GONE); //이건 사라지고
                searchResult.setVisibility(View.VISIBLE); //이건 보이게

                ArrayList<Integer> area = bundle.getIntegerArrayList("area"); //선택한 지역 필터
                ArrayList<Integer> hashTag = bundle.getIntegerArrayList("hashTag"); //선택한 해시태그 필터

                for(int i=0; i<22; i++){
                    if(hashTag.get(i) == 1){
                        TextView textView = new TextView(getContext());
                        textView.setText("#"+hashTagName[i]);
                        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_200));
                        textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.observation__hashtags));
                        selectFilterItem.addView(textView);
                    }
                }

                //처음에 아무 버튼 안눌러도 관측지 필터 결과 보이게 구현


                //관측지 필터 결과 구현
                obBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                //게시물 필터 결과 구현
                postBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                //관광지 필터 결과
                tpBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<Long> areaCodeList = new ArrayList<>();
                        List<Long> hashTagIdList = new ArrayList<>();

                        for(int i=0; i<17; i++){
                            if (area.get(i) == 1){ //선택했으면
                                areaCodeList.add(areaCode[i]);
                            }
                        }
                        for(int i=0; i<22; i++){
                            if (hashTag.get(i) == 1){ //선택했으면
                                hashTagIdList.add((long)(i+1));
                            }
                        }
                        Call<List<MyWishObTp>> call = RetrofitClient.getApiService().getTouristDataWithFilter(areaCodeList, hashTagIdList);
                        call.enqueue(new Callback<List<MyWishObTp>>() {
                            @Override
                            public void onResponse(Call<List<MyWishObTp>> call, Response<List<MyWishObTp>> response) {
                                if (response.isSuccessful()) {
                                    tpResult = response.body();

                                    MyWishObTpAdapter myWishObAdapter = new MyWishObTpAdapter(tpResult, getContext());
                                    tpSearchResult.setAdapter(myWishObAdapter);
                                    myWishObAdapter.setOnMyWishObTpItemClickListener(new OnMyWishObTpItemClickListener() {
                                        @Override
                                        public void onItemClick(MyWishObTpAdapter.ViewHolder holder, View view, int position) {
                                            MyWishObTp item = myWishObAdapter.getItem(position);
                                            Intent intent = new Intent(getContext(), TouristPointActivity.class);
                                            intent.putExtra("contentId", item.getItemId());
                                            startActivity(intent);
                                        }
                                    });
                                } else {
                                    System.out.println("관광지 필터 검색 실패");
                                }
                            }
                            @Override
                            public void onFailure(Call<List<MyWishObTp>> call, Throwable t) {
                                Log.e("연결실패", t.getMessage());
                            }
                        });
                    }
                });
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}