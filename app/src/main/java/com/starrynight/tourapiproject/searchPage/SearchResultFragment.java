package com.starrynight.tourapiproject.searchPage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
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
import com.starrynight.tourapiproject.searchPage.searchPageRetrofit.SearchParams1;
import com.starrynight.tourapiproject.searchPage.searchPageRetrofit.SearchParams2;
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

    TextView obText;
    TextView tpText;
    TextView postText;
    TextView moreObText,moreTpText,morePostText;
    Button obBtn;
    Button tpBtn;
    Button postBtn;
    Button allContentBtn;
    SearchView searchView;

    LinearLayout selectFilterItem; //선택한 필터들이 보이는 레이아웃

    List<SearchParams1> obResult; //관측지 필터 결과
    List<SearchParams2> tpResult; //관광지 필터 결과
    List<SearchParams2>finalTpResult = new ArrayList<>();
    List<SearchParams1>finalObResult = new ArrayList<>();
    List<MyPost> postResult;//게시물 필터 결과
    List<MyPost> finalPostResult= new ArrayList<>();
    ArrayList<Integer> area; //어떤 지역필터 선택했는지 Integer값(0이면 선택x, 1이면 선택o)으로 받아온 배열
    ArrayList<Integer> hashTag; //어떤 해시태그필터 선택했는지 Integer값(0이면 선택x, 1이면 선택o)으로 받아온 배열

    List<Long> areaCodeList;
    List<Long> hashTagIdList;

    String keyword;

    public static SearchResultFragment newInstance() {
        return new SearchResultFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_result, container, false);

        searchView = v.findViewById(R.id.search2);
        obText = v.findViewById(R.id.obText);
        tpText = v.findViewById(R.id.tpText);
        postText = v.findViewById(R.id.postText);
        moreObText=v.findViewById(R.id.moreTp_text);
        moreTpText=v.findViewById(R.id.moreOb_text);
        morePostText=v.findViewById(R.id.morePost_text);
        obBtn = v.findViewById(R.id.obBtn);
        tpBtn = v.findViewById(R.id.tpBtn);
        postBtn = v.findViewById(R.id.postBtn);
        allContentBtn= v.findViewById(R.id.allContentBtn);
        selectFilterItem = v.findViewById(R.id.selectFilterItem);
        selectFilterItem.removeAllViews(); //초기화


        //필터 결과 리사이클러뷰
        System.out.println("리사이클러뷰 설정");
        RecyclerView searchResult = v.findViewById(R.id.searchResult);
        RecyclerView searchResult2 = v.findViewById(R.id.searchResult2);
        RecyclerView searchResult3 = v.findViewById(R.id.searchResult3);
        LinearLayoutManager searchLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager searchLayoutManager2= new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager searchLayoutManager3= new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        searchLayoutManager.isAutoMeasureEnabled();
        searchLayoutManager2.isAutoMeasureEnabled();
        searchLayoutManager3.isAutoMeasureEnabled();
        searchResult.setLayoutManager(searchLayoutManager);
        searchResult2.setLayoutManager(searchLayoutManager2);
        searchResult3.setLayoutManager(searchLayoutManager3);
        searchResult.setHasFixedSize(true);
        searchResult2.setHasFixedSize(true);
        searchResult3.setHasFixedSize(true);
        obResult = new ArrayList<>();
        tpResult = new ArrayList<>();
        postResult = new ArrayList<>();


        if (getArguments() != null)
        {
            ((MainActivity)getActivity()).showBottom();

            int type = getArguments().getInt("type");
            if (type == 1){
                System.out.println("값 넘어옴");
                area = getArguments().getIntegerArrayList("area"); //선택한 지역 필터
                hashTag = getArguments().getIntegerArrayList("hashTag"); //선택한 해시태그 필터
                keyword = getArguments().getString("keyword");

                selectFilterItem.removeAllViews(); //초기화
                for(int i=0; i<17; i++){
                    if(area.get(i) == 1){
                        TextView textView = new TextView(getContext());
                        textView.setText(" " + areaName[i] + " ");
                        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_200));
                        textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.hashtags_empty));
                        selectFilterItem.addView(textView);
                    }
                }
                for(int i=0; i<22; i++){
                    if(hashTag.get(i) == 1){
                        TextView textView = new TextView(getContext());
                        textView.setText("#" + hashTagName[i]);
                        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_200));
                        textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.hashtags_empty));
                        selectFilterItem.addView(textView);
                    }
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
                Call<List<SearchParams2>> call = RetrofitClient.getApiService().getTouristPointWithFilter(searchKey);
                call.enqueue(new Callback<List<SearchParams2>>() {
                    @Override
                    public void onResponse(Call<List<SearchParams2>> call, Response<List<SearchParams2>> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "관광지 검색 성공");
                            tpResult = response.body();
                            tpResult = response.body();
                            if (tpResult.size()>3){
                                finalTpResult.add(tpResult.get(0));
                                finalTpResult.add(tpResult.get(1));
                                finalTpResult.add(tpResult.get(2));
                            }else{finalTpResult.addAll(tpResult);}
                            SearchResultAdapter2 searchResultAdapter2 = new SearchResultAdapter2(finalTpResult, getContext());
                            searchResult2.setAdapter(searchResultAdapter2);
                            searchResultAdapter2.setOnSearchResultItemClickListener2(new OnSearchResultItemClickListener2() {
                                @Override
                                public void onItemClick(SearchResultAdapter2.ViewHolder holder, View view, int position) {
                                    SearchParams2 item = searchResultAdapter2.getItem(position);
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
                    public void onFailure(Call<List<SearchParams2>> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });

                Filter filter2 = new Filter(areaCodeList, hashTagIdList);
                SearchKey searchKey2 = new SearchKey(filter2, keyword);
                Call<List<SearchParams1>> call2 = RetrofitClient.getApiService().getObservationWithFilter(searchKey2);
                call2.enqueue(new Callback<List<SearchParams1>>() {
                    @Override
                    public void onResponse(Call<List<SearchParams1>> call, Response<List<SearchParams1>> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "관측지 검색 성공");
                            obResult = response.body();
                            obResult = response.body();
                            if (obResult.size()>3){
                                finalObResult.add(obResult.get(0));
                                finalObResult.add(obResult.get(1));
                                finalObResult.add(obResult.get(2));
                            }else{ finalObResult.addAll(obResult);}
                            //게시물은 어댑터 따로 만들어야 함
                            SearchResultAdapter searchResultAdapter = new SearchResultAdapter(finalObResult, getContext());
                            searchResult.setAdapter(searchResultAdapter);
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
                        }
                    }
                    @Override
                    public void onFailure(Call<List<SearchParams1>> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });

                Filter filter3 = new Filter(areaCodeList, hashTagIdList);
                SearchKey searchKey3 = new SearchKey(filter3, keyword);
                Call<List<MyPost>>call3 = RetrofitClient.getApiService().getPostWithFilter(searchKey3);
                call3.enqueue(new Callback<List<MyPost>>() {
                    @Override
                    public void onResponse(Call<List<MyPost>> call, Response<List<MyPost>> response) {
                        if (response.isSuccessful()){
                            Log.d("searchPost","검색 게시물 업로드 성공");
                            postResult=response.body();
                            postResult = response.body();
                            if (postResult.size()>3){
                                finalPostResult.add(postResult.get(0));
                                finalPostResult.add(postResult.get(1));
                                finalPostResult.add(postResult.get(2));
                            }else {finalPostResult.addAll(postResult);}
                            MyPostAdapter postAdapter = new MyPostAdapter(finalPostResult,getContext());
                            searchResult3.setAdapter(postAdapter);
                            postAdapter.setOnMyWishPostItemClickListener(new OnMyPostItemClickListener() {
                                @Override
                                public void onItemClick(MyPostAdapter.ViewHolder holder, View view, int position) {
                                    MyPost item = postAdapter.getItem(position);
                                    Intent intent = new Intent(getContext(), PostActivity.class);
                                    intent.putExtra("postId", item.getPostId());
                                    System.out.println(item.getPostId());
                                    startActivity(intent);
                                }
                            });

                        }else{Log.d("searchPost","검색 게시물 업로드 실패");}
                    }

                    @Override
                    public void onFailure(Call<List<MyPost>> call, Throwable t) {
                        Log.d("searchPost","검색 게시물 인터넷 오류");
                    }
                });

            } else if (type == 3) {

                area = getArguments().getIntegerArrayList("area"); //선택한 지역 필터
                hashTag = getArguments().getIntegerArrayList("hashTag"); //선택한 해시태그 필터
                keyword = getArguments().getString("keyword");

                selectFilterItem.removeAllViews(); //초기화
                for(int i=0; i<17; i++){
                    if(area.get(i) == 1){
                        TextView textView = new TextView(getContext());
                        textView.setText(" "+ areaName[i] + " ");
                        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_200));
                        textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.hashtags_empty));
                        selectFilterItem.addView(textView);
                    }
                }
                for(int i=0; i<22; i++){
                    if(hashTag.get(i) == 1){
                        TextView textView = new TextView(getContext());
                        textView.setText("#" + hashTagName[i]);
                        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_200));
                        textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.hashtags_empty));
                        selectFilterItem.addView(textView);
                    }
                }
                if (keyword == null) {
                    searchView.setQueryHint("검색어를 입력하세요");
                } else {
                    searchView.setQueryHint(keyword);
                }
                //저 searchkey 넣어서 검색결과 새로 다 넣기

            }
        }

        allContentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obText.setVisibility(View.GONE);
                tpText.setVisibility(View.GONE);
                postText.setVisibility(View.GONE);
                moreObText.setVisibility(View.VISIBLE);
                moreTpText.setVisibility(View.VISIBLE);
                morePostText.setVisibility(View.VISIBLE);
                searchResult2.setVisibility(View.VISIBLE);
                searchResult3.setVisibility(View.VISIBLE);
                areaCodeList = new ArrayList<>();
                hashTagIdList = new ArrayList<>();

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
                searchResult2.removeAllViews();
                finalTpResult.clear();
                Filter filter = new Filter(areaCodeList, hashTagIdList);
                SearchKey searchKey = new SearchKey(filter, keyword);
                Call<List<SearchParams2>> call = RetrofitClient.getApiService().getTouristPointWithFilter(searchKey);
                call.enqueue(new Callback<List<SearchParams2>>() {
                    @Override
                    public void onResponse(Call<List<SearchParams2>> call, Response<List<SearchParams2>> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "관광지 검색 성공");
                            tpResult = response.body();
                            if (tpResult.size()>3){
                                finalTpResult.add(tpResult.get(0));
                                finalTpResult.add(tpResult.get(1));
                                finalTpResult.add(tpResult.get(2));
                            }else{finalTpResult.addAll(tpResult);}
                            SearchResultAdapter2 searchResultAdapter2 = new SearchResultAdapter2(finalTpResult, getContext());
                            searchResult2.setAdapter(searchResultAdapter2);
                            searchResultAdapter2.setOnSearchResultItemClickListener2(new OnSearchResultItemClickListener2() {
                                @Override
                                public void onItemClick(SearchResultAdapter2.ViewHolder holder, View view, int position) {
                                    SearchParams2 item = searchResultAdapter2.getItem(position);
                                    Intent intent = new Intent(getContext(), TouristPointActivity.class);
                                    System.out.println(item.getItemId());
                                    intent.putExtra("contentId", item.getItemId());
                                    startActivity(intent);
                                }
                            });
                        } else {
                            System.out.println("관광지 필터 검색 실패");
                        }
                    }
                    @Override
                    public void onFailure(Call<List<SearchParams2>> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });
                searchResult.removeAllViews();
                finalObResult.clear();
                Filter filter2 = new Filter(areaCodeList, hashTagIdList);
                SearchKey searchKey2 = new SearchKey(filter2, keyword);
                Call<List<SearchParams1>> call2 = RetrofitClient.getApiService().getObservationWithFilter(searchKey2);
                call2.enqueue(new Callback<List<SearchParams1>>() {
                    @Override
                    public void onResponse(Call<List<SearchParams1>> call, Response<List<SearchParams1>> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "관측지 검색 성공");
                            obResult = response.body();
                            if (obResult.size()>3){
                                finalObResult.add(obResult.get(0));
                                finalObResult.add(obResult.get(1));
                                finalObResult.add(obResult.get(2));
                            }else{ finalObResult.addAll(obResult);}
                            //게시물은 어댑터 따로 만들어야 함
                            SearchResultAdapter searchResultAdapter = new SearchResultAdapter(finalObResult, getContext());
                            searchResult.setAdapter(searchResultAdapter);
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
                        }
                    }
                    @Override
                    public void onFailure(Call<List<SearchParams1>> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });
                searchResult3.removeAllViews();
                finalPostResult.clear();
                Filter filter3 = new Filter(areaCodeList, hashTagIdList);
                SearchKey searchKey3 = new SearchKey(filter3, keyword);
                Call<List<MyPost>>call3 = RetrofitClient.getApiService().getPostWithFilter(searchKey3);
                call3.enqueue(new Callback<List<MyPost>>() {
                    @Override
                    public void onResponse(Call<List<MyPost>> call, Response<List<MyPost>> response) {
                        if (response.isSuccessful()){
                            Log.d("searchPost","검색 게시물 업로드 성공");
                            postResult=response.body();
                            if (postResult.size()>3){
                                finalPostResult.add(postResult.get(0));
                                finalPostResult.add(postResult.get(1));
                                finalPostResult.add(postResult.get(2));
                            }else {finalPostResult.addAll(postResult);}
                            MyPostAdapter postAdapter = new MyPostAdapter(finalPostResult,getContext());
                            searchResult3.setAdapter(postAdapter);
                            postAdapter.setOnMyWishPostItemClickListener(new OnMyPostItemClickListener() {
                                @Override
                                public void onItemClick(MyPostAdapter.ViewHolder holder, View view, int position) {
                                    MyPost item = postAdapter.getItem(position);
                                    Intent intent = new Intent(getContext(), PostActivity.class);
                                    intent.putExtra("postId", item.getPostId());
                                    System.out.println(item.getPostId());
                                    startActivity(intent);
                                }
                            });

                        }else{Log.d("searchPost","검색 게시물 업로드 실패");}
                    }

                    @Override
                    public void onFailure(Call<List<MyPost>> call, Throwable t) {
                        Log.d("searchPost","검색 게시물 인터넷 오류");
                    }
                });
            }
        });

        if (keyword == null) {
            searchView.setQueryHint("검색어를 입력하세요");
        } else {
            searchView.setQueryHint(keyword);
        }
