package com.starrynight.tourapiproject.searchPage;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.mapPage.Activities;
import com.starrynight.tourapiproject.mapPage.MapFragment;
import com.starrynight.tourapiproject.myPage.myWish.post.MyPost;
import com.starrynight.tourapiproject.myPage.myWish.post.MyPostAdapter;
import com.starrynight.tourapiproject.myPage.myWish.post.OnMyPostItemClickListener;
import com.starrynight.tourapiproject.observationPage.ObservationsiteActivity;
import com.starrynight.tourapiproject.postPage.PostActivity;
import com.starrynight.tourapiproject.searchPage.searchPageRetrofit.Filter;
import com.starrynight.tourapiproject.searchPage.searchPageRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.searchPage.searchPageRetrofit.SearchKey;
import com.starrynight.tourapiproject.searchPage.searchPageRetrofit.SearchLoadingDialog;
import com.starrynight.tourapiproject.searchPage.searchPageRetrofit.SearchParams1;
import com.starrynight.tourapiproject.touristPointPage.TouristPointActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchResultFragment extends Fragment {

    private static final String TAG = "search fragment";

    Long[] areaCode = {1L, 31L, 2L, 32L, 33L, 34L, 3L, 8L, 37L, 5L, 38L, 35L, 4L, 36L, 6L, 7L, 39L}; //관광지 지역코드
    String[] areaName = {"서울", "경기", "인천", "강원", "충북", "충남", "대전", "세종", "전북", "광주", "전남", "경북", "대구", "경남", "부산", "울산", "제주"};
    String[] hashTagName = {"공기 좋은", "깔끔한", "감성적인", "이색적인", "인생샷", "전문적인", "캠핑", "차박", "뚜벅이", "드라이브",
            "반려동물", "한적한", "근교", "도심 속", "연인", "가족", "친구", "혼자", "가성비", "소확행", "럭셔리한", "경치 좋은"};

    TextView moreObText, moreTpText, morePostText,no_result;
    Button obBtn;
    Button tpBtn;
    Button postBtn;
    Button allContentBtn;
    ImageView obBtnTap;
    ImageView tpBtnTap;
    ImageView postBtnTap;
    ImageView obline;
    ImageView tpline;
    ImageView postline;
    ImageView allContentBtnTap;
    androidx.appcompat.widget.SearchView searchView;

    RecyclerView searchResult;
    RecyclerView searchResult2;
    RecyclerView searchResult3;
    RecyclerView searchResult4;
    RecyclerView searchResult5;
    RecyclerView searchResult6;

    LinearLayout selectFilterItem; //선택한 필터들이 보이는 레이아웃

    List<SearchParams1> obResult; //관측지 필터 결과
    List<SearchParams1> tpResult; //관광지 필터 결과

    List<SearchParams1> finalTpResult = new ArrayList<>();
    List<SearchParams1> finalObResult = new ArrayList<>();
    List<MyPost> postResult;//게시물 필터 결과
    List<MyPost> finalPostResult = new ArrayList<>();
    ArrayList<Integer> area; //어떤 지역필터 선택했는지 Integer값(0이면 선택x, 1이면 선택o)으로 받아온 배열
    ArrayList<Integer> hashTag; //어떤 해시태그필터 선택했는지 Integer값(0이면 선택x, 1이면 선택o)으로 받아온 배열

    NestedScrollView nestedScrollView;
    ProgressBar searchProgressBar;
    int count = 10, end, limit; // 1페이지에 10개씩 데이터를 불러온다(관광지)
    Boolean noMoreTp;
    SearchResultAdapter2 tpAdapter; //관광지 검색결과 어댑터
    List<SearchParams1> subTpResult;

    List<Long> areaCodeList;
    List<Long> hashTagIdList;

    String keyword;
    SearchLoadingDialog dialog;

    public static SearchResultFragment newInstance() {
        return new SearchResultFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_result, container, false);

        searchView = v.findViewById(R.id.search2);
        moreObText = v.findViewById(R.id.moreOb_text);
        moreTpText = v.findViewById(R.id.moreTp_text);
        morePostText = v.findViewById(R.id.morePost_text);
        obBtn = v.findViewById(R.id.obBtn);
        tpBtn = v.findViewById(R.id.tpBtn);
        postBtn = v.findViewById(R.id.postBtn);
        obline = v.findViewById(R.id.ob_line);
        tpline = v.findViewById(R.id.tp_line);
        postline = v.findViewById(R.id.post_line);
        allContentBtn = v.findViewById(R.id.allContentBtn);
        obBtnTap = v.findViewById(R.id.obBtn_tap);
        tpBtnTap = v.findViewById(R.id.tpBtn_tap);
        postBtnTap = v.findViewById(R.id.postBtn_tap);
        allContentBtnTap = v.findViewById(R.id.allContent_tap);
        selectFilterItem = v.findViewById(R.id.selectFilterItem);
        selectFilterItem.removeAllViews(); //초기화
        dialog = new SearchLoadingDialog(getContext());
        no_result= v.findViewById(R.id.no_result_text);
        //필터 결과 리사이클러뷰
        searchResult = v.findViewById(R.id.searchResult);
        searchResult2 = v.findViewById(R.id.searchResult2);
        searchResult3 = v.findViewById(R.id.searchResult3);
        searchResult4 = v.findViewById(R.id.searchResult4);
        searchResult5 = v.findViewById(R.id.searchResult5);
        searchResult6 = v.findViewById(R.id.searchResult6);
        LinearLayoutManager searchLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager searchLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager searchLayoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager searchLayoutManager4 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager searchLayoutManager5 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager searchLayoutManager6 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        searchLayoutManager.isAutoMeasureEnabled();
        searchLayoutManager2.isAutoMeasureEnabled();
        searchLayoutManager3.isAutoMeasureEnabled();
        searchLayoutManager4.isAutoMeasureEnabled();
        searchLayoutManager5.isAutoMeasureEnabled();
        searchLayoutManager6.isAutoMeasureEnabled();
        searchResult.setLayoutManager(searchLayoutManager);
        searchResult2.setLayoutManager(searchLayoutManager2);
        searchResult3.setLayoutManager(searchLayoutManager3);
        searchResult4.setLayoutManager(searchLayoutManager4);
        searchResult5.setLayoutManager(searchLayoutManager5);
        searchResult6.setLayoutManager(searchLayoutManager6);
        obResult = new ArrayList<>();
        tpResult = new ArrayList<>();
        postResult = new ArrayList<>();
        nestedScrollView = v.findViewById(R.id.scroll_view);
        searchProgressBar = v.findViewById(R.id.searchProgressBar);


        if (getArguments() != null) {
            ((MainActivity) getActivity()).showBottom();

            int type = getArguments().getInt("type");
            if (type == 1) {
                area = getArguments().getIntegerArrayList("area"); //선택한 지역 필터
                hashTag = getArguments().getIntegerArrayList("hashTag"); //선택한 해시태그 필터
                keyword = getArguments().getString("keyword");

                selectFilterItem.removeAllViews(); //초기화
                for (int i = 0; i < 17; i++) {
                    if (area.get(i) == 1) {
                        TextView textView = new TextView(getContext());
                        textView.setText(" " + areaName[i] + " ");
                        textView.setTextSize(10);
                        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.hashtag_background));
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.rightMargin = 20;
                        textView.setLayoutParams(params);
                        selectFilterItem.addView(textView);
                        selectFilterItem.setDividerPadding(5);
                    }
                }
                for (int i = 0; i < 22; i++) {
                    if (hashTag.get(i) == 1) {
                        TextView textView = new TextView(getContext());
                        textView.setText("#" + hashTagName[i]);
                        textView.setTextSize(10);
                        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.hashtag_background));
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.rightMargin = 20;
                        textView.setLayoutParams(params);
                        selectFilterItem.addView(textView);
                        selectFilterItem.setDividerPadding(5);
                    }
                }
                areaCodeList = new ArrayList<>();
                hashTagIdList = new ArrayList<>();
                for (int i = 0; i < 17; i++) {
                    if (area.get(i) == 1) { //선택했으면
                        areaCodeList.add(areaCode[i]);
                    }
                }
                for (int i = 0; i < 22; i++) {
                    if (hashTag.get(i) == 1) { //선택했으면
                        hashTagIdList.add((long) (i + 1));
                    }
                }
                Filter filter = new Filter(areaCodeList, hashTagIdList);
                SearchKey searchKey = new SearchKey(filter, keyword);
                searchEverything(searchKey);

                if (keyword == null) {
                    searchView.setQueryHint("원하는 것을 검색해보세요");
                } else {
                    searchView.setQuery(keyword, false);
                }

            } else if (type == 2) {
                //searchpage에서 검색
                keyword = getArguments().getString("keyword");
                area = new ArrayList<Integer>(Collections.nCopies(17, 0));
                hashTag = new ArrayList<Integer>(Collections.nCopies(22, 0));
                areaCodeList = new ArrayList<>();
                hashTagIdList = new ArrayList<>();
                Filter filter = new Filter(areaCodeList, hashTagIdList);
                SearchKey searchKey = new SearchKey(filter, keyword);
                searchEverything(searchKey);

            } else if (type == 3) {
                //지도에서 넘어옴
                area = getArguments().getIntegerArrayList("area"); //선택한 지역 필터
                hashTag = getArguments().getIntegerArrayList("hashTag"); //선택한 해시태그 필터
                keyword = getArguments().getString("keyword");

                selectFilterItem.removeAllViews(); //초기화
                for (int i = 0; i < 17; i++) {
                    if (area.get(i) == 1) {
                        TextView textView = new TextView(getContext());
                        textView.setText(" " + areaName[i] + " ");
                        textView.setTextSize(10);
                        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.hashtag_background));
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.rightMargin = 20;
                        textView.setLayoutParams(params);
                        selectFilterItem.addView(textView);
                        selectFilterItem.setDividerPadding(5);
                    }
                }
                for (int i = 0; i < 22; i++) {
                    if (hashTag.get(i) == 1) {
                        TextView textView = new TextView(getContext());
                        textView.setText("#" + hashTagName[i]);
                        textView.setTextSize(10);
                        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.hashtag_background));
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.rightMargin = 20;
                        textView.setLayoutParams(params);
                        selectFilterItem.addView(textView);
                        selectFilterItem.setDividerPadding(5);
                    }
                }
                if (keyword == null) {
                    searchView.setQueryHint("원하는 것을 검색해보세요");
                } else {
                    searchView.setQuery(keyword, false);
                }

                areaCodeList = new ArrayList<>();
                hashTagIdList = new ArrayList<>();
                for (int i = 0; i < 17; i++) {
                    if (area.get(i) == 1) { //선택했으면
                        areaCodeList.add(areaCode[i]);
                    }
                }
                for (int i = 0; i < 22; i++) {
                    if (hashTag.get(i) == 1) { //선택했으면
                        hashTagIdList.add((long) (i + 1));
                    }
                }
                Filter filter = new Filter(areaCodeList, hashTagIdList);
                SearchKey searchKey = new SearchKey(filter, keyword);
                searchEverything(searchKey);
            }
        }

        allContentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreObText.setVisibility(View.VISIBLE);
                moreTpText.setVisibility(View.VISIBLE);
                morePostText.setVisibility(View.VISIBLE);
                searchResult.setVisibility(View.VISIBLE);
                searchResult2.setVisibility(View.VISIBLE);
                searchResult3.setVisibility(View.VISIBLE);
                searchResult4.setVisibility(View.GONE);
                searchResult5.setVisibility(View.GONE);
                searchResult6.setVisibility(View.GONE);
                obline.setVisibility(View.VISIBLE);
                tpline.setVisibility(View.VISIBLE);
                postline.setVisibility(View.VISIBLE);
                allContentBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap));
                tpBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap_non));
                obBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap_non));
                postBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap_non));
                areaCodeList = new ArrayList<>();
                hashTagIdList = new ArrayList<>();


                for (int i = 0; i < 17; i++) {
                    if (area.get(i) == 1) { //선택했으면
                        areaCodeList.add(areaCode[i]);
                    }
                }
                for (int i = 0; i < 22; i++) {
                    if (hashTag.get(i) == 1) { //선택했으면
                        hashTagIdList.add((long) (i + 1));
                    }
                }
                Filter filter = new Filter(areaCodeList, hashTagIdList);
                SearchKey searchKey = new SearchKey(filter, keyword);
                searchEverything(searchKey);
            }
        });
        if (keyword == null) {
            searchView.setQueryHint("원하는 것을 검색해보세요");
        } else {
            searchView.setQuery(keyword, false);
        }
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                keyword = query;
                areaCodeList = new ArrayList<>();
                hashTagIdList = new ArrayList<>();
                for (int i = 0; i < 17; i++) {
                    if (area.get(i) == 1) { //선택했으면
                        areaCodeList.add(areaCode[i]);
                    }
                }
                for (int i = 0; i < 22; i++) {
                    if (hashTag.get(i) == 1) { //선택했으면
                        hashTagIdList.add((long) (i + 1));
                    }
                }
                Filter filter = new Filter(areaCodeList, hashTagIdList);
                SearchKey searchKey = new SearchKey(filter, keyword);
                searchEverything(searchKey);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        //지도 페이지로
        ImageButton map_btn = (ImageButton) v.findViewById(R.id.mapBtn2);
        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                bundle.putSerializable("FromWhere", Activities.SEARCHRESULT);
                bundle.putIntegerArrayList("area", area);
                bundle.putIntegerArrayList("hashTag", hashTag);
                bundle.putString("keyword", keyword);
                MapFragment mapFragment = new MapFragment();
                mapFragment.setArguments(bundle);
                ((MainActivity) getActivity()).setMap(mapFragment);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.main_view, mapFragment);
                transaction.remove(SearchResultFragment.this);
