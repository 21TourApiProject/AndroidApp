package com.starrynight.tourapiproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.mapPage.Activities;
import com.starrynight.tourapiproject.mapPage.MapFragment;
import com.starrynight.tourapiproject.observationPage.ObservationsiteActivity;
import com.starrynight.tourapiproject.postItemPage.OnPostPointItemClickListener;
import com.starrynight.tourapiproject.postItemPage.Post_point_item_Adapter;
import com.starrynight.tourapiproject.postItemPage.post_point_item;
import com.starrynight.tourapiproject.searchPage.FilterFragment;
import com.starrynight.tourapiproject.searchPage.SearchResultFragment;
import com.starrynight.tourapiproject.searchPage.searchPageRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.searchPage.searchPageRetrofit.SearchFirst;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        ((MainActivity) getActivity()).showBottom();

        ((MainActivity) getActivity()).setFilter(null);

        androidx.appcompat.widget.SearchView searchView = v.findViewById(R.id.search);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("원하는 것을 검색해보세요");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                bundle.putInt("type", 2);
                bundle.putString("keyword", query);

                ((MainActivity) getActivity()).setFilter(null);
                Fragment resultfragment = new SearchResultFragment();
                resultfragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.main_view, resultfragment);
                ((MainActivity) getActivity()).setSearchResult(resultfragment);
                transaction.addToBackStack(null);
                transaction.hide(SearchFragment.this);
                transaction.commit();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //지도 페이지로
        ImageButton map_btn = (ImageButton) v.findViewById(R.id.mapBtn);
        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                bundle.putSerializable("FromWhere", Activities.SEARCH);//번들에 넘길 값 저장

                MapFragment mapFragment = new MapFragment();
                mapFragment.setArguments(bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.main_view, mapFragment);
                ((MainActivity) getActivity()).setMap(mapFragment);
                transaction.addToBackStack(null);
                transaction.hide(SearchFragment.this);
                transaction.commit();
//                ((MainActivity)getActivity()).replaceFragment(mapFragment);
            }
        });

        //필터 고르는 페이지로
        ImageButton filter_btn = (ImageButton) v.findViewById(R.id.filterBtn);
        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("fromWhere", Activities.SEARCH);

                FilterFragment filterFragment = new FilterFragment();
                filterFragment.setArguments(bundle);
                ((MainActivity) getActivity()).setFilter(filterFragment);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.main_view, filterFragment);
                transaction.addToBackStack(null);
                transaction.hide(SearchFragment.this);
                transaction.commit();
            }
        });

        if (getArguments() != null) {
            int type = getArguments().getInt("type");
            if (type == 0) {
                ((MainActivity) getActivity()).showBottom();
            }
        }

        //요즘 핫한 밤하늘 명소
        LinearLayout hotLinear = v.findViewById(R.id.hotlinearlayout);
        RecyclerView hotRecyclerView = v.findViewById(R.id.hotRecyclerview);
//        hotLinear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (hotRecyclerView.getVisibility()==View.GONE){
//                    hotRecyclerView.setVisibility(View.VISIBLE);
//                }else{hotRecyclerView.setVisibility(View.GONE);}
//            }
//        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        hotRecyclerView.setLayoutManager(linearLayoutManager);
        Post_point_item_Adapter adapter = new Post_point_item_Adapter();
        hotRecyclerView.setAdapter(adapter);
        Call<List<SearchFirst>> call = RetrofitClient.getApiService().getSearchFirst("요즘 핫한 밤하늘 명소");
        call.enqueue(new Callback<List<SearchFirst>>() {
            @Override
            public void onResponse(Call<List<SearchFirst>> call, Response<List<SearchFirst>> response) {
                if (response.isSuccessful()) {
                    List<SearchFirst> searchFirsts = response.body();
                    for (int i = 0; i < searchFirsts.size(); i++) {
                        adapter.addItem(new post_point_item(searchFirsts.get(i).getObservationName(), searchFirsts.get(i).getObservationImage()));
                    }
                    adapter.notifyDataSetChanged();
                    adapter.setOnItemClicklistener(new OnPostPointItemClickListener() {
                        @Override
                        public void onItemClick(Post_point_item_Adapter.ViewHolder holder, View view, int position) {
                            Intent intent = new Intent(getActivity(), ObservationsiteActivity.class);
                            intent.putExtra("observationId", searchFirsts.get(position).getObservationId());
                            startActivity(intent);
                        }
                    });
                } else {
                    Log.d("hot", "요즘 핫한 명소 업로드 실패");
                }
            }

            @Override
            public void onFailure(Call<List<SearchFirst>> call, Throwable t) {
                Log.d("hot", "요즘 핫한 명소 인터넷 오류");
            }
        });


        LinearLayout walkLinear = v.findViewById(R.id.walkinglinearlayout);
        RecyclerView walkRecyclerView = v.findViewById(R.id.walkingRecyclerview);