//        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                keyword = query;
                areaCodeList = new ArrayList<>();
                hashTagIdList = new ArrayList<>();

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
                searchResult2.removeAllViews();
                finalTpResult.clear();
                Filter filter = new Filter(areaCodeList, hashTagIdList);
                SearchKey searchKey = new SearchKey(filter, keyword);
                Call<List<SearchParams2>> call = RetrofitClient.getApiService().getTouristPointWithFilter(searchKey);
                call.enqueue(new Callback<List<SearchParams2>>() {
                    @Override
                    public void onResponse(Call<List<SearchParams2>> call, Response<List<SearchParams2>> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "관광지 검색 성공");
                            tpResult = response.body();
                            if (tpResult.size()>3){
                                finalTpResult.add(tpResult.get(0));
                                finalTpResult.add(tpResult.get(1));
                                finalTpResult.add(tpResult.get(2));
                            }else{finalTpResult.addAll(tpResult);}
                            SearchResultAdapter2 searchResultAdapter2 = new SearchResultAdapter2(finalTpResult, getContext());
                            searchResult2.setAdapter(searchResultAdapter2);
                            searchResultAdapter2.setOnSearchResultItemClickListener2(new OnSearchResultItemClickListener2() {
                                @Override
                                public void onItemClick(SearchResultAdapter2.ViewHolder holder, View view, int position) {
                                    SearchParams2 item = searchResultAdapter2.getItem(position);
                                    Intent intent = new Intent(getContext(), TouristPointActivity.class);
                                    System.out.println(item.getItemId());
                                    intent.putExtra("contentId", item.getItemId());
                                    startActivity(intent);
                                }
                            });
                        } else {
                            System.out.println("관광지 필터 검색 실패");
                        }
                    }
                    @Override
                    public void onFailure(Call<List<SearchParams2>> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });
                searchResult.removeAllViews();
                finalObResult.clear();
                Filter filter2 = new Filter(areaCodeList, hashTagIdList);
                SearchKey searchKey2 = new SearchKey(filter2, keyword);
                Call<List<SearchParams1>> call2 = RetrofitClient.getApiService().getObservationWithFilter(searchKey2);
                call2.enqueue(new Callback<List<SearchParams1>>() {
                    @Override
                    public void onResponse(Call<List<SearchParams1>> call, Response<List<SearchParams1>> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "관측지 검색 성공");
                            obResult = response.body();
                            if (obResult.size()>3){
                                finalObResult.add(obResult.get(0));
                                finalObResult.add(obResult.get(1));
                                finalObResult.add(obResult.get(2));
                            }else{ finalObResult.addAll(obResult);}
                            //게시물은 어댑터 따로 만들어야 함
                            SearchResultAdapter searchResultAdapter = new SearchResultAdapter(finalObResult, getContext());
                            searchResult.setAdapter(searchResultAdapter);
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
                        }
                    }
                    @Override
                    public void onFailure(Call<List<SearchParams1>> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });
                searchResult3.removeAllViews();
                finalPostResult.clear();
                Filter filter3 = new Filter(areaCodeList, hashTagIdList);
                SearchKey searchKey3 = new SearchKey(filter3, keyword);
                Call<List<MyPost>>call3 = RetrofitClient.getApiService().getPostWithFilter(searchKey3);
                call3.enqueue(new Callback<List<MyPost>>() {
                    @Override
                    public void onResponse(Call<List<MyPost>> call, Response<List<MyPost>> response) {
                        if (response.isSuccessful()){
                            Log.d("searchPost","검색 게시물 업로드 성공");
                            postResult=response.body();
                            if (postResult.size()>3){
                                finalPostResult.add(postResult.get(0));
                                finalPostResult.add(postResult.get(1));
                                finalPostResult.add(postResult.get(2));
                            }else {finalPostResult.addAll(postResult);}
                            MyPostAdapter postAdapter = new MyPostAdapter(finalPostResult,getContext());
                            searchResult3.setAdapter(postAdapter);
                            postAdapter.setOnMyWishPostItemClickListener(new OnMyPostItemClickListener() {
                                @Override
                                public void onItemClick(MyPostAdapter.ViewHolder holder, View view, int position) {
                                    MyPost item = postAdapter.getItem(position);
                                    Intent intent = new Intent(getContext(), PostActivity.class);
                                    intent.putExtra("postId", item.getPostId());
                                    System.out.println(item.getPostId());
                                    startActivity(intent);
                                }
                            });

                        }else{Log.d("searchPost","검색 게시물 업로드 실패");}
                    }

                    @Override
                    public void onFailure(Call<List<MyPost>> call, Throwable t) {
                        Log.d("searchPost","검색 게시물 인터넷 오류");
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        //지도 페이지로
        Button map_btn = (Button) v.findViewById(R.id.mapBtn2);
        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                bundle.putSerializable("FromWhere", Activities.SEARCHRESULT);
                bundle.putIntegerArrayList("area",area);
                bundle.putIntegerArrayList("hashTag",hashTag);
                bundle.putString("keyword",keyword);
                MapFragment mapFragment = new MapFragment();
                mapFragment.setArguments(bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_view, mapFragment);
                transaction.addToBackStack(null);
                transaction.commit();
//                ((MainActivity)getActivity()).replaceFragment(mapFragment);
            }
        });


        //필터 고르는 페이지로
        Button filter_btn = (Button) v.findViewById(R.id.filterBtn2);
        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("keyword", keyword);
                bundle.putSerializable("fromWhere", Activities.SEARCHRESULT);
                Fragment filterFragment = new FilterFragment();
                filterFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_view, filterFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        //처음에 아무 버튼 안눌러도 관측지 필터 결과 보이게 구현



        //관측지 필터 결과 구현 - 채형
        obBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obText.setVisibility(View.VISIBLE);
                tpText.setVisibility(View.GONE);
                postText.setVisibility(View.GONE);
                moreObText.setVisibility(View.GONE);
                moreTpText.setVisibility(View.GONE);
                morePostText.setVisibility(View.GONE);
                searchResult2.setVisibility(View.GONE);
                searchResult3.setVisibility(View.GONE);
                areaCodeList = new ArrayList<>();
                hashTagIdList = new ArrayList<>();