//                ((MainActivity) getActivity()).setSearchResult(null);
                transaction.addToBackStack("result");
                transaction.commit();
//                ((MainActivity)getActivity()).replaceFragment(mapFragment);
            }
        });


        //필터 고르는 페이지로
        ImageButton filter_btn = (ImageButton) v.findViewById(R.id.filterBtn2);
        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("keyword", keyword);
                bundle.putSerializable("fromWhere", Activities.SEARCHRESULT);
                Fragment filterFragment = ((MainActivity) getActivity()).getFilter();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (((MainActivity) getActivity()).getFilter() == null) {
                    filterFragment = new FilterFragment();
                    filterFragment.setArguments(bundle);
                    ((MainActivity) getActivity()).setFilter(filterFragment);
                    transaction.add(R.id.main_view, filterFragment);
                    transaction.remove(SearchResultFragment.this);
//                    ((MainActivity) getActivity()).setSearchResult(null);
                    transaction.addToBackStack("result");
                    transaction.commit();
                } else {
                    filterFragment.setArguments(bundle);
                    transaction.addToBackStack("result");
                    transaction.remove(SearchResultFragment.this);
//                    ((MainActivity) getActivity()).setSearchResult(null);
                    transaction.show(filterFragment);
                    transaction.commit();
                }
            }
        });

        //처음에 아무 버튼 안눌러도 관측지 필터 결과 보이게 구현


        //관측지 필터 결과 구현 - 채형
        obBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreObText.setVisibility(View.GONE);
                moreTpText.setVisibility(View.GONE);
                morePostText.setVisibility(View.GONE);
                searchResult.setVisibility(View.GONE);
                searchResult2.setVisibility(View.GONE);
                searchResult3.setVisibility(View.GONE);
                searchResult4.setVisibility(View.VISIBLE);
                searchResult5.setVisibility(View.GONE);
                searchResult6.setVisibility(View.GONE);
                obline.setVisibility(View.GONE);
                tpline.setVisibility(View.GONE);
                postline.setVisibility(View.GONE);
                allContentBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap_non));
                tpBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap_non));
                obBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap));
                postBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap_non));
                areaCodeList = new ArrayList<>();
                hashTagIdList = new ArrayList<>();