//        walkLinear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (walkRecyclerView.getVisibility()==View.GONE){
//                    walkRecyclerView.setVisibility(View.VISIBLE);
//                }else{walkRecyclerView.setVisibility(View.GONE);}
//            }
//        });
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        walkRecyclerView.setLayoutManager(linearLayoutManager2);
        Post_point_item_Adapter adapter2 = new Post_point_item_Adapter();
        walkRecyclerView.setAdapter(adapter2);
        Call<List<SearchFirst>> call2 = RetrofitClient.getApiService().getSearchFirst("뚜벅이를 위한 밤하늘 명소");
        call2.enqueue(new Callback<List<SearchFirst>>() {
            @Override
            public void onResponse(Call<List<SearchFirst>> call, Response<List<SearchFirst>> response) {
                if (response.isSuccessful()) {
                    List<SearchFirst> searchFirsts = response.body();
                    for (int i = 0; i < searchFirsts.size(); i++) {
                        adapter2.addItem(new post_point_item(searchFirsts.get(i).getObservationName(), searchFirsts.get(i).getObservationImage()));
                    }
                    adapter2.notifyDataSetChanged();
                    adapter2.setOnItemClicklistener(new OnPostPointItemClickListener() {
                        @Override
                        public void onItemClick(Post_point_item_Adapter.ViewHolder holder, View view, int position) {
                            Intent intent = new Intent(getActivity(), ObservationsiteActivity.class);
                            intent.putExtra("observationId", searchFirsts.get(position).getObservationId());
                            startActivity(intent);
                        }
                    });
                } else {
                    Log.d("walk", "뚜벅이를 위한 명소 업로드 실패");
                }
            }

            @Override
            public void onFailure(Call<List<SearchFirst>> call, Throwable t) {
                Log.d("walk", "뚜벅이를 위한 명소 인터넷 오류");
            }
        });

        LinearLayout cityLinear = v.findViewById(R.id.citylinearlayout);
        RecyclerView cityRecyclerView = v.findViewById(R.id.cityRecyclerview);
//        cityLinear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (cityRecyclerView.getVisibility()==View.GONE){
//                    cityRecyclerView.setVisibility(View.VISIBLE);
//                }else{cityRecyclerView.setVisibility(View.GONE);}
//            }
//        });
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        cityRecyclerView.setLayoutManager(linearLayoutManager3);
        Post_point_item_Adapter adapter3 = new Post_point_item_Adapter();
        cityRecyclerView.setAdapter(adapter3);
        Call<List<SearchFirst>> call3 = RetrofitClient.getApiService().getSearchFirst("도심 속 밤하늘 명소");
        call3.enqueue(new Callback<List<SearchFirst>>() {
            @Override
            public void onResponse(Call<List<SearchFirst>> call, Response<List<SearchFirst>> response) {
                if (response.isSuccessful()) {
                    List<SearchFirst> searchFirsts = response.body();
                    for (int i = 0; i < searchFirsts.size(); i++) {
                        adapter3.addItem(new post_point_item(searchFirsts.get(i).getObservationName(), searchFirsts.get(i).getObservationImage()));
                    }
                    adapter3.notifyDataSetChanged();
                    adapter3.setOnItemClicklistener(new OnPostPointItemClickListener() {
                        @Override
                        public void onItemClick(Post_point_item_Adapter.ViewHolder holder, View view, int position) {
                            Intent intent = new Intent(getActivity(), ObservationsiteActivity.class);
                            intent.putExtra("observationId", searchFirsts.get(position).getObservationId());
                            startActivity(intent);
                        }
                    });
                } else {
                    Log.d("city", "도심 속 명소 업로드 실패");
                }
            }

            @Override
            public void onFailure(Call<List<SearchFirst>> call, Throwable t) {
                Log.d("city", "도심 속 명소 인터넷 오류");
            }
        });

        LinearLayout hideLinear = v.findViewById(R.id.hidelinearlayout);
        RecyclerView hideRecyclerView = v.findViewById(R.id.hideRecyclerview);