//                areaCodeList.add(0L);
//                hashTagIdList.add(0L);

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
                            searchResult.setAdapter(searchResultAdapter);
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
                obText.setVisibility(View.GONE);
                tpText.setVisibility(View.GONE);
                postText.setVisibility(View.VISIBLE);
                moreObText.setVisibility(View.GONE);
                moreTpText.setVisibility(View.GONE);
                morePostText.setVisibility(View.GONE);
                searchResult2.setVisibility(View.GONE);
                searchResult3.setVisibility(View.GONE);
                areaCodeList = new ArrayList<>();
                hashTagIdList = new ArrayList<>();

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
                SearchKey searchKey = new SearchKey(filter, keyword);
                Call<List<MyPost>>call = RetrofitClient.getApiService().getPostWithFilter(searchKey);
                call.enqueue(new Callback<List<MyPost>>() {
                    @Override
                    public void onResponse(Call<List<MyPost>> call, Response<List<MyPost>> response) {
                        if (response.isSuccessful()){
                            Log.d("searchPost","검색 게시물 업로드 성공");
                            System.out.println(keyword);
                            postResult=response.body();
                            MyPostAdapter postAdapter = new MyPostAdapter(postResult,getContext());
                            searchResult.setAdapter(postAdapter);
                            postAdapter.setOnMyWishPostItemClickListener(new OnMyPostItemClickListener() {
                                @Override
                                public void onItemClick(MyPostAdapter.ViewHolder holder, View view, int position) {
                                    MyPost item = postAdapter.getItem(position);
                                    Intent intent = new Intent(getContext(), PostActivity.class);
                                    intent.putExtra("postId", item.getPostId());
                                    startActivity(intent);
                                }
                            });

                        }else{Log.d("searchPost","검색 게시물 업로드 실패");}
                    }

                    @Override
                    public void onFailure(Call<List<MyPost>> call, Throwable t) {
                        Log.d("searchPost","검색 게시물 인터넷 오류");
                    }
                });

            }
        });

        //관광지 필터 결과
        tpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obText.setVisibility(View.GONE);
                tpText.setVisibility(View.VISIBLE);
                postText.setVisibility(View.GONE);
                moreObText.setVisibility(View.GONE);
                moreTpText.setVisibility(View.GONE);
                morePostText.setVisibility(View.GONE);
                searchResult2.setVisibility(View.GONE);
                searchResult3.setVisibility(View.GONE);
                areaCodeList = new ArrayList<>();
                hashTagIdList = new ArrayList<>();