//                areaCodeList.add(0L);
//                hashTagIdList.add(0L);

                for (int i = 0; i < 17; i++) {
                    if (area.get(i) == 1) { //선택했으면
                        areaCodeList.add(areaCode[i]);
                    }
                }
                for (int i = 0; i < 22; i++) {
                    if (hashTag.get(i) == 1) { //선택했으면
                        hashTagIdList.add((long) (i + 1));
                    }
                }

                Filter filter = new Filter(areaCodeList, hashTagIdList);
                SearchKey searchKey = new SearchKey(filter, keyword);
                Call<List<SearchParams1>> call = RetrofitClient.getApiService().getObservationWithFilter(searchKey);
                call.enqueue(new Callback<List<SearchParams1>>() {
                    @Override
                    public void onResponse(Call<List<SearchParams1>> call, Response<List<SearchParams1>> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "관측지 검색 성공");
                            obResult = response.body();

                            //게시물은 어댑터 따로 만들어야 함
                            SearchResultAdapter searchResultAdapter = new SearchResultAdapter(obResult, getContext());
                            searchResult4.setAdapter(searchResultAdapter);
                            if (obResult.isEmpty()){
                                no_result.setVisibility(View.VISIBLE);
                            }else{no_result.setVisibility(View.GONE);}
                            searchResultAdapter.setOnSearchResultItemClickListener(new OnSearchResultItemClickListener() {
                                @Override
                                public void onItemClick(SearchResultAdapter.ViewHolder holder, View view, int position) {
                                    SearchParams1 item = searchResultAdapter.getItem(position);
                                    Intent intent = new Intent(getContext(), ObservationsiteActivity.class);
                                    intent.putExtra("observationId", item.getItemId());
                                    startActivity(intent);
                                }
                            });
                        } else {
                            Log.e(TAG, "관측지 검색 실패");
                            moreObText.setVisibility(View.GONE);
                            obline.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SearchParams1>> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });

            }
        });

        //게시물 필터 결과 구현 - 진혁
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreObText.setVisibility(View.GONE);
                moreTpText.setVisibility(View.GONE);
                morePostText.setVisibility(View.GONE);
                searchResult.setVisibility(View.GONE);
                searchResult2.setVisibility(View.GONE);
                searchResult3.setVisibility(View.GONE);
                searchResult4.setVisibility(View.GONE);
                searchResult5.setVisibility(View.GONE);
                searchResult6.setVisibility(View.VISIBLE);
                obline.setVisibility(View.GONE);
                tpline.setVisibility(View.GONE);
                postline.setVisibility(View.GONE);
                allContentBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap_non));
                tpBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap_non));
                obBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap_non));
                postBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap));
                areaCodeList = new ArrayList<>();
                hashTagIdList = new ArrayList<>();

                for (int i = 0; i < 17; i++) {
                    if (area.get(i) == 1) { //선택했으면
                        areaCodeList.add(areaCode[i]);
                    }
                }
                for (int i = 0; i < 22; i++) {
                    if (hashTag.get(i) == 1) { //선택했으면
                        hashTagIdList.add((long) (i + 1));
                    }
                }
                Filter filter = new Filter(areaCodeList, hashTagIdList);
                SearchKey searchKey = new SearchKey(filter, keyword);
                Call<List<MyPost>> call = RetrofitClient.getApiService().getPostWithFilter(searchKey);
                call.enqueue(new Callback<List<MyPost>>() {
                    @Override
                    public void onResponse(Call<List<MyPost>> call, Response<List<MyPost>> response) {
                        if (response.isSuccessful()) {
                            Log.d("searchPost", "검색 게시물 업로드 성공");
                            postResult = response.body();
                            MyPostAdapter postAdapter = new MyPostAdapter(postResult, getContext());
                            searchResult6.setAdapter(postAdapter);
                            if (postResult.isEmpty()){
                                no_result.setVisibility(View.VISIBLE);
                            }else{no_result.setVisibility(View.GONE);}
                            postAdapter.setOnMyWishPostItemClickListener(new OnMyPostItemClickListener() {
                                @Override
                                public void onItemClick(MyPostAdapter.ViewHolder holder, View view, int position) {
                                    MyPost item = postAdapter.getItem(position);
                                    Intent intent = new Intent(getContext(), PostActivity.class);
                                    intent.putExtra("postId", item.getPostId());
                                    startActivity(intent);
                                }
                            });

                        } else {
                            Log.d("searchPost", "검색 게시물 업로드 실패");
                            morePostText.setVisibility(View.GONE);
                            postline.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<MyPost>> call, Throwable t) {
                        Log.d("searchPost", "검색 게시물 인터넷 오류");
                    }
                });

            }
        });

        //관광지 필터 결과
        tpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingAsyncTask task = new LoadingAsyncTask(getContext(), 10000);
                task.execute();
                moreObText.setVisibility(View.GONE);
                moreTpText.setVisibility(View.GONE);
                morePostText.setVisibility(View.GONE);
                searchResult.setVisibility(View.GONE);
                searchResult2.setVisibility(View.GONE);
                searchResult3.setVisibility(View.GONE);
                searchResult4.setVisibility(View.GONE);
                searchResult5.setVisibility(View.VISIBLE);
                searchResult6.setVisibility(View.GONE);
                obline.setVisibility(View.GONE);
                tpline.setVisibility(View.GONE);
                postline.setVisibility(View.GONE);
                allContentBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap_non));
                tpBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap));
                obBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap_non));
                postBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap_non));
                areaCodeList = new ArrayList<>();
                hashTagIdList = new ArrayList<>();


                for (int i = 0; i < 17; i++) {
                    if (area.get(i) == 1) { //선택했으면
                        areaCodeList.add(areaCode[i]);
                    }
                }
                for (int i = 0; i < 22; i++) {
                    if (hashTag.get(i) == 1) { //선택했으면
                        hashTagIdList.add((long) (i + 1));
                    }
                }

                Filter filter = new Filter(areaCodeList, hashTagIdList);
                SearchKey searchKey = new SearchKey(filter, keyword);
                Call<List<SearchParams1>> call = RetrofitClient.getApiService().getTouristPointWithFilter(searchKey);
                call.enqueue(new Callback<List<SearchParams1>>() {
                    @Override
                    public void onResponse(Call<List<SearchParams1>> call, Response<List<SearchParams1>> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "관광지 검색 성공");
                            task.cancel(true);
                            tpResult = response.body();
                            if (tpResult.isEmpty()){
                                no_result.setVisibility(View.VISIBLE);
                            } else {
                                no_result.setVisibility(View.GONE);

                                //페이징 변수 초기화
                                end = count;
                                noMoreTp = false;
                                limit = tpResult.size();
                                subTpResult = new ArrayList<>();

                                System.out.println("1 총 개수 : " + tpResult.size());

                                if (limit < end) { noMoreTp = true; }

                                tpAdapter = new SearchResultAdapter2(tpResult.subList(0, Math.min(end, limit)), getContext());

                                searchResult5.setAdapter(tpAdapter);
                                tpAdapter.setOnSearchResultItemClickListener2(new OnSearchResultItemClickListener2() {
                                    @Override
                                    public void onItemClick(SearchResultAdapter2.ViewHolder holder, View view, int position) {
                                        SearchParams1 item = tpAdapter.getItem(position);
                                        Intent intent = new Intent(getContext(), TouristPointActivity.class);
                                        intent.putExtra("contentId", item.getItemId());
                                        startActivity(intent);
                                    }
                                });
                            }

                        } else {
                            Log.d(TAG, "관광지 검색 실패");
                        }
                    }
                    @Override
                    public void onFailure(Call<List<SearchParams1>> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });

                nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())
                        {
                            if (!noMoreTp){
                                searchProgressBar.setVisibility(View.VISIBLE);

                                end += count;
                                if (limit < end) { noMoreTp = true; }
                                tpAdapter = new SearchResultAdapter2(tpResult.subList(0, Math.min(end, limit)), getContext());
                                tpAdapter.setOnSearchResultItemClickListener2(new OnSearchResultItemClickListener2() {
                                    @Override
                                    public void onItemClick(SearchResultAdapter2.ViewHolder holder, View view, int position) {
                                        SearchParams1 item = tpAdapter.getItem(position);
                                        Intent intent = new Intent(getContext(), TouristPointActivity.class);
                                        intent.putExtra("contentId", item.getItemId());
                                        startActivity(intent);
                                    }
                                });
                                searchResult5.setAdapter(tpAdapter);
                                searchProgressBar.setVisibility(View.GONE);
                            }
                        }
                    }
                });
            }
        });

        moreObText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreObText.setVisibility(View.GONE);
                moreTpText.setVisibility(View.GONE);
                morePostText.setVisibility(View.GONE);
                searchResult.setVisibility(View.GONE);
                searchResult2.setVisibility(View.GONE);
                searchResult3.setVisibility(View.GONE);
                searchResult4.setVisibility(View.VISIBLE);
                searchResult5.setVisibility(View.GONE);
                searchResult6.setVisibility(View.GONE);
                obline.setVisibility(View.GONE);
                tpline.setVisibility(View.GONE);
                postline.setVisibility(View.GONE);
                allContentBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap_non));
                tpBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap_non));
                obBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap));
                postBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap_non));
                areaCodeList = new ArrayList<>();
                hashTagIdList = new ArrayList<>();

