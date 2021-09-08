package com.starrynight.tourapiproject.searchPage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.myPage.myWish.obtp.MyWishObTp;
import com.starrynight.tourapiproject.myPage.myWish.obtp.MyWishObTpAdapter;
import com.starrynight.tourapiproject.myPage.myWish.obtp.OnMyWishObTpItemClickListener;
import com.starrynight.tourapiproject.myPage.myWish.post.MyPost;
import com.starrynight.tourapiproject.searchPage.searchPageRetrofit.Filter;
import com.starrynight.tourapiproject.searchPage.searchPageRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.touristPointPage.TouristPointActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultFragment extends Fragment {

    Long[] areaCode = {1L, 31L, 2L, 32L, 33L, 34L, 3L, 8L, 37L, 5L, 38L, 35L, 4L, 36L, 6L, 7L, 39L}; //관광지 지역코드
    String[] areaName = {"서울", "경기", "인천", "강원", "충북", "충남", "대전", "세종", "전북", "광주", "전남", "경북", "대구", "경남", "부산", "울산", "제주"};
    String[] hashTagName = {"공기 좋은", "깔끔한", "감성적인", "이색적인", "인생샷", "전문적인", "캠핑", "차박", "뚜벅이", "드라이브",
            "반려동물", "한적한", "근교", "도심 속", "연인", "가족", "친구", "혼자", "가성비", "소확행", "럭셔리한", "경치 좋은"};

    TextView obText;
    TextView tpText;
    TextView postText;
    Button obBtn;
    Button tpBtn;
    Button postBtn;

    LinearLayout selectFilterItem; //선택한 필터들이 보이는 레이아웃

    List<MyWishObTp> obResult; //관측지 필터 결과
    List<MyWishObTp> tpResult; //관광지 필터 결과
    List<MyPost> postResult; //게시물 필터 결과

    ArrayList<Integer> area;
    ArrayList<Integer> hashTag;
    List<Long> areaCodeList;
    List<Long> hashTagIdList;

    public static SearchResultFragment newInstance() {
        return new SearchResultFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_result, container, false);

        obText = v.findViewById(R.id.obText);
        tpText = v.findViewById(R.id.tpText);
        postText = v.findViewById(R.id.postText);
        obBtn = v.findViewById(R.id.obBtn);
        tpBtn = v.findViewById(R.id.tpBtn);
        postBtn = v.findViewById(R.id.postBtn);
        selectFilterItem = v.findViewById(R.id.selectFilterItem);
        selectFilterItem.removeAllViews(); //초기화


        //필터 결과 리사이클러뷰
        System.out.println("리사이클러뷰 설정");
        RecyclerView searchResult = v.findViewById(R.id.searchResult);
        LinearLayoutManager searchLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        searchResult.setLayoutManager(searchLayoutManager);
        searchResult.setHasFixedSize(true);
        obResult = new ArrayList<>();
        tpResult = new ArrayList<>();
        postResult = new ArrayList<>();


        //지도 페이지로
        Button map_btn = (Button) v.findViewById(R.id.mapBtn2);
        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((MainActivity)getActivity()).replaceFragment(MapFragment.newInstance());
            }
        });


        //필터 고르는 페이지로
        Button filter_btn = (Button) v.findViewById(R.id.filterBtn2);
        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment filterFragment = new FilterFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_view, filterFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                ((MainActivity)getActivity()).showOffBottom();
            }
        });

        //처음에 아무 버튼 안눌러도 관측지 필터 결과 보이게 구현



        //관측지 필터 결과 구현
        obBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obText.setVisibility(View.VISIBLE);
                tpText.setVisibility(View.GONE);
                postText.setVisibility(View.GONE);


            }
        });

        //게시물 필터 결과 구현
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obText.setVisibility(View.GONE);
                tpText.setVisibility(View.GONE);
                postText.setVisibility(View.VISIBLE);


            }
        });

        //관광지 필터 결과
        tpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obText.setVisibility(View.GONE);
                tpText.setVisibility(View.VISIBLE);
                postText.setVisibility(View.GONE);

                areaCodeList = new ArrayList<>();
                hashTagIdList = new ArrayList<>();
                areaCodeList.add(0L);
                hashTagIdList.add(0L);

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

                Filter filter = new Filter(areaCodeList, hashTagIdList);
                Call<List<MyWishObTp>> call = RetrofitClient.getApiService().getTouristDataWithFilter(filter);
                call.enqueue(new Callback<List<MyWishObTp>>() {
                    @Override
                    public void onResponse(Call<List<MyWishObTp>> call, Response<List<MyWishObTp>> response) {
                        if (response.isSuccessful()) {
                            tpResult = response.body();

                            MyWishObTpAdapter myWishObAdapter = new MyWishObTpAdapter(tpResult, getContext());
                            searchResult.setAdapter(myWishObAdapter);
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

        if (getArguments() != null)
        {
            ((MainActivity)getActivity()).showBottom();

            int type = getArguments().getInt("type");
            if (type == 1){
                System.out.println("값 넘어옴");
                area = getArguments().getIntegerArrayList("area"); //선택한 지역 필터
                hashTag = getArguments().getIntegerArrayList("hashTag"); //선택한 해시태그 필터

                selectFilterItem.removeAllViews(); //초기화
                for(int i=0; i<17; i++){
                    if(area.get(i) == 1){
                        TextView textView = new TextView(getContext());
                        textView.setText(" "+ areaName[i] + " ");
                        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_200));
                        textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.observation__hashtags));
                        selectFilterItem.addView(textView);
                    }
                }
                for(int i=0; i<22; i++){
                    if(hashTag.get(i) == 1){
                        TextView textView = new TextView(getContext());
                        textView.setText("#" + hashTagName[i]);
                        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_200));
                        textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.observation__hashtags));
                        selectFilterItem.addView(textView);
                    }
                }
            }
        }
        return v;
    }
}