//                areaCodeList.add(0L);
//                hashTagIdList.add(0L);

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
                SearchKey searchKey = new SearchKey(filter, keyword);
                Call<List<SearchParams2>> call = RetrofitClient.getApiService().getTouristPointWithFilter(searchKey);
                call.enqueue(new Callback<List<SearchParams2>>() {
                    @Override
                    public void onResponse(Call<List<SearchParams2>> call, Response<List<SearchParams2>> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "관광지 검색 성공");
                            tpResult = response.body();

                            SearchResultAdapter2 searchResultAdapter2 = new SearchResultAdapter2(tpResult, getContext());
                            searchResult.setAdapter(searchResultAdapter2);
                            searchResultAdapter2.setOnSearchResultItemClickListener2(new OnSearchResultItemClickListener2() {
                                @Override
                                public void onItemClick(SearchResultAdapter2.ViewHolder holder, View view, int position) {
                                    SearchParams2 item = searchResultAdapter2.getItem(position);
                                    Intent intent = new Intent(getContext(), TouristPointActivity.class);
                                    System.out.println(item.getItemId());
                                    intent.putExtra("contentId", item.getItemId());
                                    startActivity(intent);
                                }
                            });
                        } else {
                            System.out.println("관광지 필터 검색 실패");
                        }
                    }
                    @Override
                    public void onFailure(Call<List<SearchParams2>> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });
            }
        });




        return v;
    }
}