//                areaCodeList.add(0L);
//                hashTagIdList.add(0L);

                for (int i = 0; i < 17; i++) {
                    if (area.get(i) == 1) { //선택했으면
                        areaCodeList.add(areaCode[i]);
                    }
                }
                for (int i = 0; i < 22; i++) {
                    if (hashTag.get(i) == 1) { //선택했으면
                        hashTagIdList.add((long) (i + 1));
                    }
                }


                Filter filter = new Filter(areaCodeList, hashTagIdList);
                SearchKey searchKey = new SearchKey(filter, keyword);
                Call<List<SearchParams1>> call = RetrofitClient.getApiService().getObservationWithFilter(searchKey);
                call.enqueue(new Callback<List<SearchParams1>>() {
                    @Override
                    public void onResponse(Call<List<SearchParams1>> call, Response<List<SearchParams1>> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "관측지 검색 성공");
                            obResult = response.body();

                            //게시물은 어댑터 따로 만들어야 함
                            SearchResultAdapter searchResultAdapter = new SearchResultAdapter(obResult, getContext());
                            searchResult4.setAdapter(searchResultAdapter);
                            searchResultAdapter.setOnSearchResultItemClickListener(new OnSearchResultItemClickListener() {
                                @Override
                                public void onItemClick(SearchResultAdapter.ViewHolder holder, View view, int position) {
                                    SearchParams1 item = searchResultAdapter.getItem(position);
                                    Intent intent = new Intent(getContext(), ObservationsiteActivity.class);
                                    intent.putExtra("observationId", item.getItemId());
                                    startActivity(intent);
                                }
                            });
                        } else {
                            Log.e(TAG, "관측지 검색 실패");
                            moreObText.setVisibility(View.GONE);
                            obline.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SearchParams1>> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });
            }
        });

        morePostText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreObText.setVisibility(View.GONE);
                moreTpText.setVisibility(View.GONE);
                morePostText.setVisibility(View.GONE);
                searchResult.setVisibility(View.GONE);
                searchResult2.setVisibility(View.GONE);
                searchResult3.setVisibility(View.GONE);
                searchResult4.setVisibility(View.GONE);
                searchResult5.setVisibility(View.GONE);
                searchResult6.setVisibility(View.VISIBLE);
                obline.setVisibility(View.GONE);
                tpline.setVisibility(View.GONE);
                postline.setVisibility(View.GONE);
                allContentBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap_non));
                tpBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap_non));
                obBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap_non));
                postBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap));
                areaCodeList = new ArrayList<>();
                hashTagIdList = new ArrayList<>();

                for (int i = 0; i < 17; i++) {
                    if (area.get(i) == 1) { //선택했으면
                        areaCodeList.add(areaCode[i]);
                    }
                }
                for (int i = 0; i < 22; i++) {
                    if (hashTag.get(i) == 1) { //선택했으면
                        hashTagIdList.add((long) (i + 1));
                    }
                }
                Filter filter = new Filter(areaCodeList, hashTagIdList);
                SearchKey searchKey = new SearchKey(filter, keyword);
                Call<List<MyPost>> call = RetrofitClient.getApiService().getPostWithFilter(searchKey);
                call.enqueue(new Callback<List<MyPost>>() {
                    @Override
                    public void onResponse(Call<List<MyPost>> call, Response<List<MyPost>> response) {
                        if (response.isSuccessful()) {
                            Log.d("searchPost", "검색 게시물 업로드 성공");
                            postResult = response.body();
                            MyPostAdapter postAdapter = new MyPostAdapter(postResult, getContext());
                            searchResult6.setAdapter(postAdapter);
                            postAdapter.setOnMyWishPostItemClickListener(new OnMyPostItemClickListener() {
                                @Override
                                public void onItemClick(MyPostAdapter.ViewHolder holder, View view, int position) {
                                    MyPost item = postAdapter.getItem(position);
                                    Intent intent = new Intent(getContext(), PostActivity.class);
                                    intent.putExtra("postId", item.getPostId());
                                    startActivity(intent);
                                }
                            });

                        } else {
                            Log.d("searchPost", "검색 게시물 업로드 실패");
                            morePostText.setVisibility(View.GONE);
                            postline.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<MyPost>> call, Throwable t) {
                        Log.d("searchPost", "검색 게시물 인터넷 오류");
                    }
                });
            }
        });

        moreTpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingAsyncTask task = new LoadingAsyncTask(getContext(), 10000);
                task.execute();

                moreObText.setVisibility(View.GONE);
                moreTpText.setVisibility(View.GONE);
                morePostText.setVisibility(View.GONE);
                searchResult.setVisibility(View.GONE);
                searchResult2.setVisibility(View.GONE);
                searchResult3.setVisibility(View.GONE);
                searchResult4.setVisibility(View.GONE);
                searchResult5.setVisibility(View.VISIBLE);
                searchResult6.setVisibility(View.GONE);
                obline.setVisibility(View.GONE);
                tpline.setVisibility(View.GONE);
                postline.setVisibility(View.GONE);
                allContentBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap_non));
                tpBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap));
                obBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap_non));
                postBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap_non));
                areaCodeList = new ArrayList<>();
                hashTagIdList = new ArrayList<>();


                for (int i = 0; i < 17; i++) {
                    if (area.get(i) == 1) { //선택했으면
                        areaCodeList.add(areaCode[i]);
                    }
                }
                for (int i = 0; i < 22; i++) {
                    if (hashTag.get(i) == 1) { //선택했으면
                        hashTagIdList.add((long) (i + 1));
                    }
                }

                Filter filter = new Filter(areaCodeList, hashTagIdList);
                SearchKey searchKey = new SearchKey(filter, keyword);
                Call<List<SearchParams1>> call = RetrofitClient.getApiService().getTouristPointWithFilter(searchKey);
                call.enqueue(new Callback<List<SearchParams1>>() {
                    @Override
                    public void onResponse(Call<List<SearchParams1>> call, Response<List<SearchParams1>> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "관광지 검색 성공");
                            task.cancel(true);
                            tpResult = response.body();
                            if (tpResult.isEmpty()){
                                no_result.setVisibility(View.VISIBLE);
                            } else {
                                no_result.setVisibility(View.GONE);

                                //페이징 변수 초기화
                                end = count;
                                noMoreTp = false;
                                limit = tpResult.size();
                                subTpResult = new ArrayList<>();

                                System.out.println("1 총 개수 : " + tpResult.size());

                                if (limit < end) { noMoreTp = true; }

                                tpAdapter = new SearchResultAdapter2(tpResult.subList(0, Math.min(end, limit)), getContext());

                                searchResult5.setAdapter(tpAdapter);
                                tpAdapter.setOnSearchResultItemClickListener2(new OnSearchResultItemClickListener2() {
                                    @Override
                                    public void onItemClick(SearchResultAdapter2.ViewHolder holder, View view, int position) {
                                        SearchParams1 item = tpAdapter.getItem(position);
                                        Intent intent = new Intent(getContext(), TouristPointActivity.class);
                                        intent.putExtra("contentId", item.getItemId());
                                        startActivity(intent);
                                    }
                                });
                            }

                        } else {
                            Log.d(TAG, "관광지 검색 실패");
                        }
                    }
                    @Override
                    public void onFailure(Call<List<SearchParams1>> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });

                nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())
                        {
                            if (!noMoreTp){
                                searchProgressBar.setVisibility(View.VISIBLE);

                                end += count;
                                if (limit < end) { noMoreTp = true; }
                                tpAdapter = new SearchResultAdapter2(tpResult.subList(0, Math.min(end, limit)), getContext());
                                searchResult5.setAdapter(tpAdapter);
                                searchProgressBar.setVisibility(View.GONE);
                            }
                        }
                    }
                });
            }
        });

        return v;
    }

    private void searchEverything(SearchKey searchKey) {
        searchResult4.removeAllViews();
        searchResult5.removeAllViews();
        searchResult6.removeAllViews();
        searchResult2.removeAllViews();
        finalTpResult.clear();
        moreTpText.setVisibility(View.VISIBLE);
        tpline.setVisibility(View.VISIBLE);
        allContentBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap));
        postBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap_non));
        tpBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap_non));
        obBtnTap.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.search_tap_non));
        LoadingAsyncTask task = new LoadingAsyncTask(getContext(), 10000);
        task.execute();
        Call<List<SearchParams1>> call = RetrofitClient.getApiService().getTouristPointWithFilter(searchKey);
        call.enqueue(new Callback<List<SearchParams1>>() {
            @Override
            public void onResponse(Call<List<SearchParams1>> call, Response<List<SearchParams1>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "관광지 검색 성공");
                    tpResult = response.body();
                    task.cancel(true);
                    if (tpResult.size() <= 3) {
                        moreTpText.setVisibility(View.GONE);
                    }
                    if(tpResult.size()==0){
                        moreTpText.setVisibility(View.GONE);
                        tpline.setVisibility(View.GONE);
                    }
                    if (tpResult.size() > 3) {
                        finalTpResult.add(tpResult.get(0));
                        finalTpResult.add(tpResult.get(1));
                        finalTpResult.add(tpResult.get(2));
                        moreTpText.setVisibility(View.VISIBLE);
                        tpline.setVisibility(View.VISIBLE);
                    } else {
                        finalTpResult.addAll(tpResult);
                    }
                    SearchResultAdapter2 searchResultAdapter2 = new SearchResultAdapter2(finalTpResult, getContext());
                    searchResult2.setAdapter(searchResultAdapter2);
                    if (finalTpResult.isEmpty()){
                        no_result.setVisibility(View.VISIBLE);
                    }else{no_result.setVisibility(View.GONE);}
                    searchResultAdapter2.setOnSearchResultItemClickListener2(new OnSearchResultItemClickListener2() {
                        @Override
                        public void onItemClick(SearchResultAdapter2.ViewHolder holder, View view, int position) {
                            SearchParams1 item = searchResultAdapter2.getItem(position);
                            Intent intent = new Intent(getContext(), TouristPointActivity.class);
                            intent.putExtra("contentId", item.getItemId());
                            startActivity(intent);
                        }
                    });
                } else {
                    Log.d(TAG, "관광지 검색 실패");
                    moreTpText.setVisibility(View.GONE);
                    tpline.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<SearchParams1>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
        searchResult.removeAllViews();
        finalObResult.clear();
        moreObText.setVisibility(View.VISIBLE);
        obline.setVisibility(View.VISIBLE);
        Call<List<SearchParams1>> call2 = RetrofitClient.getApiService().getObservationWithFilter(searchKey);
        call2.enqueue(new Callback<List<SearchParams1>>() {
            @Override
            public void onResponse(Call<List<SearchParams1>> call, Response<List<SearchParams1>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "관측지 검색 성공");
                    obResult = response.body();
                    if (obResult.size() <= 3) {
                        moreObText.setVisibility(View.GONE);
                    }
                    if (obResult.size() ==0){
                        moreObText.setVisibility(View.GONE);
                        obline.setVisibility(View.GONE);
                    }
                    if (obResult.size() > 3) {
                        finalObResult.add(obResult.get(0));
                        finalObResult.add(obResult.get(1));
                        finalObResult.add(obResult.get(2));
                        moreObText.setVisibility(View.VISIBLE);
                        obline.setVisibility(View.VISIBLE);
                    } else {
                        finalObResult.addAll(obResult);
                    }
                    //게시물은 어댑터 따로 만들어야 함
                    SearchResultAdapter searchResultAdapter = new SearchResultAdapter(finalObResult, getContext());
                    searchResult.setAdapter(searchResultAdapter);
                    if (finalObResult.isEmpty()){
                        no_result.setVisibility(View.VISIBLE);
                    }else{no_result.setVisibility(View.GONE);}
                    searchResultAdapter.setOnSearchResultItemClickListener(new OnSearchResultItemClickListener() {
                        @Override
                        public void onItemClick(SearchResultAdapter.ViewHolder holder, View view, int position) {
                            SearchParams1 item = searchResultAdapter.getItem(position);
                            Intent intent = new Intent(getContext(), ObservationsiteActivity.class);
                            intent.putExtra("observationId", item.getItemId());
                            startActivity(intent);
                        }
                    });
                } else {
                    Log.e(TAG, "관측지 검색 실패");
                    moreObText.setVisibility(View.GONE);
                    obline.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<SearchParams1>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
        searchResult3.removeAllViews();
        finalPostResult.clear();
        morePostText.setVisibility(View.VISIBLE);
        postline.setVisibility(View.VISIBLE);
        Call<List<MyPost>> call3 = RetrofitClient.getApiService().getPostWithFilter(searchKey);
        call3.enqueue(new Callback<List<MyPost>>() {
            @Override
            public void onResponse(Call<List<MyPost>> call, Response<List<MyPost>> response) {
                if (response.isSuccessful()) {
                    Log.d("searchPost", "검색 게시물 업로드 성공");
                    postResult = response.body();
                    if (postResult.size() <= 3) {
                        morePostText.setVisibility(View.GONE);
                    }
                    if (postResult.size()==0){
                        morePostText.setVisibility(View.GONE);
                        postline.setVisibility(View.GONE);
                    }
                    if (postResult.size() > 3) {
                        finalPostResult.add(postResult.get(0));
                        finalPostResult.add(postResult.get(1));
                        finalPostResult.add(postResult.get(2));
                        morePostText.setVisibility(View.VISIBLE);
                        postline.setVisibility(View.VISIBLE);
                    } else {
                        finalPostResult.addAll(postResult);
                    }
                    MyPostAdapter postAdapter = new MyPostAdapter(finalPostResult, getContext());
                    searchResult3.setAdapter(postAdapter);
                    if (finalPostResult.isEmpty()){
                        no_result.setVisibility(View.VISIBLE);
                    }else{no_result.setVisibility(View.GONE);}
                    postAdapter.setOnMyWishPostItemClickListener(new OnMyPostItemClickListener() {
                        @Override
                        public void onItemClick(MyPostAdapter.ViewHolder holder, View view, int position) {
                            MyPost item = postAdapter.getItem(position);
                            Intent intent = new Intent(getContext(), PostActivity.class);
                            intent.putExtra("postId", item.getPostId());
                            startActivity(intent);
                        }
                    });

                } else {
                    Log.d("searchPost", "검색 게시물 업로드 실패");
                    morePostText.setVisibility(View.GONE);
                    postline.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<MyPost>> call, Throwable t) {
                Log.d("searchPost", "검색 게시물 인터넷 오류");
            }
        });
    }

    private class LoadingAsyncTask extends AsyncTask<String, Long, Boolean> {
        private Context mContext = null;
        private Long mtime;

        public LoadingAsyncTask(Context context, long time) {
            mContext = context.getApplicationContext();
            mtime = time;
        }

        @Override
        protected void onPreExecute() {
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                Thread.sleep(mtime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return (true);
        }

        @Override
        protected void onCancelled(Boolean result) {
            dialog.dismiss();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dialog.dismiss();
        }
    }
}