//        hideLinear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (hideRecyclerView.getVisibility()==View.GONE){
//                    hideRecyclerView.setVisibility(View.VISIBLE);
//                }else{hideRecyclerView.setVisibility(View.GONE);}
//            }
//        });
        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        hideRecyclerView.setLayoutManager(linearLayoutManager4);
        Post_point_item_Adapter adapter4 = new Post_point_item_Adapter();
        hideRecyclerView.setAdapter(adapter4);
        Call<List<SearchFirst>> call4 = RetrofitClient.getApiService().getSearchFirst("숨겨진 밤하늘 명소");
        call4.enqueue(new Callback<List<SearchFirst>>() {
            @Override
            public void onResponse(Call<List<SearchFirst>> call, Response<List<SearchFirst>> response) {
                if (response.isSuccessful()) {
                    List<SearchFirst> searchFirsts = response.body();
                    for (int i = 0; i < searchFirsts.size(); i++) {
                        adapter4.addItem(new post_point_item(searchFirsts.get(i).getObservationName(), searchFirsts.get(i).getObservationImage()));
                    }
                    adapter4.notifyDataSetChanged();
                    adapter4.setOnItemClicklistener(new OnPostPointItemClickListener() {
                        @Override
                        public void onItemClick(Post_point_item_Adapter.ViewHolder holder, View view, int position) {
                            Intent intent = new Intent(getActivity(), ObservationsiteActivity.class);
                            intent.putExtra("observationId", searchFirsts.get(position).getObservationId());
                            startActivity(intent);
                        }
                    });
                } else {
                    Log.d("hide", "숨겨진 명소 업로드 실패");
                }
            }

            @Override
            public void onFailure(Call<List<SearchFirst>> call, Throwable t) {
                Log.d("hide", "숨겨진 명소 인터넷 오류");
            }
        });
        LinearLayout instaLinear = v.findViewById(R.id.instalinearlayout);
        RecyclerView instaRecyclerView = v.findViewById(R.id.instagramrecylcerview);
//        instaLinear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (instaRecyclerView.getVisibility()==View.GONE){
//                    instaRecyclerView.setVisibility(View.VISIBLE);
//                }else{instaRecyclerView.setVisibility(View.GONE);}
//            }
//        });
        LinearLayoutManager linearLayoutManager5 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        instaRecyclerView.setLayoutManager(linearLayoutManager5);
        Post_point_item_Adapter adapter5 = new Post_point_item_Adapter();
        instaRecyclerView.setAdapter(adapter5);
        Call<List<SearchFirst>> call5 = RetrofitClient.getApiService().getSearchFirst("인스타 감성 밤하늘 명소");
        call5.enqueue(new Callback<List<SearchFirst>>() {
            @Override
            public void onResponse(Call<List<SearchFirst>> call, Response<List<SearchFirst>> response) {
                if (response.isSuccessful()) {
                    List<SearchFirst> searchFirsts = response.body();
                    for (int i = 0; i < searchFirsts.size(); i++) {
                        adapter5.addItem(new post_point_item(searchFirsts.get(i).getObservationName(), searchFirsts.get(i).getObservationImage()));
                    }
                    adapter5.notifyDataSetChanged();
                    adapter5.setOnItemClicklistener(new OnPostPointItemClickListener() {
                        @Override
                        public void onItemClick(Post_point_item_Adapter.ViewHolder holder, View view, int position) {
                            Intent intent = new Intent(getActivity(), ObservationsiteActivity.class);
                            intent.putExtra("observationId", searchFirsts.get(position).getObservationId());
                            startActivity(intent);
                        }
                    });
                } else {
                    Log.d("insta", "인스타 감성 명소 업로드 실패");
                }
            }

            @Override
            public void onFailure(Call<List<SearchFirst>> call, Throwable t) {
                Log.d("insta", "인스타 감성 명소 인터넷 오류");
            }
        });

        LinearLayout campLinear = v.findViewById(R.id.campinglinearlayout);
        RecyclerView campRecyclerView = v.findViewById(R.id.campingrecyclerview);
//        campLinear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (campRecyclerView.getVisibility()==View.GONE){
//                    campRecyclerView.setVisibility(View.VISIBLE);
//                }else{campRecyclerView.setVisibility(View.GONE);}
//            }
//        });
        LinearLayoutManager linearLayoutManager6 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        campRecyclerView.setLayoutManager(linearLayoutManager6);
        Post_point_item_Adapter adapter6 = new Post_point_item_Adapter();
        campRecyclerView.setAdapter(adapter6);
        Call<List<SearchFirst>> call6 = RetrofitClient.getApiService().getSearchFirst("캠핑족을 위한 밤하늘 명소");
        call6.enqueue(new Callback<List<SearchFirst>>() {
            @Override
            public void onResponse(Call<List<SearchFirst>> call, Response<List<SearchFirst>> response) {
                if (response.isSuccessful()) {
                    List<SearchFirst> searchFirsts = response.body();
                    for (int i = 0; i < searchFirsts.size(); i++) {
                        adapter6.addItem(new post_point_item(searchFirsts.get(i).getObservationName(), searchFirsts.get(i).getObservationImage()));
                    }
                    adapter6.notifyDataSetChanged();
                    adapter6.setOnItemClicklistener(new OnPostPointItemClickListener() {
                        @Override
                        public void onItemClick(Post_point_item_Adapter.ViewHolder holder, View view, int position) {
                            Intent intent = new Intent(getActivity(), ObservationsiteActivity.class);
                            intent.putExtra("observationId", searchFirsts.get(position).getObservationId());
                            startActivity(intent);
                        }
                    });
                } else {
                    Log.d("camp", "캠핑족 명소 업로드 실패");
                }
            }

            @Override
            public void onFailure(Call<List<SearchFirst>> call, Throwable t) {
                Log.d("camp", "캠핑족 명소 인터넷 오류");
            }
        });


        return v;
    }
}